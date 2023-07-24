package cn.haizhe.cat.base;

import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewbinding.ViewBinding;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;

import cn.haizhe.cat.R;
import cn.haizhe.cat.base.iface.IActivity;
import cn.haizhe.cat.widget.dialog.LoadingFragment;

public abstract class BaseActivity<V extends ViewBinding> extends AppCompatActivity implements IActivity<V> {

    private static final String TAG = "BaseActivity";

    private static final String SP_KEY_APP_BACKGROUND = "SP_KEY_APP_BACKGROUND";

    protected V viewBinding;

    @Override
    public Resources getResources() {
        return AdaptScreenUtils.adaptWidth(super.getResources(), 3840);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        preInit();
        Log.d(TAG, "onCreate: " + this);
        super.onCreate(savedInstanceState);
        viewBinding = getViewBinding();
        initAppBackground(viewBinding);
        setContentView(viewBinding.getRoot());
        initView();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart: " + this);
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume: " + this);
        super.onResume();
        loadAppBackground();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause: " + this);
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop: " + this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: " + this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: " + this);
        super.onBackPressed();
    }

    @Override
    public void preInit() {
        Log.d(TAG, "preInit: 预加载 " + this);
    }

    @Override
    public boolean enableAppBackground() {
        return false;
    }

    protected ImageView ivAppBackground;

    @Override
    public void initAppBackground(@NonNull V viewBinding) {
        if (enableAppBackground()) {
            View root = viewBinding.getRoot();
            if (root instanceof ViewGroup) {
                ivAppBackground = new ImageView(this);
                ivAppBackground.setId(R.id.iv_app_bg);
                ivAppBackground.setScaleType(ImageView.ScaleType.FIT_XY);
                ViewGroup viewGroup = (ViewGroup) root;
                ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                viewGroup.addView(ivAppBackground, 0, layoutParams);
            }
        }
    }

    @Override
    public void loadAppBackground() {
        if (ivAppBackground != null) {
            String src = readAppBackground();
            if (src != null && !src.equals("")) {
                Object srcHashCode = src.hashCode();
                Log.d(TAG, "loadAppBackground: 背景图片资源,新的hashCode - " + srcHashCode);
                Object tag = ivAppBackground.getTag();
                Log.d(TAG, "loadAppBackground: 背景图片资源,老的hashCode - " + tag);
                if (!srcHashCode.equals(tag)) {
                    Log.d(TAG, "loadAppBackground: 背景图片资源,两者hashCode不同,重新加载图片");
                    try {
                        int resId = Integer.parseInt(src);
                        ivAppBackground.setImageResource(resId);
                    } catch (Exception e) {
                        Glide.with(BaseActivity.this)
                                .load(src)
                                .error(R.color.window_bg)
                                .into(ivAppBackground);
                    }
                    ivAppBackground.setTag(srcHashCode);
                }
            } else {
                Log.d(TAG, "loadAppBackground: 清除背景图片资源");
                ivAppBackground.setImageDrawable(null);
                ivAppBackground.setTag(null);
            }
        }
    }

    @Override
    public String readAppBackground() {
        return SPUtils.getInstance().getString(SP_KEY_APP_BACKGROUND, "");
    }

    @Override
    public void saveAppBackground(Object src) {
        if (src == null) {
            SPUtils.getInstance().remove(SP_KEY_APP_BACKGROUND);
        } else {
            SPUtils.getInstance().put(SP_KEY_APP_BACKGROUND, "" + src);
        }
        loadAppBackground();
    }

    protected LoadingFragment loadingFragment;

    @Override
    public void showLoadingView(@NonNull String msg) {
        hideLoadingView();
        loadingFragment = new LoadingFragment(msg);
        loadingFragment.showFragment(getSupportFragmentManager());
    }

    @Override
    public void hideLoadingView() {
        if (loadingFragment != null) {
            loadingFragment.hideFragment();
            loadingFragment = null;
        }
    }

}
