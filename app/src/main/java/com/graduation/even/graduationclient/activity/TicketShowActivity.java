package com.graduation.even.graduationclient.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.support.design.widget.FloatingActionButton;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.graduation.even.graduationclient.util.TimeUtil.getDayOfWeekInChinese;
import static com.graduation.even.graduationclient.util.TimeUtil.getTimeCustomFormatted;
import static com.graduation.even.graduationclient.util.TimeUtil.getTimeFormatted;

public class TicketShowActivity extends BaseActivity implements View.OnClickListener, SwipeRefreshLayout.OnRefreshListener {

    private TextView titleTv, datePromptTv;
    private Button setMonitorBtn;
    private RecyclerView ticketShowRv;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Toolbar mToolbar;
    private FloatingActionButton submitBtn;

    private LinearLayoutManager mManager;
    private TicketAdapter mTicketAdapter;
    private NetworkConnector mNetworkConnector;
    private List<TicketShowResponse.Ticket> mTicketList;
    private List<TicketShowResponse.Ticket> mTicketListSelected;//需要提交的车次list
    private ArrayList<String> mTypesSubmit;//需要提交的座位类型
    private ArrayList<String> mTrainNoSubmit;//需要提交的车次

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
        submitBtn = findViewById(R.id.btn_submit);
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

        // 初始化list
        mTicketList = new ArrayList<>();
        mTicketListSelected = new ArrayList<>();
        mTypesSubmit = new ArrayList<>();
        mTrainNoSubmit = new ArrayList<>();
        getTicketList();

        // 设置list
        mManager = new LinearLayoutManager(this);
        ticketShowRv.setLayoutManager(mManager);
        mTicketAdapter = new TicketAdapter(this, mTicketList, false);
        ticketShowRv.setAdapter(mTicketAdapter);
    }

    @Override
    protected void initEvent() {
        setMonitorBtn.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        new ToolbarUtil().initToolbar(this, mToolbar);

        // 设置list点击事件
        mTicketAdapter.setOnItemClickListener(new TicketAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, boolean isAdd) {
                PLog.i("adapter click position = " + position);
                if (isAdd) {
                    mTicketListSelected.add(mTicketList.get(position));
                } else {
                    mTicketListSelected.remove(mTicketList.get(position));
                }
                mTicketAdapter.notifyDataSetChanged();
            }
        });

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
                Button btn = (Button) view;
                if ("抢票".equals(btn.getText().toString())) {
                    btn.setText("取消");
                    mTicketAdapter.setShowCheckBox(true);
                    mTicketAdapter.notifyDataSetChanged();
                    // 显示悬浮按
                    submitBtn.setVisibility(View.VISIBLE);
                } else {
                    btn.setText("抢票");
                    mTicketAdapter.setShowCheckBox(false);
                    mTicketAdapter.notifyDataSetChanged();
                    mTicketListSelected.clear();
                    // 显示隐藏按钮
                    submitBtn.setVisibility(View.GONE);
                }
                break;
            case R.id.btn_submit:
                createSeatsDialog();
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
                    public void onTokenInvalid() {
                        ToastUtil.showToastOnUIThread(TicketShowActivity.this, "登录信息已过期，请重新登录");
                    }

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

    /* 票余量监听 */
    private void setMonitor() {
        getTrainNo();
        mNetworkConnector.setMonitor(mDeparture, mDestination, mDate, mTrainNoSubmit, mTypesSubmit,
                new NetCallBack() {
                    @Override
                    public void onTokenInvalid() {
                        ToastUtil.showToastOnUIThread(TicketShowActivity.this, "登录信息已过期，请重新登录");
                    }

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
                        // todo 提示修改方式修改一下
                        ToastUtil.showToastOnUIThread(TicketShowActivity.this, "开始监控票余量");
                        // 清空数据
                        clearData();
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

    // 创建座位类型选择框
    private void createSeatsDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("选择要提醒的座位类型");
        builder.setCancelable(false);
        final ArrayList<String> typeList = new ArrayList<>();
        if (checkGDType()) {
            typeList.add("商务座");
            typeList.add("一等座");
            typeList.add("二等座");
        }
        if (checkNormalType()) {
            typeList.add("软卧");
            typeList.add("硬卧");
            typeList.add("硬座");
            typeList.add("无座");
        }

        builder.setMultiChoiceItems(typeList.toArray(new String[0]), null,
                new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        String seatName = getSeatType(typeList.get(which));
                        if (isChecked) {
                            mTypesSubmit.add(seatName);
                        } else {
                            for (int i = 0; i < mTypesSubmit.size(); i++) {
                                if (mTypesSubmit.get(i).equals(seatName)) {
                                    mTypesSubmit.remove(i);
                                }
                            }
                        }
                    }
                });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setMonitor();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mTypesSubmit.clear();

            }
        });
        builder.show();
    }

    // 动态展示车型
    private boolean checkGDType() {
        for (int i = 0; i < mTicketListSelected.size(); i++) {
            String str = mTicketListSelected.get(i).trainNo.substring(0, 1);
            if (str.equals("G") || str.equals("D")) {
                return true;
            }
        }
        return false;
    }

    private boolean checkNormalType() {
        for (int i = 0; i < mTicketListSelected.size(); i++) {
            String str = mTicketListSelected.get(i).trainNo.substring(0, 1);
            if (str.equals("Z") || str.equals("T") || str.equals("K")) {
                return true;
            }
        }
        return false;
    }

    // 获取车次
    private void getTrainNo() {
        for (int i = 0; i < mTicketListSelected.size(); i++) {
            mTrainNoSubmit.add(mTicketListSelected.get(i).trainNo);
        }
    }

    // 获取车次类型
    private String getSeatType(String seatName) {
        switch (seatName) {
            case "商务座":
                return "business";
            case "一等座":
                return "one";
            case "二等座":
                return "two";
            case "软卧":
                return "soft";
            case "硬卧":
                return "hardSleep";
            case "硬座":
                return "hardSeat";
            case "无座":
                return "zero";
            default:
                return "";
        }
    }

    // 清空数据
    private void clearData() {
        mTicketListSelected.clear();
        mTypesSubmit.clear();
        mTrainNoSubmit.clear();
        mCurrentPage = 1;
        mIsLastPage = false;
    }
}
