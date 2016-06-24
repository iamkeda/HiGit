package com.kd.higit.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kd.gitnb.R;
import com.kd.higit.adapter.SourceCodeAdapter;
import com.kd.higit.api.ReposClient;
import com.kd.higit.api.RetrofitNetwork;
import com.kd.higit.base.BaseFragment;
import com.kd.higit.bean.FileOrDirContent;
import com.kd.higit.bean.Repository;
import com.kd.higit.ui.ReadFileActivity;
import com.kd.higit.utils.KLog;
import com.kd.higit.widget.MyBreadcrumb;

import java.util.ArrayList;

/**
 * Created by KD on 2016/6/24.
 */
public class SourceCodeFragment extends BaseFragment implements MyBreadcrumb.SelectionCallback {
    private final static String TAG = SourceCodeFragment.class.getSimpleName();
    private SourceCodeAdapter adapter;
    private RecyclerView recyclerView;
    private MyBreadcrumb myBreadcrumb;
    private Repository repos;
    private GetReposToSCF callBack;
    private String path = "";
    private boolean isHaveData = false;

    public interface GetReposToSCF {
        Repository getReposToSCF();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            callBack = (GetReposToSCF) context;
        }
    }

    //实现切换预先加载数据
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && !isHaveData) {
            isHaveData = true;
            startRefresh();
        } else {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_sourcecode_item, container, false);
        initSwipeRefreshLayout(view);
        repos = callBack.getReposToSCF();
        if (repos != null) {
            //增加
            setBreadcrumeb(view);
            setView(view);
        }
        return view;
    }

    private void setView(View view) {
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        adapter = new SourceCodeAdapter(getActivity());
        adapter.setOnItemClickListener(new SourceCodeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                FileOrDirContent fileOrDirContent = adapter.getItem(position);
                if (fileOrDirContent.isDir()) {
                    path = "/" + fileOrDirContent.getPath();
                    //KLog.d("the position is " + position);
                    startRefresh();
                    updateBreadcrumb(fileOrDirContent.getPath(), fileOrDirContent.getSha());
                }
                if (fileOrDirContent.isFile()) {
                    //KLog.d("the position is " + position);
                    showFileContent(fileOrDirContent);
                }
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);

    }

    private void setBreadcrumeb(View view) {
        myBreadcrumb = (MyBreadcrumb) view.findViewById(R.id.mybreadrumb);
        myBreadcrumb.initRootCrumb();
        myBreadcrumb.setCallback(this);
    }

    private void updateBreadcrumb(String path, String sha) {
        myBreadcrumb.addCrumb(new MyBreadcrumb.Crumb(path, sha), true);
    }

    @Override
    public void onCrumbSelection(MyBreadcrumb.Crumb crumb, String absolutePath, int count, int index) {
        for (int i = index + 1; i < count; i ++) {
            myBreadcrumb.removeCrumbAt(myBreadcrumb.size() - 1);
        }
        myBreadcrumb.setActive(crumb);
        KLog.d("crumb.getPath() is " + crumb.getPath());
        path = "/" + crumb.getPath();
        if ("/".equals(crumb.getPath())) {
            path = "/";
        }
        startRefresh();
    }

    @Override
    protected void startRefresh() {
        super.startRefresh();
        requestFileOrDirContent();
    }

    @Override
    protected void endRefresh() {
        super.endRefresh();
    }

    @Override
    protected void endError() {
        super.endError();
    }

    private void showFileContent(FileOrDirContent content) {
        Intent intent = new Intent(getActivity(), ReadFileActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable("filecontent", content);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void requestFileOrDirContent() {
        ReposClient.getNewInstance().setNetworkListener(new RetrofitNetwork.NetworkListener<ArrayList<FileOrDirContent>>() {
            @Override
            public void onOk(ArrayList<FileOrDirContent> ts) {
                KLog.d("requestFileOrDirContent is successful !");
                adapter.update(ts);
                endRefresh();
            }

            @Override
            public void onError(String msg) {
                KLog.d("requestFileOrDirContent is failed !");
                endError();
            }
        }).contents(repos.getOwner().getLogin(), repos.getName(), path);
    }
}
