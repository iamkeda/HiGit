package com.kd.higit.api;

/**
 * Created by KD on 2016/6/20.
 */
public class TrendingClient extends RetrofitNetwork {
    private TrendingService trendingService;

    private TrendingClient() {
        trendingService = ApiUrlRetrofit.getRetrofit().create(TrendingService.class);
    }

    public static TrendingClient getNewInstance() {
        return new TrendingClient();
    }

    public void trendingReposList(String language, String since) {
        execute(trendingService.trendingReposList(language, since));
    }

    public void trendingShowCase() {
        execute(trendingService.trendingShowCase());
    }

    public void trendingShowCase(String slug) {
        execute(trendingService.trendingShowCase(slug));
    }

    @Override
    public TrendingClient setNetworkListener(NetworkListener networkListener) {
        return setNetworkListener(networkListener, this);
    }
}
