package com.kd.higit.api;

/**
 * Created by KD on 2016/6/25.
 */
public class SearchClient extends RetrofitNetwork {
    private SearchApiService searchApi;

    private SearchClient() {
        searchApi = ApiUrlRetrofit.getRetrofit().create(SearchApiService.class);
    }

    public static SearchClient getInstance() {
        return new SearchClient();
    }


    public void repos(String query, int page){
        execute(searchApi.reposPaginated(query, page));
    }

    public void repos(String query, String sort, String order, int page){
        execute(searchApi.reposPaginated(query, sort, order, page));
    }

    @Override
    public SearchClient setNetworkListener(NetworkListener networkListener) {
        return setNetworkListener(networkListener, this);
    }
}
