package com.zust.playandroid.utils;

import android.graphics.Color;

import java.util.Random;

/**
 * 作 者： ZUST_YTH
 * 日 期： 2018/5/17
 * 时 间： 19:09
 * 项 目： PlayAndroid
 * 描 述：
 */


public class CommonUtil {
    public static int randomColor() {
        Random random = new Random();
        //0-190, 如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int red =random.nextInt(150);
        //0-190
        int green =random.nextInt(150);
        //0-190
        int blue =random.nextInt(150);
        //使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
        return Color.rgb(red,green, blue);
    }
}
