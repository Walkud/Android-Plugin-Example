package com.example.pluginstudy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import plugin.example.commonlib.common.PluginHelper;
import plugin.example.commonlib.util.MyToast;

public class BroadCastReceiverOne extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BroadCastReceiverOne", "BroadCastReceiverOne:广播One");
        MyToast.show(context, "BroadCastReceiverOne:广播One" + intent.getStringExtra(PluginHelper.EXTRA_PARAMS));
    }

}
