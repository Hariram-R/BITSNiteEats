package com.ram.hari.android.bitsniteeats;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodVH> {

    private ArrayList<Item> foodItems;
    private Context context;

    public FoodAdapter(ArrayList<Item> foodItems, Context context) {
        this.foodItems = foodItems;
        this.context = context;
    }

    @NonNull
    @Override
    public FoodVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new FoodVH(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_food,viewGroup,false));
    }

    @Override
    public void onBindViewHolder(@NonNull FoodVH foodVH, int i) {
        foodVH.populateItem(foodItems.get(i));
    }

    @Override
    public int getItemCount() {
        return foodItems.size();
    }

    public class FoodVH extends RecyclerView.ViewHolder
    {
        TextView nametv,pricetv,qtytv;
        Button plus,minus;

        int qty;


        public FoodVH(@NonNull View itemView) {
            super(itemView);
            nametv = itemView.findViewById(R.id.ItemName);
            pricetv = itemView.findViewById(R.id.pricetv);
            qtytv = itemView.findViewById(R.id.Qty);
            plus = itemView.findViewById(R.id.plus1);
            minus = itemView.findViewById(R.id.minus1);

        }

        void populateItem(final Item item)
        {
            nametv.setText(item.getName());
            pricetv.setText(new StringBuilder(item.getPrice()+""));
            qtytv.setText("0");

            qty = Integer.parseInt(qtytv.getText().toString());

            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    qtytv.setText(new StringBuilder((++qty) + ""));
                    item.setQuantity(qty);
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(qty>0)
                    {
                        qtytv.setText(new StringBuilder((--qty) + ""));
                        item.setQuantity(qty);
                    }
                }
            });
        }
    }
}
