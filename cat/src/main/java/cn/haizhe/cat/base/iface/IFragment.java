package cn.haizhe.cat.base.iface;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.ViewBinding;

public interface IFragment<V extends ViewBinding> {

    /**
     * 返回ViewBinding
     *
     * @param inflater  LayoutInflater
     * @param container ViewGroup
     * @return V
     */
    V getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 初始化View
     */
    void initView();

    /**
     * 显示Fragment
     *
     * @param manager FragmentManager
     */
    void showFragment(@NonNull FragmentManager manager);

    /**
     * 隐藏Fragment
     */
    void hideFragment();

    /**
     * 相应关闭
     *
     * @param isCancel 是否主动取消
     */
    void onClose(boolean isCancel);

}