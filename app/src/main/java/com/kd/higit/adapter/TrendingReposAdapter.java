package com.kd.higit.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kd.gitnb.R;
import com.kd.higit.bean.Repository;

import java.util.ArrayList;

/**
 * Created by KD on 2016/6/20.
 */
public class TrendingReposAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private static final int TYPE_NORMAL_VIEW = 0;
    private final LayoutInflater mLayoutInflater;
    private ArrayList<Repository> mRepos;
    private OnItemClickListener mOnItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public TrendingReposAdapter(Context context) {
        this.context = context;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(final OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public Repository getItem(int position) {
        return mRepos == null ? null : mRepos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(ArrayList<Repository> data) {
        mRepos = data;
        notifyDataSetChanged();
    }

    public void insertAtBack(ArrayList<Repository> data) {
        if (data != null && data.size() > 0) {
            mRepos.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mRepos == null ? 0 : mRepos.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_NORMAL_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.repos_list_item, parent, false);
        return new ReposView(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_NORMAL_VIEW:
                ReposView viewHolder = (ReposView) holder;
                Repository item = getItem(position);
                if (item != null) {
                    viewHolder.repos_name.setText(item.getName());
                    viewHolder.repos_star.setText("Star:" + item.getStargazers_count());
                    viewHolder.repos_fork.setText("owner:" + item.getOwner().getLogin());
                    viewHolder.repos_language.setText(item.getLanguage());
                    viewHolder.repos_homepage.setText(item.getHomepage());
                    viewHolder.repos_discription.setText(item.getDescription());
                }
                viewHolder.user_avatar.setVisibility(View.VISIBLE);
                if (item.getOwner() != null) {
                    viewHolder.user_avatar.setImageURI(Uri.parse(item.getOwner().getAvatar_url()));
                }
                viewHolder.repos_rank.setText(String.valueOf(position + 1) + ".");
                break;
        }
    }

    public class ReposView extends ReposViewHolder implements View.OnClickListener {
        public ReposView(View view) {
            super(view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mOnItemClickListener != null) {
                mOnItemClickListener.onItemClick(v, getLayoutPosition());
            }
        }
    }
}
