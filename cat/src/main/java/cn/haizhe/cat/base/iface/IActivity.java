package cn.haizhe.cat.base.iface;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

public interface IActivity<V extends ViewBinding> {

    V getViewBinding();

    void preInit();

    void initView();

    boolean enableAppBackground();

    void initAppBackground(@NonNull V viewBinding);

    void loadAppBackground();

    String readAppBackground();

    void saveAppBackground(Object src);

    void showLoadingView(@NonNull String msg);

    void hideLoadingView();

}
