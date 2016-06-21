package com.kd.higit.api;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.ActivityChooserView;

import com.kd.higit.base.BaseFragment;
import com.kd.higit.bean.CurrentUser;
import com.kd.higit.ui.AuthorizeActivity;
import com.kd.higit.ui.FirstActivity;
import com.kd.higit.utils.KLog;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by KD on 2016/6/18.
 */
public abstract class RetrofitNetwork {
    protected final String TAG = this.getClass().getSimpleName();
    protected NetworkListener networkListener;

    protected <T extends RetrofitNetwork> T setNetworkListener(NetworkListener networkListener, T type) {
        type.networkListener = networkListener;
        return type;
    }

    public abstract <T extends  RetrofitNetwork> T setNetworkListener(NetworkListener networkListener);

    protected void execute(Call call) {
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                KLog.d("user 有  onResponse");
                myOnResponse(response);
            }

            @Override
            public void onFailure(Call call, Throwable t) {
                myOnFailure(t);
            }
        });
    }

    protected boolean myOnResponse(Response<? extends Object> response){
        if (response.isSuccessful()) {
            if (networkListener != null) {
                networkListener.onOk(response.body());
            }
            return true;
        } else {
            KLog.d("wocao! response.isFailed");
            String msg = response.message();
            if (msg.equals("Unauthorized") && networkListener instanceof BaseFragment) {
                Activity context = ((BaseFragment) networkListener).getActivity();
                CurrentUser.delete(context);
                sureToAuthorize(context);
                networkListener.onError("Please refresg again");
            } else if (networkListener != null) {
                networkListener.onError(msg);
            }
        }
        return false;
    }

    private void sureToAuthorize(final Activity context) {
        Dialog dialog = new AlertDialog.Builder(context).setTitle("Warning")
                .setMessage("The GitHup request is unauthorized. Please authorizing again?")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        Intent intent = new Intent(context, AuthorizeActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        context.finish();
                        Intent intent = new Intent(context, FirstActivity.class);
                        context.startActivity(intent);
                    }
                })
                .setCancelable(false)
                .create();
        dialog.show();
    }

    protected void myOnFailure(Throwable t) {
        if (networkListener != null) {
            networkListener.onError(t.getMessage());
        }
    }
    public interface NetworkListener<T extends Object> {
        void onOk(T ts);
        void onError(String msg);
    }
}
