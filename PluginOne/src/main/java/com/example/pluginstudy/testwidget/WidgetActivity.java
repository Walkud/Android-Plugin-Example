package com.example.pluginstudy.testwidget;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pluginstudy.BaseActivity;
import com.example.pluginstudy.R;
import com.example.pluginstudy.notification.NotificationActivity;

import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;

@ContentViewById(R.layout.activity_widget)
public class WidgetActivity extends BaseActivity {

    @ViewById(value = R.id.button_listview, click = "onClickView")
    Button buttonListview;
    @ViewById(value = R.id.button_webview, click = "onClickView")
    Button buttonWebview;
    @ViewById(value = R.id.button_notification, click = "onClickView")
    Button buttonNotification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onClickView(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.button_listview:
                jumpPage(ListViewActivity.class);
                break;
            case R.id.button_webview:
                jumpPage(WebViewActivity.class);
                break;
            case R.id.button_notification:
                jumpPage(NotificationActivity.class);
                break;
        }
    }

}
