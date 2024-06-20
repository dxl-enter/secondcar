package com.dippy.service;

import java.io.File;
import java.util.ArrayList;

public class ReadFile {
    public ReadFile() {

    }

    public static ArrayList<String> getFiles(String filepath) {
        ArrayList<String> files = new ArrayList<String>();
        File file = new File(filepath);
        File[] tempLists = file.listFiles();
        for (int i = 0; i < tempLists.length; i++) {
            if (tempLists[i].isFile()) {
                files.add(tempLists[i].toString());
            }
        }

        for (int i = 0; i < files.size(); i++) {
            System.out.println(i + "  " + files.get(i));
        }
        return files;
    }

    public static void main(String[] args) {
        //添加文件路径
        // getFiles("E:\\workspace\\work\\my-sql-back\\ZBLCDX-ECS\\");
        String path = "E:\\workspace\\work\\src\\database\\oracle\\init_table\\sql\\";
        //文件存放路径
        File file = new File(path);
        String[] fileName = file.list();  //用于存放文件底下所有子文件名的数组
        int beginIndex = 0;//截取文件名的时候有用到
        int endIndex = 0;//截取文件名的时候有用到
        String[] studentName = new String[fileName.length];//用于存储提取出来的姓名的数组
        String[] FileType = new String[fileName.length];//用于存储提取出来的文件类型
        //先提取原文件名中有效的信息
        for (int i = 0; i < fileName.length; i++) {
            //根据原先的命名格式匹配出想要的信息（具体根据你们自己的情况咯！）
            endIndex = fileName[i].lastIndexOf(".");
            studentName[i] = fileName[i].substring(beginIndex, endIndex);//这是问价夹下文件名字的的范围
            // System.out.println(studentName[i]);
            FileType[i] = fileName[i].substring(endIndex, fileName[i].length());//这是文件类型的
            System.out.println("@" + "./sql/" + studentName[i] + FileType[i]);
        }
    }
}
