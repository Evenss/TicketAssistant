package com.graduation.even.graduationclient.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.adapter.OrderInfoAdapter;
import com.graduation.even.graduationclient.net.bean.response.MyOrderResponse;
import com.graduation.even.graduationclient.net.callback.NetCallBack;
import com.graduation.even.graduationclient.net.connector.NetworkConnector;
import com.graduation.even.graduationclient.util.PLog;
import com.graduation.even.graduationclient.util.ToastUtil;
import com.graduation.even.graduationclient.util.ToolbarUtil;

import java.util.ArrayList;
import java.util.List;

public class OrderInfoActivity extends BaseActivity {
    private RecyclerView orderInfoRv;
    private Toolbar mToolbar;

    private LinearLayoutManager mManager;
    private OrderInfoAdapter orderInfoAdapter;
    private NetworkConnector mNetworkConnector;
    private List<MyOrderResponse.Order> mOrderList;

    private int mCurrentPage = 1;
    private boolean mIsLastPage = false;
    private static final int PAGE_SIZE = 10;
    private String mDeparture;
    private String mDestination;
    private long mDate;

    @Override
    protected boolean forceScreenOrientationPortrait() {
        return false;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_order_info;
    }

    @Override
    protected void initView() {
        orderInfoRv = findViewById(R.id.rv_order_info);
        mToolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void initData() {
        // 初始化网络请求工具
        mNetworkConnector = NetworkConnector.getInstance();

        // 初始化list
        mOrderList = new ArrayList<>();
        getOrderList();

        // 设置list
        mManager = new LinearLayoutManager(this);
        orderInfoRv.setLayoutManager(mManager);
        orderInfoAdapter = new OrderInfoAdapter(this, mOrderList);
        orderInfoRv.setAdapter(orderInfoAdapter);
    }

    @Override
    protected void initEvent() {
        new ToolbarUtil().initToolbar(this, mToolbar);
        // 上拉加载
        orderInfoRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = mManager.findLastVisibleItemPosition();
                    if (lastVisiblePosition >= mManager.getItemCount() - 1) {
                        getMoreOrder();
                    }
                }
            }
        });
    }

    /* 获取自己订阅票信息 */
    private void getOrderList() {
        mNetworkConnector.getOrderList(PAGE_SIZE, mCurrentPage, new NetCallBack() {
            @Override
            public void onTokenInvalid() {
                ToastUtil.showToastOnUIThread(OrderInfoActivity.this, "登录信息已过期，请重新登录");
            }

            @Override
            public void onNetworkError() {
                ToastUtil.showToastOnUIThread(OrderInfoActivity.this, "网络错误");
            }

            @Override
            public void onFailed(String error) {
                ToastUtil.showToastOnUIThread(OrderInfoActivity.this, error);
            }

            @Override
            public void onSuccess(Object object) {
                MyOrderResponse.Data orderData = (MyOrderResponse.Data) object;
                mOrderList.addAll(orderData.list);
                mIsLastPage = orderData.lastPage;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        orderInfoAdapter.notifyDataSetChanged();

                    }
                });
            }
        });
    }

    // 上拉加载
    private void getMoreOrder() {
        PLog.i("load more order");
        if (mIsLastPage) {
            ToastUtil.showToast(this, "没有更多的数据了");
        } else {
            mCurrentPage++;
            PLog.i("current page = " + mCurrentPage);
            getOrderList();
        }
    }
}
