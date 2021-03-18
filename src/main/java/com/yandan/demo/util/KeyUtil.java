package com.yandan.demo.util;

import java.util.Random;

/**
 * Create by yandan
 * 2020/11/14  15:30
 */
public class KeyUtil {
    public static String getKey(int n){
        Random random=new Random();
        int bounder= (int)Math.pow(10,n);
        Integer num=random.nextInt(bounder)+bounder;
        String numStr=String.valueOf(num);
        return numStr.substring(1,numStr.length());
    }
}
