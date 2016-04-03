package com.example.xinzhe.sockettestapplication;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Xinzhe on 2016/2/26.
 */
public class MyNotificationBuilder extends NotificationCompat.Builder{

    /**
     * Constructor.
     * <p/>
     * Automatically sets the when field to {@link System#currentTimeMillis()
     * System.currentTimeMillis()} and the audio stream to the
     * {@link Notification#STREAM_DEFAULT}.
     *
     * @param context A {@link Context} that will be used to construct the
     *                RemoteViews. The Context will not be held past the lifetime of this
     *                Builder object.
     */
    public MyNotificationBuilder(Context context,String info) {
        super(context);
        this.setContentTitle("测试通知栏");
        this.setSmallIcon(R.mipmap.ic_launcher);
        this.setContentText(info);
        this.setWhen(System.currentTimeMillis());
        this.setTicker(info);

    }
}
