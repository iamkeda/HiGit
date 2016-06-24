package com.kd.higit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.widget.TextView;

import com.kd.gitnb.R;
import com.kd.higit.api.MyOkHttpClient;
import com.kd.higit.api.RetrofitNetwork;
import com.kd.higit.bean.FileOrDirContent;
import com.kd.higit.utils.Utils;
import com.kd.higit.widget.ProgressWebView;

/**
 * Created by KD on 2016/6/24.
 */
public class ReadFileActivity extends BaseSwipeActivity {
    private static final String TAG = ReadFileActivity.class.getSimpleName();
    private FileOrDirContent content;
    private TextView file_content;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void setTitle(TextView view) {
        if (content != null) {
            view.setText(content.getName());
        }
    }

    private SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        content = intent.getParcelableExtra("filecontent");
        setContentView(R.layout.readfile_activity);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        file_content = (TextView) findViewById(R.id.file_content);
    }

    @Override
    protected void startRefresh() {
        Utils.setRefreshing(getSwipeRefreshLayout(), true);
        if (content != null) {
            requestFileContent(content.getUrl());
        }
    }

    @Override
    protected void endRefresh() {
        Utils.setRefreshing(getSwipeRefreshLayout(), false);
        updateFileContent();
    }

    @Override
    protected void endError() {
        Utils.setRefreshing(getSwipeRefreshLayout(), false);
        finish();
    }

    private void updateFileContent() {
        file_content.setText(new String(Base64.decode(content.getContent(), Base64.DEFAULT)));
    }

    private void requestFileContent(String url) {
        MyOkHttpClient.getInstance().setNetworkListener(new RetrofitNetwork.NetworkListener<FileOrDirContent>() {
            @Override
            public void onOk(FileOrDirContent ts) {
                content = ts;
                endRefresh();
            }

            @Override
            public void onError(String msg) {
                endError();
            }
        }).request(url, FileOrDirContent.class);
    }

}
