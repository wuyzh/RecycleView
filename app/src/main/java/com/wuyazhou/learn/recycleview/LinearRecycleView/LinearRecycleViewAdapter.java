package com.wuyazhou.learn.recycleview.LinearRecycleView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wuyazhou.learn.recycleview.R;

import java.util.List;

/**
 * @author wuyzh
 * */
public class LinearRecycleViewAdapter extends RecyclerView.Adapter<LinearRecycleViewAdapter.DemoViewHolder> {
    private Context mContext;
    private List<String> mList;
    private OnItemClickListener mOnItemClickListener;

    public LinearRecycleViewAdapter(Context context, List<String> list){
        mContext = context;
        mList = list;
    }
    /**
     * 用来创建ViewHolder
     * 引入xml给ViewHolder
     * */
    @Override
    public DemoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_linear_layout, parent, false);
        return new DemoViewHolder(view);
    }

    /**
     * 操作每一项item的地方
     * */
    @Override
    public void onBindViewHolder(DemoViewHolder viewHolder, final int position) {
        String text = mList.get(position);
        if (position%100 < 3){
            viewHolder.itemView.setBackgroundResource(R.color.green);
        }else {
            viewHolder.itemView.setBackgroundResource(R.color.gray);
        }

        viewHolder.textView.setText(Html.fromHtml( "<font color=#97CB60>"+ text+"</font> "+ "<font color=#FFFFFF>"+ text+"</font>"));

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemClick(v,position);
                }
            }
        });

        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.onItemLongClick(v,position);
                }
                return true;
            }
        });
    }

    /**
     * 一共多少项
     * */
    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        mOnItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{
        /**
         * 点击响应
         * @param view
         * @param position
         * */
        void onItemClick(View view, int position);
        /**
         * 长按响应
         * @param view
         * @param position
         * */
        void onItemLongClick(View view, int position);
    }

    public class DemoViewHolder extends RecyclerView.ViewHolder{
        TextView textView;
        public DemoViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.message);
        }
    }
}
