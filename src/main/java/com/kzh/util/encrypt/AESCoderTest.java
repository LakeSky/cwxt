package com.kzh.util.encrypt;

import com.kzh.system.ApplicationConstant;

import java.nio.charset.Charset;

/**
 * 对称加密算法测试用例
 *
 * @author Administrator
 */
public class AESCoderTest {

    public static void main(String args[]) {
        try {
            /*//初始化密钥
//            String secretKey=AESCoder.initKeyString();
            String secretKey= ApplicationConstant.encryptKey;
            System.out.println("密钥为:"+secretKey);
            String s="起的的啊1sdfafsafasdfasfddd";
            //加密数据
            byte[] encryptData=AESCoder.encrypt(s.getBytes(), secretKey);
            System.out.println(encryptData.toString());
            //解密数据
            byte[] data=AESCoder.decrypt(encryptData, secretKey);
//            byte[] data = AESCoder.decrypt("[B@1b228d1".getBytes(), ApplicationConstant.encryptKey);
            System.out.println(data);*/
            byte[] decrypt = AES.parseHexStr2Byte("C9FEF9F8883820B8651B883B6800AF01A36425FD720B41DB426E0957A22EEBDF");
            String aa = String.valueOf(AES.decrypt(decrypt, ApplicationConstant.encryptKey));
            System.out.println(aa);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}