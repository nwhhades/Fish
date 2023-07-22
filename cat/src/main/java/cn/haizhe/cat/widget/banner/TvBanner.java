package cn.haizhe.cat.widget.banner;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.zhpan.bannerview.BannerViewPager;

public class TvBanner extends BannerViewPager<String> implements View.OnFocusChangeListener, View.OnKeyListener, View.OnClickListener {

    private static final String TAG = "TvBanner";

    protected OnTvBannerListener onTvBannerListener;

    public TvBanner(Context context) {
        this(context, null);
    }

    public TvBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TvBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    /*
    #添加按键处理
     */
    protected void initView() {
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setDescendantFocusability(FOCUS_BLOCK_DESCENDANTS);
        setOnFocusChangeListener(this);
        setOnKeyListener(this);
        setOnClickListener(this);
    }

    public void setOnTvBannerListener(OnTvBannerListener onTvBannerListener) {
        this.onTvBannerListener = onTvBannerListener;
    }

    private boolean actionBanner(int action) {
        int count = getData().size();
        int start = 0;
        int end = count - 1;
        int index;
        if (action == 1) {
            //左
            index = getCurrentItem() - 1;
        } else {
            //右
            index = getCurrentItem() + 1;
        }
        if (index < start) {
            LogUtils.dTag(TAG, "焦点需要往左边");
            return false;
        } else if (index > end) {
            LogUtils.dTag(TAG, "焦点需要往右边");
            return false;
        } else {
            setCurrentItem(index);
            return true;
        }
    }

    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_DPAD_LEFT:
                    return actionBanner(1);
                case KeyEvent.KEYCODE_DPAD_RIGHT:
                    return actionBanner(2);
                case KeyEvent.KEYCODE_DPAD_UP:
                    LogUtils.dTag(TAG, "焦点需要往上边");
                    return false;
                case KeyEvent.KEYCODE_DPAD_DOWN:
                    LogUtils.dTag(TAG, "焦点需要往下边");
                    return false;
                default:
                    break;
            }
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (hasFocus) {
            LogUtils.dTag(TAG, "onFocusChange: 停止轮播");
            setAutoPlay(false);
            stopLoop();
        } else {
            LogUtils.dTag(TAG, "onFocusChange: 开始轮播");
            setAutoPlay(true);
            startLoop();
        }
    }

    @Override
    public void onClick(View v) {
        if (onTvBannerListener != null) {
            onTvBannerListener.onClickPage(getCurrentItem());
        }
    }

}
