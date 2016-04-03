package com.example.xinzhe.sockettestapplication.udputil;

import android.content.Context;
import android.media.AudioRecord;
import android.util.Log;

import com.example.xinzhe.sockettestapplication.MySocket;
import com.example.xinzhe.sockettestapplication.P2P.P2PDatagramSocket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Xinzhe on 2016/3/1.
 */
public class UdpCallHelper {
    public UdpCallHelper(Context context){
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
                    DatagramSocket socket = new P2PDatagramSocket().getSocket();

               //     socket.receive(new DatagramPacket(new byte[2],2));
                  //  byte [] buf = new byte[100];

                  //  ss = new ServerSocket(8001);
                   // socket= ss.accept();
                    runSuccess=true;
                    UdpAudioPlay audioPlay=new UdpAudioPlay(socket);
                    audioPlay.start();
                 //   new UdpAudioSend().start();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
        onlineThread.start();
    }
    public void startCalling(){
        //if(onlineThread!=null&&onlineThread.isAlive()){
        //    onlineThread.interrupt();
        //}
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    Log.i("test"," disIP is: "+MySocket.getP2PIp());
                    //socket=new Socket(MySocket.getTheOtherIp(context),8001);
                    String disIP=MySocket.getP2PIp();
                    Log.i("test","in startCalling,the distIP is:"+InetAddress.getByName(disIP).toString());
                    DatagramSocket socket= new P2PDatagramSocket().getSocket();


                    UdpAudioSend udpAudioSend=new UdpAudioSend(socket);
                    udpAudioSend.setIP(disIP);
                    udpAudioSend.setPort(MySocket.getP2PPort());
                    udpAudioSend.start();

                   // new UdpAudioPlay(socket).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}

