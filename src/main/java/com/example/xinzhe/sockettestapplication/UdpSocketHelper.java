package com.example.xinzhe.sockettestapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * Created by Xinzhe on 2016/1/17.
 */
public class UdpSocketHelper {
    String ip;
    int localPort=8080;
    int outputPort;

    private static DatagramSocket datagramSocket;

    DatagramPacket datagramPacket;

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public int getLocalPort() {
        return localPort;
    }
    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }
    public int getOutputPort() {
        return outputPort;
    }
    public void setOutputPort(int outputPort) {
        this.outputPort = outputPort;
    }
    public DatagramSocket getDatagramSocket() {
        return datagramSocket;
    }
    public void setDatagramSocket(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }
    public DatagramPacket getDatagramPacket() {
        return datagramPacket;
    }
    public void setDatagramPacket(DatagramPacket datagramPacket) {
        this.datagramPacket = datagramPacket;
    }



    public UdpSocketHelper(String ip,int outputPort ){
        this.ip=ip;
        this.outputPort=outputPort;
        try {
            datagramPacket=new DatagramPacket(null,0,InetAddress.getByName(ip), outputPort);
        } catch (UnknownHostException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
    }
    public UdpSocketHelper(){

        try {
            if(datagramSocket==null){
                datagramSocket=new DatagramSocket(localPort);

            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    public UdpSocketHelper(int localPort){
        this.localPort=localPort;
    }
    public void close() throws IOException {
        datagramSocket.close();
    }

    public String getInputString(DatagramSocket datagramSocket) throws IOException {//receive less than 100 byte packet,just for command use
        String result=null;
        byte[] data=new byte[100];

        datagramPacket=new DatagramPacket(data,data.length);
        datagramSocket.receive(datagramPacket);

        result=new String(data,0,datagramPacket.getLength());
        return result;
    }
    public String getInputString(int port) throws IOException {//receive less than 100 byte packet,just for command use
        String result=null;
        byte[] data=new byte[100];

        // datagramSocket=new DatagramSocket(port) ;
        datagramPacket=new DatagramPacket(data,data.length);
        datagramSocket.receive(datagramPacket);

        result=new String(data,0,datagramPacket.getLength());
        Log.i("test","接收到的字符串为"+result);
        return result;
    }

    public String getInputString() throws IOException {//receive less than 100 byte packet,just for command use
        String result=null;
        byte[] data=new byte[100];

        // datagramSocket=new DatagramSocket(localPort) ;
        datagramPacket=new DatagramPacket(data,data.length);
        datagramSocket.receive(datagramPacket);

        result=new String(data,0,datagramPacket.getLength());
        Log.i("test","接收到的字符串为"+result);
        return result;
    }


    public void outPutString( InetSocketAddress socketAddress,String info) throws IOException{

        byte[] data=info.getBytes();
        datagramPacket=new DatagramPacket(data, data.length);
        datagramPacket.setSocketAddress(socketAddress);
        System.out.println("发送了一个数据到  "+socketAddress.toString()+"  内容是"+new String(data));
        //  datagramSocket =new DatagramSocket();
        datagramSocket.send(datagramPacket);
    }

    public void outPutString(DatagramPacket datagramPacket,String info) throws IOException{

        byte[] data=info.getBytes();
        datagramPacket.setData(data);

        // datagramSocket =new DatagramSocket();
        datagramSocket.send(datagramPacket);
    }
    public  void outPutString(String ip,int port,String info) throws IOException {//to send String less than 100bytes only for command
        byte[] data=info.getBytes();
        datagramPacket=new DatagramPacket(data,info.length(), InetAddress.getByName(ip),port);
        // datagramSocket=new DatagramSocket();
        datagramSocket.send(datagramPacket);
    }



}

