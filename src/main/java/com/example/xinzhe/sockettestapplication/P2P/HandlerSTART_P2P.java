package com.example.xinzhe.sockettestapplication.P2P;

import android.content.Context;
import android.provider.ContactsContract;
import android.util.Log;

import com.example.xinzhe.sockettestapplication.MyService;
import com.example.xinzhe.sockettestapplication.MySocket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Xinzhe on 2016/3/6.
 */
 public class HandlerSTART_P2P extends Thread {
    JSONObject jsonObject;
    Context context;
 public HandlerSTART_P2P(Context context,JSONObject jsonObject) {
  super();
    this.jsonObject=jsonObject;
     this.context=context;
 }

 @Override
 public void run() {
  super.run();

     try {
        // MySocket.setP2PIp((String) jsonObject.get("ip"));
        // MySocket.setP2PPort((int) jsonObject.get("port"));

        // HoleMakerReceiver holeMakerReceiver=new HoleMakerReceiver( MySocket.getToServerPort());
        // ExecutorService executorService= Executors.newCachedThreadPool();
        // Log.i("test","准备执行holeMakerReceiver");
         //Future<Boolean> future=executorService.submit(holeMakerReceiver);
         Log.i("test", "准备执行HoleMaker");
         new HoleMaker((String)jsonObject.get("ip"),(int)jsonObject.get("port")).start();
       /*
       if(((Boolean)future.get()).equals(new Boolean(true))){
       Log.i("test","holeMakerReceiver的返回结果为"+future.get());
       Log.i("test","收到对点打洞消息，开始执行CallMaker");
       new CallMaker(context).start();
       }
       */

     } catch (JSONException e) {
         e.printStackTrace();
     }
 }
}
