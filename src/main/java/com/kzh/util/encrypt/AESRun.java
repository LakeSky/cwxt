package com.kzh.util.encrypt;

import com.kzh.system.ApplicationConstant;

public class AESRun {
    public static void main(String[] agrs) {
        String content = "safadfasdfsdfds";
        byte[] encrypt = AES.encrypt(content, ApplicationConstant.encryptKey);
        String hex = AES.parseByte2HexStr(encrypt);

        byte[] result = AES.parseHexStr2Byte(hex);

        byte[] last = AES.decrypt(result, ApplicationConstant.encryptKey);
        System.out.println(new String(last));

    }
}
