package com.wuyazhou.learn.recycleview.DragRecycleView;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;
import java.util.List;

/**
 * @author wuyzh
 * */
public class SimpleItemTouchCallback extends ItemTouchHelper.Callback {
    private DragRecycleViewAdapter mDragRecycleViewAdapter;
    private List<String> mDataList;
    public SimpleItemTouchCallback(DragRecycleViewAdapter dragRecycleViewAdapter, List<String> list){
        this.mDragRecycleViewAdapter = dragRecycleViewAdapter;
        this.mDataList = list;
    }
    /**
     * 设置支持的拖拽、滑动的方向
     * */
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        //s上下拖拽
        int dragFlag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //左->右和右->左滑动
        //ItemTouchHelper.START | ItemTouchHelper.END
        int swipeFlag = ItemTouchHelper.START;
        return makeMovementFlags(dragFlag,swipeFlag);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder targetViewHolder) {
        int from = viewHolder.getAdapterPosition();
        int to = targetViewHolder.getAdapterPosition();
        Collections.swap(mDataList, from, to);
        mDragRecycleViewAdapter.notifyItemMoved(from, to);
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        int pos = viewHolder.getAdapterPosition();
        mDataList.remove(pos);
        mDragRecycleViewAdapter.notifyItemRemoved(pos);
    }
    /**
     * 状态改变时回调
     * */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        super.onSelectedChanged(viewHolder, actionState);
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            DragRecycleViewAdapter.DemoViewHolder holder = (DragRecycleViewAdapter.DemoViewHolder)viewHolder;
            //设置拖拽和侧滑时的背景色
            holder.itemView.setBackgroundColor(0xffda1ab7);
        }
    }

    /**
     * 拖拽或滑动完成之后调用，用来清除一些状态
     * */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        DragRecycleViewAdapter.DemoViewHolder holder = (DragRecycleViewAdapter.DemoViewHolder)viewHolder;
        //背景色还原
        holder.itemView.setBackgroundColor(0xffcdcdcd);
    }
}
