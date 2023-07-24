package cn.haizhe.fish;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PathUtils;
import com.google.gson.reflect.TypeToken;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.haizhe.cat.base.BaseActivity;
import cn.haizhe.cat.base.BaseDialogFragment;
import cn.haizhe.cat.download.DownloadDialog;
import cn.haizhe.cat.download.iface.OnDownListener;
import cn.haizhe.cat.network.NetUtils;
import cn.haizhe.cat.network.OnNetListener;
import cn.haizhe.cat.network.base.CacheType;
import cn.haizhe.cat.network.base.GetRequest;
import cn.haizhe.cat.network.base.PostRequest;
import cn.haizhe.cat.weather.base.OnWeatherListener;
import cn.haizhe.cat.weather.base.WeatherBean;
import cn.haizhe.cat.weather.tianqi.TianqiFactory;
import cn.haizhe.cat.widget.banner.OnTvBannerListener;
import cn.haizhe.cat.widget.banner.TvBannerAdapter;
import cn.haizhe.fish.databinding.ActivityMainBinding;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private static final String TAG = "MainActivity";

    protected Disposable d1;
    protected TianqiFactory tianqiFactory;

    @Override
    public void preInit() {
        super.preInit();
        //saveAppBackground("https://lmg.jj20.com/up/allimg/1113/051220112022/200512112022-1-1200.jpg");
        saveAppBackground(cn.haizhe.cat.R.drawable.bg_image_loading);

        XXPermissions.with(this).permission(Permission.MANAGE_EXTERNAL_STORAGE).request(new OnPermissionCallback() {
            @Override
            public void onGranted(@NonNull List<String> permissions, boolean allGranted) {

            }
        });

    }

    @Override
    public boolean enableAppBackground() {
        return true;
    }

    @Override
    public ActivityMainBinding getViewBinding() {
        return ActivityMainBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        viewBinding.btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startActivity(NetActivity.class);
            }
        });


        initBanner();

        TianqiFactory.setUrl("https://www.yiketianqi.com/free/day?appid=41599835&appsecret=3I9UiEZd", null);

        viewBinding.tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //testPost();
                //testGet();
                //getWeather();
                saveAppBackground("http://lmg.jj20.com/up/allimg/1113/051220112022/200512112022-1-1200.jpg");
                String apk = "https://cdn.tvmars.com/huoxing/mars_2.0.6/2.0.6new/mars_common_official_2.0.6_169_release-signed.apk";
                DownloadDialog downloadDialog = new DownloadDialog("下载APK", "爱奇艺", apk, PathUtils.getInternalAppCachePath() + "/");
                downloadDialog.setOnDownListener(new OnDownListener() {
                    @Override
                    public void onDownSuccess(BaseDialogFragment<?> dialogFragment, File file) {
                        Log.d(TAG, "onDownSuccess: 下载成功了 " + file.getAbsolutePath());
                    }

                    @Override
                    public void onDownFail(BaseDialogFragment<?> dialogFragment) {
                        Log.d(TAG, "onDownFail: 下载失败了");
                    }

                    @Override
                    public void onDialogClose(BaseDialogFragment<?> dialogFragment, boolean isCancel) {
                        Log.d(TAG, "onDialogClose: 对话框关闭了");

                    }
                });
                downloadDialog.showFragment(getSupportFragmentManager());

            }
        });
        viewBinding.tianqiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveAppBackground(null);
                showLoadingView("ssss");

                new Handler(Looper.myLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        hideLoadingView();
                    }
                }, 3000);
            }
        });

        viewBinding.tianqiView.setColor(cn.haizhe.cat.R.color.yellow);
        getLifecycle().addObserver(viewBinding.tianqiView);

    }

    private void initBanner() {
        List<String> stringList = new ArrayList<>();
        stringList.add("https://t7.baidu.com/it/u=4198287529,2774471735&fm=193&f=GIF");
        stringList.add("https://t7.baidu.com/it/u=1956604245,3662848045&fm=193&f=GIF");
        stringList.add("https://t7.baidu.com/");
        stringList.add("https://img2.baidu.com/it/u=1577373388,3492284830&fm=253&fmt=auto&app=120&f=JPEG?w=1280&h=800");
        stringList.add("https://img2.baidu.com/it/u=63249423,2260265143&fm=253&fmt=auto&app=120&f=JPEG?w=889&h=500");
        stringList.add("https://img1.baidu.com/it/u=1839135015,723795615&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500");
        stringList.add("https://img0.baidu.com/it/u=922902802,2128943538&fm=253&fmt=auto&app=120&f=JPEG?w=1422&h=800");

        TvBannerAdapter tvBannerAdapter = new TvBannerAdapter();


        viewBinding.tbBanner.setOnTvBannerListener(new OnTvBannerListener() {
            @Override
            public void onChangePage(int page) {

            }

            @Override
            public void onClickPage(int page) {
                Log.d(TAG, "onClickPage: " + page);
            }
        });
        viewBinding.tbBanner.registerLifecycleObserver(getLifecycle());
        viewBinding.tbBanner.setAdapter(tvBannerAdapter);
        viewBinding.tbBanner.create();
        viewBinding.tbBanner.refreshData(stringList);

//        PicBannerAdapter picBannerAdapter = new PicBannerAdapter();
//        viewBinding.tbBanner.registerLifecycleObserver(getLifecycle());
//        viewBinding.tbBanner.setAdapter(picBannerAdapter);
//        viewBinding.tbBanner.setOnPageClickListener(new BannerViewPager.OnPageClickListener() {
//            @Override
//            public void onPageClick(View clickedView, int position) {
//                Log.d(TAG, "onPageClick: 点击了" + position);
//            }
//        });
//        viewBinding.tbBanner.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Log.d(TAG, "onClick: 我点击了主容器");
//                viewBinding.tbBanner.getCurrentItem();
//            }
//        });
//        viewBinding.tbBanner.create();
//
//        viewBinding.tbBanner.refreshData(stringList);
    }

    public static class A {
        private String a;
        private String b;
        private String c;

        public String getA() {
            return a;
        }

        public void setA(String a) {
            this.a = a;
        }

        public String getB() {
            return b;
        }

        public void setB(String b) {
            this.b = b;
        }

        public String getC() {
            return c;
        }

        public void setC(String c) {
            this.c = c;
        }
    }

    public void testPost() {
        if (d1 != null && !d1.isDisposed()) {
            d1.dispose();
        }

        A a = new A();
        a.setA("1");
        a.setB("2");
        a.setC("3");

        Map<String, Object> args = new HashMap<>();
        args.put("id", 23);
        args.put("name", "naizhe");
        args.put("msg", "这个是信息");
        args.put("A", a);

        PostRequest postRequest = new PostRequest();
        postRequest.setKey("sss");
        postRequest.setUrl1("http://192.168.50.246:8080/post");
        postRequest.setUrl2(null);
        postRequest.setArgs(args);
        postRequest.setCacheType(CacheType.ONLY_CACHE);
        postRequest.setCacheTime(3000);

        NetUtils.instance.post(postRequest, new OnNetListener<String>() {
            @Override
            public TypeToken<String> getTypeToken() {
                return new TypeToken<String>() {
                };
            }

            @Override
            public void onStart(Disposable d) {
                d1 = d;
                Log.d(TAG, "onStart: 请求开始");
            }

            @Override
            public void onSucceeded(String data, boolean isCache) {
                Log.d(TAG, "onSucceeded: " + isCache);
                Log.d(TAG, "onSucceeded: " + data);
            }

            @Override
            public void onFailed(Exception e) {
                e.printStackTrace();
            }

            @Override
            public void onEnd() {
                Log.d(TAG, "onEnd: 请求结束");
            }

            @Override
            public String decode(String data) {
                return data;
            }
        });

    }

    public void testGet() {
        if (d1 != null && !d1.isDisposed()) {
            d1.dispose();
        }

        //CacheUtils.instance.getCache("abc").put(TAG, "ss" + System.currentTimeMillis());

        GetRequest getRequest = new GetRequest();
        getRequest.setKey("sss");
        getRequest.setUrl1("http://www.baidu.com/");
        getRequest.setUrl2(null);
        getRequest.setCacheType(CacheType.ONLY_CACHE);
        getRequest.setCacheTime(3000);

        NetUtils.instance.get(getRequest, new OnNetListener<String>() {
            @Override
            public TypeToken<String> getTypeToken() {
                return new TypeToken<String>() {
                };
            }

            @Override
            public void onStart(Disposable d) {
                Log.d(TAG, "onStart: 请求开始");
                d1 = d;
            }

            @Override
            public void onSucceeded(String data, boolean isCache) {
                Log.d(TAG, "onSucceeded: " + isCache);
                Log.d(TAG, "onSucceeded: " + data);
            }

            @Override
            public void onFailed(Exception e) {
                Log.d(TAG, "onFailed: ", e);
            }

            @Override
            public void onEnd() {
                Log.d(TAG, "onEnd: 请求结束");
            }

            @Override
            public String decode(String data) {
                Log.d(TAG, "decode: " + data);
                return data;
            }
        });
    }

    public void getWeather() {
        if (tianqiFactory == null) {
            tianqiFactory = new TianqiFactory();
        }
        tianqiFactory.startGetWeather(new OnWeatherListener() {
            @Override
            public void onWeather(WeatherBean bean) {
                Log.d(TAG, "onWeather: " + bean);
            }
        });
    }

}