package com.example.geng.lab3;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;

/**
 * Created by Geng on 2017/10/29.
 */

public class DynamicReceiver extends BroadcastReceiver {
    private static int num = 1;
    private static final String DYNAMICACTION = "myDynamicFilter";
    static DynamicReceiver dynamicReceiver=new DynamicReceiver();
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(DYNAMICACTION)){
            Bundle extras = intent.getExtras();

            Intent toCarIntent = new Intent(context, MainActivity.class);
            toCarIntent.putExtra("from","notification");
            PendingIntent pendingIntent = PendingIntent.getActivity(context, num, toCarIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            Notification.Builder builder = new Notification.Builder(context)
                    .setContentTitle("马上下单")
                    .setContentText(extras.getString("name") + "已添加到购物车")
                    .setTicker("您有一条新消息")
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), get_img(extras.getString("name"))))
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            Notification notification = builder.build();
            manager.notify(num, notification);
            num += 1;
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

    static public DynamicReceiver getInstance() {
        return dynamicReceiver;
    }

}
