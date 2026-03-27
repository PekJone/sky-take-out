package com.java.algo;

/**
 * @author 王朋飞
 * @version 1.0
 * @date 2026-03-27  17:02
 */
public class MyParseInt {

    public static int myParseInt(String s ){
        if(s==null || s.length()==0){
            throw new NumberFormatException("输入字符串不能为空");
        }

        int index = 0;
        int sign = 1;
        int result = 0 ;
        int len = s.length();

        //处理正负号
        char first = s.charAt(0);
        if(first =='+'||first=='-'){
            sign =first=='-'?-1:1;
            index++;
        }

        for (;index<len;index++){
            char c = s.charAt(index);
            if(c<'0'||c>'9'){
                throw new NumberFormatException("输入字符串包含非数字字符");
            }
            int digit = c - '0';
            result = result*10+digit;
        }
        return result*sign;
    }

    public static void main(String[] args) {
        System.out.println(myParseInt("123"));   // 123
        System.out.println(myParseInt("-456"));  // -456
        System.out.println(myParseInt("+789"));  // 789
    }
}
