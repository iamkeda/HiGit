package com.kd.higit.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.TextView;

import com.kd.gitnb.R;
import com.kd.higit.adapter.TrendingReposAdapter;
import com.kd.higit.api.RetrofitNetwork;
import com.kd.higit.api.TrendingClient;
import com.kd.higit.bean.Repository;
import com.kd.higit.bean.ShowCase;
import com.kd.higit.bean.ShowCaseSearch;
import com.kd.higit.fragment.ShowCaseFragment;
import com.kd.higit.utils.MessageUtils;
import com.kd.higit.utils.Utils;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by KD on 2016/6/22.
 */
public class ReposListActivity extends BaseSwipeActivity implements RetrofitNetwork.NetworkListener<ShowCaseSearch> {
    private String TAG = ReposListActivity.class.getSimpleName();
    private TrendingReposAdapter adapter;
    private RecyclerView recyclerView;
    private ShowCase showCase;
    private SwipeRefreshLayout swipeRefreshLayout;
    public static String REPOS = "repos";

    @Override
    protected void setTitle(TextView view) {
        view.setText(showCase.name);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        showCase = intent.getParcelableExtra(ShowCaseFragment.SHOWCASE);
        setContentView(R.layout.activity_reposlist);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        initSwipeRefreshLayout();
        adapter = new TrendingReposAdapter(this);
        adapter.setOnItemClickListener(new TrendingReposAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ReposListActivity.this, ReposDetail.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(REPOS, adapter.getItem(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);
        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(scaleAdapter);
        slideAdapter.setDuration(300);
        slideAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(slideAdapter);
    }

    public void initSwipeRefreshLayout() {
        swipeRefreshLayout.setColorSchemeResources(
                android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                startRefresh();
            }
        });
    }

    private SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }
    @Override
    protected void startRefresh() {
        Utils.setRefreshing(getSwipeRefreshLayout(), true);
        showCaseReposList();
    }

    @Override
    protected void endRefresh() {
        Utils.setRefreshing(getSwipeRefreshLayout(), false);
    }

    @Override
    protected void endError() {
        Utils.setRefreshing(getSwipeRefreshLayout(), false);
    }

    @Override
    public void onOk(ShowCaseSearch ts) {
        adapter.update((ArrayList<Repository>)ts.repositories);
        endRefresh();
    }


    @Override
    public void onError(String msg) {
        endError();
        MessageUtils.showErrorMessage(this, msg);
    }

    private void showCaseReposList() {
        TrendingClient.getNewInstance().setNetworkListener(this).trendingShowCase(showCase.slug);
    }

}
