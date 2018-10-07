package com.mine.common;

import java.util.Random;

public class PubGetColorUtil {

    /*** 获取十六进制的颜色代码.例如  "#6E36B4" , For HTML ,* @return String*/
    public static String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();
        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;
        return "#" + r + g + b;
    }

    public static void main(String[] args){
        String randColorCode = PubGetColorUtil.getRandColorCode();
        System.out.println(randColorCode);
        String[] arr =  {"#D01F3C","#356AA0","#C79810"};
        String randOnlyColorCode = PubGetColorUtil.getOnlyRandColorCode(arr);
        System.out.println(randOnlyColorCode);
    }

    public static String getOnlyRandColorCode(String[] arr){
        String randColorCode = PubGetColorUtil.getRandColorCode();
        boolean flag = true;
        String colorCode = "";
        while(colorCode.equals("")){
            for(int i = 0 ;i < arr.length; i ++ ){
                if(randColorCode.equals(arr[i])){
                    flag = false;
                    break;
                }
            }
            if(flag){
                colorCode = randColorCode;
            }else{
                randColorCode = PubGetColorUtil.getRandColorCode();
            }
        }
        return colorCode;
    }
}
