package com.example.xinzhe.sockettestapplication.udputil;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import com.example.xinzhe.sockettestapplication.P2P.EncodeHelpler;

import java.io.InputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;

/**
 * Created by Xinzhe on 2016/2/29.
 */

public class UdpAudioPlay extends Thread {
    EncodeHelpler encodeHelpler;
    DatagramSocket socket;
    int bufferLength=0;
    public UdpAudioPlay(DatagramSocket socket) {
        encodeHelpler=new EncodeHelpler();
         android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        this.socket=socket;
    }

    @Override
    public void run() {
        super.run();
        try {
            //InputStream is = socket.getInputStream();
            // 获得音频缓冲区大小
            int bufferSize = android.media.AudioTrack.getMinBufferSize(UdpAudioSend.SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            Log.e("test", "播放缓冲区大小" + bufferSize);
            bufferLength=bufferSize;

            // 获得音轨对象
            AudioTrack player = new AudioTrack(AudioManager.STREAM_VOICE_CALL,
                    UdpAudioSend.SAMPLE_RATE,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                  bufferLength,
                    AudioTrack.MODE_STREAM);

            // 设置喇叭音量
            player.setStereoVolume(1.0f, 1.0f);


            // 开始播放声音
            player.play();
            byte[] audio = new byte[bufferLength];// 音频读取缓存
          //  int length = 0;

            DatagramPacket dp = new DatagramPacket(audio,bufferLength);

            while (!UdpAudioSend.isStopTalk) {
                Log.e("test", "在Play的while循环中");
                socket.receive(dp);

               // length = is.read(audio);// 从网络读取音频数据
             //   byte[] temp = audio.clone();
                if (dp.getLength()>0 ) {
                    Log.e("test", "在Play的while循环中的write中");
                    byte[] effective= Arrays.copyOfRange(audio,0,dp.getLength());
                  //  byte [] effective=dp.getData();
                    int offset=dp.getOffset();
                    Log.e("test","接收到的数据包解码输入长度为：(dp.getLength)"+dp.getLength()+" getData.length:"+dp.getData().length+
                    "  起始偏移量是:"+offset);
                    // for(int
                    // i=0;i<length;i++)audio[i]=(byte)(audio[i]*2);//音频放大1倍
                    byte[] origData=encodeHelpler.decodeBytes(effective);
                    player.write(origData, 0,origData.length);// 播放音频数据
                 //   player.write((audio), 0, dp.getLength());// 播放音频数据

                    Log.i("test","datagram.getLength():  "+dp.getLength());
                }
            }
            player.stop();
            player.release();
            player = null;
            //is.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

