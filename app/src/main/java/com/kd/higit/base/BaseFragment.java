package com.kd.higit.base;

import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.kd.gitnb.R;
import com.kd.higit.utils.Utils;

/**
 * Created by KD on 2016/6/18.
 */
public class BaseFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    protected int page = 1;

    public void initSwipeRefreshLayout(View view) {
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(
            android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light
        );
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                page = 1;
                startRefresh();
            }
        });
    }

    public SwipeRefreshLayout getSwipeRefreshLayout() {
        return swipeRefreshLayout;
    }

    protected void startRefresh() {
        Utils.setRefreshing(getSwipeRefreshLayout(), true);
    }

    protected void endRefresh() {
        Utils.setRefreshing(getSwipeRefreshLayout(), false);
    }

    protected void endError() {
        Utils.setRefreshing(getSwipeRefreshLayout(), false);
    }
}
