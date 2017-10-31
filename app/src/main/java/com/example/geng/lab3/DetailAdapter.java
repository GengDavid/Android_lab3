package com.example.geng.lab3;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * Created by Geng on 2017/10/22.
 */

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
    private Good showgood;
    private Intent intent;
    public Activity gact;
    public Context context;
    public static int car_numbers = 0;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        public View layout;
        public TextView gprice;
        public TextView gweight;
        public ImageView gimang;
        public TextView gname;
        public ImageButton gback;
        public ImageButton gstar;
        public ImageButton gcar;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            gprice = v.findViewById(R.id.price);
            gweight = v.findViewById(R.id.goodsweight);
            gimang = v.findViewById(R.id.pic);
            gname = v.findViewById(R.id.goodsname);
            gback= v.findViewById(R.id.back);
            gstar= v.findViewById(R.id.star);
            gcar= v.findViewById(R.id.shopcaricon);
            gstar.setTag("0");
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public DetailAdapter(Good good, Context context, Activity act) {
        showgood = good;
        gact = act;
        this.context = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
        // create a new view
        LayoutInflater inflater = LayoutInflater.from(
                context);
        View v = inflater.inflate(R.layout.detail_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Good good = showgood;
        holder.gname.setText(good.getName());
        holder.gprice.setText(good.getPrice());
        holder.gweight.setText(good.getInfo());
        holder.gback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gact.finish();
            }
        });
        holder.gstar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Object tag=holder.gstar.getTag();
                if(tag=="0"){
                    holder.gstar.setTag("1");
                    holder.gstar.setImageResource(R.mipmap.full_star);
                }
                else
                {
                    holder.gstar.setTag("0");
                    holder.gstar.setImageResource(R.mipmap.empty_star);
                }
            }
        });
        holder.gcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,"商品已添加到购物车", Toast.LENGTH_SHORT).show();
                car_numbers++;
                EventBus.getDefault().post(
                        new Good(holder.gname.getText().toString(),
                                holder.gweight.getText().toString(),
                                holder.gprice.getText().toString()));
                IntentFilter dynamicFilter = new IntentFilter();
                dynamicFilter.addAction("myDynamicFilter");
                context.registerReceiver(DynamicReceiver.getInstance(),dynamicFilter);
                context.registerReceiver(MyWidget.getInstance(),dynamicFilter);
                Intent intentBroadcast = new Intent("myDynamicFilter");
                intentBroadcast.putExtra("name", holder.gname.getText().toString());
                intentBroadcast.putExtra("info", holder.gweight.getText().toString());
                intentBroadcast.putExtra("price", holder.gprice.getText().toString());
                context.sendBroadcast(intentBroadcast);
            }
        });

        String gname = good.getName();
        if(gname.equals("Enchated Forest")){
            holder.gimang.setImageResource(R.mipmap.dnchatedforest);
        }else if (gname.equals("Arla Milk")) {
            holder.gimang.setImageResource(R.mipmap.arla);
        }else if (gname.equals("Devondale Milk")) {
            holder.gimang.setImageResource(R.mipmap.devondale);
        }else if (gname.equals("Kindle Oasis")) {
            holder.gimang.setImageResource(R.mipmap.kindle);
        }else if (gname.equals("waitrose 早餐麦片")) {
            holder.gimang.setImageResource(R.mipmap.waitrose);
        }else if (gname.equals("Mcvitie\'s 饼干")) {
            holder.gimang.setImageResource(R.mipmap.mcvitie);
        }else if (gname.equals("Ferrero Rocher")) {
            holder.gimang.setImageResource(R.mipmap.ferrero);
        }else if (gname.equals("Maltesers")) {
            holder.gimang.setImageResource(R.mipmap.maltesers);
        }else if (gname.equals("Lindt")) {
            holder.gimang.setImageResource(R.mipmap.lindt);
        }else if (gname.equals("Borggreve")) {
            holder.gimang.setImageResource(R.mipmap.borggreve);
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return 1;
    }


}