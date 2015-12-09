package com.example.pluginstudy.testwidget;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.example.pluginstudy.BaseActivity;
import com.example.pluginstudy.R;
import com.example.pluginstudy.testwidget.adapter.ListViewAdapter;
import com.example.pluginstudy.testwidget.bean.ImgUrl;
import com.example.pluginstudy.testwidget.bean.ListTestInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import plugin.example.commonlib.common.lib.viewioc.ContentViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewById;
import plugin.example.commonlib.util.MyToast;
import plugin.example.commonlib.view.recyclerview.decoration.HorizontalDividerItemDecoration;

/**
 * MaterialRefreshLayout 控件开源地址
 * https://github.com/android-cjj/Android-MaterialRefreshLayout
 */
@ContentViewById(R.layout.activity_listview)
public class ListViewActivity extends BaseActivity {

    @ViewById(R.id.refresh)
    MaterialRefreshLayout refresh;
    @ViewById(R.id.my_recycler_view)
    RecyclerView myRecyclerView;

    private RecyclerView.LayoutManager mLayoutManager;

    private ListViewAdapter adapter;
    List<ListTestInfo> mListTestInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        myRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        myRecyclerView.setHasFixedSize(true);
        myRecyclerView.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this)
                .drawable(R.drawable.sample)
                .size(5)
                .build());

        init();

        bindListener();
    }


    /**
     * 初始化
     */
    private void init() {


        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                mHandler.sendEmptyMessageDelayed(0, 2000);
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                mHandler.sendEmptyMessageDelayed(1, 2000);
            }
        });

        refresh.setLoadMore(true);


        mListTestInfo = new ArrayList<>();
        adapter = new ListViewAdapter(this, mListTestInfo);
        myRecyclerView.setAdapter(adapter);

        mHandler.sendEmptyMessageDelayed(0, 500);
    }

    /**
     * 绑定时间
     */
    private void bindListener() {
        adapter.setOnItemClickListener(new ListViewAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(View view, ListTestInfo listTestInfo) {
                MyToast.show(getApplicationContext(), "点击了：" + listTestInfo.getDesc());
            }
        });

        adapter.setOnItemLongClickListener(new ListViewAdapter.OnRVItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, ListTestInfo listTestInfo) {
                MyToast.show(getApplicationContext(), "长按了：" + listTestInfo.getDesc());
            }
        });
    }

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            updateUI(msg.what);
        }
    };

    /**
     * 更新UI
     */
    private void updateUI(int what) {

        if (what == 0) {//刷新
            mListTestInfo.clear();
        }

        mListTestInfo.addAll(createTestData());

        adapter.notifyDataSetChanged();

        if (what == 0) {
            //refresh complete
            refresh.finishRefresh();
        } else {
            refresh.finishRefreshLoadMore();
        }
    }

    /**
     * 创建测试数据
     *
     * @return
     */
    private List<ListTestInfo> createTestData() {

        List<ListTestInfo> list = new ArrayList<>();

        Random r = new Random();
        List<String> imgUrl = ImgUrl.sImgUrls;
        for (int i = 0; i < 15; i++) {
            ListTestInfo listTestInfo = new ListTestInfo();
            listTestInfo.setDesc("ListView Desc" + r.nextInt(50000));
            listTestInfo.setImgUrl(imgUrl.get(r.nextInt(imgUrl.size())));
            list.add(listTestInfo);
        }

        return list;
    }

}
