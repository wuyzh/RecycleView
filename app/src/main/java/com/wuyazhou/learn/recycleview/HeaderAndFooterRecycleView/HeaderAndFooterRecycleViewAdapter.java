package com.wuyazhou.learn.recycleview.HeaderAndFooterRecycleView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.wuyazhou.learn.recycleview.LinearRecycleView.LinearRecycleViewAdapter;

/**
 * @author wuyzh
 * */
public class HeaderAndFooterRecycleViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LinearRecycleViewAdapter mLinearRecycleViewAdapter;
    private View mHeaderView;
    private View mFooterView;
    enum ITEM_TYPE{
        HEADER,
        FOOTER,
        NORMAL
    }

    public HeaderAndFooterRecycleViewAdapter(LinearRecycleViewAdapter linearRecycleViewAdapter){
        this.mLinearRecycleViewAdapter = linearRecycleViewAdapter;
    }
    /**
     * 用来创建ViewHolder
     * 引入xml给ViewHolder
     * */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.HEADER.ordinal()){
            return new RecyclerView.ViewHolder(mHeaderView) {};
        }else if (viewType == ITEM_TYPE.FOOTER.ordinal()){
            return new RecyclerView.ViewHolder(mFooterView) {};
        }else {
            return mLinearRecycleViewAdapter.onCreateViewHolder(parent,viewType);
        }
    }

    /**
     * 操作每一项item的地方
     * */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (position == 0){
            return;
        }else if (position == mLinearRecycleViewAdapter.getItemCount()+1){
            return;
        }else {
            mLinearRecycleViewAdapter.onBindViewHolder((LinearRecycleViewAdapter.DemoViewHolder) viewHolder,position-1);
        }
    }

    /**
     * 一共多少项
     * */
    @Override
    public int getItemCount() {
        return mLinearRecycleViewAdapter.getItemCount()+2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return ITEM_TYPE.HEADER.ordinal();
        }else if (position == mLinearRecycleViewAdapter.getItemCount()+1){
            return ITEM_TYPE.FOOTER.ordinal();
        }else {
            return ITEM_TYPE.NORMAL.ordinal();
        }
    }

    public void addHeaderView(View view){
        this.mHeaderView = view;
    }
    public void addFooterView(View view){
        this.mFooterView = view;
    }
}
