package com.zbar.lib;

import android.os.Bundle;

import plugin.example.commonlib.PluginBaseActivity;
import plugin.example.commonlib.common.lib.viewioc.ViewFactory;


/**
 * Created by jan on 15/8/21.
 */
public class BaseActivity extends PluginBaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ViewFactory.InitActivity(this);
    }
}
