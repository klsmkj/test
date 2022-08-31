package com;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class ExcelDemo {
    public static void main(String[] args) throws Exception {
      //  XSSFWorkbook sheets = new XSSFWorkbook(new File("C:\\Users\\NIGHT\\Desktop\\职位导入模板.xlsx"));
        File file = new File("C:\\Users\\NIGHT\\Desktop\\职位导入模板.xlsx");
        /*FileInputStream fis = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
        fis = new FileInputStream(file);
        byte[] b = new byte[1000];
        int n;
        while ((n = fis.read(b)) != -1) {
            bos.write(b, 0, n);
        }
        byte[] data = bos.toByteArray();
        fis.close();
        bos.close();
        String name = file.getName();
        String filePath = "D:\\";
        String filePathName = filePath + name;
        FileOutputStream outputStream = null;
        outputStream = new FileOutputStream(filePathName);
        outputStream.write(data);
        outputStream.close();*/
        XSSFWorkbook sheets = new XSSFWorkbook(new File("C:\\Users\\NIGHT\\Desktop\\职位导入模板.xlsx"));
        XSSFSheet sheet = sheets.getSheetAt(0);
        short lastCellNum = sheet.getRow(0).getLastCellNum();
        for (int i = 0; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            for (int j = 0; j < lastCellNum; j++) {
                XSSFCell cell = row.getCell(j);
                System.out.println(cell);
            }
        }
    }
}
