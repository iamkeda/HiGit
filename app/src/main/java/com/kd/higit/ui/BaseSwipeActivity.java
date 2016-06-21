package com.kd.higit.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kd.gitnb.R;
import com.kd.higit.utils.Utils;
import com.kd.myswipeback.base.SwipeBackActivity;


/**
 * Created by KD on 2016/6/17.
 */
public abstract class BaseSwipeActivity extends SwipeBackActivity {
    protected static int START_UPDATE = 10;
    protected static int END_UPDATE = 20;
    protected static int END_ERROR = 30;
    private Toolbar mToolbar;
    private View mView;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == START_UPDATE) {
                startRefresh();
            } else if (msg.what == END_UPDATE) {
                endRefresh();
            } else if (msg.what == END_ERROR) {
                endError();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatus();
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        mView = ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
        mView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                mHandler.sendEmptyMessage(START_UPDATE);
            }
        });
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setTitle((TextView) mToolbar.findViewById(R.id.title_auth));
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(getNavigationIcon());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    abstract protected void setTitle(TextView view);

    protected int getNavigationIcon(){
        return R.drawable.ic_back_arrow;
    }

    private void setStatus() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
            window.setNavigationBarColor(Color.TRANSPARENT);
        }
    }

    protected void startRefresh() {
        //Utils.setRefreshing(getSwipeRefreshLayout(), true);
    }

    protected void endRefresh() {
        //Utils.setRefreshing(getSwipeRefreshLayout(), false);
    }

    protected void endError() {
        //Utils.setRefreshing(getSwipeRefreshLayout(), false);
    }
}
