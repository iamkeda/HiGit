package com.kd.higit.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by KD on 2016/6/21.
 */
public class ApiUrlRetrofit {
    static final int CONNECT_TIMEOUT_MILLIS = 30 * 1000;
    static final int READ_TIMEOUT_MILLIS = 30 * 1000;

    public static Retrofit getRetrofit(){
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(getOkHttpClient())
                .build();
        return retrofit;
    }

    public static String getBaseUrl() {
        return GitHub.API_URL;
    }
    public static OkHttpClient getOkHttpClient() {
        SSLContext context = null;
        TrustManager[] trustManagers = new TrustManager[] {new MyX509TrustManager()};
        try {
            context = SSLContext.getInstance("TLS");
            context.init(null, trustManagers, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(CONNECT_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .readTimeout(READ_TIMEOUT_MILLIS, TimeUnit.MILLISECONDS)
                .sslSocketFactory(context.getSocketFactory())
                .hostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String hostname, SSLSession session) {
                        return true;
                    }
                })
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request().newBuilder()
                                .header("Accept", "application/json")
                                .addHeader("Authorization", "token " + GitHub.getInstance().getToken())
                                .addHeader("User-Agent", "HiGit")
                                .build();
                        return chain.proceed(request);
                    }
                })
                .build();
        HttpsURLConnection.setDefaultSSLSocketFactory(context.getSocketFactory());
        return client;
    }

}
