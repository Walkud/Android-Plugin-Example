package com.example.pluginstudy.testwidget.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pluginstudy.R;
import com.example.pluginstudy.testwidget.bean.ListTestInfo;
import com.squareup.picasso.Picasso;

import java.util.List;

import plugin.example.commonlib.common.lib.viewioc.ViewById;
import plugin.example.commonlib.common.lib.viewioc.ViewFactory;

/**
 * Created by zly on 2015/9/24.
 */
public class ListViewAdapter extends RecyclerView.Adapter<ListViewAdapter.ViewHolder> implements
        View.OnClickListener, View.OnLongClickListener {

    private Context mContext;

    private List<ListTestInfo> mListTestInfos;

    public OnRVItemClickListener mOnRVItemClickListener;
    public OnRVItemLongClickListener mOnRVItemLongClickListener;

    // Provide a reference to the type of views that you are using
    // (custom viewholder)
    public static class ViewHolder extends RecyclerView.ViewHolder {

        @ViewById(R.id.item_img)
        ImageView itemImg;
        @ViewById(R.id.item_text)
        TextView itemText;

        public ViewHolder(View v) {
            super(v);
            ViewFactory.InitView(this, v);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ListViewAdapter(Context context, List<ListTestInfo> listTestInfos) {
        mContext = context;
        mListTestInfos = listTestInfos;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // create a new view
        View v = LayoutInflater.from(mContext)
                .inflate(R.layout.cell_listview_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        v.setOnClickListener(this);
        v.setOnLongClickListener(this);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        ListTestInfo listTestInfo = mListTestInfos.get(position);

        String desc = listTestInfo.getDesc();

        Picasso.with(mContext).load(listTestInfo.getImgUrl()).into(holder.itemImg);
        holder.itemText.setText(desc);

        //绑定TAG
        holder.itemView.setTag(listTestInfo);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mListTestInfos.size();
    }


    /**
     * Item点击事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        if (mOnRVItemClickListener != null) {
            mOnRVItemClickListener.onItemClick(v, (ListTestInfo) v.getTag());
        }
    }

    /**
     * Item长按事件
     *
     * @param v
     * @return
     */
    @Override
    public boolean onLongClick(View v) {

        if (mOnRVItemLongClickListener != null) {
            mOnRVItemLongClickListener.onItemLongClick(v, (ListTestInfo) v.getTag());
        }

        return true;
    }


    /**
     * 注入Item点击事件
     *
     * @param listener
     */
    public void setOnItemClickListener(OnRVItemClickListener listener) {
        this.mOnRVItemClickListener = listener;
    }

    /**
     * 注入Item点击事件
     *
     * @param listener
     */
    public void setOnItemLongClickListener(OnRVItemLongClickListener listener) {
        this.mOnRVItemLongClickListener = listener;
    }

    public interface OnRVItemClickListener {
        void onItemClick(View view, ListTestInfo listTestInfo);
    }

    public interface OnRVItemLongClickListener {
        void onItemLongClick(View view, ListTestInfo listTestInfo);
    }

}
