package com.example.xinzhe.sockettestapplication;

import android.content.Context;
import android.media.AudioRecord;
import android.util.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Xinzhe on 2016/3/1.
 */
public class CallHelper {
    public CallHelper(Context context){
        this.context=context;
    }
    Context context;
    ServerSocket ss;
    Thread onlineThread;
    Socket socket;
    static boolean runSuccess=false;
    public void startOnline(){
        onlineThread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                     ss = new ServerSocket(8001);
                    socket= ss.accept();
                    runSuccess=true;
                    AudioPlay audioPlay=new AudioPlay(socket);
                    audioPlay.start();
                    new AudioSend(socket).start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        onlineThread.start();
    }
    public void startCalling(){
        if(onlineThread!=null&&onlineThread.isAlive()){
            onlineThread.interrupt();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Log.i("test","getLocalIP"+MySocket.getLocalIP(context) +"  getTheOtherIP"+MySocket.getTheOtherIp(context));
                    socket=new Socket(MySocket.getTheOtherIp(context),8001);
                    new AudioSend(socket).start();
                    new AudioPlay(socket).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
