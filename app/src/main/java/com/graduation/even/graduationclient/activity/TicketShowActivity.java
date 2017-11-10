package com.graduation.even.graduationclient.activity;

import android.content.Intent;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.adapter.TicketAdapter;
import com.graduation.even.graduationclient.net.bean.response.TicketShowResponse;
import com.graduation.even.graduationclient.net.callback.NetCallBack;
import com.graduation.even.graduationclient.net.connector.NetworkConnector;
import com.graduation.even.graduationclient.util.PLog;
import com.graduation.even.graduationclient.util.ToastUtil;
import com.graduation.even.graduationclient.util.ToolbarUtil;

import java.util.ArrayList;
import java.util.List;

import static com.graduation.even.graduationclient.util.TimeUtil.getDayOfWeekInChinese;
import static com.graduation.even.graduationclient.util.TimeUtil.getTimeCustomFormatted;
import static com.graduation.even.graduationclient.util.TimeUtil.getTimeFormatted;

public class TicketShowActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    // UI
    private TextView titleTv, datePromptTv;
    private Button setMonitorBtn;
    private RecyclerView ticketShowRv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar mToolbar;

    private LinearLayoutManager mManager;
    private TicketAdapter mTicketAdapter;
    private NetworkConnector mNetworkConnector;
    private List<TicketShowResponse.Ticket> mTicketList;

    private int mCurrentPage = 1;
    private boolean mIsLastPage = false;
    private static final int PAGE_SIZE = 10;
    private String mDeparture;
    private String mDestination;
    private long mDate;
    private Boolean mIsGD;

    @Override
    protected boolean forceScreenOrientationPortrait() {
        return false;
    }

    @Override
    protected int getResourceId() {
        return R.layout.activity_ticket_show;
    }

    @Override
    protected void initView() {
        mToolbar = findViewById(R.id.toolbar);
        titleTv = findViewById(R.id.tv_title);
        setMonitorBtn = findViewById(R.id.btn_set_monitor);
        datePromptTv = findViewById(R.id.tv_date_prompt);
        ticketShowRv = findViewById(R.id.rv_ticket_show);
        swipeRefreshLayout = findViewById(R.id.swipe_layout);
    }

    @Override
    protected void initData() {
        // 初始化界面UI等信息
        Intent intent = getIntent();
        mDeparture = intent.getStringExtra("departure");
        mDestination = intent.getStringExtra("destination");
        mDate = intent.getLongExtra("date", 0);
        mIsGD = intent.getBooleanExtra("isGD", false);

        titleTv.setText(mDeparture + "——" + mDestination);
        String dateStr = getTimeFormatted(mDate, "MM月dd日");
        String week = "星期" + getDayOfWeekInChinese(mDate);
        datePromptTv.setText("选票日期： " + dateStr + " " + week);

        // 初始化网络请求工具
        mNetworkConnector = NetworkConnector.getInstance();

        // 获取list内容
        mTicketList = new ArrayList<>();
        getTicketList();

        // 设置list
        mManager = new LinearLayoutManager(this);
        ticketShowRv.setLayoutManager(mManager);
        mTicketAdapter = new TicketAdapter(this, mTicketList,false);


    }

    @Override
    protected void initEvent() {
        setMonitorBtn.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        new ToolbarUtil().initToolbar(this, mToolbar);

        // 上拉加载
        ticketShowRv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    int lastVisiblePosition = mManager.findLastVisibleItemPosition();
                    if (lastVisiblePosition >= mManager.getItemCount() - 1) {
                        getMoreTicket();
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_set_monitor:
                //todo
                break;
        }
    }

    // 下拉刷新
    @Override
    public void onRefresh() {
        PLog.i("onRefresh");
        mCurrentPage = 1;
        getTicketList();
    }

    /* 联网获取票的信息 */
    private void getTicketList() {
        mNetworkConnector.getTicketList(mDeparture, mDestination,
                mDate, mIsGD, PAGE_SIZE, mCurrentPage, new NetCallBack() {
                    @Override
                    public void onNetworkError() {
                        ToastUtil.showToastOnUIThread(TicketShowActivity.this, "网络错误");
                    }

                    @Override
                    public void onFailed(String error) {
                        ToastUtil.showToastOnUIThread(TicketShowActivity.this, error);
                    }

                    @Override
                    public void onSuccess(Object object) {
                        TicketShowResponse.Data ticketData = (TicketShowResponse.Data) object;
                        mTicketList.addAll(ticketData.list);
                        mIsLastPage = ticketData.lastPage;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mTicketAdapter.notifyDataSetChanged();
                                swipeRefreshLayout.setRefreshing(false);
                            }
                        });
                    }
                });
    }

    // 上拉加载
    private void getMoreTicket() {
        PLog.i("load more ticket");
        if (mIsLastPage) {
            ToastUtil.showToast(this, "没有更多的数据了");
        } else {
            mCurrentPage++;
            PLog.i("current page = " + mCurrentPage);
            getTicketList();
        }
    }

}
