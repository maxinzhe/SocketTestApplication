package com.example.xinzhe.sockettestapplication.P2P;

import com.sklois.haiyunKms.SoftLibs;

/**
 * Created by Xinzhe on 2016/3/10.
 */
public class EncodeHelpler {
    final String key="This is the key!";
    final int ALGORI=201;
    SoftLibs softLibs;
    public EncodeHelpler(){
        softLibs=new  SoftLibs();


    }
    public byte[] encodeBytes(byte [] data){
        return softLibs.SymEncrypt(ALGORI,key,data);

    }
    public byte[] decodeBytes(byte [] data){
        return softLibs.SymDecrypt(ALGORI,key,data);
    }
}
