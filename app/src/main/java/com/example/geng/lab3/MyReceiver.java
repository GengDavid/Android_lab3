package com.example.geng.lab3;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;
import android.widget.Toast;

import java.util.concurrent.BrokenBarrierException;

/**
 * Created by Geng on 2017/10/28.
 */


public class MyReceiver extends BroadcastReceiver {
    private RemoteViews remoteView;
    private String name;
    private String price;
    private String info;
    @Override
    public void onReceive(Context context, Intent intent) {
        name=intent.getStringExtra("name");
        price=intent.getStringExtra("price");
        info=intent.getStringExtra("info");

        remoteView = new RemoteViews(context.getPackageName(), R.layout.notification);
        remoteView.setImageViewResource(R.id.noticeimage, get_img(name));
        remoteView.setTextViewText(R.id.noticetext,"新商品热卖");
        remoteView.setTextViewText(R.id.noticetext1,name);
        remoteView.setTextViewText(R.id.noticetext2,"现仅需"+price+"!");

        Intent mIntent = new Intent(context, Detail.class);
        mIntent.putExtra("name",name);
        mIntent.putExtra("price",price);
        mIntent.putExtra("info",info);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, mIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(context)
                        .setContentTitle("新商品热卖")
                        .setTicker("全年最低价")
                        .setSmallIcon(R.mipmap.ic_launcher_round)
                        .setContentIntent(pendingIntent)
                        .setContent(remoteView)
                        .setDefaults(Notification.DEFAULT_ALL)
                        .setAutoCancel(true);
        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = builder.build();
        manager.notify(0, notification);

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
}
