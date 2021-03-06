package com.wuyazhou.learn.recycleview.GridRecycleView;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
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
public class GridRecyclePagerView extends FrameLayout implements View.OnClickListener,GridRecycleViewAdapter.OnItemClickListener {
    private Context mContext = null;
    private RelativeLayout mLayout;
    private List<String> mDataList = new ArrayList<String>();
    private RecyclerView mRecyclerView;
    private GridLayoutManager mGridLayoutManager;
    private GridRecycleViewAdapter mGridRecycleViewAdapter;

    public GridRecyclePagerView(Context context) {
        super(context);
        mContext = context;
        initView();
        initData();
    }

    public GridRecyclePagerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
        initData();
    }

    public GridRecyclePagerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initView();
        initData();
    }

    public void initView() {
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mLayout = (RelativeLayout) inflater.inflate(R.layout.pager_grid_layout, null);

        addView(mLayout);

        Button add = mLayout.findViewById(R.id.add);
        add.setOnClickListener(this);
        Button delete = mLayout.findViewById(R.id.delete);
        delete.setOnClickListener(this);

        mRecyclerView  = mLayout.findViewById(R.id.recycle_view);
        mGridLayoutManager = new GridLayoutManager(mContext,3);

        //设置布局管理器
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        //设置分割线
        mRecyclerView.addItemDecoration(new GridRvDividerDecoration(mContext));
        //设置增加或删除条目的动画
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
    }

    private void initData(){
        for (int i=0;i<40;i++){
            mDataList.add("测试："+i);
        }
        mGridRecycleViewAdapter = new GridRecycleViewAdapter(mContext,mDataList);
        mGridRecycleViewAdapter.setOnItemClickListener(this);
        mRecyclerView.setAdapter(mGridRecycleViewAdapter);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.add:
                mDataList.add(0,"添加");
                mGridRecycleViewAdapter.notifyItemInserted(0);
                mGridLayoutManager.scrollToPosition(0);
                break;
            case R.id.delete:
                mDataList.remove(0);
                mGridRecycleViewAdapter.notifyItemRemoved(0);
                //mGridLayoutManager.scrollToPosition(0);
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
