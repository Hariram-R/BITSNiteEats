package com.ram.hari.android.bitsniteeats;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class MyOrdersAdapter extends RecyclerView.Adapter<MyOrdersAdapter.OrdersVH> {

    private ArrayList<Order> orderList;
    private Context context;

    public MyOrdersAdapter(ArrayList<Order> orderList, Context context) {
        this.orderList = orderList;
        this.context = context;
    }

    @NonNull
    @Override
    public OrdersVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new OrdersVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_order,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersVH ordersVH, int i) {
        ordersVH.populateOrder(orderList.get(i));
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public class OrdersVH extends RecyclerView.ViewHolder
    {
        TextView OrderNum,Ordertime,OrderItems,price;

        public OrdersVH(@NonNull View itemView) {
            super(itemView);
            OrderNum = itemView.findViewById(R.id.OrderNo);
            Ordertime = itemView.findViewById(R.id.timestamp);
            OrderItems = itemView.findViewById(R.id.OrderItems);
            price = itemView.findViewById(R.id.price);
        }

        void populateOrder(Order order)
        {
            OrderNum.setText(order.getNumber());
            OrderItems.setText(order.getItems());
            Ordertime.setText(order.getTimestamp());
            price.setText(new StringBuilder(order.getPrice()+""));
        }
    }
}
