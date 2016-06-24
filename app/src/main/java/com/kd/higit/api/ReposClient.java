package com.kd.higit.api;

/**
 * Created by KD on 2016/6/24.
 */
public class ReposClient extends RetrofitNetwork {
    private ReposApiService repoService;

    private ReposClient(){
        repoService = ApiUrlRetrofit.getRetrofit().create(ReposApiService.class);
    }

    public static ReposClient getNewInstance() {
        return new ReposClient();
    }

    public void get(String path){
        execute(repoService.get(path));
    }

    public void get(String owner, String repo){
        execute(repoService.get(owner, repo));
    }

    public void contents(String owner, String repo){
        execute(repoService.contents(owner, repo));
    }

    public void contentsByRef(String owner, String repo, String path, String pref){
        if(path == null || path.isEmpty()){
            execute(repoService.contentsByRef(owner, repo, pref));
        }
        else{
            execute(repoService.contentsByRef(owner, repo, path, pref));
        }
    }

    public void readme(String owner, String repo){
        execute(repoService.readme(owner, repo));
    }

    public void contents(String owner, String repo, String path){
        if(path == null || path.isEmpty()){
            execute(repoService.contents(owner, repo));
        }
        else{
            if(path.equals("/")) path = "";
            execute(repoService.contents(owner, repo, path));
        }
    }

    public void contributors(String owner, String repo, int page){
        execute(repoService.contributors(owner, repo, page));
    }

    public void contributors(String owner, String repo){
        execute(repoService.contributors(owner, repo));
    }

    public void stargazers(String owner, String repo){
        execute(repoService.stargazers(owner, repo));
    }

    public void stargazers(String owner, String repo, int page){
        execute(repoService.stargazers(owner, repo, page));
    }

    public void delete(String owner, String repo){
        execute(repoService.delete(owner, repo));
    }

    @Override
    public ReposClient setNetworkListener(NetworkListener networkListener) {
        return setNetworkListener(networkListener, this);
    }

}
