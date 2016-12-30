package com.arlen.frame.view.base;

/**
 * Created by Arlen on 2016/12/29 17:26.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecycleAdapter<T> extends Adapter<BaseViewHolder> {

    public interface MultiItemType<T> {
        int getLayoutId(int var1);

        int getLayoutViewType(int var1, T var2);
    }

    private Context context;
    private List<T> tList;
    private int itemLayoutId;
    private MultiItemType<T> multiItemType;
    private BaseRecycleAdapter.OnItemClickListener listener;
    private BaseRecycleAdapter.OnItemLongClickListener onItemLongClickListener;

    public void setOnItemClickListener(BaseRecycleAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setOnItemLongClickListener(BaseRecycleAdapter.OnItemLongClickListener onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    public BaseRecycleAdapter(Context context, int layoutResId) {
        this.context = context;
        this.tList = new ArrayList();
        this.itemLayoutId = layoutResId;
    }

    protected BaseRecycleAdapter(Context context, List<T> tList, int itemLayoutId) {
        this.context = context;
        this.tList = tList;
        this.itemLayoutId = itemLayoutId;
    }

    protected BaseRecycleAdapter(Context context, MultiItemType<T> multiItemType) {
        this.context = context;
        this.tList = new ArrayList();
        this.multiItemType = multiItemType;
    }

    protected BaseRecycleAdapter(Context context, List<T> tList, MultiItemType<T> multiItemType) {
        this.context = context;
        this.tList = tList == null?new ArrayList():new ArrayList(tList);
        this.multiItemType = multiItemType;
    }

    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if(this.multiItemType != null) {
            int layoutId = this.multiItemType.getLayoutId(viewType);
            view = LayoutInflater.from(this.context).inflate(layoutId, parent, false);
        } else {
            view = LayoutInflater.from(this.context).inflate(this.itemLayoutId, parent, false);
        }

        return new BaseViewHolder(view);
    }

    public int getItemCount() {
        return null == this.tList?0:this.tList.size();
    }

    public int getItemViewType(int position) {
        return this.multiItemType != null?this.multiItemType.getLayoutViewType(position, this.getItem(position)):super.getItemViewType(position);
    }

    public T getItem(int position) {
        return position >= this.tList.size()?null:this.tList.get(position);
    }

    public void onBindViewHolder(BaseViewHolder holder, final int position) {
        holder.itemView.setTag(Integer.valueOf(position));
        holder.itemView.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                if(BaseRecycleAdapter.this.listener != null) {
                    BaseRecycleAdapter.this.listener.onItemClick(position);
                }

            }
        });
        holder.itemView.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View v) {
                if(BaseRecycleAdapter.this.onItemLongClickListener != null) {
                    BaseRecycleAdapter.this.onItemLongClickListener.onItemLongClick(position);
                }

                return true;
            }
        });
        T item = this.tList.get(position);
        this.BindViewHolder(holder, item, position);
    }

    public abstract void BindViewHolder(BaseViewHolder holder, T item, int position);

    public void add(T elem) {
        this.tList.add(elem);
        this.notifyDataSetChanged();
    }

    public void addAll(List<T> elem) {
        this.tList.addAll(elem);
        this.notifyDataSetChanged();
    }

    public void set(T oldElem, T newElem) {
        this.set(this.tList.indexOf(oldElem), newElem);
    }

    public void set(int index, T elem) {
        this.tList.set(index, elem);
        this.notifyDataSetChanged();
    }

    public void remove(T elem) {
        this.tList.remove(elem);
        this.notifyDataSetChanged();
    }

    public void remove(int index) {
        this.tList.remove(index);
        this.notifyDataSetChanged();
    }

    public void replaceAll(List<T> elem) {
        this.tList.clear();
        this.tList.addAll(elem);
        this.notifyDataSetChanged();
    }

    public boolean contains(T elem) {
        return this.tList.contains(elem);
    }

    public void clear() {
        this.tList.clear();
        this.notifyDataSetChanged();
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(int var1);
    }

    public interface OnItemClickListener {
        void onItemClick(int var1);
    }
}