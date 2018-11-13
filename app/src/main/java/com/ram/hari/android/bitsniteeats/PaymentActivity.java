package com.ram.hari.android.bitsniteeats;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener{

    int amt;
    String mess;

    Order newOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mess = getIntent().getStringExtra("mess");
        amt = getIntent().getIntExtra("price",0) * 100;

        startPayment();
    }

    public void startPayment() {
        /*
         * Instantiate Checkout
         */
        Checkout checkout = new Checkout();
        /*
         * Set your logo here
         */
        checkout.setImage(R.mipmap.ic_launcher);
        /*
         * Reference to current activity
         */
        final Activity activity = this;
        /*
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();
            /*
             * Merchant Name
             * eg: Rentomojo || HasGeek etc.
             */
            options.put("name", mess);
            /*
             * Description can be anything
             * eg: Order #123123
             *     Invoice Payment
             *     etc.
             */
            options.put("description", "Night Canteen");
            options.put("currency", "INR");

            /*
             * Amount is always passed in PAISE
             * Eg: "500" = Rs 5.00
             */
            options.put("amount", amt);

            checkout.open(activity, options);
        }
        catch(Exception e)
        {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Intent launchMain = new Intent(PaymentActivity.this,MainActivity.class);
        newOrder = new Order();
        newOrder.setMess(getIntent().getStringExtra("mess"));
        newOrder.setPrice(getIntent().getIntExtra("price",0));
        newOrder.setTimestamp(getIntent().getStringExtra("timestamp"));
        newOrder.setItems(getIntent().getStringExtra("items"));
        newOrder.setUserEmail(getIntent().getStringExtra("Email"));

        System.out.println(">>>>>>>>>>"+newOrder.getUserEmail());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("orders");
        String key = databaseReference.push().getKey();
        databaseReference.child(key).setValue(newOrder);

        launchMain.putExtra("Email",newOrder.getUserEmail());
        startActivity(launchMain);
        Toast.makeText(this,"Order successful",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Intent launchMain = new Intent(PaymentActivity.this,MainActivity.class);
        startActivity(launchMain);
        Toast.makeText(this,"Order unsuccessful",Toast.LENGTH_SHORT).show();
        finish();
    }
}

