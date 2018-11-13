package com.ram.hari.android.bitsniteeats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class PlaceOrderActivity extends AppCompatActivity {

    TextView items,totalprice;
    Button proceed,cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        items = findViewById(R.id.itemList);
        totalprice = findViewById(R.id.totalPrice);
        proceed = findViewById(R.id.confirmbtn);
        cancel = findViewById(R.id.cancelbtn);

        final int totalPrice = getIntent().getIntExtra("price",0);
        System.out.println("price>>>>>"+totalPrice);
        final String itemList = getIntent().getStringExtra("items");
        final String timest = getIntent().getStringExtra("timestamp");
        final String mess = getIntent().getStringExtra("mess");

        items.setText(itemList);
        totalprice.setText(new StringBuilder(totalPrice+""));

        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startPayment = new Intent(PlaceOrderActivity.this,PaymentActivity.class);
                startPayment.putExtra("price",totalPrice);
                startPayment.putExtra("mess",mess);
                startPayment.putExtra("items",itemList);
                startPayment.putExtra("timestamp",timest);
                startPayment.putExtra("Email",getIntent().getStringExtra("Email")); //TODO get and add email
                startActivity(startPayment);
                finish();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
