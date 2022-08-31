package com;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProExcelDemo {
    public static void main(String[] args) throws Exception {
        List<String> title = new ArrayList<String>();
        title.add("名称");
        title.add("性别");
        title.add("年龄");
        User user1 = new User("blues","man",20);
        User user2 = new User("blues","woman",19);
        User user3 = new User("blues","woman",18);
        User user4 = new User("blues","woman",17);
        List<User> users = new ArrayList<User>();
        users.add(user1);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        Workbook wb = new HSSFWorkbook();
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Sheet sheet = wb.createSheet("用户表");
        wb.createSheet("sheet2");
       /* sheet.createRow(0).setHeight((short) (400));*/
        sheet.setColumnWidth(0,5000);
        Row row = sheet.createRow(0);
        Row row2 = sheet.createRow(1);
        row.createCell(3).setCellValue("0.0");
        row.getCell(3).setCellStyle(cellStyle);
        for (int i = 0; i < title.size(); i++) {
            row.createCell(i).setCellValue(title.get(i));
            row.getCell(i).setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(0,1,i,i));
        }
        row2.createCell(3).setCellValue("设置");
        row2.createCell(4).setCellValue("设置");
        row2.createCell(5).setCellValue("设置");
       /* row.createCell(3).setCellStyle(cellStyle);*/
        sheet.addMergedRegion(new CellRangeAddress(0,0,3,5));
        sheet.addMergedRegion(new CellRangeAddress(0,1,6,6));
        short lastCellNum = sheet.getRow(1).getLastCellNum();
        System.out.println(lastCellNum);
        for (int i = 0; i < 3; i++) {
            Row row1 = sheet.createRow(i + 2);
            row1.createCell(0).setCellValue(users.get(i).getName());
            row1.createCell(1).setCellValue(users.get(i).getSex());
            row1.createCell(2).setCellValue(users.get(i).getAge());
        }
        OutputStream outputStream = null;
        outputStream = new FileOutputStream(new File("D://用户表.xls"));
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
