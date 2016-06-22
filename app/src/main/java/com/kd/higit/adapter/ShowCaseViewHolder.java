package com.kd.higit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kd.gitnb.R;

/**
 * Created by KD on 2016/6/21.
 */
public class ShowCaseViewHolder extends RecyclerView.ViewHolder {
    public TextView showcase_name;
    public TextView showcase_discription;
    public SimpleDraweeView showcase_avatar;

    public ShowCaseViewHolder(View view) {
        super(view);
        showcase_name = (TextView) view.findViewById(R.id.showcase_name);
        showcase_avatar = (SimpleDraweeView) view.findViewById(R.id.showcase_avatar);
        showcase_discription = (TextView) view.findViewById(R.id.showcase_description);
    }
}
