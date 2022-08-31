package com;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.*;

public class Dao {


    public static void main(String[] args) throws IOException {
        File file = new File("D:\\测试职位导入模板.xlsx");
        if (file.exists()){
            file.delete();
        }

        String path = Dao.class.getClassLoader().getResource("./templates/职位导入模板.xlsx").getPath();
        path = java.net.URLDecoder.decode(path,"utf-8");
        FileInputStream inputStream = new FileInputStream(path);
        FileOutputStream outputStream = new FileOutputStream(file);
        Workbook wb = WorkbookFactory.create(inputStream);
        wb.write(outputStream);
        outputStream.flush();
        outputStream.close();
    }
}
