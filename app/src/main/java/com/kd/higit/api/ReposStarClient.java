package com.kd.higit.api;

/**
 * Created by KD on 2016/6/26.
 */
public class ReposStarClient extends RetrofitNetwork {
    private static final String TAG = ReposStarClient.class.getSimpleName();
    private ReposStarApiService reposStarApiService;
    private ReposStarClient() {
        reposStarApiService = ApiUrlRetrofit.getRetrofit().create(ReposStarApiService.class);
    }

    public static ReposStarClient getInstance() {
        return new ReposStarClient();
    }

    public void checkStarStatus(String owner_name, String repos_name) {
        execute(reposStarApiService.checkStarStatus(owner_name, repos_name));
    }

    public void starRepos(String owner_name, String repos_name) {
        execute(reposStarApiService.starRepos(owner_name, repos_name));
    }

    public void unstarRepos(String owner_name, String repos_name) {
        execute(reposStarApiService.unstarRepos(owner_name, repos_name));
    }

    @Override
    public ReposStarClient setNetworkListener(NetworkListener networkListener) {
        return setNetworkListener(networkListener, this);
    }
}
