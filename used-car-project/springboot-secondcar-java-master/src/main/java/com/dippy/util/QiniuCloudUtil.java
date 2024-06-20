package com.dippy.util;

import cn.hutool.core.util.StrUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.io.IOException;


/**
 * 本项目暂没有用这个工具类，用的是QiniuUpload
 */
public class QiniuCloudUtil {

    // 设置需要操作的账号的AK和SK
    private static final   String accessKey = "rvp99RfE5JthhjERgGzUYdHZgRbjeH7wdnib81LI";
    private static final String secretKey = "pMG7aFilYOZp6hDVS0p1T2jRvZ5pJfPGua-hcBcx";

    // 要上传的空间

    private static String bucketName = "yuechiperson";

    // 密钥
    private static  Auth auth = Auth.create(accessKey, secretKey);

    //如果是Windows情况下，格式是 D:\\qiniu\\test.png
    // private static final String DOMAIN = "E:\\Picture\\超清壁纸\\00e18857242a2a6bc01b0be15a8c088a.jpg";

    // private  String style = "自定义的图片样式";

    public static  String getUpToken() {
        return auth.uploadToken(bucketName, null, 3600, new StringMap().put("insertOnly", 1));
    }

    // 普通上传
    public static String upload(String filePath, String fileName) throws IOException {

        Configuration cfg = new Configuration(Region.region2()); // Region.region2() =》 华南地区
        // 创建上传对象
        UploadManager uploadManager = new UploadManager(cfg);
        try {
            // 调用put方法上传
            String token = auth.uploadToken(bucketName);
            //判断字符串为空的工具类，可用自己的
            if (StrUtil.isEmpty(token)) {
                System.out.println("未获取到token，请重试！");
                return "未获取到token，请重试！";
            }
            Response res = uploadManager.put(filePath, fileName, token);
            // 打印返回的信息
            System.out.println(res.bodyString());
            if (res.isOK()) {
                Ret ret = res.jsonToObject(Ret.class);
                //如果不需要对图片进行样式处理，则使用以下方式即可
                return filePath + ret.key;
                // return filePath + fileName + ret.key + "?" + style;
            }
        } catch (QiniuException e) {
            Response r = e.response;
            // 请求失败时打印的异常的信息
            System.out.println(r.toString());
            try {
                // 响应的文本信息
                System.out.println(r.bodyString());
            } catch (QiniuException e1) {
                // ignore
            }
        }
        return null;
    }


    //base64方式上传
    public static String put64image(byte[] base64, String key) throws Exception {
        String file64 = Base64.encodeToString(base64, 0);
        Integer l = base64.length;
        String url = "http://upload.qiniu.com/putb64/" + l + "/key/" + UrlSafeBase64.encodeToString(key);
        //非华东空间需要根据注意事项 1 修改上传域名
        RequestBody rb = RequestBody.create(null, file64);
        Request request = new Request.Builder().
                url(url).
                addHeader("Content-Type", "application/octet-stream")
                .addHeader("Authorization", "UpToken " + getUpToken())
                .post(rb).build();
        //System.out.println(request.headers());
        OkHttpClient client = new OkHttpClient();
        okhttp3.Response response = client.newCall(request).execute();
        System.out.println(response);
        //如果不需要添加图片样式，使用以下方式
        return key;
        // return  key + "?" + style;
    }


    // 普通删除(暂未使用以下方法，未测试)
    public void delete(String key) throws IOException {
        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.region2()); // Region.region2() =》 华南地区
        // 实例化一个BucketManager对象
        BucketManager bucketManager = new BucketManager(auth,cfg);
        // 此处的6666是去掉：http://你的图片上传路径/,剩下的key就是图片在七牛云的名称
        key = key.substring(6666);
        try {
            // 调用delete方法移动文件
            bucketManager.delete(bucketName, key);
        } catch (QiniuException e) {
            // 捕获异常信息
            Response r = e.response;
            System.out.println(r.toString());
        }
    }

    class Ret {
        public long fsize;
        public String key;
        public String hash;
        public int width;
        public int height;
    }
}

