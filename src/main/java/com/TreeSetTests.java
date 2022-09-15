package com;

import java.util.TreeSet;

/**
 * @author lkp
 * @describe
 * @date 2022/9/15
 */
public class TreeSetTests {
    public static void main(String[] args) {
        TreeSet<Person> people = new TreeSet<Person>();
        people.add(new Person(18,"zhangsan"));
        people.add(new Person(20,"wangwu"));
        people.add(new Person(20,"lisi"));
        System.out.println(people.size());
        System.out.println(people);
    }
}
