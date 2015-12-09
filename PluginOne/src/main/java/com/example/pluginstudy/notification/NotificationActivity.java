package com.example.pluginstudy.notification;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RemoteViews;

import com.example.pluginstudy.BaseActivity;
import com.example.pluginstudy.R;
import com.example.pluginstudy.SoActivity;
import com.plugin.core.PluginIntentResolver;

import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;

/**
 * Created by jan on 15/8/28.
 */

@ContentViewById(R.layout.activity_notification)
public class NotificationActivity extends BaseActivity implements View.OnClickListener {
    @ViewById(R.id.send_notification_normal)
    private Button notifyNormalBtn;
    @ViewById(R.id.send_notification_view)
    private Button notifyViewBtn;
    @ViewById(R.id.cancel_notification)
    private Button cancelBtn;
    @ViewById(R.id.cancel_all_notification)
    private Button cancelALlBtn;
    private NotificationManager nm;
    private Notification n;
    // 通知的ID
    public static final int ID = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        notifyNormalBtn.setOnClickListener(this);
        notifyViewBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);
        cancelALlBtn.setOnClickListener(this);
        // 1.获取NotificationManager对象
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        // 2.初始化Notification对象
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.send_notification_normal:
                n = getNormalNotification("普通Notification");
                nm.notify(ID, n);
                break;
            case R.id.send_notification_view:
                n = getCustomNotification("带View的Notification");
                nm.notify(ID, n);
                break;
            case R.id.cancel_notification:
                // 取消通知
                nm.cancel(ID);
                break;
            case R.id.cancel_all_notification:
                // 取消所有通知
                nm.cancelAll();
                break;
        }
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
                .setContentTitle("Notification Title")// 设置在下拉status
                // bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
                .setContentText("This is the notification message")// TextView中显示的详细内容
                .setContentIntent(pi); // 关联PendingIntent

        return notifyBuilder.getNotification();
    }

    /**
     * 自定义布局的Notification
     *
     * @param text
     * @return
     */
    private Notification getCustomNotification(String text) {

        Notification.Builder notifyBuilder = new Notification.Builder(this)
                .setSmallIcon(plugin.example.commonlib.R.drawable.sign_in_red_gift)// 设置状态栏中的小图片，尺寸一般建议在24×24，这个图片同样也是在下拉状态栏中所显示，如果在那里需要更换更大的图片，可以使用setLargeIcon(Bitmap
                .setTicker("TickerText:" + "您有新短消息，请注意查收！")// 设置在status
                // bar上显示的提示文字
                .setAutoCancel(true)//设置自动取消
                .setContentTitle("Notification Title")// 设置在下拉status
                // bar后Activity，本例子中的NotififyMessage的TextView中显示的标题
                .setContentText("This is the notification message");// TextView中显示的详细内容

        Intent intent = new Intent();
        //唤起插件Activity
        intent.setClassName(getPackageName(), SoActivity.class.getName());
        intent.putExtra("param1", "这是来自通知栏的参数");
        // 为了解决插件支持Notification
        // 此处写法必须为PluginIntentResolver.resolveNotificationIntent(intent);
        intent = PluginIntentResolver.resolveNotificationIntent(intent);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notify = notifyBuilder.getNotification();
        RemoteViews rv = new RemoteViews(getPackageName(),
                plugin.example.commonlib.R.layout.test_notification_layout);
        rv.setImageViewResource(plugin.example.commonlib.R.id.songer_pic, plugin.example.commonlib.R.mipmap.ic_menu);
        rv.setOnClickPendingIntent(plugin.example.commonlib.R.id.last_music, pendingIntent);
        rv.setOnClickPendingIntent(plugin.example.commonlib.R.id.paly_pause_music, pendingIntent);
        rv.setOnClickPendingIntent(plugin.example.commonlib.R.id.next_music, pendingIntent);

        notify.contentView = rv;
        return notify;
    }
}
