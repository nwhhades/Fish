package cn.haizhe.fish;

import android.text.method.ScrollingMovementMethod;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.google.gson.reflect.TypeToken;

import cn.haizhe.cat.base.BaseActivity;
import cn.haizhe.cat.network.NetUtils;
import cn.haizhe.cat.network.OnNetListener;
import cn.haizhe.cat.network.base.CacheType;
import cn.haizhe.cat.network.base.GetRequest;
import cn.haizhe.fish.databinding.ActivityNetBinding;
import io.reactivex.disposables.Disposable;

public class NetActivity extends BaseActivity<ActivityNetBinding> implements View.OnClickListener {

    @Override
    public ActivityNetBinding getViewBinding() {
        return ActivityNetBinding.inflate(getLayoutInflater());
    }

    @Override
    public void initView() {
        viewBinding.tvMsg.setMovementMethod(ScrollingMovementMethod.getInstance());
        viewBinding.btnGet.setOnClickListener(this);
        viewBinding.btnPost.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_get) {
            get();
        } else {
            post();
        }
    }

    private CacheType cacheType = CacheType.NO_CACHE;
    private Disposable disposable1;
    private Disposable disposable2;

    private void get() {
        GetRequest getRequest = new GetRequest();
        getRequest.setKey("get");
        getRequest.setUrl1("http://192.168.50.246:8080/get?id=2&name=abc&msg=这是消息");
        getRequest.setUrl1("asdfasd");
        getRequest.setUrl2("https://www.baidu.com/");
        getRequest.setCacheType(cacheType);
        getRequest.setCacheTime(5000);
        NetUtils.instance.get(getRequest, new OnNetListener<String>() {
            @Override
            public TypeToken<String> getTypeToken() {
                return new TypeToken<String>() {
                };
            }

            @Override
            public void onStart(Disposable d) {
                if (disposable1 != null && !disposable1.isDisposed()) {
                    disposable1.dispose();
                }
                disposable1 = d;
            }

            @Override
            public void onSucceeded(String data, boolean isCache) {
                LogUtils.d(data, isCache);
                setMsg(data);
            }

            @Override
            public void onFailed(Exception e) {
                LogUtils.e(e);
            }

            @Override
            public void onEnd() {

            }

            @Override
            public String decode(String data) {
                return data;
            }
        });

    }

    private void post() {

    }

    private void setMsg(String msg) {
        viewBinding.tvMsg.setText(msg);
    }

}