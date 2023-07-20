package cn.haizhe.fish;

import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.google.gson.reflect.TypeToken;

import java.util.HashMap;
import java.util.Map;

import cn.haizhe.cat.base.BaseActivity;
import cn.haizhe.cat.cache.CacheUtils;
import cn.haizhe.cat.network.NetUtils;
import cn.haizhe.cat.network.OnNetListener;
import cn.haizhe.cat.network.base.CacheType;
import cn.haizhe.cat.network.base.GetRequest;
import cn.haizhe.cat.network.base.PostRequest;
import cn.haizhe.cat.weather.base.OnWeatherListener;
import cn.haizhe.cat.weather.base.WeatherBean;
import cn.haizhe.cat.weather.tianqi.TianqiFactory;
import cn.haizhe.fish.databinding.ActivityMainBinding;
import io.reactivex.disposables.Disposable;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private static final String TAG = "MainActivity";

    private final Handler handler = new Handler();
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.d(TAG, "run: " + CacheUtils.instance.getCache().get(TAG, "默认值"));
            handler.postDelayed(runnable, 2000);
        }
    };

    protected Disposable d1;
    protected TianqiFactory tianqiFactory;

    @Override
    public void preInit() {
        super.preInit();
        //saveAppBackground("https://lmg.jj20.com/up/allimg/1113/051220112022/200512112022-1-1200.jpg");
        saveAppBackground(cn.haizhe.cat.R.drawable.bg_image_loading);
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
        TianqiFactory.setUrl("https://www.yiketianqi.com/free/day?appid=41599835&appsecret=3I9UiEZd", null);

        viewBinding.tvBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //testPost();
                //testGet();
                //getWeather();
                saveAppBackground("http://lmg.jj20.com/up/allimg/1113/051220112022/200512112022-1-1200.jpg");
            }
        });
        viewBinding.tianqiView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //saveAppBackground(null);
                showLoadingView("ssss");
            }
        });

        viewBinding.tianqiView.setColor(cn.haizhe.cat.R.color.yellow);
        getLifecycle().addObserver(viewBinding.tianqiView);

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