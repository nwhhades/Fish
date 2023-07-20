package cn.haizhe.cat.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewbinding.ViewBinding;

import cn.haizhe.cat.base.iface.IFragment;

public abstract class BaseDialogFragment<V extends ViewBinding> extends AppCompatDialogFragment implements IFragment<V> {

    public interface OnActionListener {

        void onAction(@NonNull BaseDialogFragment<?> dialogFragment, int btn_index);

        default void onCancel(@NonNull DialogInterface dialog) {
        }

        default void onDismiss(@NonNull DialogInterface dialog) {
        }

    }

    private static final String TAG = "BaseDialogFragment";

    protected boolean isShow = false;

    protected V viewBinding;

    protected OnActionListener onActionListener;

    public void setOnActionListener(OnActionListener onActionListener) {
        this.onActionListener = onActionListener;
    }

    @Override
    public void showFragment(@NonNull FragmentManager manager) {
        if (isShow) return;
        isShow = true;
        showNow(manager, TAG + hashCode());
    }

    @Override
    public void hideFragment() {
        dismissAllowingStateLoss();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //取消默认的背景
        Dialog dialog = getDialog();
        if (dialog != null) {
            Window window = dialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }
        }
        //设置View
        viewBinding = getViewBinding(inflater, container);
        View view = viewBinding.getRoot();
        initView();
        return view;
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onActionListener != null) {
            onActionListener.onDismiss(dialog);
        }
    }

    @Override
    public void onCancel(@NonNull DialogInterface dialog) {
        super.onCancel(dialog);
        if (onActionListener != null) {
            onActionListener.onCancel(dialog);
        }
    }

}
