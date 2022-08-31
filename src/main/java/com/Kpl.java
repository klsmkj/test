package com;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


public class Kpl {
    public static void main(String[] args) throws Exception {
        /*File f = new File("D:"+File.separator+"test.xlsx");*/
        User user = new User();
        User user1 = new User();
        User user2 = new User();
        user1.setAge(0);
        user2.setAge(20);
        user.setAge(30);
        List<User> users =new ArrayList<>();
        users.add(user);
        users.add(user1);
        users.add(user2);
        List<User> collect = users.stream().filter(user3 -> user3.getAge() == 0).collect(Collectors.toList());
        for (User user3 : collect) {
            System.out.println(user3.getAge());
        }

    }

}
