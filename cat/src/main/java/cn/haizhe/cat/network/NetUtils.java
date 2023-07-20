package cn.haizhe.cat.network;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.GsonUtils;

import java.lang.reflect.Type;

import cn.haizhe.cat.cache.CacheUtils;
import cn.haizhe.cat.network.base.CacheType;
import cn.haizhe.cat.network.base.GetRequest;
import cn.haizhe.cat.network.base.PostRequest;
import cn.haizhe.cat.utils.LOG;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public enum NetUtils {
    instance;

    private static final String TAG = "Net网络请求工具";

    NetUtils() {
    }

    public <T> void get(@NonNull GetRequest request, @NonNull OnNetListener<T> onNetListener) {
        if (request.check()) {
            LOG.D(TAG, "get请求: 请求参数不完整");
            return;
        }
        final Type dataType = onNetListener.getTypeToken().getType();
        final CacheType cacheType = request.getCacheType();
        boolean returnData = true;
        T data;
        LOG.D(TAG, "get请求: 缓存类型" + cacheType);
        switch (cacheType) {
            case ONLY_CACHE:
                //缓存存在的话，就不请求了
                data = readData(request.getKey(), dataType);
                if (data != null) {
                    onNetListener.onStart(null);
                    onNetListener.onSucceeded(data, true);
                    onNetListener.onEnd();
                    return;
                }
                break;
            case FIRST_CACHE:
                //先尝试读取缓存，再请求服务器更新数据
                data = readData(request.getKey(), dataType);
                if (data != null) {
                    onNetListener.onStart(null);
                    onNetListener.onSucceeded(data, true);
                    onNetListener.onEnd();
                    //继续请求，不需要返回数据了
                    returnData = false;
                }
                break;
            default:
                break;
        }
        final OnNetListener<T> listener = returnData ? onNetListener : null;
        //备用url的请求体
        Observable<String> observable2 = ServiceUtils.instance.getGetService().get(request.getUrl2())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach();
        //主url的请求体
        ServiceUtils.instance.getGetService().get(request.getUrl1())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(observable2)
                .onTerminateDetach()
                .doOnDispose(() -> {
                    LOG.D(TAG, "get请求: 请求被主动终止");
                    if (listener != null) {
                        listener.onFailed(new Exception("请求被主动取消了"));
                        listener.onEnd();
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LOG.D(TAG, "get请求: 网络请求开始");
                        if (listener != null) {
                            listener.onStart(d);
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        Exception exception = new Exception("解析数据失败了");
                        try {
                            //添加解密的接口
                            s = onNetListener.decode(s);
                            T data = parseData(dataType, s);
                            if (data != null) {
                                //只有当数据格式是正确的才保存
                                writeData(cacheType, request.getKey(), s, request.getCacheTime());
                                if (listener != null) {
                                    listener.onSucceeded(data, false);
                                }
                                return;
                            }
                        } catch (Exception e) {
                            exception = e;
                        }
                        if (listener != null) {
                            listener.onFailed(exception);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LOG.D(TAG, "get请求: 网络请求失败", e);
                        if (listener != null) {
                            listener.onFailed(new Exception(e.getMessage()));
                            listener.onEnd();
                        }
                    }

                    @Override
                    public void onComplete() {
                        LOG.D(TAG, "get请求: 网络请求结束");
                        if (listener != null) {
                            listener.onEnd();
                        }
                    }
                });
    }


    public <T> void post(@NonNull PostRequest request, @NonNull OnNetListener<T> onNetListener) {
        if (request.check()) {
            LOG.D(TAG, "post请求: 请求参数不完整");
            return;
        }
        final Type dataType = onNetListener.getTypeToken().getType();
        final CacheType cacheType = request.getCacheType();
        boolean returnData = true;
        T data;
        LOG.D(TAG, "post请求: 缓存类型" + cacheType);
        switch (cacheType) {
            case ONLY_CACHE:
                //缓存存在的话，就不请求了
                data = readData(request.getKey(), dataType);
                if (data != null) {
                    onNetListener.onStart(null);
                    onNetListener.onSucceeded(data, true);
                    onNetListener.onEnd();
                    return;
                }
                break;
            case FIRST_CACHE:
                //先尝试读取缓存，再请求服务器更新数据
                data = readData(request.getKey(), dataType);
                if (data != null) {
                    onNetListener.onStart(null);
                    onNetListener.onSucceeded(data, true);
                    onNetListener.onEnd();
                    //继续请求，不需要返回数据了
                    returnData = false;
                }
                break;
            default:
                break;
        }
        final OnNetListener<T> listener = returnData ? onNetListener : null;
        //备用url的请求体
        Observable<String> observable2 = ServiceUtils.instance.getPostService().post(request.getUrl2(), request.getArgs())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onTerminateDetach();
        //主url的请求体
        ServiceUtils.instance.getPostService().post(request.getUrl1(), request.getArgs())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .onErrorResumeNext(observable2)
                .onTerminateDetach()
                .doOnDispose(() -> {
                    LOG.D(TAG, "post请求: 请求被主动终止");
                    if (listener != null) {
                        listener.onFailed(new Exception("请求被主动取消了"));
                        listener.onEnd();
                    }
                })
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LOG.D(TAG, "post请求: 网络请求开始");
                        if (listener != null) {
                            listener.onStart(d);
                        }
                    }

                    @Override
                    public void onNext(String s) {
                        Exception exception = new Exception("解析数据失败了");
                        try {
                            //添加解密的接口
                            s = onNetListener.decode(s);
                            T data = parseData(dataType, s);
                            if (data != null) {
                                //只有当数据格式是正确的才保存
                                writeData(cacheType, request.getKey(), s, request.getCacheTime());
                                if (listener != null) {
                                    listener.onSucceeded(data, false);
                                }
                                return;
                            }
                        } catch (Exception e) {
                            exception = e;
                        }
                        if (listener != null) {
                            listener.onFailed(exception);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        LOG.D(TAG, "post请求: 网络请求失败", e);
                        if (listener != null) {
                            listener.onFailed(new Exception(e.getMessage()));
                            listener.onEnd();
                        }
                    }

                    @Override
                    public void onComplete() {
                        LOG.D(TAG, "post请求: 网络请求结束");
                        if (listener != null) {
                            listener.onEnd();
                        }
                    }
                });
    }

    private <T> T readData(@NonNull String dataKey, @NonNull Type dataType) {
        String data = CacheUtils.instance.getCache(TAG).get(dataKey, null);
        return parseData(dataType, data);
    }

    private void writeData(@NonNull CacheType cacheType, @NonNull String key, String data, long cacheTime) {
        if (cacheType != CacheType.NO_CACHE && data != null) {
            CacheUtils.instance.getCache(TAG).put(key, data, cacheTime);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T parseData(@NonNull final Type type, final String data) {
        if (data != null) {
            try {
                if (type == String.class) {
                    return (T) data;
                } else {
                    return GsonUtils.fromJson(data, type);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
