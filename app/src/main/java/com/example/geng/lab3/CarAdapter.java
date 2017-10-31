package com.example.geng.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Geng on 2017/10/22.
 */

public class CarAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Good> cargood;

    public CarAdapter(Context context, ArrayList<Good> cargood) {
        this.context = context;
        this.cargood = cargood;
    }
    private class ViewHolder {
        public TextView icontxt;
        public TextView name;
        public TextView price;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.shop_car, null);
            holder = new ViewHolder();
            holder.icontxt = convertView.findViewById(R.id.icon);
            holder.name = convertView.findViewById(R.id.Description);
            holder.price = convertView.findViewById(R.id.price);
            convertView.setTag(holder);
        }    else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.price.setText( cargood.get(position).getPrice() );
        holder.name.setText( cargood.get(position).getName() );
        String first = cargood.get(position).getName();
        if(first.equals("购物车")){
            holder.icontxt.setText("*");
        }
        else holder.icontxt.setText(cargood.get(position).getName().substring(0,1).toUpperCase());
        return convertView;
    }

    @Override
    public int getCount() {
        if (cargood != null) {
            return cargood.size();
        } else return 1;
    }

    @Override
    public Object getItem(int i) {
        if (cargood == null) {
            return null;
        }
        return cargood.get(i);
    }
    @Override
    public long getItemId(int i) {
        return i;
    }
}
