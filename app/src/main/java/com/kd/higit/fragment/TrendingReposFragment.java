package com.kd.higit.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.OvershootInterpolator;

import com.kd.gitnb.R;
import com.kd.higit.adapter.TrendingReposAdapter;
import com.kd.higit.api.RetrofitNetwork;
import com.kd.higit.api.TrendingClient;
import com.kd.higit.base.BaseFragment;
import com.kd.higit.bean.Repository;
import com.kd.higit.ui.MainActivity;
import com.kd.higit.ui.ReposDetail;
import com.kd.higit.ui.ReposListActivity;
import com.kd.higit.utils.MessageUtils;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;

/**
 * Created by KD on 2016/6/20.
 */
public class TrendingReposFragment extends BaseFragment implements RetrofitNetwork.NetworkListener<ArrayList<Repository>>, MainActivity.UpdateLanguageListener {
    private String TAG = TrendingReposFragment.class.getSimpleName();
    private boolean isAlreadyLoadData = false;
    private LinearLayoutManager mLinearLayoutManager;
    private TrendingReposAdapter adapter;
    private RecyclerView recyclerView;
    private String language;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_data_fragment, container, false);
        initSwipeRefreshLayout(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter= new TrendingReposAdapter(getActivity());
        adapter.setOnItemClickListener(new TrendingReposAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ReposDetail.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(ReposListActivity.REPOS, adapter.getItem(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);
        SlideInBottomAnimationAdapter slideAdapter = new SlideInBottomAnimationAdapter(scaleAdapter);
        slideAdapter.setDuration(300);
        slideAdapter.setInterpolator(new OvershootInterpolator());
        recyclerView.setAdapter(slideAdapter);
        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isAlreadyLoadData) {
            isAlreadyLoadData = true;
            startRefresh();
        } else {}
    }

    @Override
    protected void startRefresh() {
        super.startRefresh();
        requestTrendingRepos();
    }

    @Override
    protected void endRefresh() {
        super.endRefresh();
    }

    @Override
    protected void endError() {
        super.endError();
    }

    @Override
    public void onOk(ArrayList<Repository> ts) {
        adapter.update(ts);
        endRefresh();
    }

    @Override
    public void onError(String msg) {
        endError();
        MessageUtils.showErrorMessage(getActivity(), msg);
    }

    private void requestTrendingRepos() {
        TrendingClient.getNewInstance().setNetworkListener(this).trendingReposList(language, "daily");
    }

    @Override
    public Void updateLanguage(String language) {
        this.language = language;
        startRefresh();
        return null;
    }

    @Override
    public Void moveToUp() {
        recyclerView.smoothScrollToPosition(0);
        return null;
    }
}
