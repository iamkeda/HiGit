package com.kd.higit.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;


import com.kd.gitnb.R;
import com.kd.higit.bean.CurrentUser;
import com.kd.higit.bean.User;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by KD on 2016/6/16.
 */
public class FirstActivity extends Activity {
    public static final String VIDEO_NAME = "welcome_video.mp4";
    private static int FOR_AUTHORIZE = 300;
    private boolean alreadyJump = false;
    private VideoView mVideoView;
    //使用ObjectAnimator设置动画
    private ObjectAnimator mObjectAnimator;
    private Button mButton;
    private TextView mTextView;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = CurrentUser.get(FirstActivity.this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        setContentView(R.layout.first_activity);
        findView();
        initView();
        //获取file实例
        File videoFile = getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists()) {
            videoFile = copyVideoFile();
        }

        playVideo(videoFile);
        playAnim();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == FOR_AUTHORIZE && resultCode == RESULT_OK) {
            jumpToManiActivity();
        }
    }

    private void findView() {
        mVideoView = (VideoView) findViewById(R.id.videoView);
        mButton = (Button) findViewById(R.id.buttonLeft);
        mTextView = (TextView) findViewById(R.id.appName);
    }

    private void initView() {
        if (mUser != null) {
            mButton.setText("welcome");
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    jumpToManiActivity();
                }
            });
        }
        else {
            mButton.setText("login");
            mButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FirstActivity.this, AuthorizeActivity.class);
                    startActivityForResult(intent, FOR_AUTHORIZE);
                }
            });
        }
    }

    private void playVideo(File videoFile) {
        mVideoView.setVideoPath(videoFile.getPath());
        mVideoView.setLayoutParams(new RelativeLayout.LayoutParams(-1, -1));
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.setLooping(true);
                mp.start();
            }
        });
    }

    private void playAnim() {
        mObjectAnimator = ObjectAnimator.ofFloat(mTextView, "alpha", 0, 1, 0);
        mObjectAnimator.setDuration(8000);
        mObjectAnimator.start();
        if (mUser != null) {
            mObjectAnimator.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {
                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    jumpToManiActivity();
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                }

                @Override
                public void onAnimationRepeat(Animator animation) {
                }
            });
        }
    }

    private void jumpToManiActivity() {
        if (!alreadyJump) {
            alreadyJump = true;
            Intent intent = new Intent(FirstActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    private File copyVideoFile() {
        File videoFile;
        try {
            FileOutputStream output = openFileOutput(VIDEO_NAME, MODE_PRIVATE);
            InputStream input = getResources().openRawResource(R.raw.welcome_video);
            byte[] buffer = new byte[1024];
            int length = 0;
            while ((length = input.read(buffer)) != -1) {
                output.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        videoFile = getFileStreamPath(VIDEO_NAME);
        if (!videoFile.exists()) {
            throw new RuntimeException("file has problem!");
        }
        return videoFile;

    }
}
