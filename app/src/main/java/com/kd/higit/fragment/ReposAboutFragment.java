package com.kd.higit.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.kd.gitnb.R;
import com.kd.higit.api.ReposStarClient;
import com.kd.higit.api.RetrofitNetwork;
import com.kd.higit.base.BaseFragment;
import com.kd.higit.bean.Repository;
import com.kd.higit.ui.ReadmeActivity;
import com.kd.higit.utils.KLog;


/**
 * Created by KD on 2016/6/22.
 */
public class ReposAboutFragment extends BaseFragment {
    private String TAG = ReposAboutFragment.class.getSimpleName();
    public static String README = "readme";
    private SimpleDraweeView user_avatar;
    private TextView text_owner;
    private TextView text_desp;
    private TextView text_language;
    private TextView text_issues;
    private TextView text_issues_num;
    private TextView text_star;
    private TextView text_star_num;
    private TextView text_fork;
    private TextView text_fork_num;
    private TextView text_codesize;
    private TextView text_codesize_num;
    private TextView text_readme;
    private TextView text_event;
    private FloatingActionButton fab;
    private boolean starStatus = false;
    public GetRepos mCallBack;
    private View view;
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
        view = inflater.inflate(R.layout.repos_about_activity, container, false);
        initSwipeRefreshLayout(view);
        setAbout(view);
        initFab(view);
        return view;
    }

    private void setAbout(View view) {
        user_avatar = (SimpleDraweeView) view.findViewById(R.id.user_avatar);
        text_owner = (TextView) view.findViewById(R.id.text_owner);
        text_desp = (TextView) view.findViewById(R.id.text_desp);
        text_language = (TextView) view.findViewById(R.id.text_language);
        text_issues = (TextView) view.findViewById(R.id.text_issues);
        text_issues_num = (TextView) view.findViewById(R.id.text_issues_num);
        text_star = (TextView) view.findViewById(R.id.text_star);
        text_star_num = (TextView) view.findViewById(R.id.text_star_num);
        text_fork = (TextView) view.findViewById(R.id.text_fork);
        text_fork_num = (TextView) view.findViewById(R.id.text_fork_num);
        text_codesize = (TextView) view.findViewById(R.id.text_codesize);
        text_codesize_num = (TextView) view.findViewById(R.id.text_codesize_num);
        text_readme = (TextView) view.findViewById(R.id.text_readme);
        text_event = (TextView) view.findViewById(R.id.text_event);

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

        text_readme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ReadmeActivity.class);
                intent.putExtra(README, mCallBack.getRepos().getUrl());
                startActivity(intent);
            }
        });
    }

    private void initFab(View view) {
        fab = (FloatingActionButton) view.findViewById(R.id.fab);
        checkStarStatus();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!starStatus) {
                    starRepos();
                } else {
                    unStarRepos();
                }
            }
        });
    }

    private void starRepos() {
        if (mCallBack.getRepos() != null) {
            Repository repos = mCallBack.getRepos();
            final Snackbar snackbar = Snackbar.make(getSwipeRefreshLayout(), "is staring now...", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            ReposStarClient.getInstance().setNetworkListener(new RetrofitNetwork.NetworkListener<Object>() {
                @Override
                public void onOk(Object ts) {
                    starStatus = true;
                    snackbar.dismiss();
                    showStarStatusInFab();
                }

                @Override
                public void onError(String msg) {
                    starStatus = false;
                    snackbar.dismiss();
                    showStarStatusInFab();
                }
            }).starRepos(repos.getOwner().getLogin(), repos.getName());
        }
    }

    private void unStarRepos() {
        if (mCallBack.getRepos() != null) {
            Repository repos = mCallBack.getRepos();
            final Snackbar snackbar = Snackbar.make(getSwipeRefreshLayout(), "is unstaring now...", Snackbar.LENGTH_INDEFINITE);
            snackbar.show();
            ReposStarClient.getInstance().setNetworkListener(new RetrofitNetwork.NetworkListener<Object>() {
                @Override
                public void onOk(Object ts) {
                    starStatus = false;
                    snackbar.dismiss();
                    showStarStatusInFab();
                }

                @Override
                public void onError(String msg) {
                    starStatus = true;
                    snackbar.dismiss();
                    showStarStatusInFab();
                }
            }).starRepos(repos.getOwner().getLogin(), repos.getName());
        }
    }

    private void checkStarStatus() {
        if (mCallBack.getRepos() != null) {
            Repository repos = mCallBack.getRepos();
            ReposStarClient.getInstance().setNetworkListener(new RetrofitNetwork.NetworkListener<Object>() {
                @Override
                public void onOk(Object ts) {
                    starStatus = true;
                    showStarStatusInFab();
                }

                @Override
                public void onError(String msg) {
                    starStatus = false;
                    showStarStatusInFab();
                }
            }).checkStarStatus(repos.getOwner().getLogin(), repos.getName());
        }
    }

    private void showStarStatusInFab() {
        AnimatorSet bouncer = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(fab, "alpha", 0.0f, 1.0f);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(fab, "scaleX", 0.0f, 1.0f);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(fab, "scaleY", 0.0f, 1.0f);
        bouncer.play(alpha).with(scaleX).with(scaleY);
        bouncer.setDuration(500);
        bouncer.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                fab.setVisibility(View.VISIBLE);
                if(starStatus) {
                    ColorStateList colorRed = ColorStateList.valueOf(getResources().getColor(R.color.colorAccent));
                    fab.setBackgroundTintList(colorRed);
                    fab.setImageResource(R.drawable.ic_done_write_24dp);
                }
                else{
                    ColorStateList colorStateList = ColorStateList.valueOf(Color.WHITE);
                    ViewCompat.setBackgroundTintList(fab, colorStateList);
                    fab.setImageResource(R.drawable.ic_done_black_24dp);
                }
            }
        });
        bouncer.start();
    }

    @Override
    protected void startRefresh() {
        setAbout(view);
        checkStarStatus();
        endRefresh();
    }
}
