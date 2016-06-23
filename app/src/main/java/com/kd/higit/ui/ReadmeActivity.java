package com.kd.higit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.kd.gitnb.R;
import com.kd.higit.api.MyOkHttpClient;
import com.kd.higit.api.RetrofitNetwork;
import com.kd.higit.bean.ReadmeContent;
import com.kd.higit.fragment.ReposAboutFragment;
import com.kd.higit.utils.MessageUtils;
import com.kd.higit.utils.Utils;
import com.kd.higit.widget.ProgressWebView;

/**
 * Created by KD on 2016/6/23.
 */
public class ReadmeActivity extends BaseSwipeActivity {
    private String TAG = ReadmeActivity.class.getSimpleName();
    private ReadmeContent readmeContent;
    private String repos_url ;
    private ProgressWebView mWebView;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void setTitle(TextView view) {
        view.setText("Read Me");
    }

    private SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        repos_url = intent.getStringExtra(ReposAboutFragment.README);
        setContentView(R.layout.readme_activity);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        mWebView = (ProgressWebView) findViewById(R.id.web_view);
    }

    @Override
    protected void startRefresh() {
        Utils.setRefreshing(getSwipeRefreshLayout(), true);
        requestReadmeContent(repos_url + "/readme");
    }

    @Override
    protected void endRefresh() {
        Utils.setRefreshing(getSwipeRefreshLayout(), false);
        updateWebView();
    }

    @Override
    protected void endError() {
        Utils.setRefreshing(getSwipeRefreshLayout(), false);
        finish();
    }

    private void updateWebView() {
        mWebView.getSettings().setJavaScriptEnabled(true);
        if (!TextUtils.isEmpty(readmeContent.getHtml_url())) {
            mWebView.loadUrl(readmeContent.getHtml_url());
        }
    }
    private void requestReadmeContent(String url) {
        MyOkHttpClient.getInstance().setNetworkListener(new RetrofitNetwork.NetworkListener<ReadmeContent>() {
            @Override
            public void onOk(ReadmeContent ts) {
                readmeContent = ts;
                endRefresh();
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(ReadmeActivity.this, "NO Readme File In This Project", Toast.LENGTH_SHORT).show();
                try {
                    Thread.currentThread().sleep(1000);
                } catch (Exception e) {
                }
                endError();
            }
        }).request(url, ReadmeContent.class);
    }
}
