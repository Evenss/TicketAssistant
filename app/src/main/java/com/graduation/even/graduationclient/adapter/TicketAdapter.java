package com.graduation.even.graduationclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.net.bean.response.TicketShowResponse;

import java.util.List;

/**
 * Created by Even on 2017/11/8.
 */

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {
    private Context context;
    private List<TicketShowResponse.Ticket> mData;//todo 票要展示的数据

    public TicketAdapter(Context context, List<TicketShowResponse.Ticket> mData) {
        this.context = context;
        this.mData = mData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.adapter_ticket, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView start;
        private TextView startDate;
        private TextView end;
        private TextView endDate;
        private TextView trainNum;
        private TextView interval;
        private TextView price;
        private CheckBox selected;
        private TextView seatType1;
        private TextView seatType2;
        private TextView seatType3;
        private TextView seatType4;

        TextView mTvName, mTvTel, mTvTime, mTvDesc;
        RelativeLayout mRelativeLayout;

        public MyViewHolder(View view) {
            super(view);
            initView(view);
        }

        public void initView(View convertView) {
            start = (TextView) convertView.findViewById(R.id.tv_start);
            startDate = (TextView) convertView.findViewById(R.id.tv_start_date);
            end = (TextView) convertView.findViewById(R.id.tv_end);
            endDate = (TextView) convertView.findViewById(R.id.tv_end_date);
            trainNum = (TextView) convertView.findViewById(R.id.tv_train_num);
            interval = (TextView) convertView.findViewById(R.id.tv_interval);
            price = (TextView) convertView.findViewById(R.id.tv_price);
            selected = (CheckBox) convertView.findViewById(R.id.cb_selected);

            seatType1 = (TextView) convertView.findViewById(R.id.tv_seat_type1);
            seatType2 = (TextView) convertView.findViewById(R.id.tv_seat_type2);
            seatType3 = (TextView) convertView.findViewById(R.id.tv_seat_type3);
            seatType4 = (TextView) convertView.findViewById(R.id.tv_seat_type4);
        }
    }
}
