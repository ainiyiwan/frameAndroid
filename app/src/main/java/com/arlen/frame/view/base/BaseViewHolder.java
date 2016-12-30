package com.arlen.frame.view.base;

/**
 * Created by Arlen on 2016/12/29 17:23.
 */

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * Created by Arlen on 2016/7/18 15:40.
 */
public class BaseViewHolder extends RecyclerView.ViewHolder {
    private SparseArray<View> sparseArray = new SparseArray();

    public BaseViewHolder(View itemView) {
        super(itemView);
    }

    public <T extends View> T getView(int viewId) {
        View view = this.sparseArray.get(viewId);
        if(view == null) {
            view = this.itemView.findViewById(viewId);
            this.sparseArray.put(viewId, view);
        }

        return (T) view;
    }

    public BaseViewHolder setOnClickListener(int viewId, View.OnClickListener listener) {
        View view = this.getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public BaseViewHolder setOnTouchListener(int viewId, View.OnTouchListener listener) {
        View view = this.getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }
}
