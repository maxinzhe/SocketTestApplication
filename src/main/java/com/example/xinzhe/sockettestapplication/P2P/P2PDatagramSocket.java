package com.example.xinzhe.sockettestapplication.P2P;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * Created by Xinzhe on 2016/3/7.
 */
public class P2PDatagramSocket {
    private static DatagramSocket datagramSocket=null;
    private static int P2PPort=8005;
    public DatagramSocket getSocket() throws SocketException {
        if(datagramSocket==null){
            datagramSocket=new DatagramSocket(P2PPort);
        }
        return datagramSocket;
    }
}
