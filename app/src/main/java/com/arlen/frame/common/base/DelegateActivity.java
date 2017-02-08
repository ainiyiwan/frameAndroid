package com.arlen.frame.common.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.arlen.frame.R;
import com.arlen.frame.common.activity.ActivityManager;
import com.arlen.frame.common.view.NavBarHeader;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Arlen on 2016/12/23 17:48.
 */
public abstract class DelegateActivity extends FragmentActivity implements IBaseView {

    private View mEmptyView;
    private View mErrorView;
    private View mHeaderView;
    private View mContentView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityManager.getInstance().pushActivity(this);
    }

    private LinearLayout initCommonView(){
        LinearLayout linearLayout = new LinearLayout(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        linearLayout.setLayoutParams(params);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        return linearLayout;
    }

    private View initHeaderView(ViewGroup linearLayout){
        LayoutInflater inflater = LayoutInflater.from(this);
        mHeaderView = inflater.inflate(R.layout.view_nav_bar_header, linearLayout, false);
        return mHeaderView;
    }

    private View initEmptyView(ViewGroup linearLayout){
        LayoutInflater inflater = LayoutInflater.from(this);
        mEmptyView = inflater.inflate(R.layout.view_empty, linearLayout, false);
        mEmptyView.setVisibility(View.GONE);
        return mEmptyView;
    }

    private View initErrorView(ViewGroup linearLayout){
        LayoutInflater inflater = LayoutInflater.from(this);
        mErrorView = inflater.inflate(R.layout.view_net_error, linearLayout, false);
        mErrorView.setVisibility(View.GONE);
        return mErrorView;
    }

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        initActivity();
    }

    /**
     * 带头部
     * @param layoutResID
     */
    public void setContentViewHeader(int layoutResID){
        LinearLayout linearLayout = initCommonView();
        linearLayout.addView(initHeaderView(linearLayout));
        mContentView = LayoutInflater.from(this).inflate(layoutResID,linearLayout,false);
        linearLayout.addView(mContentView);
        super.setContentView(linearLayout);
        initActivity();
    }

    /**
     * 带头部、空视图、网络错误视图
     * @param layoutResID
     */
    public void setContentViewAll(int layoutResID){
        LinearLayout linearLayout = initCommonView();
        linearLayout.addView(initHeaderView(linearLayout));
        linearLayout.addView(initEmptyView(linearLayout));
        linearLayout.addView(initErrorView(linearLayout));
        mContentView = LayoutInflater.from(this).inflate(layoutResID,linearLayout,false);
        linearLayout.addView(mContentView);
        super.setContentView(linearLayout);
        initActivity();
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityManager.getInstance().popActivity(this);
    }

    public NavBarHeader getHeaderView(){
        if(mHeaderView == null){
            throw new RuntimeException("sorry,you hasn't add headerView!");
        }
        return (NavBarHeader) mHeaderView.findViewById(R.id.nav_bar_header);
    }

    public void setHeaderTitle(String text){
        getHeaderView().setHeaderTitle(text);
    }

    @Override
    public void showLoadingView() {

    }

    @Override
    public void showDataView() {
        if(mContentView == null){
            throw new RuntimeException("sorry,you hasn't add contentView!");
        }
        mContentView.setVisibility(View.VISIBLE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showEmptyView(){
       if(mEmptyView == null){
            throw new RuntimeException("sorry,you hasn't add emptyView!");
       }
        mContentView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.VISIBLE);
        mErrorView.setVisibility(View.GONE);
    }

    @Override
    public void showErrorView(){
        if(mErrorView == null){
            throw new RuntimeException("sorry,you hasn't add errorView!");
        }
        mContentView.setVisibility(View.GONE);
        mEmptyView.setVisibility(View.GONE);
        mErrorView.setVisibility(View.VISIBLE);
    }

    public abstract void initActivity();
}
