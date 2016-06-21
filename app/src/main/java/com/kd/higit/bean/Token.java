package com.kd.higit.bean;

/**
 * Created by KD on 2016/6/18.
 */
public class Token {
    public String access_token;
    public String scope;
    public String token_type;
    @Override
    public String toString() {
        return "access_token is " + access_token;
    }
}
