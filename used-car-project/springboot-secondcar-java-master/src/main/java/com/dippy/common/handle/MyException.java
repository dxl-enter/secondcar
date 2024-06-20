package com.dippy.common.handle;

import io.swagger.annotations.ApiModelProperty;

/**
 * 自定义异常类
 */

public class MyException extends RuntimeException {

    @ApiModelProperty(value = "状态码")
    private Integer code;

    @ApiModelProperty(value = "错误信息")
    private String errMsg;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }

    public MyException(Integer code, String errMsg) {
        this.code = code;
        this.errMsg = errMsg;
    }

    public MyException(String message, Integer code, String errMsg) {
        super(message);
        this.code = code;
        this.errMsg = errMsg;
    }

    public MyException(String message, Throwable cause, Integer code, String errMsg) {
        super(message, cause);
        this.code = code;
        this.errMsg = errMsg;
    }

    public MyException(Throwable cause, Integer code, String errMsg) {
        super(cause);
        this.code = code;
        this.errMsg = errMsg;
    }

    public MyException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Integer code, String errMsg) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.errMsg = errMsg;
    }
}
