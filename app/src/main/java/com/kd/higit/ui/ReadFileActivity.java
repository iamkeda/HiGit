package com.kd.higit.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;

import com.kd.gitnb.R;
import com.kd.higit.api.MyOkHttpClient;
import com.kd.higit.api.RetrofitNetwork;
import com.kd.higit.bean.FileOrDirContent;
import com.kd.higit.utils.Utils;

import thereisnospon.codeview.CodeView;
import thereisnospon.codeview.CodeViewTheme;
import us.feras.mdv.MarkdownView;

/**
 * Created by KD on 2016/6/24.
 */
public class ReadFileActivity extends BaseSwipeActivity {
    private static final String TAG = ReadFileActivity.class.getSimpleName();
    private FileOrDirContent content;
    private CodeView file_content;
    private MarkdownView markdownView;
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
        file_content = (CodeView) findViewById(R.id.codeview);
        file_content.setTheme(CodeViewTheme.DARKULA).fillColor();
        markdownView = (MarkdownView) findViewById(R.id.markdownview);
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
        if (content.getName().endsWith(".md")) {
            file_content.setVisibility(View.GONE);
            markdownView.setVisibility(View.VISIBLE);
            markdownView.loadUrl(content.getHtml_url());
        } else {
            file_content.setVisibility(View.VISIBLE);
            markdownView.setVisibility(View.GONE);
            file_content.showCode(new String(Base64.decode(content.getContent(), Base64.DEFAULT)));
        }

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
