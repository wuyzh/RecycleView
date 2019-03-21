package com.wuyazhou.learn.recycleview.GridRecycleView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.wuyazhou.learn.recycleview.R;

/**
 * @author wuyzh
 * */
public class GridRvDividerDecoration extends RecyclerView.ItemDecoration {
    private Drawable mDividerGray,mDividerRed;
    public GridRvDividerDecoration(Context context){
        mDividerGray = context.getResources().getDrawable(R.drawable.grid_divider_gray);
        mDividerRed = context.getResources().getDrawable(R.drawable.grid_divider_red);
    }

    @Override
    public void onDraw(Canvas canvas, RecyclerView parent, RecyclerView.State state) {
        // 绘制间隔，每一个item，绘制右边和下方间隔样式
        int childCount = parent.getChildCount();
        int spanCount = ((GridLayoutManager)parent.getLayoutManager()).getSpanCount();
        int orientation = ((GridLayoutManager)parent.getLayoutManager()).getOrientation();

        boolean isDrawHorizontalDivider = true;
        boolean isDrawVerticalDivider = true;

        int extra = childCount % spanCount;

        extra = extra == 0 ? spanCount : extra;

        for(int i = 0; i < childCount; i++) {
            isDrawVerticalDivider = true;
            isDrawHorizontalDivider = true;
            // 如果是竖直方向，最右边一列不绘制竖直方向的间隔
            if(orientation == OrientationHelper.VERTICAL && (i + 1) % spanCount == 0) {
                isDrawVerticalDivider = false;
            }
            // 如果是竖直方向，最后一行不绘制水平方向间隔
            if(orientation == OrientationHelper.VERTICAL && i >= childCount - extra) {
                isDrawHorizontalDivider = false;
            }
            // 如果是水平方向，最下面一行不绘制水平方向的间隔
            if(orientation == OrientationHelper.HORIZONTAL && (i + 1) % spanCount == 0) {
                isDrawHorizontalDivider = false;
            }
            // 如果是水平方向，最后一列不绘制竖直方向间隔
            if(orientation == OrientationHelper.HORIZONTAL && i >= childCount - extra) {
                isDrawVerticalDivider = false;
            }
            if(isDrawHorizontalDivider) {
                drawHorizontalDivider(canvas, parent, i);
            }

            if(isDrawVerticalDivider) {
                drawVerticalDivider(canvas, parent, i);
            }
        }
    }
    /**
     * 绘制竖直间隔线
     * @param canvas
     * @param parent   父布局，RecyclerView
     * @param position item在父布局中所在的位置
     * */
    private void drawVerticalDivider(Canvas canvas, RecyclerView parent, int position) {
        final View child = parent.getChildAt(position);
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        final int top = child.getTop() - params.topMargin;
        final int bottom = child.getBottom() + params.bottomMargin + mDividerGray.getIntrinsicHeight() - mDividerRed.getIntrinsicHeight();
        final int left = child.getRight() + params.rightMargin;
        final int right = left + mDividerGray.getIntrinsicWidth();
        mDividerGray.setBounds(left, top, right, bottom);
        mDividerGray.draw(canvas);
    }

    /**
     * 绘制水平间隔线
     * @param canvas
     * @param parent   父布局，RecyclerView
     * @param position item在父布局中所在的位置
     * */
    private void drawHorizontalDivider(Canvas canvas, RecyclerView parent, int position) {
        final View child = parent.getChildAt(position);
        final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
                .getLayoutParams();
        final int top = child.getBottom() + params.bottomMargin;
        final int bottom = top + mDividerRed.getIntrinsicHeight();
        final int left = child.getLeft() - params.leftMargin;
        final int right = child.getRight() + params.rightMargin + mDividerRed.getIntrinsicWidth() - mDividerGray.getIntrinsicWidth();
        mDividerRed.setBounds(left, top, right, bottom);
        mDividerRed.draw(canvas);
    }



    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int spanCount = ((GridLayoutManager) parent.getLayoutManager()).getSpanCount();
        int orientation = ((GridLayoutManager)parent.getLayoutManager()).getOrientation();
        int position = parent.getChildLayoutPosition(view);
        if(orientation == OrientationHelper.VERTICAL && (position + 1) % spanCount == 0) {
            outRect.set(0, 0, 0, mDividerRed.getIntrinsicHeight());
            return;
        }

        if(orientation == OrientationHelper.HORIZONTAL && (position + 1) % spanCount == 0) {
            outRect.set(0, 0, mDividerGray.getIntrinsicWidth(), 0);
            return;
        }

        outRect.set(0, 0, mDividerGray.getIntrinsicWidth(), mDividerRed.getIntrinsicHeight());
    }
}
