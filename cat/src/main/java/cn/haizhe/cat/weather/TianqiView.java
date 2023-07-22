package cn.haizhe.cat.weather;

import android.content.Context;
import android.graphics.PorterDuff;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.blankj.utilcode.util.LogUtils;

import cn.haizhe.cat.R;
import cn.haizhe.cat.databinding.LayoutTianqiViewBinding;
import cn.haizhe.cat.weather.base.OnWeatherListener;
import cn.haizhe.cat.weather.base.WeatherBean;
import cn.haizhe.cat.weather.tianqi.TianqiFactory;

public class TianqiView extends FrameLayout implements LifecycleEventObserver, OnWeatherListener {

    private static final String TAG = "TianqiView";

    public TianqiView(@NonNull Context context) {
        this(context, null);
    }

    public TianqiView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TianqiView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private LayoutTianqiViewBinding binding;
    private TianqiFactory tianqiFactory;

    private void initView() {
        binding = LayoutTianqiViewBinding.inflate(LayoutInflater.from(getContext()), this, true);
        tianqiFactory = new TianqiFactory();
    }

    @Override
    public void onWeather(WeatherBean bean) {
        LogUtils.dTag(TAG, "设置天气状态:" + bean);
        if (bean == null) {
            binding.ivWeatherWea.setImageResource(R.mipmap.ic_weather_err);
            binding.tvWeatherWea.setText("");
            binding.tvWeatherCity.setText("");
            binding.tvWeatherTem.setText("");
            binding.tvWeatherWendu.setText("");
            binding.tvWeatherErr.setText("暂无数据");
        } else {
            binding.ivWeatherWea.setImageResource(TianqiFactory.getResIdByWeaImg(bean.getWeaImg()));
            binding.tvWeatherWea.setText(bean.getWea());
            binding.tvWeatherCity.setText(bean.getCity());
            binding.tvWeatherTem.setText(bean.getTem());
            String text = bean.getTemNight() + " ~ " + bean.getTemDay();
            binding.tvWeatherWendu.setText(text);
            binding.tvWeatherErr.setText("");
        }
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        switch (event) {
            case ON_RESUME:
                tianqiFactory.startGetWeather(TianqiView.this);
                break;
            case ON_PAUSE:
                tianqiFactory.stopGetWeather();
                break;
            default:
                break;
        }
    }

    public void setColor(@ColorRes int res_color) {
        if (binding != null) {
            binding.ivWeatherWea.setColorFilter(ContextCompat.getColor(getContext(), res_color), PorterDuff.Mode.SRC_IN);
            binding.tvWeatherWea.setTextColor(ResourcesCompat.getColor(getResources(), res_color, null));
            binding.tvWeatherCity.setTextColor(ResourcesCompat.getColor(getResources(), res_color, null));
            binding.tvWeatherTem.setTextColor(ResourcesCompat.getColor(getResources(), res_color, null));
            binding.tvWeatherWendu.setTextColor(ResourcesCompat.getColor(getResources(), res_color, null));
            binding.tvWeatherErr.setTextColor(ResourcesCompat.getColor(getResources(), res_color, null));
        }
    }

}
