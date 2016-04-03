package com.example.xinzhe.sockettestapplication;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.util.Log;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Xinzhe on 2016/2/29.
 */

public class AudioPlay extends Thread {
    Socket socket;
    public AudioPlay(Socket socket) {
        // android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_URGENT_AUDIO);
        this.socket=socket;
    }

    @Override
    public void run() {
        super.run();
        try {
            InputStream is = socket.getInputStream();
            // 获得音频缓冲区大小
            int bufferSize = android.media.AudioTrack.getMinBufferSize(AudioSend.SAMPLE_RATE,
                    AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            Log.e("test", "播放缓冲区大小" + bufferSize);

            // 获得音轨对象
            AudioTrack player = new AudioTrack(AudioManager.STREAM_VOICE_CALL,
                    AudioSend.SAMPLE_RATE,
                    AudioFormat.CHANNEL_OUT_MONO,
                    AudioFormat.ENCODING_PCM_16BIT,
                    2*bufferSize,
                    AudioTrack.MODE_STREAM);

            // 设置喇叭音量
            player.setStereoVolume(1.0f, 1.0f);

            // 开始播放声音
            player.play();
            byte[] audio = new byte[2*bufferSize];// 音频读取缓存
            int length = 0;

            while (!AudioSend.isStopTalk) {
                Log.e("test", "在Play的while循环中");

                length = is.read(audio);// 从网络读取音频数据
                byte[] temp = audio.clone();
                if (length > 0 ) {
                    Log.e("test", "在Play的while循环中的write中");
                    // for(int
                    // i=0;i<length;i++)audio[i]=(byte)(audio[i]*2);//音频放大1倍
                    player.write(audio, 0, temp.length);// 播放音频数据
                }
            }
            player.stop();
            player.release();
            player = null;
            is.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}