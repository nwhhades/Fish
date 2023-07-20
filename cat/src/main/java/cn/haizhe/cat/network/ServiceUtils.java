package cn.haizhe.cat.network;

import cn.haizhe.cat.network.service.GetService;
import cn.haizhe.cat.network.service.PostService;

public enum ServiceUtils {
    instance;

    private final GetService getService;

    private final PostService postService;

    ServiceUtils() {
        this.getService = RetrofitUtils.instance.createStringService(GetService.class);
        this.postService = RetrofitUtils.instance.createStringService(PostService.class);
    }

    public GetService getGetService() {
        return getService;
    }

    public PostService getPostService() {
        return postService;
    }

}
