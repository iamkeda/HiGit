package com.kd.higit.api;

import com.kd.higit.bean.RequestToken;
import com.kd.higit.bean.Token;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by KD on 2016/6/18.
 */
public interface LoginService {
    @POST("login/oauth/access_token")
    Call<Token> requsetToken(@Body RequestToken requestToken);
}
