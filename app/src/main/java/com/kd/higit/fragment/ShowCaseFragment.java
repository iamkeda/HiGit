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
import com.kd.higit.adapter.ShowCaseAdapter;
import com.kd.higit.api.RetrofitNetwork;
import com.kd.higit.api.TrendingClient;
import com.kd.higit.base.BaseFragment;
import com.kd.higit.bean.ShowCase;
import com.kd.higit.ui.MainActivity;
import com.kd.higit.ui.ReposListActivity;

import java.util.ArrayList;

import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

/**
 * Created by KD on 2016/6/21.
 */
public class ShowCaseFragment extends BaseFragment implements RetrofitNetwork.NetworkListener<ArrayList<ShowCase>>, MainActivity.UpdateLanguageListener {
    private String TAG = ShowCaseFragment.class.getSimpleName();
    private boolean isAlreadyLoadData = false;
    public static String SHOWCASE = "showcase_key";
    private LinearLayoutManager mLinearLayoutManager;
    private ShowCaseAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_data_fragment, container, false);
        initSwipeRefreshLayout(view);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new ShowCaseAdapter(getActivity());
        adapter.setOnItemClickListener(new ShowCaseAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(getActivity(), ReposListActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable(SHOWCASE, adapter.getItem(position));
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        mLinearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLinearLayoutManager);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(adapter);
        SlideInRightAnimationAdapter slideAdapter = new SlideInRightAnimationAdapter(scaleAdapter);
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
        requestShowCase();
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
    public void onOk(ArrayList<ShowCase> ts) {
        adapter.update(ts);
        endRefresh();
    }

    @Override
    public void onError(String msg) {
        endError();
    }

    private void requestShowCase() {
        TrendingClient.getNewInstance().setNetworkListener(this).trendingShowCase();
    }

    @Override
    public Void updateLanguage(String language) {
        return null;
    }

    @Override
    public Void moveToUp() {
        recyclerView.smoothScrollToPosition(0);
        return null;
    }
}
