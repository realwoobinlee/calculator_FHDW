package de.fhdw.hackstreetboys.calculator;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

// WOOBIN LEE
// MATR.NR. 9715750
public class SpaceItemDecorator extends RecyclerView.ItemDecoration {

    private int Left, Top, Right, Bottom;

    public SpaceItemDecorator(int left,int top,int right,int bottom) {
        this.Left = left;
        this.Top = top;
        this.Right = right;
        this.Bottom = bottom;
    }

    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = this.Left;
        outRect.top = this.Top;
        outRect.right = this.Right;
        outRect.bottom = this.Bottom;
    }
}
