package com.kd.higit.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.kd.gitnb.R;
import com.kd.higit.adapter.TrendingReposAdapter;
import com.kd.higit.api.RetrofitNetwork;
import com.kd.higit.api.SearchClient;
import com.kd.higit.bean.ReposSearch;
import com.kd.higit.bean.Repository;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by KD on 2016/6/25.
 */
public class SearchReposActivity extends AppCompatActivity {
    private static final String TAG = SearchReposActivity.class.getSimpleName();
    private Toolbar mToolbar;
    private RecyclerView recyclerView;
    private TextView title;
    private TrendingReposAdapter adapter;
    private String search;
    private int page = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
        setContentView(R.layout.activity_searchrepos);
        Intent intent = this.getIntent();
        search = intent.getStringExtra("search_content");
        initView();
        requestSearchResult(search);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        title = (TextView) findViewById(R.id.title);
        title.setText(search);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        adapter = new TrendingReposAdapter(this);
        adapter.setOnItemClickListener(new TrendingReposAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(SearchReposActivity.this, ReposDetail.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(ReposListActivity.REPOS, adapter.getItem(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);
        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(scaleAdapter);
        slideAdapter.setDuration(300);
        slideAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(slideAdapter);
    }

    private void requestSearchResult(String search) {
        if (!TextUtils.isEmpty(search)) {
            SearchClient.getInstance().setNetworkListener(new RetrofitNetwork.NetworkListener<ReposSearch>() {
                @Override
                public void onOk(ReposSearch ts) {
                    if (page == 1) {
                        adapter.update(((ArrayList<Repository>)ts.getItems()));
                        recyclerView.smoothScrollToPosition(0);
                    }
                }

                @Override
                public void onError(String msg) {

                }
            }).repos(search, "stars", "desc", page);
        }
    }
}
