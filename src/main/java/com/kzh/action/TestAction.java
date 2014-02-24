package com.kzh.action;

import com.kzh.system.ApplicationConstant;
import com.kzh.util.encrypt.AES;
import com.kzh.util.struts.BaseAction;
import org.apache.struts2.convention.annotation.ResultPath;

@ResultPath("/")
public class TestAction extends BaseAction {
    private String classpath;
    private String decryptStr;

    public String execute() {
        classpath = this.getClass().getResource("/").toString();
        byte[] encrypt = AES.encrypt("320584195810090012", ApplicationConstant.encryptKey);
        decryptStr = new String(AES.decrypt(encrypt, ApplicationConstant.encryptKey));
        return SUCCESS;
    }

    //-----------------------------------------------
    public String getClasspath() {
        return classpath;
    }

    public void setClasspath(String classpath) {
        this.classpath = classpath;
    }

    public String getDecryptStr() {
        return decryptStr;
    }

    public void setDecryptStr(String decryptStr) {
        this.decryptStr = decryptStr;
    }
}
