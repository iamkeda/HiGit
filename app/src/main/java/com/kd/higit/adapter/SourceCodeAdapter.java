package com.kd.higit.adapter;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kd.gitnb.R;
import com.kd.higit.bean.FileOrDirContent;

import java.util.ArrayList;

/**
 * Created by KD on 2016/6/24.
 */
public class SourceCodeAdapter extends RecyclerView.Adapter<SourceCodeViewHolder> {
    private static final String TAG = SourceCodeAdapter.class.getSimpleName();
    private LayoutInflater layoutInflater;
    private ArrayList<FileOrDirContent> fileOrDirContents;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private static final int TYPE_NORMAL = 1;

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }
    public SourceCodeAdapter(Context context) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public FileOrDirContent getItem(int position) {
        return fileOrDirContents != null ? fileOrDirContents.get(position) : null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void update(ArrayList<FileOrDirContent> fileOrDirContents) {
        this.fileOrDirContents = fileOrDirContents;
        notifyDataSetChanged();
    }

    public void insertAtBack(ArrayList<FileOrDirContent> data) {
        if (data != null && data.size() > 0) {
            fileOrDirContents.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return fileOrDirContents != null ? fileOrDirContents.size() : 0;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_NORMAL;
    }

    @Override
    public SourceCodeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_NORMAL) {
            View view = layoutInflater.inflate(R.layout.source_code_list_item, parent, false);
            return new FileOrDirContentView(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(SourceCodeViewHolder holder, int position) {
        switch (getItemViewType(position)) {
            case TYPE_NORMAL:
                FileOrDirContentView viewHolder= (FileOrDirContentView) holder;
                FileOrDirContent  fileOrDirContent = getItem(position);
                if (fileOrDirContent != null) {
                    viewHolder.file_or_dir_name.setText(fileOrDirContent.getName());

                    if (fileOrDirContent.isDir()) {
                        viewHolder.file_or_dir_size.setText("");
                        viewHolder.file_or_dir_avatar.setImageResource(R.drawable.ic_folder_black_24dp);
                    }
                    if (fileOrDirContent.isFile()) {
                        viewHolder.file_or_dir_size.setText(intToKb(fileOrDirContent.getSize()));
                        viewHolder.file_or_dir_avatar.setImageResource(R.drawable.ic_sort_black_24dp);
                    }
                }
                break;

        }
    }

    private String intToKb(int a) {
        if (a < 1024) {
            return a + "b";
        }
        if (a > 1024) {
            int b = a / 1024;
            int c = a % 1024;
            if (c == 0) {
                return b + "Kb";
            } else {
                return b + "." + c +"Kb";
            }
        }
        return "";
    }

    public class FileOrDirContentView extends SourceCodeViewHolder {
        public FileOrDirContentView(View v) {
            super(v);
            file_or_dir_name.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v, getLayoutPosition());
                    }
                }
            });
        }
    }
}
