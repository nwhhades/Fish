package cn.haizhe.fish;

import android.text.method.ScrollingMovementMethod;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import cn.haizhe.cat.base.BaseActivity;
import cn.haizhe.cat.network.NetUtils;
import cn.haizhe.cat.network.OnNetListener;
import cn.haizhe.cat.network.base.CacheType;
import cn.haizhe.cat.network.base.GetRequest;
import cn.haizhe.cat.network.base.PostRequest;
import cn.haizhe.fish.databinding.ActivityNetBinding;
import io.reactivex.disposables.Disposable;

public class NetActivity extends BaseActivity<ActivityNetBinding> implements View.OnClickListener {

    @Override
    public ActivityNetBinding getViewBinding() {
        return ActivityNetBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        viewBinding.tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
        viewBinding.btnGet.setOnClickListener(this);
        viewBinding.btnPost.setOnClickListener(this);

        viewBinding.r1.setChecked(true);
        viewBinding.rgCacheType.setOnCheckedChangeListener((group, checkedId) -> {
            LogUtils.d("选中了：" + checkedId);
            if (checkedId == R.id.r1) {
                cacheType = CacheType.NO_CACHE;
            } else if (checkedId == R.id.r2) {
                cacheType = CacheType.ONLY_CACHE;
            } else {
                cacheType = CacheType.FIRST_CACHE;
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_get) {
            get();
        } else {
            post();
        }
    }

    private CacheType cacheType = CacheType.NO_CACHE;
    private Disposable disposable1;
    private Disposable disposable2;

    private void get() {
        GetRequest getRequest = new GetRequest();
        getRequest.setKey("get");
        getRequest.setUrl1("http://192.168.50.246:8080/get?id=2&name=张三&msg=这是消息");
        getRequest.setUrl2("https://www.baidu.com/");
        getRequest.setCacheType(cacheType);
        getRequest.setCacheTime(5000);
        NetUtils.instance.get(getRequest, new OnNetListener<String>() {
            @Override
            public TypeToken<String> getTypeToken() {
                return new TypeToken<String>() {
                };
            }

            @Override
            public void onStart(Disposable d) {
                if (disposable1 != null && !disposable1.isDisposed()) {
                    disposable1.dispose();
                }
                disposable1 = d;
                showLoadingView("请求中，耐心等待");
            }

            @Override
            public void onSucceeded(String data, boolean isCache) {
                LogUtils.d(data, isCache);
                setMsg(getRequest, data, isCache);
                hideLoadingView();
            }

            @Override
            public void onFailed(Exception e) {
                LogUtils.e(e);
                setMsg(getRequest, e.getMessage(), false);
            }

            @Override
            public void onEnd() {

            }

            @Override
            public String decode(String data) {
                return data;
            }
        });

    }

    private void post() {
        Map<String, Object> map = new HashMap<>();
        map.put("id", 20);
        map.put("name", "李四");
        map.put("msg", "这个是消息");
        PostRequest postRequest = new PostRequest();
        postRequest.setKey("post");
        postRequest.setUrl1("http://192.168.50.246:8080/post");
        postRequest.setUrl2("https://www.baidu.com/");
        postRequest.setCacheType(cacheType);
        postRequest.setCacheTime(5000);
        postRequest.setArgs(map);

        NetUtils.instance.post(postRequest, new OnNetListener<String>() {
            @Override
            public TypeToken<String> getTypeToken() {
                return new TypeToken<String>() {
                };
            }

            @Override
            public void onStart(Disposable d) {
                if (disposable2 != null && !disposable2.isDisposed()) {
                    disposable2.dispose();
                }
                disposable2 = d;
                showLoadingView("请求中，耐心等待");
            }

            @Override
            public void onSucceeded(String data, boolean isCache) {
                LogUtils.d(data, isCache);
                setMsg(postRequest, data, isCache);
            }

            @Override
            public void onFailed(Exception e) {
                LogUtils.e(e);
                setMsg(postRequest, e.getMessage(), false);
            }

            @Override
            public void onEnd() {

            }

            @Override
            public String decode(String data) {
                return data;
            }
        });
    }

    private void setMsg(Object o, String msg, boolean isCache) {
        hideLoadingView();
        String str = o.toString() + "\n是否使用缓存：" + isCache + "\n" + msg;
        viewBinding.tvMsg.setText(str);
    }

}