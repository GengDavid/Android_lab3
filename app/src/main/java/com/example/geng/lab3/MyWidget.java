package com.example.geng.lab3;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

/**
 * Implementation of App Widget functionality.
 */
public class MyWidget extends AppWidgetProvider {

    private String recommond_name;
    private String recommond_price;
    private String recommond_info;
    final String[] infos = new String[]{"作者 Johanna Basford", "产地 德国", "产地 澳大利亚", "版本 8GB", "重量 2Kg", "产地 英国", "重量 300g", "重量 118g", "重量 249g", "重量 640g"};
    final String[] prices = new String[]{"¥ 5.00", "¥ 59.00", "¥ 79.00", "¥ 2399.00", "¥ 179.00", "¥ 14.00", "¥ 132.59", "¥ 141.43", "139.43", "28.90"};
    private static boolean recommonded = false;
    static MyWidget myWidget=new MyWidget();

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        CharSequence widgetText = context.getString(R.string.appwidget_text);
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.my_widget);
        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.widget);//实例化RemoteView,其对应相应的Widget布局
        updateViews.setTextViewText(R.id.appwidget_text,"当前没有任何信息");
        PendingIntent pi;

        Intent i=new Intent("CLICKWIDGET");
        pi=PendingIntent.getBroadcast(context,0,i,PendingIntent.FLAG_UPDATE_CURRENT);

        //给RemoteView上的Button设置按钮事件
        updateViews.setOnClickPendingIntent(R.id.mwidget,pi);
        ComponentName componentName=new ComponentName(context,MyWidget.class);
        appWidgetManager.updateAppWidget(componentName,updateViews);

    }

    @Override
    public void onEnabled(Context context) {

    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

    @Override
    public void onReceive(Context context,Intent intent)
    {
        super.onReceive(context,intent);
        if(intent.getAction().equals("CLICKWIDGET"))
        {
            String[] names = context.getResources().getStringArray(R.array.names);
            Random random = new Random();
            int p = random.nextInt(infos.length);
            recommond_name = names[p];
            recommond_price = prices[p];
            recommond_info = infos[p];
            RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.widget);
            updateViews.setTextViewText(R.id.appwidget_text,names[p]+"仅售"+prices[p]+"!");
            updateViews.setImageViewResource(R.id.appwidget_imag, get_img(names[p]));

            Intent mIntent = new Intent(context, Detail.class);
            mIntent.putExtra("name",recommond_name);
            mIntent.putExtra("price",recommond_price);
            mIntent.putExtra("info",recommond_info);

            PendingIntent pi;
            pi = PendingIntent.getActivity(context, 0, mIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.mwidget,pi);

            ComponentName componentName=new ComponentName(context,MyWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(componentName,updateViews);
        }
        else if(intent.getAction().equals("myDynamicFilter")){
            RemoteViews updateViews=new RemoteViews(context.getPackageName(),R.layout.widget);

            Bundle extras = intent.getExtras();
            recommond_name = extras.getString("name");
            recommond_price = extras.getString("price");
            recommond_info = extras.getString("info");
            updateViews.setTextViewText(R.id.appwidget_text,recommond_name+"已添加到购物车");
            updateViews.setImageViewResource(R.id.appwidget_imag, get_img(recommond_name));

            Intent mIntent = new Intent(context, Detail.class);
            mIntent.putExtra("name",recommond_name);
            mIntent.putExtra("price",recommond_price);
            mIntent.putExtra("info",recommond_info);

            PendingIntent pi;
            pi = PendingIntent.getActivity(context, 0, mIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);
            updateViews.setOnClickPendingIntent(R.id.mwidget,pi);

            ComponentName componentName=new ComponentName(context,MyWidget.class);
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            appWidgetManager.updateAppWidget(componentName,updateViews);
        }
    }

    public int get_img(String name){
        if(name.equals("Enchated Forest")){
            return R.mipmap.dnchatedforest;
        }else if (name.equals("Arla Milk")) {
            return R.mipmap.arla;
        }else if (name.equals("Devondale Milk")) {
            return R.mipmap.devondale;
        }else if (name.equals("Kindle Oasis")) {
            return R.mipmap.kindle;
        }else if (name.equals("waitrose 早餐麦片")) {
            return R.mipmap.waitrose;
        }else if (name.equals("Mcvitie\'s 饼干")) {
            return R.mipmap.mcvitie;
        }else if (name.equals("Ferrero Rocher")) {
            return R.mipmap.ferrero;
        }else if (name.equals("Maltesers")) {
            return R.mipmap.maltesers;
        }else if (name.equals("Lindt")) {
            return R.mipmap.lindt;
        }else {
            return R.mipmap.borggreve;
        }
    }

    static public MyWidget getInstance() {
        return myWidget;
    }
}

