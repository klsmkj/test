package com;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiPredicate;

public class CompareObjectValue {

    /**
     * 比较两个对象属性值是否相同
     *
     * @param obj1         比较对象1
     * @param obj2         比较对象2
     * @param cls          对象class
     * @param ignoreFields 忽略字段
     * @return true 相等
     */
    public static boolean compareTwoObject(Object obj1, Object obj2, Class cls, String[] ignoreFields) {
        boolean isEqual = true;
        BiPredicate biPredicate = new BiPredicate() {
            @Override
            public boolean test(Object object1, Object object2) {
                Object obj1 = object1 == null ? "" : object1;
                Object obj2 = object2 == null ? "" : object2;
                //BigDecimal 类型比较数值使用字符串不太合适，其它特殊类型可以继续添加
                if (obj1 instanceof BigDecimal && obj2 instanceof BigDecimal) {
                    return ((BigDecimal) object1).compareTo((BigDecimal) object2) == 0;
                }
                if (obj1.equals(obj2)) {
                    return true;
                }
                return false;
            }
        };
        try {
            List<String> ignoreFieldList = Arrays.asList(ignoreFields);
            Field[] fields = cls.getDeclaredFields();
            for (Field field : fields) {
/*                if (ignoreFieldList.contains(field.getName())) {
                    continue;
                }*/
                PropertyDescriptor pd = new PropertyDescriptor(field.getName(), cls);
                Method getMethod = pd.getReadMethod();
                Object o1 = getMethod.invoke(obj1);
                Object o2 = getMethod.invoke(obj2);
                isEqual = biPredicate.test(o1, o2);
                if (!isEqual) {
                    break;
                }
            }
        } catch (Exception e) {
            isEqual = false;
        }
        return isEqual;
    }

    public static void main(String[] args) {
        User user1 = new User();
        User user2 = new User();
        user1.setAge(10);
        user1.setName("蔡徐坤");
        user1.setSex("男");
        user2.setAge(10);
        user2.setName("蔡徐坤");
        user2.setSex("女");
        String [] list = {};
        if (compareTwoObject(user1,user2,User.class,list)){
            System.out.println("属性值相同");
        } else {
            System.out.println("属性值不相同");
        }
    }



}
