package com.example.pluginstudy.bean;


import plugin.example.commonlib.bean.BaseRequestBean;

/**
 * Created by Walkud on 15/10/15.
 */
public class ServerTimeReqBean extends BaseRequestBean {

    @Override
    public String getApi() {
        return "http://123.147.190.204:8077/womthr/usemeal_getSysDate.cst";
    }

    @Override
    public void putParams() {
    }
}
