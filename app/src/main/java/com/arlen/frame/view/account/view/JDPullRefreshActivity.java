package com.arlen.frame.view.account.view;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;

import com.arlen.frame.R;
import com.arlen.frame.common.thirdsdk.share.ShareController;
import com.arlen.frame.common.base.BaseRecycleAdapter;
import com.arlen.frame.common.base.BaseViewHolder;
import com.arlen.frame.common.base.DelegateActivity;
import com.aspsine.swipetoloadlayout.OnLoadMoreListener;
import com.aspsine.swipetoloadlayout.OnRefreshListener;
import com.aspsine.swipetoloadlayout.SwipeToLoadLayout;

import java.util.ArrayList;
import java.util.List;

import cn.sharesdk.onekeyshare.model.ShareContent;

/**
 * Created by Arlen on 2016/12/27 18:17.
 */
public class JDPullRefreshActivity extends DelegateActivity implements OnRefreshListener, OnLoadMoreListener {

    private SwipeToLoadLayout swipeToLoadLayout;
    private RecyclerView recyclerView;
    List<String> list = new ArrayList<>();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pullrefresh);

    }

    @Override
    public void initActivity() {
        for(int i = 0;i<10;i++){
            list.add(i+"");
        }
        swipeToLoadLayout = (SwipeToLoadLayout) findViewById(R.id.swipeToLoadLayout);
        recyclerView = (RecyclerView) findViewById(R.id.swipe_target);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        swipeToLoadLayout.initTargetView(recyclerView);
        swipeToLoadLayout.setOnRefreshListener(this);
        swipeToLoadLayout.setOnLoadMoreListener(this);
//        RequestLocalClient requestLocalClient = new RequestLocalClient(this, new LocalListener() {
//            @Override
//            public void callBack(BDLocation bdLocation) {
//                Log.d("tag", "callBack: "+bdLocation.getCity());
////                ToastUtils.toastShort(bdLocation.getCity());
//            }
//        });
//        requestLocalClient.startLocal();
//        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//                if (newState == RecyclerView.SCROLL_STATE_IDLE ){
//                    if (!ViewCompat.canScrollVertically(recyclerView, 1)){
//                        swipeToLoadLayout.setLoadingMore(true);
//                    }
//                }
//            }
//        });
        BaseRecycleAdapter adapter = new BaseRecycleAdapter<String>(this,R.layout.item_view) {
            @Override
            public void BindViewHolder(BaseViewHolder holder, String item, int position) {
                TextView tv = holder.getView(R.id.tv_title);
                tv.setText(item);
            }
        };
        adapter.setOnItemClickListener(new BaseRecycleAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int var1) {
                ShareContent shareContent = new ShareContent();
                shareContent.setTitle("wdd专用");
                shareContent.setContent("那就这有吧还有什么好说的");
                shareContent.setLink("http://www.baidu.com");
                shareContent.setIconUrl("http://www.baidu.com");
                ShareController.getInstance().showShare(JDPullRefreshActivity.this,shareContent);
            }
        });
        recyclerView.setAdapter(adapter);
        adapter.addAll(list);
    }

    @Override
    public void onRefresh() {
        Log.d("tag", "onRefresh: "+1);
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setRefreshing(false);
            }
        }, 30000);
    }

    @Override
    public void onLoadMore() {
        Log.d("tag", "onLoadMore: ");
        swipeToLoadLayout.postDelayed(new Runnable() {
            @Override
            public void run() {
                swipeToLoadLayout.setLoadingMore(false);
            }
        }, 3000);
    }
}
