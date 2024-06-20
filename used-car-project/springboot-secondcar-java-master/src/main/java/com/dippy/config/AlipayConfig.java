package com.dippy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */
@Configuration
@ConfigurationProperties(prefix = "alipayconfig", ignoreUnknownFields = false)
@PropertySource("classpath:alipay.properties")//读取配置文件
@Component
public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    private String app_id = "2016110300788834";

    // 商户私钥，您的PKCS8格式RSA2私钥
    private String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQC7rbWKpFJQi7MD94h4awhZtIcCPrAmQlANlagKHYZ5CMEUBNyOxkQKE+GtJVloKxz3wBIvWk4zRj1/05WivKA1Z5BlYvOPbMnRorxoFiP4t1vXWPuSkUtj1yTmLW46Y27hU6oSOiFlfta4wliHy1iCdeJ+19Q15gvnOpAhIL096uhgwMaZWhZ7aQ5QGgRmr9SlFppR/zr29WMlz4LuA/SZsx2llSSAUxQ7J3zYPs/i6Js8Gy8JtSFk/rRmLWCCcratFDWiB1w2wjO9ZieJOfdAqY7xRUpxgKr5lZC3GMVm0WQVV/nifAQqnrsehnwAfmbkeuS359yCIRKP/Ilb3ZJzAgMBAAECggEBAJCbB4YLlmD5QrWLxFsEHSsBLgVMx+yyq3vME+23UiVv2VTTah2YJlSdfQUsAfeBQJCkLV7O9fIL7NbQ8Dqzm72KTnfQBlo8OESOXxzT8mNw5BLJPQ1xZeVHn+a6mnPiqUx0alYg9Bp/4HH7TLtUsp3StvGE2r2swv8m4GamEyngw9O3qf21ZnPIZ5gpy0AddyH6uFEaKUP2cGPz98EF7VGOvbCQOQ10/tAYYsM6h0ySKc53KBE0xxxiRtjDsmcsOQTE6APfVmH9h+8CJ4FqjcAES2gBZbRqVuPvvJpHEwHMwKKCrFVWjRSNTGTVBBr/4+ptEmqTG8arWHeZ1t8fbAECgYEA333ITKARAsMNOa0VmYm5WuAG4kjUZGH6WoYg7hU2e1W9d/20oRoIe7A0MHPhAfZpEoxOSo1iLfmoYxbaORKEuZsVEHj05mO7E2NJN5CM1s2C/eQL9rtmVXj5CKpNh2CaNUKaK2uOI9zVVGCfg0y7oVOLAm7jRKk4ZA1RE0MVppkCgYEA1vpazMPqJqeS2am+J3y3QAS6QJL4QDQhYRFiMUsGWA0lQ/8WKbKhfUMgdxS5ktX7M9Y+gIJXscqE3IdnNstFwR78UopfjIem347y972IYr+hzdNnp8xoy9vAYbZ381pqHGnsdCSAW7SB2lIC/fl/8C429PvuLFHlmoqGuUvFROsCgYEAtRKJ+FsvrRcvXe14NJ+7R39+1FPBkVs4paNKhts0+xRqoA80C9q1yNDoV+FrClYEp3x56jlGtI0z+qSHRfpVArQ9is0VTpy682hb5+L72QVE/kOH1e3baaDL1FN24RpJlqFRm6BW14OSa/ylAJJo6rpvRBdnlzercpS/fWW64vECgYBH7RWKIxZpfnGUn8U+YuzQ34QNbN7gZFCysGVBBtAFj8hjGqqootqDFX18OhPou+1DJ7BbwMBkfNS9EBSJcbNR7PMRDrjvRMehTqueFezW1TTmr4+jxbFeW5TfXHR/mEWeYhY5uai0hg1YJU5Y+SxECMJcbxn9/Bys+yIeRuqmpwKBgQCK0WTxLRQftH27gTjh52lX6sHFLDNkxrhCAePTR31Zv8Wuvj0JgGWK8G50NsxmPT1Vzd6KmzfgciC1UngwZuwzesXJFTGDtKShaTd2LhE5ozQWUjLErm32KfAhtpeuDAIGuclxTc0r5U9Be8nvmLLssYnXnFeEP4JAKnV3yV3eIg==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    private String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAhQnacJLoHqldRtX7mD2+YiSnB4BJ8asRSdS1Kxtw6wpxmJLwLmIOz26ShBQQzHq+5qGQLwirNEdY/0IHTgZhJm9gHRHP2z6Tf4mCDeWVDT/kSy891DEC54Wrq0rMWyQZ/nGMiTQEw31xmX/IL1HukN1OzdnEF1G9lLFbyU8eu1i4kA+KvSCOMOmf8g69wZ/WMTBrSSfBpyAkv3dH1HBoGH6RL8ltBn6peqqBi85rNmVxbypVuvTwFB8Emn8zj9yv41HwV6qZWo67uHADRWgx5nld5KiTz2Zs8B4RmSKFReiNZ5cgxeGPv0WJi4Xn4sj4cbnKXAm1fS7rssPJZlCXmQIDAQAB";

    /**
     * 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
     * 需要改成自己的内网穿透地址
     */
    private String notify_url = "http://kqzhrc.natappfree.cc/sysOrder/notify_url";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    private String return_url = "http://localhost:8080/yueChi/sysUser/myInfo";

    // 签名方式
    private String sign_type = "RSA2";

    // 字符编码格式
    private String charset = "utf-8";

    // 支付宝网关
    private String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

    //返回格式
    private String format = "json";

    // 支付宝网关
    private String log_path = "logs/alipay";

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getMerchant_private_key() {
        return merchant_private_key;
    }

    public void setMerchant_private_key(String merchant_private_key) {
        this.merchant_private_key = merchant_private_key;
    }

    public String getAlipay_public_key() {
        return alipay_public_key;
    }

    public void setAlipay_public_key(String alipay_public_key) {
        this.alipay_public_key = alipay_public_key;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getReturn_url() {
        return return_url;
    }

    public void setReturn_url(String return_url) {
        this.return_url = return_url;
    }

    public String getSign_type() {
        return sign_type;
    }

    public void setSign_type(String sign_type) {
        this.sign_type = sign_type;
    }

    public String getCharset() {
        return charset;
    }

    public void setCharset(String charset) {
        this.charset = charset;
    }

    public String getGatewayUrl() {
        return gatewayUrl;
    }

    public void setGatewayUrl(String gatewayUrl) {
        this.gatewayUrl = gatewayUrl;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getLog_path() {
        return log_path;
    }

    public void setLog_path(String log_path) {
        this.log_path = log_path;
    }

//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /**
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     *
     * @param sWord 要写入日志里的文本内容
     */
    public void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(this.log_path + "alipay_log_" + System.currentTimeMillis() + ".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
