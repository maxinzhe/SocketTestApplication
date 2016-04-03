package com.example.xinzhe.sockettestapplication.P2P;

import android.provider.ContactsContract;
import android.util.Log;

import com.example.xinzhe.sockettestapplication.MySocket;

import org.json.JSONObject;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.Callable;

/**
 * Created by Xinzhe on 2016/3/5.
 */
public class HoleMakerReceiver implements Callable<Boolean> {//receive a Udp package from the port to contact with the server
    Socket socket;
    String ipAddress;
    int port;
    public HoleMakerReceiver( int port) {
        super();

        this.port=port;
    }



    @Override
    public Boolean call() throws Exception {
        Boolean finish=false;
              try{
            byte[] temp=new byte[100];
            DatagramPacket datagramPacket=new DatagramPacket(temp,100);
            DatagramSocket datagramSocket=new DatagramSocket( port);
                  Log.i("test","HoleMakerReceiver端口号："+port+"udp端口等待接收数据");
            datagramSocket.receive(datagramPacket);
          MySocket.setP2PIp(datagramSocket.getInetAddress().getHostAddress());
          Log.i("test","the remote address of the udp is:"+datagramSocket.getInetAddress().getHostAddress());
          MySocket.setP2PPort(datagramSocket.getPort());
          Log.i("test","the remote port of th udp is:"+datagramSocket.getPort());
            JSONObject jsonObject=new JSONObject(temp.toString());
                  Log.i("test","received udp from the otherPoint ,and the command is:"+jsonObject.get("command"));
            if(jsonObject.get("command").equals("HOLE_MAKER")){
                finish=true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return finish;
    }
}
