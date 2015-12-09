package com.example.pluginstudy;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.pluginstudy.database.OrmDbActivity;
import com.example.pluginstudy.database.SdkDbActivity;

import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;

@ContentViewById(R.layout.activity_database)
public class DatabaseActivity extends BaseActivity {


    @ViewById(value = R.id.button_sdk_db, click = "onClickView")
    Button buttonSdkDb;
    @ViewById(value = R.id.button_orm_db, click = "onClickView")
    Button buttonOrmDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onClickView(View v) {

        int id = v.getId();
        switch (id) {
            case R.id.button_sdk_db:
                jumpPage(SdkDbActivity.class);
                break;
            case R.id.button_orm_db:
                jumpPage(OrmDbActivity.class);
                break;
        }
    }

}
