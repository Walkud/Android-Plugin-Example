package com.example.pluginstudy.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.IBinder;
import android.util.Log;

import com.example.pluginstudy.SoActivity;
import com.plugin.core.PluginIntentResolver;

import plugin.example.commonlib.common.PluginHelper;
import plugin.example.commonlib.util.MyToast;

public class ServiceThree extends Service {

    private static final String TAG = ServiceThree.class.getSimpleName();

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
                "ServiceThree onStartCommand:"
                        + intent.getStringExtra(PluginHelper.EXTRA_PARAMS));

        final Intent i = intent;
        new Thread() {

            @Override
            public void run() {
                int it = 0;
                while (it < 5) {
                    it++;
                    Log.d(ServiceThree.class.getSimpleName(),
                            "ServiceThree onStartCommand:"
                                    + i.getStringExtra(PluginHelper.EXTRA_PARAMS) + ",太阳花");
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }

        }.start();

        startForeground(100,getNormalNotification("ServiceOne startForeground"));

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


    /**
     * 普通Notification
     *
     * @param text
     * @return
     */
    private Notification getNormalNotification(String text) {
        //唤起插件Activity
        Intent intent = new Intent(this, SoActivity.class);
        intent.putExtra("param1", "这是来自通知栏的参数");
        // 为了解决插件支持Notification
        // 此处写法必须为PluginIntentResolver.resolveNotificationIntent(intent);
        intent = PluginIntentResolver.resolveNotificationIntent(intent);
        PendingIntent pi = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Drawable drawable = getResources().getDrawable(plugin.example.commonlib.R.mipmap.ic_menu);
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();

        Notification.Builder notifyBuilder = new Notification.Builder(this)
                .setLargeIcon(bitmap)//设置大图标
                .setSmallIcon(plugin.example.commonlib.R.drawable.sign_in_red_gift)// 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap
                .setTicker("TickerText:" + "您有新短消息，请注意查收！")// 设置在status
                        // bar上显示的提示文字
                .setAutoCancel(true)//设置自动取消
                .setContentTitle(text)// 设置在下拉status
                        // bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
                .setContentText("This is the notification message")// TextView中显示的详细内容
                .setContentIntent(pi); // 关联PendingIntent

        return notifyBuilder.getNotification();
    }
}
