package com.wuyazhou.learn.recycleview.DragRecycleView;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import com.wuyazhou.learn.logview.LogShowUtil;
import com.wuyazhou.learn.recycleview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 吴亚洲
 * @date 2018.7.7
 * @function
 */
public class DragRecyclePagerView extends FrameLayout implements View.OnClickListener,DragRecycleViewAdapter.OnItemClickListener {
    private Context mContext = null;
    private RelativeLayout mLayout;
    private List<String> mDataList = new ArrayList<String>();
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLinearLayoutManager;
    private DragRecycleViewAdapter mDragRecycleViewAdapter;

    public DragRecyclePagerView(Context context) {
        super(context);
        mContext = context;
        initView();
        initData();
    }

    public DragRecyclePagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initData();
    }

    public DragRecyclePagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initData();
    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (RelativeLayout) inflater.inflate(R.layout.pager_linear_layout, null);

        addView(mLayout);

        Button add = mLayout.findViewById(R.id.add);
        add.setOnClickListener(this);
        Button delete = mLayout.findViewById(R.id.delete);
        delete.setOnClickListener(this);

        mRecyclerView  = mLayout.findViewById(R.id.recycle_view);
        mLinearLayoutManager = new LinearLayoutManager(mContext);

        //布局管理器设置为垂直布局
        mLinearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        //设置布局管理器
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        //设置分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext,OrientationHelper.VERTICAL));
        //设置增加或删除条目的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData(){
        for (int i=0;i<40;i++){
            mDataList.add("测试："+i);
        }
        mDragRecycleViewAdapter = new DragRecycleViewAdapter(mContext,mDataList);
        mDragRecycleViewAdapter.setOnItemClickListener(this);

        ItemTouchHelper helper = new ItemTouchHelper(new SimpleItemTouchCallback(mDragRecycleViewAdapter, mDataList));
        helper.attachToRecyclerView(mRecyclerView);
        mRecyclerView.setAdapter(mDragRecycleViewAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.add:
                mDataList.add(0,"添加");
                mDragRecycleViewAdapter.notifyItemInserted(0);
                mLinearLayoutManager.scrollToPosition(0);
                break;
            case R.id.delete:
                mDataList.remove(0);
                mDragRecycleViewAdapter.notifyItemRemoved(0);
                mLinearLayoutManager.scrollToPosition(0);
                break;
            default:
                break;
        }
    }

    @Override
    public void onItemClick(View view, int position) {
        LogShowUtil.addLog("RecycleView","点击 item: "+position,true);
    }

    @Override
    public void onItemLongClick(View view, int position) {
        LogShowUtil.addLog("RecycleView","长按 item:  "+position,true);
    }
}
