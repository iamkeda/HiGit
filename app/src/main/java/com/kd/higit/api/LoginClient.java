package com.kd.higit.api;

import com.kd.higit.bean.RequestToken;
import com.kd.higit.bean.Token;
import com.kd.higit.utils.KLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KD on 2016/6/18.
 */
public class LoginClient extends RetrofitNetwork {

    private LoginService loginService;

    private LoginClient() {
        loginService = AuthUrlRetrofit.getRetrofit().create(LoginService.class);
    }

    public static LoginClient getNewInstance() {
        return new LoginClient();
    }

    public void requserTokenAsync() {
        loginService.requsetToken(getToken()).enqueue(new Callback<Token>() {
            @Override
            public void onResponse(Call<Token> call, Response<Token> response) {
                myOnResponse(response);
            }

            @Override
            public void onFailure(Call<Token> call, Throwable t) {
                myOnFailure(t);
            }
        });
    }

    private RequestToken getToken() {
        RequestToken token = new RequestToken();
        token.client_id = GitHub.CLIENT_ID;
        token.client_secret = GitHub.CLIENT_SECRET;
        token.redirect_uri = GitHub.REDIRECT_URI;
        token.code = GitHub.getInstance().getCode();
        token.state = GitHub.STATE;
        return token;
    }

    @Override
    public LoginClient setNetworkListener(NetworkListener networkListener) {
        return setNetworkListener(networkListener, this);
    }
}
