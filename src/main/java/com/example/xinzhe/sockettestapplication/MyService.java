package com.example.xinzhe.sockettestapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.xinzhe.sockettestapplication.P2P.CallMaker;
import com.example.xinzhe.sockettestapplication.P2P.HandlerHOLE_MAKER;
import com.example.xinzhe.sockettestapplication.P2P.HandlerSTART_P2P;
import com.example.xinzhe.sockettestapplication.P2P.HoleMaker;
import com.example.xinzhe.sockettestapplication.P2P.HoleMakerReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Xinzhe on 2016/2/24.
 */
public class MyService extends Service{
    volatile boolean stop=false;
   Thread thread;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("test","MyService:onDestroy");
        thread.interrupt();
        stop=true;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        final Handler handler=new Handler();

        thread= new Thread(new Runnable() {

            @Override
            public void run() {
                final int ServerPort=MySocket.getServerPort();
                final String ServerIP=MySocket.getServerIp();
                try{
                    final UdpSocketHelper udpSocketHelper= new UdpSocketHelper();
                    udpSocketHelper.outPutString(ServerIP,ServerPort,"The first Udp Message");
                    while(!stop){
                        final String message = udpSocketHelper.getInputString();
                        Log.i("test", "收到的message字符串为: " + message);

                        handler.post(new Runnable() {
                         @Override
                         public void run() {

                             if(MainActivity.isShow==false){
                                 NotificationCompat.Builder mBuilder=new MyNotificationBuilder(MyService.this,message);
                                 NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                                 Intent intent=new Intent(MyService.this,MainActivity.class);
                                 PendingIntent pendingIntent=PendingIntent.getActivity(MyService.this, 0, intent, 0);
                                 mBuilder.setContentIntent(pendingIntent);
                                 mBuilder.setPriority(Notification.PRIORITY_DEFAULT);
                                 notificationManager.notify(1, mBuilder.build());

                             }
                             if(message.equals("open")){
                                 try {
                                     Intent intent = new Intent(MyService.this, OpenActivity.class);
                                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                     startActivity(intent);
                                 }catch (Exception e){
                                     e.printStackTrace();
                                 }

                             }
                             if(message.equals("openMain")){

                                 Intent intent=new Intent(MyService.this,MainActivity.class);
                                 intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);//?
                                 intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                 startActivity(intent);
                             }

                             Toast.makeText(MyService.this, message, Toast.LENGTH_SHORT).show();

                             Intent broadIntent=new Intent(MainActivity.action);
                             broadIntent.putExtra("data", message);
                             sendBroadcast(broadIntent);
                         }
                     });






                        JSONObject jsonObject;
                        String command;
                        try{

                            jsonObject=new JSONObject(message);
                            command=jsonObject.get("command").toString();
                        } catch (Exception e){
                            Log.i("test", "接收Json格式命令发生了异常");
                            e.printStackTrace();
                            command="";
                            jsonObject=new JSONObject();

                        }
                        final String finalCommand = command;
                        final JSONObject finalJsonObjetct=jsonObject;
                        if(finalCommand.equals("START_P2P")){
                            new HandlerSTART_P2P(MyService.this,finalJsonObjetct).start();
                        }
                        if(finalCommand.equals("HOLE_MAKER")){
                            InetSocketAddress socketAddress=(InetSocketAddress)udpSocketHelper.getDatagramPacket().getSocketAddress();
                            Log.i("test","Hole_Maker的源地址端口为:"+socketAddress.getAddress().getHostAddress()+":"+socketAddress.getPort());
                            new HandlerHOLE_MAKER(MyService.this,socketAddress).start();
                        }
                    }
                    Log.i("test","out:"+"listening Thread stopped");
                }catch(Exception e){
                    Log.i("test","out:"+"catch Thread interruption");
                    e.printStackTrace();
                    if(e instanceof InterruptedException){
                        stop=true;
                    }
                }
            }
        });
        thread.start();
    }
}
