package com.arlen.frame.view.account.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.arlen.frame.R;
import com.arlen.frame.common.utils.ImageUtil;
import com.arlen.frame.view.AppContext;
import com.arlen.frame.view.account.model.Account;
import com.arlen.frame.view.account.presenter.UserPresenter;
import com.arlen.frame.view.base.BaseActivity;

public class MainActivity extends BaseActivity<IUserView,UserPresenter> implements IUserView {

    private long mBackPressed;

    private ImageView mIvImage;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentViewAll(R.layout.activity_main);
        getPresenter().loadAccount();
    }

    @Override
    public UserPresenter initPresenter() {
        return new UserPresenter();
    }

    @Override
    public void initActivity() {
        setHeaderTitle("用户中心");
        mIvImage = (ImageView) findViewById(R.id.iv_image);
    }

    @Override
    public void showContentView(Account obj) {
        Log.d("tag", "showContentView: "+obj.toString());
        ImageUtil.load(this,"http://7xjpiw.com1.z0.glb.clouddn.com/u73960/avatar1586",mIvImage);
    }

    @Override
    public void onBackPressed() {
        if (mBackPressed + 2000 > System.currentTimeMillis()) {
            ((AppContext)AppContext.getAppContext()).exitApp();
            super.onBackPressed();
        } else {
            Toast.makeText(this, R.string.quit_app_text, Toast.LENGTH_SHORT).show();
            mBackPressed = System.currentTimeMillis();
        }
    }
}
