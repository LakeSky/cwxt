package com.kzh.util.download.action;

import java.io.*;

import com.kzh.system.ApplicationConstant;
import org.apache.struts2.convention.annotation.Results;
import org.apache.struts2.convention.annotation.Result;

import com.opensymphony.xwork2.ActionSupport;

/**
 * 所下载文件相关的的几个属性:文件格式 contentType,
 * 获取文件名的方法inputName,
 * 文件内容（显示的）属性contentDisposition,
 * 限定下载文件 缓冲区的值bufferSize
 */

@Results({@Result(name = "success", type = "stream",
        params = {
                "contentType", "application/octet-stream;charset=ISO8859-1",
                "inputName", "inputStream",
                "contentDisposition", "attachment;filename=${fileName}",
                "bufferSize", "4096"
        })})
public class DownloadAction extends ActionSupport {
    private String fileName;
    private String storageId;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getStorageId() {
        return storageId;
    }

    public void setStorageId(String storageId) {
        this.storageId = storageId;
    }

    public String getDownloadFileName() {
        String downloadFileName = fileName;
        try {
            downloadFileName = new String(downloadFileName.getBytes(), "ISO8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return downloadFileName;
    }

    public InputStream getInputStream() throws Exception {
        /**
         * 下载用的Action应该返回一个InputStream实例，
         * 该方法对应在result里的inputName属性值为targetFile
         **/
        String fullFileName = ApplicationConstant.TempFilePath + fileName;
        File file = new File(fullFileName);
        FileInputStream fileInputStream = new FileInputStream(file);
//        InputStream is = ServletActionContext.getServletContext().getResourceAsStream(fullFileName);
        return fileInputStream;
    }

    public String execute() throws Exception {
        return SUCCESS;
    }

}
