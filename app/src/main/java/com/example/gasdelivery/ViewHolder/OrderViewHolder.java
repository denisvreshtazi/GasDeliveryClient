package com.example.gasdelivery.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.gasdelivery.R;

public class OrderViewHolder extends RecyclerView.ViewHolder  {
    public TextView txtOrderId, txtOrderStatus, txtOrderAddress, txtOrderTime, txtOrderPhone;

    public OrderViewHolder(View itemView) {
        super(itemView);
        //Assign order characteristics from order_layout
        txtOrderId = (TextView)itemView.findViewById(R.id.order_id);
        txtOrderStatus = (TextView)itemView.findViewById(R.id.order_status);
        txtOrderAddress = (TextView)itemView.findViewById(R.id.order_address);
        txtOrderTime = (TextView)itemView.findViewById(R.id.order_time_line);
        txtOrderPhone = (TextView)itemView.findViewById(R.id.order_phone);
    }
}
