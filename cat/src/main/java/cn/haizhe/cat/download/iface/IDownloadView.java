package cn.haizhe.cat.download.iface;

import java.io.File;

public interface IDownloadView {

    void initDownTask(String url, String path);

    void startDownload();

    void stopDownload();

    boolean isDownloading();

    void onViewDownloadProgress(int progress);

    void onViewDownloadStart();

    void onViewDownloadSuccess();

    void onViewDownloadFail();

    void onViewDownloadStop();

    void onDownProgress(long current, long total);

    void onDownStart();

    void onDownSuccess(File file);

    void onDownFail();

    void onDownStop();

}
