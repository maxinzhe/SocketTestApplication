package com.example.xinzhe.sockettestapplication.udputil;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import com.example.xinzhe.sockettestapplication.MySocket;
import com.example.xinzhe.sockettestapplication.P2P.EncodeHelpler;

import java.io.OutputStream;
import java.lang.reflect.Array;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Xinzhe on 2016/2/29.
 */
public class UdpAudioSend extends Thread {
    EncodeHelpler encodeHelpler;
    static int SAMPLE_RATE=8000;
    int bufferLength=0;
    private String ip;
    private int port;
    DatagramSocket socket;
    static volatile boolean isStopTalk=false;
    public UdpAudioSend(DatagramSocket socket) {
        // android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        this.socket=socket;
        encodeHelpler=new EncodeHelpler();
    }
    public UdpAudioSend(){

    }
    public void setIP(String ip){
        this.ip=ip;
    }
    public void setPort(int port){
        this.port=port;
    }
    @Override
    public void run() {
        super.run();
       // OutputStream os = null;
        AudioRecord recorder = null;
        try {

            //socket.setSoTimeout(5000);
            //os = socket.getOutputStream();
            // 获得录音缓冲区大小
            int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT);
            Log.e("test", "录音缓冲区大小" + bufferSize);
            bufferLength=bufferSize;

            // 获得录音机对象
            recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    bufferLength);

            recorder.startRecording();// 开始录音
            byte[] readBuffer = new byte[bufferLength];// 录音缓冲区

            int length = 0;
            InetAddress inetAddress=InetAddress.getByName(ip);
            while (!isStopTalk) {

                length = recorder.read(readBuffer, 0, bufferLength);// 从mic读取音频数据
             //   Log.i("test","recorder.read length"+length);
             //   Log.e("test", "在Send的while循环中");
                if (length > 0 ) {
                    Log.i("test", "在Send的while循环的read循环中");

                    Log.i("test","要发送的这个数据包读取的实际的长度为："+length);
                    byte [] effective= Arrays.copyOfRange(readBuffer,0,length);
                    Log.i("test","下面连续加密");
                    byte [] effective2=encodeHelpler.encodeBytes(effective);
                    Log.i("test","加密以后的长度为"+effective2.length+"下面连续解密");
                    byte [] effective3=encodeHelpler.decodeBytes(effective2);
                    //DatagramPacket dp = new DatagramPacket(encodeHelpler.encodeBytes(effective3),length,inetAddress,port);//发送地址，端口为8600
                   // DatagramPacket dp = new DatagramPacket(encodeHelpler.encodeBytes(effective),length,inetAddress,port);//发送地址，端口为8600
                   // DatagramPacket dp = new DatagramPacket(readBuffer,length,inetAddress,port);//发送地址，端口为8600
                     DatagramPacket dp = new DatagramPacket(effective2,effective2.length,inetAddress,port);//发送地址，端口为8600

                    socket.send(dp);

                    //os.write(readBuffer, 0, length);// 写入到输出流，把音频数据通过网络发送给对方
                }
            }
            recorder.stop();
            recorder.release();
            recorder = null;
           // os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

