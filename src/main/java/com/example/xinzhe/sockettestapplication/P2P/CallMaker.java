package com.example.xinzhe.sockettestapplication.P2P;

import android.content.Context;
import android.util.Log;

import com.example.xinzhe.sockettestapplication.udputil.UdpCallHelper;

/**
 * Created by Xinzhe on 2016/3/5.
 */
public class CallMaker extends Thread{
    Context context;

    public CallMaker(Context context) {

        super();
        this.context=context;
    }

    @Override
    public void run() {
        super.run();
        //sleep to wait both the HoleMaker have reached
        // Log.i("test","即将睡眠一秒钟");
        //this.sleep(1000);
        Log.i("test","睡眠一秒钟完毕");
        UdpCallHelper udpCallHelper=new UdpCallHelper(context);
        udpCallHelper.startCalling();
        udpCallHelper.startOnline();
    }
}
