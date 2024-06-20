package com.dippy.mq;

import java.io.*;

public class CharacterConvertor {


    /**
     * 实现将一个源文件从一种特定编码转换为另一种特定编码并存储到指定的目录中
     *
     * @param source     源文件
     * @param dir        目标目录
     * @param oldCharset 源文件的编码(GBK)
     * @param newCharset 新文件的编码(UTF-8)
     * @throws IOException
     */
    public static void convertor(File source, File dir, String oldCharset, String newCharset) throws IOException {

        //获取源文件的字节输入流
        FileInputStream fis = new FileInputStream(source);
        //获取新文件的字节输出流
        FileOutputStream fos = new FileOutputStream(new File(dir, source.getName()));

        //将源文件的字节输入流使用源文件的编码转换为字符输入流
        InputStreamReader isr = new InputStreamReader(fis, oldCharset);
        //将目标文件的字节输出流使用特定的编码转换为字符输出流
        OutputStreamWriter osw = new OutputStreamWriter(fos, newCharset);

        char[] c = new char[512];
        int len = 0;
        while ((len = isr.read(c)) != -1) {
            osw.write(c, 0, len);
            osw.flush();
        }
        osw.close();
        isr.close();
    }

    /**
     * 将整个工作空间中的所有java文件全部转码为UTF-8，要求保持原来的目录结构
     *
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        //准备源文件
        File f1 = new File("E:\\workspace\\work\\my-sql-back\\DINGHAOLIN.sql");
        //准备目标目录
        File f2 = new File("E:\\workspace\\work");
        String oldCharset ="UTF-8";
        String newCharset ="GBK";

        //转换
        convertor(f1, f2, oldCharset,newCharset );
    }
}
