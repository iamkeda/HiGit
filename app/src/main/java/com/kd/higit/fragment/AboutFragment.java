package com.kd.higit.fragment;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.kd.gitnb.R;

/**
 * Created by KD on 2016/6/27.
 */
public class AboutFragment extends PreferenceFragment implements Preference.OnPreferenceClickListener{
    private final String INTRODUCTION = "introduction";
    private final String CURRENT_VERSION = "current_version";
    private final String SHARE = "share";
    private final String ENCOURAGE = "encourage";
    private final String BLOG = "blog";
    private final String GITHUB = "github";
    private final String EMAIL = "email";
    private final String CHECK = "check_version";

    private Preference mIntroduction;
    private Preference mVersion;
    private Preference mShare;
    private Preference mEncourage;
    private Preference mBlog;
    private Preference mEmail;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.about);

        mIntroduction = findPreference(INTRODUCTION);
        mVersion = findPreference(CURRENT_VERSION);
        mShare = findPreference(SHARE);
        mEncourage = findPreference(ENCOURAGE);
        mBlog = findPreference(BLOG);
        mEmail = findPreference(EMAIL);

        mIntroduction.setOnPreferenceClickListener(this);
        mVersion.setOnPreferenceClickListener(this);
        mShare.setOnPreferenceClickListener(this);
        mEncourage.setOnPreferenceClickListener(this);
        mBlog.setOnPreferenceClickListener(this);
        mEmail.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        if (mIntroduction == preference) {
            new AlertDialog.Builder(getActivity()).setTitle("介绍")
                    .setMessage("HiGit是一款GitHub第三方客户端，完全基于MD设计，界面简洁优雅，欢迎大家使用。")
                    .setPositiveButton("关闭", null)
                    .show();
        } else if (mShare == preference) {
            new AlertDialog.Builder(getActivity()).setTitle("分享")
                    .setMessage("待应用上线后，再增加分享功能。")
                    .setPositiveButton("关闭", null)
                    .show();
        } else if (mEncourage == preference) {
            new AlertDialog.Builder(getActivity()).setTitle("鼓励下开发者？")
                    .setMessage("点击按钮后，作者支付宝账号将会复制到剪切板，" + "你就可以使用支付宝转账给作者啦。 ")
                    .setPositiveButton("好的", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            copyToClipboard(getView(), getActivity().getResources().getString(R.string.alipay));
                        }
                    }).show();
            }
        else if (mBlog == preference) {
            Snackbar.make(getView(), "博客正在建设中...", Snackbar.LENGTH_SHORT).show();
        } else if (mEmail == preference) {
            copyToClipboard(getView(), mEmail.getSummary().toString());
        } else if (mVersion == preference) {
            new AlertDialog.Builder(getActivity()).setTitle("致谢")
                    .setMessage("开源库：Gson + Retrofit + Fresco")
                    .setPositiveButton("关闭", null)
                    .show();
        }
        return false;

    }

    private void copyToClipboard(View view, String info) {
        ClipboardManager manager = (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clipData = ClipData.newPlainText("msg", info);
        manager.setPrimaryClip(clipData);
        Snackbar.make(view, "已经复制到剪切板啦。", Snackbar.LENGTH_SHORT).show();
    }
}
