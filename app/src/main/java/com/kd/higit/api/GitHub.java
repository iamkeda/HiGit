package com.kd.higit.api;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by KD on 2016/6/16.
 */
public class GitHub implements ApiClient {
    public static String NAME = "Github";
    public static String CLIENT_ID = "";
    public static String CLIENT_SECRET = "";
    public static String REDIRECT_URI = "https://github.com/iamkeda/HiGit";
    public static String API_AUTHORIZE_URL = "https://github.com/login/oauth/authorize/";
    public static String API_OAUTH_URL = "https://github.com/";
    public static String API_URL = "https://api.github.com/";
    public static String TOKEN_KEY = "token";
    public static String CODE_KEY = "code";
    public static String STATE = "2016";
    public static String SCOPE = "user,public_repo";
    private static GitHub mGitHub;
    private Context mcontext;
    private String token;
    private String code;

    public GitHub(Context context) {
        this.mcontext = context;
        SharedPreferences sp = context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        token = sp.getString(TOKEN_KEY, "4076c79e4757b9c53b310277941cac5a40f20e4d");
        code = sp.getString(CODE_KEY, "cca6deb635f2957b1a16");
    }

    public static GitHub getInstance() {
        return mGitHub;
    }

    public static void init(Context context) {
        mGitHub = new GitHub(context);
    }

    @Override
    public String getApiOauthUrlEndpoint() {
        return API_OAUTH_URL;
    }

    @Override
    public String getApiEndpoint() {
        return API_URL;
    }

    @Override
    public String getType() {
        return "github";
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
        SharedPreferences sp = mcontext.getSharedPreferences(NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(CODE_KEY, code);
        editor.commit();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
        SharedPreferences sp = mcontext.getSharedPreferences(NAME, mcontext.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString(TOKEN_KEY, token);
        editor.commit();
    }
}
