package com.ram.hari.android.bitsniteeats;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MyOrders extends Fragment {


    RecyclerView Orders;
    DatabaseReference databaseReference;
    ArrayList<Order> orderList;

    public MyOrders() {
    }


    public static MyOrders newInstance(String param1, String param2) {
        MyOrders fragment = new MyOrders();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_orders, container, false);
        Orders = view.findViewById(R.id.OrdersRV);
        Orders.setLayoutManager(new LinearLayoutManager(getContext()));

        orderList = new ArrayList<>();
        final MyOrdersAdapter ordersAdapter = new MyOrdersAdapter(orderList,getContext());

        databaseReference = FirebaseDatabase.getInstance().getReference("orders");
        databaseReference.keepSynced(true);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child:dataSnapshot.getChildren())
                {
                    Order order = child.getValue(Order.class);
//                    if(getActivity().getIntent().getStringExtra("Email").equalsIgnoreCase(order.getUserEmail()))
                    //{
                        orderList.add(order);
                    //}
                }

                Orders.setAdapter(ordersAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }

}
