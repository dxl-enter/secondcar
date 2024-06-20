package com.dippy.util;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Kak on 2020/9/15.
 */
@Slf4j
public class QiniuUpload {
    //设置好账号的ACCESS_KEY和SECRET_KEY
    private static final String ACCESS_KEY = VariableNames.accessKey; //这两个登录七牛
    private static final String SECRET_KEY = VariableNames.secretKey;
    //要上传的空间
    private static final String bucketName = VariableNames.bucket; //对应要上传到七牛上
    //密钥配置
    private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
    private static Configuration cfg = new Configuration(Region.huanan());
    private static UploadManager uploadManager = new UploadManager(cfg);

    // 采用默认策略，只需设置存储空间名
    public static String getUpToken() {
        return auth.uploadToken(bucketName);
    }

    // 上传通用实现
    public static String uploadFile(MultipartFile file) {
        try {
            byte[] uploadBytes = file.getBytes();//文件流转字节流
            //获取随机文件名
            String fileName = getRandomCharacterAndNumber(10) + ".jpg";
            Response response = uploadManager.put(uploadBytes, fileName, getUpToken());
            //解析上传结果
            DefaultPutRet putPet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //外部访问的资源地址
            String resUrl = VariableNames.pubdomain + putPet.key;
            return resUrl;
        } catch (IOException e) {
            log.error("七牛云上传失败 : {}",e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    public static void deleteFile(String key) {
        //创建凭证
        BucketManager bucketManager = new BucketManager(auth, new Configuration());
        try {
            bucketManager.delete(bucketName, key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            log.error("七牛云文件删除失败:code = {}, msg = {}",ex.code(),ex.response.toString());
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }


    /**
     * 上传的辅助函数
     *
     * @param length
     * @return
     */
    private static String getRandomCharacterAndNumber(int length) {
        String val = "";
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num"; // 输出字母还是数字

            if ("char".equalsIgnoreCase(charOrNum)) // 字符串
            {
                int choice = random.nextInt(2) % 2 == 0 ? 65 : 97; // 取得大写字母还是小写字母
                val += (char) (choice + random.nextInt(26));
                // int choice = 97; // 指定字符串为小写字母
                val += (char) (choice + random.nextInt(26));
            } else if ("num".equalsIgnoreCase(charOrNum)) // 数字
            {
                val += String.valueOf(random.nextInt(10));
            }
        }
        return val;
    }


}
