package com.graduation.even.graduationclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.graduation.even.graduationclient.R;
import com.graduation.even.graduationclient.net.bean.response.TicketShowResponse;

import java.util.List;

import static android.support.v4.content.ContextCompat.getColor;

/**
 * Created by Even on 2017/11/8.
 */

public class TicketAdapter extends RecyclerView.Adapter<TicketAdapter.MyViewHolder> {
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;
    private List<TicketShowResponse.Ticket> ticketList;
    private boolean isShowCheckBox;

    // 点击接口
    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    // 设置点击接口
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
    }

    public TicketAdapter(Context mContext, List<TicketShowResponse.Ticket> ticketList, boolean isShowCheckBox) {
        this.mContext = mContext;
        this.ticketList = ticketList;
        this.isShowCheckBox = isShowCheckBox;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                mContext).inflate(R.layout.adapter_ticket, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        // 如果设置了回调，则设置点击事件
        if (mOnItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = holder.getLayoutPosition();
                    mOnItemClickListener.onItemClick(holder.itemView, pos);
                }
            });
        }
        TicketShowResponse.Ticket ticket = ticketList.get(position);
        holder.start.setText(ticket.dptStationName);
        holder.startDate.setText(ticket.dptTime);
        holder.end.setText(ticket.arrStationName);
        holder.endDate.setText(ticket.arrTime);
        holder.trainNum.setText(ticket.trainNo);
        holder.interval.setText(ticket.interval);
        holder.price.setText(ticket.cheapestPrice);
        if (isShowCheckBox) {
            holder.selected.setVisibility(View.VISIBLE);
        }
        setSeats(holder, ticket);
    }

    @Override
    public int getItemCount() {
        return ticketList.size();
    }

    // 设置座位信息
    private void setSeats(final MyViewHolder holder, TicketShowResponse.Ticket ticket) {
        if (ticket.seats.business != null) {
            setSeatInfo(ticket.seats.business, holder.seatType1, "商务座");
        }
        if (ticket.seats.one != null) {
            setSeatInfo(ticket.seats.one, holder.seatType2, "一等座");
        }
        if (ticket.seats.two != null) {
            setSeatInfo(ticket.seats.two, holder.seatType3, "二等座");
        }
        if (ticket.seats.zero != null) {
            setSeatInfo(ticket.seats.zero, holder.seatType4, "无座");
        }

        if (ticket.seats.soft != null) {
            setSeatInfo(ticket.seats.soft, holder.seatType1, "软卧");
        }
        if (ticket.seats.hardSleep != null) {
            setSeatInfo(ticket.seats.hardSleep, holder.seatType2, "硬卧");
        }
        if (ticket.seats.hardSeat != null) {
            setSeatInfo(ticket.seats.hardSeat, holder.seatType3, "硬座");
        }

    }

    // 座位信息的UI设置
    private void setSeatInfo(TicketShowResponse.SeatInfo seatInfo, TextView seatType, String description) {
        int count = seatInfo.count;
        if (count < 10) {
            seatType.setText(description + count + "(抢)");
            seatType.setTextColor(getColor(mContext,R.color.orangeRed));
        } else if (count > 0) {
            seatType.setText(description + count);
            seatType.setTextColor(getColor(mContext,R.color.grayDarkDark));
        } else if (count >= 100) {
            seatType.setText(description + count + "+");
            seatType.setTextColor(getColor(mContext,R.color.grayDarkDark));
        }
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
