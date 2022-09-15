package com;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lkp
 * @describe
 * @date 2022/9/15
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person implements Comparable<Person> {
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 姓名
     */
    private String name;

    @Override
    public int compareTo(Person o) {
        int cmp = age - o.age;
        return cmp != 0 ? cmp : name.compareTo(o.name);
    }
}
