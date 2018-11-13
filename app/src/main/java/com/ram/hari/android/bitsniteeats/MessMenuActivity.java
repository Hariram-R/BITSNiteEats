package com.ram.hari.android.bitsniteeats;

import android.content.ClipData;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MessMenuActivity extends AppCompatActivity {

    RecyclerView food,bev;
    DatabaseReference databaseReference;

    ArrayList<Item> foodList;
    ArrayList<Item> bevList;

    FoodAdapter foodAdapter,bevAdapter;

    Button done;
    TextView count;

    int oderCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mess_menu);
        food = findViewById(R.id.FoodRV);
        bev = findViewById(R.id.BevRV);
        done = findViewById(R.id.done);
        count = findViewById(R.id.orderCount);
        food.setLayoutManager(new LinearLayoutManager(this));
        bev.setLayoutManager(new LinearLayoutManager(this));

        foodList = new ArrayList<>();
        bevList = new ArrayList<>();
        oderCount = 0;

        foodAdapter = new FoodAdapter(foodList,this);
        bevAdapter = new FoodAdapter(bevList,this);

        final String mess = getIntent().getStringExtra("mess");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("mess").child(mess.substring(0,1));
        databaseReference.keepSynced(true);

        System.out.println(">>>>>>>>>>>>>>>>>>"+mess.substring(0,1));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child:dataSnapshot.getChildren())
                {
                    try
                    {
                        Item item =  child.getValue(Item.class);
                        item.setQuantity(0);
                        System.out.println(">>>>>>>>>>>>>>>>>"+item);

                        if(!item.isAvailable())
                        {
                            continue;
                        }
                        if(item.getType().equalsIgnoreCase("food"))
                        {
                            foodList.add(item);
                        }
                        else
                        {
                            bevList.add(item);
                        }
                    }
                    catch (DatabaseException d)
                    {
                        oderCount = child.getValue(Integer.class);
                        System.out.println("count>>>>>>>"+oderCount);
                    }

                }

                food.setAdapter(foodAdapter);
                bev.setAdapter(bevAdapter);
                count.setText(new StringBuilder(oderCount+""));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Order thisOrder = new Order();

                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                Date date = new Date();
                thisOrder.setTimestamp(formatter.format(date));

                thisOrder.setMess(mess);

                // Iterating through foods
                for(int i =0;i<foodList.size();i++)
                {
                    if(foodList.get(i).getQuantity()>0)
                    {
                        if(thisOrder.getItems().equalsIgnoreCase("")) //Just for formatting
                        {
                            thisOrder.setItems(foodList.get(i).getName()+" x"+foodList.get(i).getQuantity());
                        }
                        else
                        {
                            thisOrder.setItems(thisOrder.getItems()+"\n"+foodList.get(i).getName()+" x"+foodList.get(i).getQuantity());
                        }

                        thisOrder.setPrice(thisOrder.getPrice()+foodList.get(i).getPrice() * foodList.get(i).getQuantity());
                    }
                }

                //Iterating through beverages
                for(int i =0;i<bevList.size();i++)
                {
                    if(bevList.get(i).getQuantity()>0)
                    {
                        if(thisOrder.getItems().equalsIgnoreCase(""))
                        {
                            thisOrder.setItems(bevList.get(i).getName()+" x"+bevList.get(i).getQuantity());
                        }
                        else
                        {
                            thisOrder.setItems(thisOrder.getItems()+"\n"+bevList.get(i).getName()+" x"+bevList.get(i).getQuantity());
                        }
                    }

                    thisOrder.setPrice(thisOrder.getPrice()+bevList.get(i).getPrice() * bevList.get(i).getQuantity());

                }

                // TODO add user email
                System.out.println("order>>>>>"+thisOrder.getItems()+thisOrder.getMess()+thisOrder.getTimestamp()+thisOrder.getPrice());

                Intent placeOrderActivity = new Intent(MessMenuActivity.this,PlaceOrderActivity.class);
                placeOrderActivity.putExtra("items",thisOrder.getItems());
                placeOrderActivity.putExtra("timestamp",thisOrder.getTimestamp());
                placeOrderActivity.putExtra("Email",MessMenuActivity.this.getIntent().getStringExtra("Email"));
                placeOrderActivity.putExtra("price",thisOrder.getPrice());
                placeOrderActivity.putExtra("mess",thisOrder.getMess());
                startActivity(placeOrderActivity);
                finish();
            }
        });
    }
}
