package com.kd.higit.fragment;

import com.kd.higit.api.UsersClient;
import com.kd.higit.bean.CurrentUser;
import com.kd.higit.utils.Utils;

/**
 * Created by KD on 2016/6/26.
 */
public class StarsFragment extends TrendingReposFragment {
    private static final String TAG = StarsFragment.class.getSimpleName();

    @Override
    protected void startRefresh() {
        Utils.setRefreshing(getSwipeRefreshLayout(), true);
        userStarredReposList();
    }

    private void userStarredReposList() {
        UsersClient.getNewInstance().setNetworkListener(this).userStarred(CurrentUser.get(getActivity()).getLogin(), "created", "desc");
    }
}
