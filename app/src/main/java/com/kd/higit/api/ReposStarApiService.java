package com.kd.higit.api;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.PUT;
import retrofit2.http.Path;

/**
 * Created by KD on 2016/6/26.
 */
public interface ReposStarApiService {
    @GET("user/starred/{owner}/{repo}")
    Call<Object> checkStarStatus(@Path("owner") String owner, @Path("repo") String repo);

    @Headers("Content-Length: 0")
    @PUT("user/starred/{owner}/{repo}")
    Call<Object> starRepos(@Path("owner") String owner, @Path("repo") String repo);

    @DELETE("/user/starred/{owner}/{repo}")
    Call<Object> unstarRepos(@Path("owner") String owner, @Path("repo") String repo);
}
