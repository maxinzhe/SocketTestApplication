package com.example.xinzhe.sockettestapplication;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.Socket;

/**
 * Created by Xinzhe on 2016/1/23.
 */
public class MySocket {//singleton


    static int clientLocalP2PPort=8005;
    static  int serverPort=8000;
    static String serverIp="172.29.66.111";

    static String P2PIp;
    static int P2PPort;

    static int P2PLocalPort;

    public static int getClientLocalP2PPort() {
        return clientLocalP2PPort;
    }

    public static void setClientLocalP2PPort(int clientLocalP2PPort) {
        MySocket.clientLocalP2PPort = clientLocalP2PPort;
    }
    public static int getServerPort() {


        return serverPort;
    }

    public static void setServerPort(int serverPort) {
        MySocket.serverPort = serverPort;
    }

    public static String getServerIp() {
        return serverIp;
    }

    public static void setServerIp(String serverIp) {
        MySocket.serverIp = serverIp;
    }


    public static int getP2PLocalPort() {
        return P2PLocalPort;
    }

    public static void setP2PLocalPort(int p2PLocalPort) {
        P2PLocalPort = p2PLocalPort;
    }

    public static String getP2PIp() {
        return P2PIp;
    }

    public static void setP2PIp(String p2PIp) {
        P2PIp = p2PIp;
    }

    public static int getP2PPort() {
        return P2PPort;
    }

    public static void setP2PPort(int p2PPort) {
        P2PPort = p2PPort;
    }

    private static Socket socket=null;

    private MySocket(){

    }

    public static synchronized Socket getSocket(){
        if(socket==null){
            try{
                socket = new Socket(serverIp,serverPort);

            }catch(Exception exception){
            exception.printStackTrace();
            Log.e("test", "socket initial failed");
        }
        }
        return socket;
    }

public static synchronized Socket getSet1Socket(){
        if(socket==null){
            try{
                socket = new Socket("192.168.253.2",8001);

            }catch(Exception exception){
                exception.printStackTrace();
                Log.e("test", "socket initial failed");
            }
        }
        return socket;
}

public static synchronized Socket getSet2Socket(){
        if(socket==null){
            try{
                socket = new Socket("192.168.253.6",8001);

            }catch(Exception exception){
            exception.printStackTrace();
            Log.e("test", "socket initial failed");
                }
        }
        return socket;
}

    private static String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }
    public static String getLocalIP(Context context){
        //获取wifi服务
        WifiManager wifiManager = (WifiManager)context.getSystemService(Context.WIFI_SERVICE);
        //判断wifi是否开启
        if (!wifiManager.isWifiEnabled()) {
            wifiManager.setWifiEnabled(true);
        }
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        String ip = intToIp(ipAddress);
        return ip;
    }
    public static String getTheOtherIp(Context context){
        if(getLocalIP(context).equals("192.168.253.2")){
            return "192.168.253.6";
        }else{
            return "192.168.253.2";
        }
    }
    public static int getToServerPort(){
        return serverPort;
    }
}

