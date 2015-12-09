package com.example.pluginstudy.database;

import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pluginstudy.BaseActivity;
import com.example.pluginstudy.R;
import com.j256.ormlite.dao.RuntimeExceptionDao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;

@ContentViewById(R.layout.activity_db)
public class OrmDbActivity extends BaseActivity {

    @ViewById(value = R.id.button2, click = "onClickView")
    Button button2;
    @ViewById(value = R.id.button3, click = "onClickView")
    Button button3;
    @ViewById(value = R.id.button4, click = "onClickView")
    Button button4;
    @ViewById(value = R.id.button5, click = "onClickView")
    Button button5;
    @ViewById(value = R.id.list1)
    ListView list1;

    List<DataMap> datas;
    MyAdapter myAdapter;

    OrmDbHelper odbHelper;
    RuntimeExceptionDao<DataMap, Integer> dataMapRuntimeDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");

        datas = new ArrayList<DataMap>();
        odbHelper = OrmDbHelper.getHelper(this);
        dataMapRuntimeDao = odbHelper.getDataMapRuntimeDao();

        myAdapter = new MyAdapter();
        list1.setAdapter(myAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
    }

    int count = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && count == 0) {
            Toast.makeText(getApplicationContext(), "点击返回键", Toast.LENGTH_LONG)
                    .show();
            count++;
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    public void onClickView(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.button2:// 插入
                dataMapRuntimeDao.create(createDataMap());
                break;
            case R.id.button3:// 更新
                List<DataMap> ddmsUpdate = dataMapRuntimeDao.queryForAll();
                if (ddmsUpdate != null && ddmsUpdate.size() > 0) {
                    DataMap dm = ddmsUpdate.get(0);
                    DataMap dataMap = createDataMap();
                    dm.setKey(dataMap.getKey());
                    dm.setValue(dataMap.getValue());
                    dataMapRuntimeDao.update(dm);
                }
                break;
            case R.id.button4:// 删除
                List<DataMap> ddms = dataMapRuntimeDao.queryForAll();
                if (ddms != null && ddms.size() > 0) {
                    dataMapRuntimeDao.delete(ddms.get(0));
                }
                break;
            case R.id.button5:// 查询
                List<DataMap> dms = dataMapRuntimeDao.queryForAll();
                datas.clear();
                datas.addAll(dms);
                myAdapter.notifyDataSetChanged();
                break;
        }
    }

    /**
     * 创建的数据
     *
     * @return
     */
    private DataMap createDataMap() {
        DataMap dm = new DataMap();
        Random r = new Random();
        int index = r.nextInt(1000);
        dm.setKey("Key" + index);
        dm.setTime(r.nextInt(10000));
        dm.setValue("Value" + index);
        return dm;
    }

    /**
     * 适配器
     */
    public class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public DataMap getItem(int position) {
            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        android.R.layout.simple_list_item_1, null);
            }

            TextView tv = (TextView) convertView
                    .findViewById(android.R.id.text1);

            DataMap dm = getItem(position);
            tv.setText(dm.getKey() + "," + dm.getValue());

            return convertView;
        }
    }
}
