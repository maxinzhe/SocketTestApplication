package com.example.xinzhe.sockettestapplication.P2P;

import android.content.Context;
import android.util.Log;

import com.example.xinzhe.sockettestapplication.MyService;
import com.example.xinzhe.sockettestapplication.MySocket;

import java.net.InetSocketAddress;

/**
 * Created by Xinzhe on 2016/3/7.
 */
public class HandlerHOLE_MAKER  extends Thread{
    InetSocketAddress socketAddress;
    Context context;
    public HandlerHOLE_MAKER(Context context,InetSocketAddress socketAddress) {
        super();
        this.socketAddress=socketAddress;
        this.context=context;
    }

    @Override
    public void run() {
        super.run();
        MySocket.setP2PPort(socketAddress.getPort());
        Log.i("test","设置的P2P地址和端口为 ："+socketAddress.getAddress().getHostAddress()+":"+socketAddress.getPort());
        MySocket.setP2PIp(socketAddress.getAddress().getHostAddress());
        Log.i("test","即将启动CallMaker") ;

        new CallMaker(context).start();
        Log.i("test","CallMaker启动完毕");
    }
}
