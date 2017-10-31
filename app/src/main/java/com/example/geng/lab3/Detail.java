package com.example.geng.lab3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Geng on 2017/10/22.
 */

public class Detail extends AppCompatActivity{
    private RecyclerView recyclerView;
    private ListView listView;
    private ArrayList<String> arrayList;
    private DetailAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail);

        Intent intent=getIntent();
        final String iname=intent.getStringExtra("name");
        final String iprice=intent.getStringExtra("price");
        final String iinfo=intent.getStringExtra("info");

        recyclerView = (RecyclerView) findViewById(R.id.detail_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final Good input = new Good(iname, iinfo, iprice);
        mAdapter = new DetailAdapter(input, this, this);
        recyclerView.setAdapter(mAdapter);

        listView = (ListView) findViewById(R.id.particulars);
        final String[] more= new String[]{"一键下单","分享商品","不感兴趣","查看更多商品促销信息"};
        arrayList = new ArrayList<>();
        for (String s:more){
            arrayList.add(s);
        }
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);
        listView.setAdapter(arrayAdapter);


    }

}
