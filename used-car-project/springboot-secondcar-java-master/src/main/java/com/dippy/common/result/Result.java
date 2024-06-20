package com.dippy.common.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @NoArgsConstructor ： 生成一个无参数的构造方法
 * @AllArgsContructor： ?会生成一个包含所有变量
 * @RequiredArgsConstructor： 会生成一个包含常量，和标识了NotNull的变量的构造方法。生成的构造方法是私有的private。
 */
//返回的结果集
@Data
@NoArgsConstructor
@AllArgsConstructor
// @RequiredArgsConstructor
public class Result implements Serializable {

    private Integer code;// 状态码，200为正常，非200为异常
    private String msg;//返回的信息
    private Object data;//返回数据


    public static Result succ() {
        return succ(null);
    }

    public static Result succ(String msg)  {
        return succ(200,msg,null);
    }
    public static Result succ(Object data)  {
        return succ(200,"操作成功",data);
    }

    public static Result succ(Integer code,String msg)  {
        return succ(code,msg,null);
    }

    public static Result succ(String msg, Object data)  {
        return succ(200,msg,data);
    }

    // 操作成功返回的数据
    public static Result succ(Integer code,String msg, Object data)  {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    public static Result fail(String msg)  {
        return fail(400,msg,null);
    }
    public static Result fail(Integer code,String msg)  {
        return fail(code,msg,null);
    }

    public static Result fail(Integer code)  {
        return fail(code,"失败",null);
    }

    public static Result fail(String msg, Object data)  {
        return fail(400,msg,data);
    }
    /**
     * 失败返回的数据集
     * @param code
     * @param msg
     * @param data
     * @return
     */
    public static Result fail(Integer code,String msg, Object data)  {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }


    public static Result error(Integer code,String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(null);
        return result;
    }


}
