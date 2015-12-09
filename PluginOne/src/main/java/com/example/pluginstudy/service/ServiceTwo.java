package com.example.pluginstudy.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import plugin.example.commonlib.common.PluginHelper;
import plugin.example.commonlib.util.MyToast;

public class ServiceTwo extends Service {

    private static final String TAG = ServiceTwo.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        MyToast.show(this,
                "ServiceTwo onStartCommand:"
                        + intent.getStringExtra(PluginHelper.EXTRA_PARAMS));

        final Intent i = intent;
        new Thread() {

            @Override
            public void run() {
                int it = 0;
                while (it < 5) {
                    it++;
                    Log.d(ServiceTwo.class.getSimpleName(),
                            "ServiceTwo onStartCommand:"
                                    + i.getStringExtra(PluginHelper.EXTRA_PARAMS) + ",太阳花");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Intent stopIntent = new Intent(getApplicationContext(),ServiceTwo.class);
                stopService(stopIntent);

            }

        }.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onRebind(Intent intent) {
        Log.d(TAG, "onRebind");
        super.onRebind(intent);
    }


    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

}
