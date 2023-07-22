package cn.haizhe.cat.download.iface;

import java.io.File;

import cn.haizhe.cat.base.BaseDialogFragment;

public interface OnDownListener {

    void onDownSuccess(BaseDialogFragment<?> dialogFragment, File file);

    void onDownFail(BaseDialogFragment<?> dialogFragment);

    void onDialogClose(BaseDialogFragment<?> dialogFragment, boolean isCancel);

}
