package cn.haizhe.cat.weather.base;

import cn.haizhe.cat.network.base.GetRequest;

public interface WeatherFactory {

    String getUrl1();

    String getUrl2();

    GetRequest getRequest();

    void startGetWeather(OnWeatherListener onWeatherListener);

    void stopGetWeather();

    void getWeather(WeatherBean weather);

}
