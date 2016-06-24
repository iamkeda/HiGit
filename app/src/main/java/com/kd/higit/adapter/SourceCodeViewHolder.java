package com.kd.higit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kd.gitnb.R;

/**
 * Created by KD on 2016/6/24.
 */
public class SourceCodeViewHolder extends RecyclerView.ViewHolder {
    public SimpleDraweeView file_or_dir_avatar;
    public TextView file_or_dir_name;
    public TextView file_or_dir_size;

    public SourceCodeViewHolder(View view) {
        super(view);
        file_or_dir_avatar = (SimpleDraweeView) view.findViewById(R.id.file_or_dir_avatar);
        file_or_dir_name = (TextView) view.findViewById(R.id.file_or_dir_name);
        file_or_dir_size = (TextView) view.findViewById(R.id.file_or_dir_size);
    }
}
