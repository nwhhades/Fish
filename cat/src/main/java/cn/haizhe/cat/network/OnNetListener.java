package cn.haizhe.cat.network;

import com.google.gson.reflect.TypeToken;

import io.reactivex.disposables.Disposable;

public interface OnNetListener<T> {

    TypeToken<T> getTypeToken();

    void onStart(Disposable d);

    void onSucceeded(T data, boolean isCache);

    void onFailed(Exception e);

    void onEnd();

    String decode(String data);

}
