package com.example.pluginstudy.bean;

import plugin.example.commonlib.bean.BaseResponseBean;

/**
 * Created by Walkud on 15/9/28.
 */
public class ServerTime extends BaseResponseBean {

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
