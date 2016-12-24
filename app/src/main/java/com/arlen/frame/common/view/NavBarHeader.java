package com.arlen.frame.common.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.arlen.frame.R;
import com.arlen.frame.common.activity.ActivityManager;

/**
 * Created by Arlen on 2016/12/23 16:02.
 */
public class NavBarHeader extends LinearLayout{
    private LayoutInflater mInflater;

    private String mTitle;
    private String mRightText;
    private int mRightIcon;
    private int mBackIcon;
    private int mTitleColor;
    private int mRightTextColor;
    private int mBackgroundColor;
    private int mLineColor;
    private boolean mIsShowLine;

    private TextView mTvTitle;
    private TextView mTvRightText;
    private ImageView mIvBack;
    private ImageView mIvRight;
    private View mLine;
    private RelativeLayout mRvBg;
    private FrameLayout mFlContent;

    public NavBarHeader(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context,attrs);
    }

    private void init(Context context,AttributeSet attrs){
        initView(context);
        initStyleable(context,attrs);
    }

    private void initStyleable(Context context,AttributeSet attrs){
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.NavBar,0,0);
        mTitle = typedArray.getString(R.styleable.NavBar_titleText);
        mRightText = typedArray.getString(R.styleable.NavBar_rightText);
        mRightIcon = typedArray.getResourceId(R.styleable.NavBar_rightIcon,0);
        mBackIcon = typedArray.getResourceId(R.styleable.NavBar_backIcon,R.mipmap.ic_action_back);
        mTitleColor = typedArray.getColor(R.styleable.NavBar_titleColor,getResources().getColor(R.color.cl_26));
        mRightTextColor=typedArray.getColor(R.styleable.NavBar_rightTextColor,getResources().getColor(R.color.cl_e2));
        mBackgroundColor = typedArray.getColor(R.styleable.NavBar_backgroundColor,getResources().getColor(R.color.cl_ff));
        mLineColor = typedArray.getColor(R.styleable.NavBar_lineColor,getResources().getColor(R.color.line));
        mIsShowLine = typedArray.getBoolean(R.styleable.NavBar_isShowLine,true);
        initStyleableData();
    }

    private void initStyleableData(){
        setHeaderTitle(mTitle);
        setRightText(mRightText);
        setRightIcon(mRightIcon);
        setBackIcon(mBackIcon);
        setTitleColor(mTitleColor);
        setRightTextColor(mRightTextColor);
        setHeaderBackgroundColor(mBackgroundColor);
        setLineColor(mLineColor);
        setIsShowLine(mIsShowLine);
    }

    private void initView(Context context){
        mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.view_nav_bar,this,false);
        mTvTitle = (TextView) view.findViewById(R.id.tv_title);
        mTvRightText = (TextView) view.findViewById(R.id.tv_right);
        mIvBack  = (ImageView) view.findViewById(R.id.iv_back);
        mIvRight = (ImageView) view.findViewById(R.id.iv_right);
        mLine = view.findViewById(R.id.line);
        mRvBg = (RelativeLayout) view.findViewById(R.id.rv_nav_bar);
        mFlContent = (FrameLayout) view.findViewById(R.id.fl_content);
        addView(view);
    }

    public void setHeaderTitle(String text){
        mTvTitle.setText(text);
    }

    public void setTitleColor(int color){
        mTvTitle.setTextColor(color);
    }

    public void setRightText(String text){
        setRightText(text,null);
    }

    public void setRightText(String text, OnClickListener onClickListener){
        mTvRightText.setVisibility(TextUtils.isEmpty(text)?GONE:VISIBLE);
        mTvRightText.setText(text);
        if(onClickListener != null){
            mTvRightText.setOnClickListener(onClickListener);
        }
    }

    public void setRightTextColor(int color){
        mTvRightText.setTextColor(color);
    }

    public void setBackIcon(int icon){
        setBackIcon(icon,null);
    }

    public void setBackIcon(int icon, OnClickListener onClickListener){
        mIvBack.setVisibility(icon == 0?GONE:VISIBLE);
        mIvBack.setImageResource(icon);
        if(onClickListener == null){
            mIvBack.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityManager.getInstance().currentActivity().finish();
                }
            });
        }else{
            mIvBack.setOnClickListener(onClickListener);
        }
    }

    public void setHeaderBackgroundColor(int color){
        mRvBg.setBackgroundColor(color);
    }

    public void setRightIcon(int icon){
        setRightIcon(icon,null);
    }

    public void setRightIcon(int icon, OnClickListener onClickListener){
        mIvRight.setVisibility(icon == 0?GONE:VISIBLE);
        mIvRight.setImageResource(icon);
        if(onClickListener != null){
            mIvRight.setOnClickListener(onClickListener);
        }
    }

    public void setLineColor(int color){
        mLine.setBackgroundColor(color);
    }

    public void setIsShowLine(boolean show){
        mLine.setVisibility(show?VISIBLE:GONE);
    }

    public void setCustomContent(View view){
        if(view != null){
            mFlContent.addView(view);
        }
    }
}
