package com.kd.higit.api;

import com.kd.higit.bean.Repository;
import com.kd.higit.bean.ShowCase;
import com.kd.higit.bean.ShowCaseSearch;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by KD on 2016/6/20.
 */
public interface TrendingService {
    @GET("http://trending.codehub-app.com/v2/trending?")
    Call<List<Repository>> trendingReposList(@Query("language") String language, @Query("since") String since);

    @GET("http://trending.codehub-app.com/v2/showcases")
    Call<List<ShowCase>> trendingShowCase();

    @GET("http://trending.codehub-app.com/v2/showcases/{slug}")
    Call<ShowCaseSearch> trendingShowCase(@Path("slug") String slug);
}
