package com.example.pluginstudy;

import android.content.Intent;
import android.os.Bundle;

import plugin.example.commonlib.common.lib.viewioc.ViewFactory;
import plugin.example.commonlib.PluginBaseActivity;


public class BaseActivity extends PluginBaseActivity {


    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        ViewFactory.InitActivity(this);
    }

    public void jumpPage(Class cls) {
        Intent intent = new Intent(this, cls);
        startActivity(intent);
    }
}
