package com.kd.higit.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import com.kd.higit.api.UsersClient;
import com.kd.higit.bean.CurrentUser;
import com.kd.higit.bean.User;
import com.kd.higit.utils.Utils;

/**
 * Created by KD on 2016/6/22.
 */
public class ShowRepositoriesFragment extends TrendingReposFragment {
    private String TAG = ShowRepositoriesFragment.class.getSimpleName();
    private User me;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        me = CurrentUser.get(getActivity());
    }

    @Override
    protected void startRefresh() {
        Utils.setRefreshing(getSwipeRefreshLayout(), true);
        userReposList();
    }

    private void userReposList(){
        UsersClient.getNewInstance().setNetworkListener(this)
                .userReposList(me.getLogin(), "updated", page);
    }

}
