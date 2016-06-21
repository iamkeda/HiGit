package com.kd.higit.ui;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.kd.gitnb.R;
import com.kd.higit.api.GitHub;
import com.kd.higit.api.LoginClient;
import com.kd.higit.api.RetrofitNetwork;
import com.kd.higit.api.UsersClient;
import com.kd.higit.bean.CurrentUser;
import com.kd.higit.bean.Token;
import com.kd.higit.bean.User;
import com.kd.higit.utils.KLog;
import com.kd.higit.utils.MessageUtils;
import com.kd.higit.widget.ProgressWebView;

/**
 * Created by KD on 2016/6/17.
 */
public class AuthorizeActivity extends BaseSwipeActivity {
    private ProgressWebView web_content;
    private SimpleDraweeView loading_gif;
    private FrameLayout loading_bg;
    private CoordinatorLayout coordinatorLayout;

    @Override
    protected void setTitle(TextView view) {
        view.setText("Authorize");
    }

    public CoordinatorLayout getCoordinatorLayout() {
        return this.coordinatorLayout;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.authorize);
        web_content = (ProgressWebView) findViewById(R.id.web_content);
        loading_gif = (SimpleDraweeView) findViewById(R.id.loading_gif);
        loading_bg = (FrameLayout) findViewById(R.id.loading_bg);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.container_snackbar);

        StringBuilder sp = new StringBuilder(GitHub.API_AUTHORIZE_URL);
        sp.append("?client_id=").append(GitHub.CLIENT_ID).append("&state=").append(GitHub.STATE)
                .append("&redirect_uri=").append(GitHub.REDIRECT_URI)
                .append("&scope=").append(GitHub.SCOPE);
        String url = sp.toString();
        web_content.getSettings().setJavaScriptEnabled(true);
        web_content.loadUrl(url);
        web_content.setUrlLoadingListener(new ProgressWebView.UrlLoadingListener() {
            @Override
            public void loading(String url) {
                if (url.contains(GitHub.REDIRECT_URI)) {
                    Uri uri = Uri.parse(url);
                    String code = uri.getQueryParameter(GitHub.CODE_KEY);
                    GitHub.getInstance().setCode(code);
                    KLog.d("页面loading");
                    loading_gif.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingInfo();
                        }
                    });
                    getToken();
                }
            }
        });
    }

    private void loadingInfo() {
        loading_bg.setVisibility(View.VISIBLE);
        Uri path = (new Uri.Builder()).scheme("res").path(String.valueOf(R.drawable.github_loading)).build();
        ////DraweeController这个类的作用是可以动态去播放你的gif或者一些动态图
        DraweeController draweeController = Fresco.newDraweeControllerBuilder()
                .setAutoPlayAnimations(true)
                .setUri(path)
                .build();
        loading_gif.setController(draweeController);
    }

    private void getToken() {
        LoginClient.getNewInstance().setNetworkListener(new RetrofitNetwork.NetworkListener<Token>() {
            @Override
            public void onOk(Token ts) {
                GitHub.getInstance().setToken(ts.access_token);
                getUserInfo();
                KLog.d("成功getToken()!");
                KLog.d(GitHub.getInstance().getToken());
            }

            @Override
            public void onError(String msg) {
                MessageUtils.showErrorMessage(AuthorizeActivity.this, msg);
                setResult(Activity.RESULT_CANCELED, null);
            }
        }).requserTokenAsync();
    }

    private void getUserInfo() {
        UsersClient.getNewInstance().setNetworkListener(new RetrofitNetwork.NetworkListener<User>() {
            @Override
            public void onOk(User user) {
                CurrentUser.save(AuthorizeActivity.this, user);
                setResult(Activity.RESULT_OK, null);
                KLog.d("获取用户信息");
                KLog.d(user.toString());
                finish();
            }

            @Override
            public void onError(String msg) {
                MessageUtils.showErrorMessage(AuthorizeActivity.this, msg);
                setResult(Activity.RESULT_CANCELED, null);
                KLog.d("获取用户信息失败");
                finish();
            }
        }).me();
    }

    @Override
    protected void startRefresh() {
    }

    @Override
    protected void endRefresh() {
    }

    @Override
    protected void endError() {
    }
}
