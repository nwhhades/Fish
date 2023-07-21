package cn.haizhe.cat.download;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hjq.toast.Toaster;

import java.io.File;

import cn.haizhe.cat.R;
import cn.haizhe.cat.base.BaseDialogFragment;
import cn.haizhe.cat.databinding.FragmentDownloadDialogBinding;
import cn.haizhe.cat.download.iface.IDownloadView;

public abstract class AbsDownloadDialog extends BaseDialogFragment<FragmentDownloadDialogBinding> implements IDownloadView, View.OnClickListener {

    protected String title;
    protected String content;
    protected String url;
    protected String path;

    public AbsDownloadDialog(String title, String content, String url, String path) {
        this.title = title;
        this.content = content;
        this.url = url;
        this.path = path;
    }

    @Override
    public FragmentDownloadDialogBinding getViewBinding(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return FragmentDownloadDialogBinding.inflate(inflater, container, false);
    }

    @Override
    public void initView() {
        viewBinding.tvTitle.setText(title);
        viewBinding.tvContent.setText(content);
        viewBinding.ctvBtn1.setOnClickListener(this);
        viewBinding.ctvBtn2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (onActionListener != null) {
            onActionListener.onAction(this, v.getId() == R.id.ctv_btn1 ? 0 : 1);
        }
        if (v.getId() == R.id.ctv_btn1) {
            //下载与暂停
            if (isDownloading()) {
                stopDownload();
            } else {
                startDownload();
            }
        } else {
            //取消
            hideFragment();
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        //停止下载
        stopDownload();
        super.onDismiss(dialog);
    }

    @Override
    public void onViewDownloadProgress(int progress) {
        if (viewBinding != null && isVisible()) {
            viewBinding.pbProgress.setProgress(progress);
            viewBinding.tvProgress.setText(getString(R.string.progress, progress));
        }
    }

    @Override
    public void onViewDownloadStart() {
        if (viewBinding != null && isVisible()) {
            viewBinding.ctvBtn1.setText(R.string.pause);
        }
    }

    @Override
    public void onViewDownloadSuccess() {
        if (viewBinding != null && isVisible()) {
            viewBinding.ctvBtn1.setText(R.string.download_success);
        }
    }

    @Override
    public void onViewDownloadFail() {
        if (viewBinding != null && isVisible()) {
            viewBinding.ctvBtn1.setText(R.string.retry);
        }
    }

    @Override
    public void onViewDownloadStop() {
        if (viewBinding != null && isVisible()) {
            viewBinding.ctvBtn1.setText(R.string.resume);
        }
    }

    @Override
    public void onDownProgress(long current, long total) {
        float progress = (100f * current) / total;
        onViewDownloadProgress((int) progress);
    }

    @Override
    public void onDownStart() {
        Toaster.showShort("下载开始");
        onViewDownloadStart();
    }

    @Override
    public void onDownSuccess(File file) {
        Toaster.showShort("下载成功");
        onViewDownloadSuccess();
    }

    @Override
    public void onDownFail() {
        Toaster.showShort("下载失败");
        onViewDownloadFail();
    }

    @Override
    public void onDownStop() {
        Toaster.showShort("下载结束");
        onViewDownloadStop();
    }

}
