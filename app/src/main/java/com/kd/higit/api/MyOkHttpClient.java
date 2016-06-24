package com.kd.higit.api;

import android.os.Handler;
import android.os.Looper;

import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by KD on 2016/6/23.
 */
public class MyOkHttpClient {
    private String TAG = MyOkHttpClient.class.getSimpleName();
    private RetrofitNetwork.NetworkListener networkListener;
    private Gson gson = new Gson();
    private Handler handler = new Handler(Looper.getMainLooper());
    private MyOkHttpClient() {
    }

    public static MyOkHttpClient getInstance() {
        return new MyOkHttpClient();
    }

    public <T> void request(String url, final Class<T> className) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        ApiUrlRetrofit.getOkHttpClient().newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        if (networkListener != null) {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    networkListener.onError("request error");
                                }
                            });
                        }
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.isSuccessful()) {
                            if (networkListener != null) {
                                String resp = response.body().string();
                                final Object o = gson.fromJson(resp, className);
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        networkListener.onOk(o);
                                    }
                                });
                            }
                        }  else {
                            if (networkListener != null) {
                                final String mes = response.message();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        networkListener.onError(mes);
                                    }
                                });
                            }
                        }
                    }
                });
    }
    public MyOkHttpClient setNetworkListener(RetrofitNetwork.NetworkListener networkListener) {
        this.networkListener = networkListener;
        return this;
    }
}
