package com.example.pluginstudy.event;

/**
 * Created by Walkud on 15/9/18.
 */
public class PluginMainEvent {

    public String params;

    public PluginMainEvent(String p) {
        this.params = p;
    }

    public String getParams() {
        return params;
    }

    public void setParams(String params) {
        this.params = params;
    }
}
