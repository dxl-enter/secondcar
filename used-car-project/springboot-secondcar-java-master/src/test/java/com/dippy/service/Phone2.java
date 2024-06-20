package com.dippy.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Phone2 {
    //移动电话号码前三位
    public static final String[] YD = {
            "134", "135", "136",
            "137", "138", "139",
            "150", "151", "152",
            "157", "158", "159",
            "180", "181", "182",
            "183", "184", "185",
            "174", "192", "178",
            // "134"

    };
    //电信号码前三位
    public static final String[] DX = {
            "133", "149", "153",
            "173", "177", "180",
            "181", "189", "199"
    };
    //联通号码前三位
    public static final String[] LT = {
            "130", "131", "132",
            "145", "155", "156",
            "166", "171", "175",
            "176", "185", "186", "166"
    };

    public static void main(String[] args) {
        try {
            writer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writer() throws IOException {
        FileOutputStream fos = new FileOutputStream(new File("E:\\78109921.txt"));
        Scanner scanner = new Scanner(System.in);
        int count = 0;
        while (true) {
            //写入移动
            fos.write(scyall().toString().getBytes());

            //累加计数
            count++;
            //判断是否换行
            if (count % 3 == 0) {
                if (count % 1000 == 0) {
                    String next = scanner.next();
                    if (next.equals("q")) {
                        fos.close();
                        System.out.println(count);
                        break;
                    }
                }
                //每行9个电话号码换行
                fos.write("\n".getBytes());
                // System.out.println("a");

            } else {
                fos.write("\t".getBytes());
            }
        }
    }

    public static StringBuilder scyall() {
        //定义随机数
        Random random = new Random();
        //从移动号码规则里面随机一个号码前三位
        int i = random.nextInt(YD.length);
        // int n = random.nextInt(LT.length);
        // int w = random.nextInt(DX.length);
        //随机号码的第4位数字
        // int i1 = random.nextInt(10);
        int i1 = 7;
        //随机号码的第5位数字
        int i2 = 8;
        //随机号码的第6位数字
        int i3 = 1;
        //随机号码的第7位数字
        int i4 = 0;
        //随机号码的第8位数字
        int i5 = 9;
        //随机号码的第9位数字
        int i6 = 9;
        //随机号码的第10位数字
        int i7 = 2;
        //随机号码的第11位数字
        int i8 = 1;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(YD[i]).append(i1).append(i2).append(i3).append(i4).append(i5).append(i6).append(i7).append(i8).append("\t");
        // stringBuilder.append(DX[w]).append(i1).append(i2).append(i3).append(i4).append(i5).append(i6).append(i7).append(i8).append("\t");
        // stringBuilder.append(LT[n]).append(i1).append(i2).append(i3).append(i4).append(i5).append(i6).append(i7).append(i8);
        return stringBuilder;
    }
}
