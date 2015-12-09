package com.example.pluginstudy;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;

import com.example.pluginstudy.service.IServiceProcessBindOne;
import com.example.pluginstudy.service.ServiceBindOne;
import com.example.pluginstudy.service.ServiceBindOne.MyBinderOne;
import com.example.pluginstudy.service.ServiceBindTwo;
import com.example.pluginstudy.service.ServiceBindTwo.MyBinderTwo;
import com.example.pluginstudy.service.ServiceOne;
import com.example.pluginstudy.service.ServiceProcessBindOne;
import com.example.pluginstudy.service.ServiceThree;
import com.example.pluginstudy.service.ServiceTwo;

import plugin.example.commonlib.common.PluginHelper;
import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;
import plugin.example.commonlib.util.MLog;
import plugin.example.commonlib.util.MyToast;

@ContentViewById(R.layout.activity_service)
public class ServiceActivity extends BaseActivity {

    @ViewById(value = R.id.button_service_one, click = "onClickView")
    Button buttonServiceOne;
    @ViewById(value = R.id.button_service_two, click = "onClickView")
    Button buttonServiceTwo;
    @ViewById(value = R.id.button_service_three, click = "onClickView")
    Button buttonServiceThree;
    @ViewById(value = R.id.button_service_bind_one, click = "onClickView")
    Button buttonServiceBindOne;
    @ViewById(value = R.id.button_service_bind_method_one, click = "onClickView")
    Button buttonServiceBindMethodOne;
    @ViewById(value = R.id.button_service_bind_two, click = "onClickView")
    Button buttonServiceBindTwo;
    @ViewById(value = R.id.button_service_bind_method_two, click = "onClickView")
    Button buttonServiceBindMethodTwo;
    @ViewById(value = R.id.button_service_process_bind_one, click = "onClickView")
    Button buttonServiceProcessBindOne;
    @ViewById(value = R.id.button_service_process_bind_show, click = "onClickView")
    Button buttonServiceProcessBindShow;
    @ViewById(value = R.id.button_service_process_bind_calculate, click = "onClickView")
    Button buttonServiceProcessBindCalculate;

    private ServiceBindOne mServiceBindOne;
    private ServiceBindTwo mServiceBindTwo;
    private IServiceProcessBindOne mIServiceProcessBindOne;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onClickView(View v) {
        int pid = android.os.Process.myPid();
        int id = v.getId();
        switch (id) {
            case R.id.button_service_one://启动第一个Service
                startService(ServiceOne.class, "第一个 pid:" + pid);
                break;
            case R.id.button_service_two://启动第二个Service
                startService(ServiceTwo.class, "第二个pid:" + pid);
                break;
            case R.id.button_service_three://启动第二个Service
                startService(ServiceThree.class, "第三个pid:" + pid);
                break;
            case R.id.button_service_bind_one://绑定ServiceBindOne
                Intent bindOneIntent = new Intent(this, ServiceBindOne.class);
                bindService(bindOneIntent, connOne, Context.BIND_AUTO_CREATE);
                break;
            case R.id.button_service_bind_method_one:
                if (mServiceBindOne != null) {
                    mServiceBindOne.showToast();
                }
                break;
            case R.id.button_service_bind_two://绑定ServiceBindTwo
                Intent bindTwoIntent = new Intent(this, ServiceBindTwo.class);
                bindService(bindTwoIntent, connTwo, Context.BIND_AUTO_CREATE);
                break;
            case R.id.button_service_bind_method_two:
                if (mServiceBindTwo != null) {
                    mServiceBindTwo.showToast();
                }
                break;
            case R.id.button_service_process_bind_one://绑定跨进程ServiceProcessBindOne
                Intent bindProcessIntent = new Intent(this, ServiceProcessBindOne.class);
                bindService(bindProcessIntent, connProcessOne, Context.BIND_AUTO_CREATE);
                break;
            case R.id.button_service_process_bind_show:
                if (mIServiceProcessBindOne != null) {
                    try {
                        mIServiceProcessBindOne.showToast();
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;

            case R.id.button_service_process_bind_calculate:
                if (mIServiceProcessBindOne != null) {
                    try {
                        int result = mIServiceProcessBindOne.calculate(3, 6);
                        MyToast.show(this, "计算3+6，结果：" + result);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * 启动Service
     *
     * @param cls
     * @param params
     */
    private void startService(Class cls, String params) {
        Intent intent = new Intent(this, cls);
        intent.putExtra(PluginHelper.EXTRA_PARAMS, params);
        startService(intent);
    }


    private ServiceConnection connOne = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            buttonServiceBindMethodOne.setVisibility(View.GONE);
            MyToast.show(ServiceActivity.this, "BindService 失败");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinderOne binder = (MyBinderOne) service;
            mServiceBindOne = binder.getService();
            buttonServiceBindMethodOne.setVisibility(View.VISIBLE);
            MyToast.show(ServiceActivity.this, "Service绑定成功:" + mServiceBindOne);
        }
    };

    private ServiceConnection connTwo = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            buttonServiceBindMethodTwo.setVisibility(View.GONE);
            MyToast.show(ServiceActivity.this, "BindService 失败");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MyBinderTwo binder = (MyBinderTwo) service;
            mServiceBindTwo = binder.getService();
            buttonServiceBindMethodTwo.setVisibility(View.VISIBLE);
            MyToast.show(ServiceActivity.this, "Service绑定成功:" + mServiceBindTwo);
        }
    };

    private ServiceConnection connProcessOne = new ServiceConnection() {

        @Override
        public void onServiceDisconnected(ComponentName name) {
            buttonServiceProcessBindShow.setVisibility(View.GONE);
            buttonServiceProcessBindCalculate.setVisibility(View.GONE);
            MyToast.show(ServiceActivity.this, "BindService 失败");
        }

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIServiceProcessBindOne = IServiceProcessBindOne.Stub.asInterface(service);
            buttonServiceProcessBindShow.setVisibility(View.VISIBLE);
            buttonServiceProcessBindCalculate.setVisibility(View.VISIBLE);
            MyToast.show(ServiceActivity.this, "Service绑定成功:" + mIServiceProcessBindOne);
        }
    };

    @Override
    protected void onStop() {
        MLog.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        MLog.d(TAG, "onDestroy");
        super.onDestroy();
        if (mServiceBindOne != null) {
            unbindService(connOne);
            Intent intent = new Intent(this, ServiceBindOne.class);
            stopService(intent);
        }

        if (mServiceBindTwo != null) {
            unbindService(connTwo);
            Intent intent = new Intent(this, ServiceBindTwo.class);
            stopService(intent);
        }

        if (mIServiceProcessBindOne != null) {
            unbindService(connProcessOne);
            Intent intent = new Intent(this, ServiceProcessBindOne.class);
            stopService(intent);
        }

    }
}
