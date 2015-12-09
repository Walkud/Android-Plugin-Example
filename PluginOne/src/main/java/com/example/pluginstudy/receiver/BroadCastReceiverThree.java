package com.example.pluginstudy.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import plugin.example.commonlib.common.PluginHelper;
import plugin.example.commonlib.util.MyToast;

public class BroadCastReceiverThree extends BroadcastReceiver {

    public static final String ACTION_THREE = "receiver_three";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("BroadCastReceiverThree", "BroadCastReceiverThree:广播Three");
        MyToast.show(context, "BroadCastReceiverThree:广播Three" + intent.getStringExtra(PluginHelper.EXTRA_PARAMS));
    }

}
