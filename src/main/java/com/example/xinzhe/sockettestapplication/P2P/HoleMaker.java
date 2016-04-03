package com.example.xinzhe.sockettestapplication.P2P;

import android.util.Log;

import com.example.xinzhe.sockettestapplication.MySocket;
import com.example.xinzhe.sockettestapplication.SocketHelper;

import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Xinzhe on 2016/3/5.
 */
public class HoleMaker extends Thread{//send a Udp to the address:port
    Socket socket;
    int port;
    String ipAddress;
    static DatagramSocket datagramSocket;
    @Override
    public void run() {
        super.run();
       try{
       //   socket=new Socket(ipAddress,port);
           String info ="I'm the HoleMaker!";
           JSONObject jsonObject=new JSONObject();
           jsonObject.put("command","HOLE_MAKER");
           jsonObject.put("info",info);
           String message=jsonObject.toString();

           byte[] data= message.getBytes();
           if(datagramSocket==null){

               datagramSocket=new P2PDatagramSocket().getSocket();
           }
           Log.i("test", "HoleMaker将要发送数据包,地址为address is:"+InetAddress.getByName((ipAddress))+" and port is"+port);

           DatagramPacket datagramPacket=new DatagramPacket(data,data.length, InetAddress.getByName(ipAddress),port);
           datagramSocket.send(datagramPacket);
           Log.i("test","HoleMaker发送完毕,address is:"+InetAddress.getByName((ipAddress))+" and port is"+port);
           MySocket.setP2PLocalPort(datagramSocket.getLocalPort());

       }catch (Exception e){
            e.printStackTrace();
       }


    }

    public HoleMaker( String ipAddress,int port) {
        super();
        this.ipAddress=ipAddress;
        this.port=port;

    }
}
