package cn.haizhe.cat.widget.dialog;

import android.app.Dialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hjq.toast.Toaster;

import cn.haizhe.cat.base.BaseDialogFragment;
import cn.haizhe.cat.databinding.FragmentLoadingDialogBinding;

public class LoadingFragment extends BaseDialogFragment<FragmentLoadingDialogBinding> {

    private final String msg;

    public LoadingFragment(String msg) {
        this.msg = msg;
    }

    @Override
    public FragmentLoadingDialogBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentLoadingDialogBinding.inflate(inflater, container, false);
    }

    @Override
    public void initView() {
        setCancelable(false);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setOnKeyListener((dialog1, keyCode, event) -> {
                if (KeyEvent.ACTION_DOWN == event.getAction()) {
                    if (KeyEvent.KEYCODE_BACK == keyCode) {
                        showMsg();
                        return true;
                    }
                }
                return false;
            });
        }
        viewBinding.getRoot().setOnClickListener(v -> showMsg());
    }

    @Override
    public void onClose(boolean isCancel) {

    }

    public void showMsg() {
        if (msg != null) {
            Toaster.showShort(msg);
        }
    }

}
