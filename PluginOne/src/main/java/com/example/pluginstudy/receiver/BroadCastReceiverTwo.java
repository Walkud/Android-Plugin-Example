package com.example.pluginstudy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import plugin.example.commonlib.common.PluginHelper;
import plugin.example.commonlib.util.MyToast;

public class BroadCastReceiverTwo extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BroadCastReceiverTwo", "BroadCastReceiverTwo:广播Two");
        MyToast.show(context, "BroadCastReceiverTwo:广播Two" + intent.getStringExtra(PluginHelper.EXTRA_PARAMS));
    }

}
