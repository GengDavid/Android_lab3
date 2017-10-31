package com.example.geng.lab3;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;


public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private ListView listView;
    private CarAdapter carAdapter;


    final String[] infos = new String[]{"作者 Johanna Basford", "产地 德国", "产地 澳大利亚", "版本 8GB", "重量 2Kg", "产地 英国", "重量 300g", "重量 118g", "重量 249g", "重量 640g"};
    final String[] prices = new String[]{"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00", "¥ 14.00", "¥ 132.59", "¥ 141.43", "139.43", "28.90"};

    private ArrayList<Good> carinput = new ArrayList<>();
    private ArrayList<Good> input;
    ImageButton car;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        input = new ArrayList<>();

        String[] names = getResources().getStringArray(R.array.names);
        for(int i = 0; i < names.length; i++){
            input.add(new Good(names[i], infos[i], prices[i]));
        }

        Random random = new Random();
        int p = random.nextInt(infos.length);
        Intent intentBroadcast = new Intent("com.example.geng.lab3.recommend");
        intentBroadcast.putExtra("name", input.get(p).getName());
        intentBroadcast.putExtra("price", input.get(p).getPrice());
        intentBroadcast.putExtra("info", input.get(p).getInfo());
        sendBroadcast(intentBroadcast);


        mAdapter = new MyAdapter(input);
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener(){
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(MainActivity.this, Detail.class);
                intent.putExtra("name", input.get(position).getName());
                intent.putExtra("price", input.get(position).getPrice());
                intent.putExtra("info", input.get(position).getInfo());
                startActivityForResult(intent,0);
            }
            @Override
            public void onLongClick(int position) {
                Toast.makeText(MainActivity.this,"移除第"+String.valueOf(position+1)+"个商品",Toast.LENGTH_SHORT).show();
                mAdapter.remove(position);
                mAdapter.notifyDataSetChanged();
            }
        });
        AlphaInAnimationAdapter animatorAdapter = new AlphaInAnimationAdapter(mAdapter);
        animatorAdapter.setDuration(1000);
        recyclerView.setAdapter(animatorAdapter);
        //recyclerView.setAdapter(mAdapter);

        carinput.add(new Good("购物车", "0", "价格"));
        listView = findViewById(R.id.carview);
        listView.setVisibility(View.INVISIBLE);
        carAdapter = new CarAdapter(this, carinput);
        listView.setAdapter(carAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> adapterView,View view, final int i,long l){
                if(i==0) return;
                Intent intent = new Intent(MainActivity.this, Detail.class);
                intent.putExtra("name", carinput.get(i).getName());
                intent.putExtra("price", carinput.get(i).getPrice());
                intent.putExtra("info", carinput.get(i).getInfo());
                startActivityForResult(intent,0);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                if(i==0) return true;
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setTitle("移除商品").setMessage("从购物车移除" + carinput.get(i).getName() + "?").setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        if (carinput.remove(i)!=null )
                            carAdapter.notifyDataSetChanged();
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "你选择了取消", Toast.LENGTH_SHORT).show();
                    }
                }).show();
                return true;
            }
        });

        car = (ImageButton) findViewById(R.id.fab);
        car.setTag("0");
        car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (car.getTag() == "0") {
                    car.setImageResource(R.mipmap.mainpage);
                    car.setTag("1");
                    listView.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.INVISIBLE);
                } else {
                    car.setImageResource(R.mipmap.shoplist);
                    car.setTag("0");
                    listView.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });

        EventBus.getDefault().register(this);

    }

/*
    // 第一个参数为请求码，即调用startActivityForResult()传递过去的值
    // 第二个参数为结果码，结果码用于标识返回数据来自哪个新Activity
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if(resultCode==1){
            String str=intent.getExtras().getString("name");
            String pri=intent.getExtras().getString("price");
            String info=intent.getExtras().getString("info");
            carinput.add(new Good(str,info,pri));
            carAdapter.notifyDataSetChanged();
        }
    }
*/


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Good good) {
        carinput.add(good);
        carAdapter.notifyDataSetChanged();
    }


    @Override
    protected void  onDestroy(){
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onNewIntent(Intent intent){
        super.onNewIntent(intent);
        setIntent(intent);
        intent = getIntent();
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            final String from=intent.getStringExtra("from");
            if (from.equals("notification")) {
                car.setImageResource(R.mipmap.mainpage);
                car.setTag("1");
                listView.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                carAdapter.notifyDataSetChanged();
            }
        }
    }


}
