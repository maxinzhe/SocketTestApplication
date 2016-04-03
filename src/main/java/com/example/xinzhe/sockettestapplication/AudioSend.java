package com.example.xinzhe.sockettestapplication;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.OutputStream;
import java.net.Socket;

/**
 * Created by Xinzhe on 2016/2/29.
 */
public class AudioSend extends Thread {
    static int SAMPLE_RATE=8000;
    Socket socket;
    static volatile boolean isStopTalk=false;
    public AudioSend(Socket socket) {
        // android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        this.socket=socket;
    }


    @Override
    public void run() {
        super.run();
        OutputStream os = null;
        AudioRecord recorder = null;
        try {

            //socket.setSoTimeout(5000);
            os = socket.getOutputStream();
            // 获得录音缓冲区大小
            int bufferSize = AudioRecord.getMinBufferSize(SAMPLE_RATE, AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT);
            Log.e("test", "录音缓冲区大小" + bufferSize);

            // 获得录音机对象
            recorder = new AudioRecord(MediaRecorder.AudioSource.MIC,
                    SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    bufferSize * 2);

            recorder.startRecording();// 开始录音
            byte[] readBuffer = new byte[bufferSize*2];// 录音缓冲区

            int length = 0;

            while (!isStopTalk) {

                length = recorder.read(readBuffer, 0, 2*bufferSize);// 从mic读取音频数据
                Log.e("test", "在Send的while循环中");
                if (length > 0 ) {
                    Log.e("test", "在Send的while循环的read循环中");
                    os.write(readBuffer, 0, length);// 写入到输出流，把音频数据通过网络发送给对方
                }
            }
            recorder.stop();
            recorder.release();
            recorder = null;
            os.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
