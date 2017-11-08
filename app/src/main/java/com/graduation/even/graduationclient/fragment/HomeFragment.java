package com.graduation.even.graduationclient.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.fourmob.datetimepicker.date.DatePickerDialog;
import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.activity.TicketShowActivity;
import com.graduation.even.graduationclient.util.PLog;
import com.graduation.even.graduationclient.util.TimeUtil;
import com.graduation.even.graduationclient.util.ToastUtil;
import com.zaaach.citypicker.CityPickerActivity;

import java.util.Calendar;
import java.util.Date;

import static android.app.Activity.RESULT_OK;
import static com.graduation.even.graduationclient.util.TimeUtil.dateToStamp;

/**
 * Created by Even on 2017/11/2.
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    // UI
    private TextView startTv, endTv;
    private Button exchangeBtn, dateBtn, queryBtn;
    private CheckBox isGDCB;
    private Boolean isGD;

    //date&time
    public static final String DATE_PICKER_TAG = "date_picker";
    private final Calendar calendar = Calendar.getInstance();
    private DatePickerDialog datePickerDialog;
    private String date;

    //map
    private static final int REQUEST_CODE_START_CITY = 0;
    private static final int REQUEST_CODE_END_CITY = 1;

    @Override
    int getResourceId() {
        return R.layout.fragment_home;
    }

    @Override
    void initConfigure() {

    }

    @Override
    void initView(View view) {
        startTv = view.findViewById(R.id.tv_start);
        endTv = view.findViewById(R.id.tv_end);
        exchangeBtn = view.findViewById(R.id.btn_exchange);
        dateBtn = view.findViewById(R.id.btn_date);
        queryBtn = view.findViewById(R.id.btn_query);
        isGDCB = view.findViewById(R.id.cb_isGD);
    }

    @Override
    void initData() {
        String currentDate = TimeUtil.getTimeFormatted(new Date(), "MM月dd日");
        PLog.i("currentDate = " + currentDate);
        date = "2017年" + currentDate;
        dateBtn.setText("出发时间：" + currentDate);
    }

    @Override
    void initEvent() {
        startTv.setOnClickListener(this);
        endTv.setOnClickListener(this);
        exchangeBtn.setOnClickListener(this);
        dateBtn.setOnClickListener(this);
        queryBtn.setOnClickListener(this);
        isGDCB.setOnClickListener(this);

        datePickerDialog = DatePickerDialog.newInstance(this,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH),
                false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_start:
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                        REQUEST_CODE_START_CITY);
                break;
            case R.id.tv_end:
                startActivityForResult(new Intent(getActivity(), CityPickerActivity.class),
                        REQUEST_CODE_END_CITY);
                break;
            case R.id.btn_exchange:
                String start = startTv.getText().toString();
                String end = endTv.getText().toString();
                startTv.setText(end);
                endTv.setText(start);
                break;
            case R.id.btn_date:
                selectDate();
                break;
            case R.id.btn_query:
                start = startTv.getText().toString();
                end = endTv.getText().toString();
                if("出发地".equals(start) || "目的地".equals(end)){
                    ToastUtil.showToast(getActivity(),"请先选择出发地和目的地");
                    return;
                }
                queryTicket();
                break;
            case R.id.cb_isGD:
                break;
        }
    }

    //城市选择返回结果
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_CODE_START_CITY:
                    if (data != null) {
                        String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                        startTv.setText(city);
                    }
                    break;
                case REQUEST_CODE_END_CITY:
                    if (data != null) {
                        String city = data.getStringExtra(CityPickerActivity.KEY_PICKED_CITY);
                        endTv.setText(city);
                    }
                    break;
            }
        }
    }

    //日期选择
    @Override
    public void onDateSet(DatePickerDialog datePickerDialog, int year, int month, int day) {
        month++;
        String monthStr = String.valueOf(month);
        String dayStr = String.valueOf(day);
        if (month < 10) {
            monthStr = "0" + month;
        }
        if (day < 10) {
            dayStr = "0" + day;
        }
        date = year + "年" + monthStr + "月" + dayStr + "日";
        dateBtn.setText("出发时间：" + monthStr + "月" + dayStr + "日");
    }

    //打开日期选择框
    private void selectDate() {
        datePickerDialog.setVibrate(false);
        datePickerDialog.setYearRange(2017, 2030);
        datePickerDialog.setCloseOnSingleTapDay(false);
        datePickerDialog.show(getActivity().getSupportFragmentManager(), DATE_PICKER_TAG);
    }

    /* 查询票请求 */
    private void queryTicket() {
        String departure = startTv.getText().toString();
        String destination = endTv.getText().toString();
        long date = dateToStamp(this.date,"yyyy年MM月dd日");
        isGD = isGDCB.isChecked();

        Intent intent = new Intent(getActivity(), TicketShowActivity.class);
        intent.putExtra("departure", departure);
        intent.putExtra("destination", destination);
        intent.putExtra("date", date);//传入时间戳 long
        intent.putExtra("isGD", isGD);
        startActivity(intent);
    }

}
