package com.example.pluginstudy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.VolleyError;
import com.example.pluginstudy.bean.ServerTime;
import com.example.pluginstudy.bean.ServerTimeReqBean;
import com.example.pluginstudy.event.PluginMainEvent;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;

import de.greenrobot.event.EventBus;
import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;
import plugin.example.commonlib.network.HttpTools;
import plugin.example.commonlib.network.request.CsocRequest;
import plugin.example.commonlib.util.MyToast;

/**
 * 第三方库
 */
@ContentViewById(R.layout.activity_third_party)
public class ThirdPartyActivity extends BaseActivity {


    @ViewById(value = R.id.button_eventbus, click = "onClickView")
    Button buttonEventbus;
    @ViewById(value = R.id.button_picasso, click = "onClickView")
    Button buttonPicasso;
    @ViewById(value = R.id.button_service_volley, click = "onClickView")
    Button buttonServiceVolley;
    @ViewById(value = R.id.button_service_volley_vo, click = "onClickView")
    Button buttonServiceVolleyVo;
    @ViewById(R.id.img_picasso)
    ImageView imgPicasso;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onClickView(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.button_eventbus:
                EventBus.getDefault().post(new PluginMainEvent("测试EventBus"));
                break;
            case R.id.button_picasso:
                Picasso.with(this).load("http://tupian.qqjay.com/u/2013/0402/5_223819_4.jpg").into(imgPicasso);
                break;
            case R.id.button_service_volley:

                HttpTools.addRequest(this,
                        new ServerTimeReqBean(), new CsocRequest.ResponeListener<String>() {

                            @Override
                            public void onResponse(String response) {
                                MyToast.show(getApplication(), "response:" + response);
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                MyToast.show(getApplication(), "网络错误");
                            }
                        });
                break;
            case R.id.button_service_volley_vo:
                Type type = new TypeToken<ServerTime>() {
                }.getType();
                HttpTools.addRequest(this,
                        new ServerTimeReqBean(), type, new CsocRequest.ResponeListener<ServerTime>() {

                            @Override
                            public void onResponse(ServerTime response) {
                                MyToast.show(getApplication(), "response isSuccess:" + response.isSuccess() + ", date:" + response.getDate());
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                MyToast.show(getApplication(), "网络错误:");
                                error.printStackTrace();
                            }
                        });
                break;
        }
    }

}
