package com.kd.higit.api;

import com.kd.higit.bean.ReposSearch;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by KD on 2016/6/25.
 */
public interface SearchApiService {

    @GET("search/repositories")
    Call<ReposSearch> repos(@Query(value = "q", encoded = true) String query);

    @GET("search/repositories")
    Call<ReposSearch> reposPaginated(@Query(value = "q", encoded = true) String query, @Query("page") int page);

    @GET("search/repositories")
    Call<ReposSearch> reposPaginated(@Query(value = "q", encoded = true) String query, @Query("sort") String sort, @Query("order") String order, @Query("page") int page);


//    @GET("search/users")
//    Call<UsersSearch> users(@Query(value = "q", encoded = true) String query);
//
//    @GET("search/users")
//    Call<UsersSearch> usersPaginated(@Query(value = "q", encoded = true) String query, @Query("page") int page);
//
//    @GET("search/users")
//    Call<UsersSearch> usersPaginated(@Query(value = "q", encoded = true) String query, @Query("sort") String sort, @Query("order") String order, @Query("page") int page);

}
