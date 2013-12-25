package com.kzh.util.excel;

import com.kzh.system.ApplicationConstant;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Excel {
    public static String simpleExportExcel(List<String[]> list) {
        HSSFWorkbook wb = new HSSFWorkbook();
        HSSFSheet sheet = wb.createSheet();
        HSSFRow row;
        HSSFCell cell;
        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow(i);
            String[] strs = list.get(i);
            for (int j = 0; j < strs.length; j++) {
                cell = row.createCell(j);
                cell.setCellValue(strs[j]);
            }
        }
        try {
            SimpleDateFormat sim = new SimpleDateFormat("yyyy-MM-dd-HHmmss");
            String filePath = ApplicationConstant.TempFilePath + "exchangeInfo" + sim.format(new Date()) + ".xls";
            String fileName = "exchangeInfo" + sim.format(new Date()) + ".xls";
            FileOutputStream fs = new FileOutputStream(filePath);
            wb.write(fs);
            fs.close();
            return fileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List obtainFirstSheetAndCell(File file) throws Exception {
        List list = new ArrayList();
        Workbook workBook = null;
        try {
            FileInputStream fis = new FileInputStream(file);
            workBook = new XSSFWorkbook(fis);
        } catch (Exception ex) {
            workBook = new HSSFWorkbook(new FileInputStream(file));
        }
        Sheet sheet = workBook.getSheetAt(0);
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);
            Cell cell = row.getCell(0);
            list.add(getValue(cell));
        }

        return list;
    }

    private static String getValue(Cell cell) {
        if (cell.getCellType() == cell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else if (cell.getCellType() == cell.CELL_TYPE_NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else {
            return String.valueOf(cell.getStringCellValue());
        }
    }


}
