package com.example.xinzhe.sockettestapplication;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Created by Xinzhe on 2016/1/17.
 */
public class SocketHelper {
    Socket socket;
    String string;

    InputStreamReader isr;
    InputStream is;
    public BufferedReader br;

    OutputStream outputStream;
    OutputStreamWriter outputStreamWriter;
    PrintWriter printWriter;

    public void close() throws IOException {
        if(isr!=null){
            isr.close();
        }
        if(is!=null){
            is.close();
        }
        if(outputStreamWriter!=null){
            outputStreamWriter.close();
        }
        if(outputStream!=null){
            outputStreamWriter.close();
        }

        if(socket!=null){
            socket.close();
        }
    }
    public String getInputString(){
        try{

           string= br.readLine() ;
        }catch (Exception e){
            e.printStackTrace();
        }

        return string;
    }
    public  void outPutString(String outString){
        try{
             //outputStream=socket.getOutputStream();
             //outputStreamWriter=new OutputStreamWriter(outputStream);
             //printWriter=new PrintWriter(outputStreamWriter,true);
            printWriter.println(outString);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public BufferedReader getBufferedReader(){
    	return br;
    }



    public SocketHelper(Socket socket){
       this.socket=socket;

        try{
            is =socket.getInputStream() ;
             isr = new InputStreamReader(is) ;
             br = new BufferedReader(isr) ;

            outputStream=socket.getOutputStream();
            outputStreamWriter=new OutputStreamWriter(outputStream);
            printWriter=new PrintWriter(outputStreamWriter,true);

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
