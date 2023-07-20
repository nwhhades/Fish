package cn.haizhe.cat.weather.tianqi;

import androidx.annotation.NonNull;

import cn.haizhe.cat.weather.base.WeatherBean;

@SuppressWarnings("unused")
public class TianqiBean implements WeatherBean {

    private String city;//城市
    private String wea;//天气情况
    private String wea_img;//天气图标
    private String tem;//实时温度
    private String tem_day;//白天温度（高温）
    private String tem_night;//夜间温度（低温）
    private String win;//风向
    private String win_speed;//风力等级
    private String win_meter;//风速
    private String air;//空气质量
    private String update_time;//更新时间


    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getWea() {
        return wea;
    }

    @Override
    public String getWeaImg() {
        return wea_img;
    }

    @Override
    public String getTem() {
        return tem;
    }

    @Override
    public String getTemDay() {
        return tem_day;
    }

    @Override
    public String getTemNight() {
        return tem_night;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setWea(String wea) {
        this.wea = wea;
    }

    public String getWea_img() {
        return wea_img;
    }

    public void setWea_img(String wea_img) {
        this.wea_img = wea_img;
    }

    public void setTem(String tem) {
        this.tem = tem;
    }

    public String getTem_day() {
        return tem_day;
    }

    public void setTem_day(String tem_day) {
        this.tem_day = tem_day;
    }

    public String getTem_night() {
        return tem_night;
    }

    public void setTem_night(String tem_night) {
        this.tem_night = tem_night;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) {
        this.win = win;
    }

    public String getWin_speed() {
        return win_speed;
    }

    public void setWin_speed(String win_speed) {
        this.win_speed = win_speed;
    }

    public String getWin_meter() {
        return win_meter;
    }

    public void setWin_meter(String win_meter) {
        this.win_meter = win_meter;
    }

    public String getAir() {
        return air;
    }

    public void setAir(String air) {
        this.air = air;
    }

    public String getUpdate_time() {
        return update_time;
    }

    public void setUpdate_time(String update_time) {
        this.update_time = update_time;
    }

    @NonNull
    @Override
    public String toString() {
        return "TianqiBean{" +
                "city='" + city + '\'' +
                ", wea='" + wea + '\'' +
                ", wea_img='" + wea_img + '\'' +
                ", tem='" + tem + '\'' +
                ", tem_day='" + tem_day + '\'' +
                ", tem_night='" + tem_night + '\'' +
                ", win='" + win + '\'' +
                ", win_speed='" + win_speed + '\'' +
                ", win_meter='" + win_meter + '\'' +
                ", air='" + air + '\'' +
                ", update_time='" + update_time + '\'' +
                '}';
    }

}
