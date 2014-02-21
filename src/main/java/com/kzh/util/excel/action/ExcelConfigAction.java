package com.kzh.util.excel.action;

import com.kzh.system.ApplicationConstant;
import com.kzh.util.struts.BaseAction;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.ResultPath;

import java.io.File;

@ResultPath("/pages/util/excel")
public class ExcelConfigAction extends BaseAction {
    //导出excel缓存文件的地址
    private String tmpPath;

    public String changeTmpPath() {
        if (StringUtils.isNotBlank(tmpPath)) {
            if (!tmpPath.endsWith(File.separator)) {
                tmpPath = tmpPath + File.separator;
            }
            ApplicationConstant.TempFilePath = tmpPath;
        }
        return SUCCESS;
    }

    //-----get/set-----------------

    public String getTmpPath() {
        return ApplicationConstant.TempFilePath;
    }

    public void setTmpPath(String tmpPath) {
        this.tmpPath = tmpPath;
    }
}
