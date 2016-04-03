package com.example.xinzhe.sockettestapplication;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.xinzhe.sockettestapplication.udputil.UdpCallHelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {
    public static final String action="xinzhe.test";
    public static boolean isShow;//to flag if the current activity is at the top of the task
    Thread thread;

    Button btnstop;
    TextView textShow;

    Button btn_online;
    Button btn_call;

    //CallHelper callHelper=new CallHelper(this);

    UdpCallHelper callHelper=new UdpCallHelper(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent=new Intent(MainActivity.this,MyService.class);
       startService(intent);
        textShow=(TextView)findViewById(R.id.textView2);

        btnstop=(Button)findViewById(R.id.btn_stop);
        btnstop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent stopIntent=new Intent(MainActivity.this,MyService.class);
                stopService(stopIntent);
            }
        });

        btn_online=(Button)findViewById(R.id.btn_online);
        btn_online.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHelper.startOnline();
            }
        });

        btn_call=(Button)findViewById(R.id.btn_call);
        btn_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callHelper.startCalling();
            }
        });
        NotificationCompat.Builder mBuilder=new NotificationCompat.Builder(this);
         Intent pintent=new Intent(MainActivity.this,MainActivity.class);
         PendingIntent pendingIntent= PendingIntent.getActivity(MainActivity.this, 0, pintent, 0);
         mBuilder.setSmallIcon(R.mipmap.ic_launcher);
         mBuilder.setContentIntent(pendingIntent);
         mBuilder.setPriority(Notification.PRIORITY_DEFAULT);
         NotificationManager notificationManager=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
         notificationManager.notify(100, mBuilder.build());

        IntentFilter intentFilter=new IntentFilter(action);
        registerReceiver(broadcastReceiver,intentFilter);




    }
    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            textShow.append(intent.getExtras().getString("data"));
            if(intent.getExtras().getString("data").equals("close")){
                finish();
            }
        }
    };
    @Override
    protected void onStart() {
        super.onStart();


        //thread= new Thread(new Runnable() {

        //    @Override
        //    public void run() {

        //        final Socket socket= MySocket.getSocket();
        //        try{
        //            SocketHelper socketHelper= new SocketHelper(socket);
        //            socketHelper.outPutString("The first Message");
        //            while(true){
        //                final String message = socketHelper.br.readLine();
        //                Log.i("test", "out: " + message);
        //             runOnUiThread(new Runnable() {
        //                 @Override
        //                 public void run() {
        //                     Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show();
        //                 }
        //             });
        //            }
        //        }catch(Exception e){
        //            e.printStackTrace();
        //        }
        //    }
        //});
        //thread.start();
    }

    @Override
    protected void onStop() {

        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadcastReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        isShow=false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        isShow=true;
    }
}
