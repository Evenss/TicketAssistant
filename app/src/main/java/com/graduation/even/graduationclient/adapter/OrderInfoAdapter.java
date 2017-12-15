package com.graduation.even.graduationclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.net.bean.response.MyOrderResponse;
import com.graduation.even.graduationclient.util.TimeUtil;

import java.util.List;

/**
 * Created by Even on 2017/11/13.
 */

public class OrderInfoAdapter extends RecyclerView.Adapter<OrderInfoAdapter.MyViewHolder> {

    private Context mContext;
    private List<MyOrderResponse.Order> orderList;

    public OrderInfoAdapter(Context context, List<MyOrderResponse.Order> orderList) {
        this.mContext = context;
        this.orderList = orderList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.adapter_order_info, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MyOrderResponse.Order order = orderList.get(position);
        holder.placeTv.setText(order.dptStationName + " — " + order.arrStationName);
        holder.priceTv.setText("￥" + String.valueOf(order.price));
        holder.priceTv.setTextSize(20);
        String time = TimeUtil.getTimeFormatted(order.startDate, "yyyy-MM-dd HH:mm");
        holder.timeTv.setText("出发时间：" + time);
        holder.trainNumTv.setText(order.trainNo.toString());
        if (order.state == 1) {
            holder.stateTv.setText("已完成");
        } else {
            holder.stateTv.setText("票量监控中");
            holder.priceTv.setText("");
        }
        if(0 == order.price && 1 == order.state){
            holder.priceTv.setText("订单已超期");
            holder.priceTv.setTextSize(14);
            holder.stateTv.setText("");
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView placeTv, trainNumTv, timeTv, priceTv, stateTv;

        public MyViewHolder(View view) {
            super(view);
            initView(view);
        }

        public void initView(View convertView) {
            placeTv = convertView.findViewById(R.id.tv_place);
            trainNumTv = convertView.findViewById(R.id.tv_train_num);
            timeTv = convertView.findViewById(R.id.tv_time);
            priceTv = convertView.findViewById(R.id.tv_price);
            stateTv = convertView.findViewById(R.id.tv_state);
        }
    }
}
