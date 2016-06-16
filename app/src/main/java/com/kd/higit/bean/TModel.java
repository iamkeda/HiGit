package com.kd.higit.bean;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by KD on 2016/6/16.
 */
public abstract class TModel implements Serializable {
    private static final long serialVersionUID = 1248060059094676956L;
    abstract  public void parse(JSONObject jsonObject) throws JSONException;
}
