package com.graduation.even.graduationclient.activity;

import android.content.Intent;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.adapter.TicketAdapter;
import com.graduation.even.graduationclient.util.ToolbarUtil;

import java.util.List;

import static com.graduation.even.graduationclient.util.TimeUtil.getDayOfWeekInChinese;
import static com.graduation.even.graduationclient.util.TimeUtil.getTimeCustomFormatted;
import static com.graduation.even.graduationclient.util.TimeUtil.getTimeFormatted;

public class TicketShowActivity extends BaseActivity implements View.OnClickListener {

    // UI
    private TextView titleTv, datePromptTv;
    private Button setMonitorBtn;
    private RecyclerView ticketShowRv;
    private Toolbar mToolbar;

    private LinearLayoutManager mManager;
    private TicketAdapter mTicketAdapter;
    private List<String> mTicketList;//todo 票要展示的数据


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
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        titleTv = findViewById(R.id.tv_title);
        setMonitorBtn = findViewById(R.id.btn_set_monitor);
        datePromptTv = findViewById(R.id.tv_date_prompt);
        ticketShowRv = findViewById(R.id.rv_ticket_show);
    }

    @Override
    protected void initData() {
        // 获取list内容
        getTicketList();

        // 设置list
        mManager = new LinearLayoutManager(this);
        ticketShowRv.setLayoutManager(mManager);
        mTicketAdapter = new TicketAdapter(this,mTicketList);

        // 初始化界面UI等信息
        Intent intent = getIntent();
        String departure = intent.getStringExtra("departure");
        String destination = intent.getStringExtra("destination");
        long date = intent.getLongExtra("date", 0);
        Boolean isGD = intent.getBooleanExtra("isGD", false);

        titleTv.setText(departure + "——" + destination);
        String dateStr = getTimeFormatted(date, "MM月dd日");
        String week = "星期" + getDayOfWeekInChinese(date);
        datePromptTv.setText("选票日期： " + dateStr + " " + week);
    }

    @Override
    protected void initEvent() {
        setMonitorBtn.setOnClickListener(this);
        new ToolbarUtil().initToolbar(this, mToolbar);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_set_monitor:
                //todo
                break;
        }
    }

    //todo
    /* 联网获取票的信息 */
    private void getTicketList(){

    }
}
