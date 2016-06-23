package com.kd.higit.fragment;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kd.gitnb.R;
import com.kd.higit.base.BaseFragment;
import com.kd.higit.bean.Repository;


/**
 * Created by KD on 2016/6/22.
 */
public class ReposAboutFragment extends BaseFragment {
    private String TAG = ReposAboutFragment.class.getSimpleName();

    public GetRepos mCallBack;
    public interface GetRepos {
        Repository getRepos();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            mCallBack = (GetRepos) context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.repos_about_activity, container, false);
        initSwipeRefreshLayout(view);
        setAbout(view);
        return view;
    }

    private void setAbout(View view) {
        SimpleDraweeView user_avatar = (SimpleDraweeView) view.findViewById(R.id.user_avatar);
        TextView text_owner = (TextView) view.findViewById(R.id.text_owner);
        TextView text_desp = (TextView) view.findViewById(R.id.text_desp);
        TextView text_language = (TextView) view.findViewById(R.id.text_language);
        TextView text_issues = (TextView) view.findViewById(R.id.text_issues);
        TextView text_issues_num = (TextView) view.findViewById(R.id.text_issues_num);
        TextView text_star = (TextView) view.findViewById(R.id.text_star);
        TextView text_star_num = (TextView) view.findViewById(R.id.text_star_num);
        TextView text_fork = (TextView) view.findViewById(R.id.text_fork);
        TextView text_fork_num = (TextView) view.findViewById(R.id.text_fork_num);
        TextView text_codesize = (TextView) view.findViewById(R.id.text_codesize);
        TextView text_codesize_num = (TextView) view.findViewById(R.id.text_codesize_num);
        TextView text_readme = (TextView) view.findViewById(R.id.text_readme);
        TextView text_event = (TextView) view.findViewById(R.id.text_event);

        if (mCallBack.getRepos() != null) {
            Repository repos = mCallBack.getRepos();
            if (repos.getOwner() != null) {
                user_avatar.setImageURI(Uri.parse(repos.getOwner().getAvatar_url()));
                text_owner.setText(repos.getOwner().getLogin());
            }
            text_desp.setText(repos.getDescription());
            text_language.setText(repos.getLanguage());
            text_issues.setText("issues");
            text_issues_num.setText(String.valueOf(repos.getOpen_issues()));
            text_star.setText("star");
            text_star_num.setText(String.valueOf(repos.getStargazers_count()));
            text_fork.setText("fork");
            text_fork_num.setText(String.valueOf(repos.getForks_count()));
            text_codesize.setText("size");
            text_codesize_num.setText(String.valueOf(((repos.getSize()/1024*100))/100)+"M");
            text_readme.setText("README");
            text_event.setText("EVENT");
        }



    }
}
