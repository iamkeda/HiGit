package com.kd.higit.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kd.gitnb.R;
import com.kd.higit.bean.ShowCase;

import java.util.ArrayList;

/**
 * Created by KD on 2016/6/21.
 */
public class ShowCaseAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private static final int TYPE_NORMAL_VIEW = 0;
    protected final LayoutInflater mLayoutInflater;
    private ArrayList<ShowCase> showCases;
    private OnItemClickListener mOnItemClickListener;
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public ShowCaseAdapter(Context context) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(final OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public ShowCase getItem(int position) {
        return showCases == null ? null : showCases.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(ArrayList<ShowCase> data) {
        showCases = data;
        notifyDataSetChanged();
    }

    public void insertAtBack(ArrayList<ShowCase> data) {
        if (data != null && data.size() > 0) {
            showCases.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return showCases == null ? 0 : showCases.size();
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_NORMAL_VIEW;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mLayoutInflater.inflate(R.layout.showcase_list_item, parent, false);
        return new ShowCaseView(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_NORMAL_VIEW:
                ShowCaseView viewHolder = (ShowCaseView) holder;
                ShowCase item = getItem(position);
                if (item != null) {
                    viewHolder.showcase_name.setText(item.name);
                    viewHolder.showcase_discription.setText(item.description);
                    viewHolder.showcase_avatar.setImageURI(Uri.parse(item.image_url));
                }
                break;
        }
    }

    private class ShowCaseView extends ShowCaseViewHolder implements View.OnClickListener {
        public ShowCaseView(View view) {
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
