package cn.haizhe.cat.weather.tianqi;

import com.google.gson.reflect.TypeToken;

import cn.haizhe.cat.R;
import cn.haizhe.cat.cache.CacheUtils;
import cn.haizhe.cat.network.NetUtils;
import cn.haizhe.cat.network.OnNetListener;
import cn.haizhe.cat.network.base.CacheType;
import cn.haizhe.cat.network.base.GetRequest;
import cn.haizhe.cat.utils.LOG;
import cn.haizhe.cat.weather.base.OnWeatherListener;
import cn.haizhe.cat.weather.base.WeatherBean;
import cn.haizhe.cat.weather.base.WeatherFactory;
import io.reactivex.disposables.Disposable;

public class TianqiFactory implements WeatherFactory {

    private static final String TAG = "天气工厂";
    private static final String key1 = "Url1";
    private static final String key2 = "Url2";
    private static final String defaultUrl = "https://yiketianqi.com/free/day?appid=43656176&appsecret=I42og6Lm";

    public static void setUrl(String url1, String url2) {
        CacheUtils.instance.getCache(TAG).put(key1, url1);
        CacheUtils.instance.getCache(TAG).put(key2, url2);
    }

    private final String url1;
    private final String url2;
    private Disposable disposable;
    private OnWeatherListener onWeatherListener;

    public TianqiFactory() {
        url1 = CacheUtils.instance.getCache(TAG).get(key1, defaultUrl);
        url2 = CacheUtils.instance.getCache(TAG).get(key2, null);
    }

    @Override

    public String getUrl1() {
        return url1;
    }

    @Override
    public String getUrl2() {
        return url2;
    }

    @Override
    public GetRequest getRequest() {
        GetRequest getRequest = new GetRequest();
        getRequest.setKey(TAG);
        getRequest.setUrl1(getUrl1());
        getRequest.setUrl2(getUrl2());
        getRequest.setCacheType(CacheType.ONLY_CACHE);
        getRequest.setCacheTime(3 * 6 * 1000);
        LOG.D(TAG, getRequest.toString());
        return getRequest;
    }

    @Override
    public void startGetWeather(OnWeatherListener onWeatherListener) {
        this.onWeatherListener = onWeatherListener;
        stopGetWeather();
        NetUtils.instance.get(getRequest(), new OnNetListener<TianqiBean>() {
            @Override
            public TypeToken<TianqiBean> getTypeToken() {
                return new TypeToken<TianqiBean>() {
                };
            }

            @Override
            public void onStart(Disposable d) {
                disposable = d;
            }

            @Override
            public void onSucceeded(TianqiBean data, boolean isCache) {
                getWeather(data);
            }

            @Override
            public void onFailed(Exception e) {
                getWeather(null);
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

    @Override
    public void stopGetWeather() {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }

    @Override
    public void getWeather(WeatherBean weather) {
        if (onWeatherListener != null) {
            onWeatherListener.onWeather(weather);
        }
    }

    public static int getResIdByWeaImg(String weaImg) {
        int icon = R.mipmap.ic_weather_err;
        if (weaImg != null) {
            switch (weaImg) {
                case "xue":
                    icon = R.mipmap.ic_weather_xue;
                    break;
                case "lei":
                    icon = R.mipmap.ic_weather_lei;
                    break;
                case "shachen":
                    icon = R.mipmap.ic_weather_shachen;
                    break;
                case "wu":
                    icon = R.mipmap.ic_weather_wu;
                    break;
                case "bingbao":
                    icon = R.mipmap.ic_weather_bingbao;
                    break;
                case "yun":
                    icon = R.mipmap.ic_weather_yun;
                    break;
                case "yu":
                    icon = R.mipmap.ic_weather_yu;
                    break;
                case "yin":
                    icon = R.mipmap.ic_weather_yin;
                    break;
                case "qing":
                    icon = R.mipmap.ic_weather_qing;
                    break;
                default:
                    break;
            }
        }
        return icon;
    }

}
