package cn.haizhe.cat.base.iface;

import androidx.annotation.NonNull;
import androidx.viewbinding.ViewBinding;

public interface IActivity<V extends ViewBinding> {

    /**
     * 返回 ViewBinding
     *
     * @return V
     */
    V getViewBinding();

    /**
     * 预初始化
     */
    void preInit();

    /**
     * 初始化View
     */
    void initView();

    /**
     * 是否启用App背景
     *
     * @return bool
     */
    boolean enableAppBackground();

    /**
     * 初始化App背景
     *
     * @param viewBinding Activity的V
     */
    void initAppBackground(@NonNull V viewBinding);

    /**
     * 加载App背景
     */
    void loadAppBackground();

    /**
     * 读取App背景
     *
     * @return App背景(资源ID, 图片URL)
     */
    String readAppBackground();

    /**
     * 保存App背景
     *
     * @param src App背景(资源ID, 图片URL)
     */
    void saveAppBackground(Object src);

    /**
     * 显示加载View
     *
     * @param msg 提示消息
     */
    void showLoadingView(@NonNull String msg);

    /**
     * 隐藏加载View
     */
    void hideLoadingView();

}
