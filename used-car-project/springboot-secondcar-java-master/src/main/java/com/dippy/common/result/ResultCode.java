package com.dippy.common.result;

/**
 * @Description: 返回码定义
 * 规定:
 * #200表示成功
 * #1001～1999 区间表示参数错误
 * #2001～2999 区间表示用户错误
 * #3001～3999 区间表示接口异常
 * #后面对什么的操作自己在这里注明就行了
 */
public enum ResultCode implements CustomizeResultCode{
    /* 成功 */
    SUCCESS(200, "成功"),

    /* 默认失败 */
    COMMON_FAIL(999, "失败"),

    /* 参数错误：1000～1999 */
    PARAM_NOT_VALID(1001, "参数无效"),
    PARAM_IS_BLANK(1002, "参数为空"),
    PARAM_TYPE_ERROR(1003, "参数类型错误"),
    PARAM_NOT_COMPLETE(1004, "参数缺失"),
    EXCEPTION_SQL(1005,"SQL异常"),

    /* 用户错误 */
    USER_NOT_LOGIN(2001, "用户未登录"),
    USER_ACCOUNT_EXPIRED(2002, "账号已过期"),
    USER_CREDENTIALS_ERROR(2003, "密码错误"),
    USER_CREDENTIALS_EXPIRED(2004, "密码过期"),
    USER_ACCOUNT_DISABLE(2005, "账号不可用"),
    USER_ACCOUNT_LOCKED(2006, "账号被锁定"),
    USER_ACCOUNT_NOT_EXIST(2007, "账号不存在"),
    USER_ACCOUNT_ALREADY_EXIST(2008, "账号已存在"),
    USER_ACCOUNT_USE_BY_OTHERS(2009, "账号下线"),


    // 1. 注册
    YES_USER_REGISTER(2010, "注册成功"),
    NO_USER_REGISTER(2011, "注册失败"),
    NO_USER_REGISTER_HAS_ROLE(2012,"该用户名已经有该角色了"),

    // 2. 密码
    NO_USER_PASSWORD_NO_SAME(2013,"两次输入的密码不一致"),

    // 3. 头像修改和上传
    YES_USER_AVATAR_UPLOAD(2014,"上传成功"),
    NO_USER_AVATAR_UPLOAD(2015,"上传失败"),

    YES_UPDATE_PASSWORD(2016,"密码修改成功！请重新登录"),
    NO_UPDATE_PASSWORD(2017,"密码修改失败！"),




    // 4. 用户退出了
    YES_USER_LOGOUT(2016, "退出成功"),
    YES_USER_LOGIN(2017, "登录成功"),
    // 用户修改信息
    YES_USER_UPDATE_INFO(2018,"修改成功"),
    NO_USER_UPDATE_INFO(2019,"修改失败"),
    NO_USER_EXCEPTION(2020,"用户异常！请退出后重新登录"),



    // 车辆信息
    YES_CAR_OF_PERSONAL(2021,"获取车辆信息成功"),
    NO_CAR_OF_PERSONAL(2022,"您没有在卖的车辆"),

    // 用户收藏的车辆
    YES_GET_ALL_COLLECT_CARS(2023,"获取收藏的车辆成功！"),
    NO_GET_ALL_COLLECT_CARS(2024,"您尚未过收藏车辆！"),

    // 收藏车辆
    YES_COLLECT_CAR(2025,"已收藏该车辆"),
    NO_COLLECT_CAR(2026,"已取消收藏"),


    // 新增汽车
    YES_ADD_CAR(2027,"新增汽车成功"),
    NO_ADD_CAR(2028,"新增汽车失败"),
    YES_ADD_CAR_PICTURE(2029,"图片上传成功"),
    NO_ADD_CAR_PICTURE(2030,"您尚未选择图片"),
    YES_DELETE_CAR(2031,"删除汽车成功"),
    NO_DELETE_CAR(2032,"删除汽车失败"),
    YES_UPDATE_CAR(2033,"修改汽车信息成功"),

    // 收藏的汽车列表
    YES_ALL_MY_COLLECT_CARS(2034,"加载我收藏的汽车完成"),
    NO_ALL_MY_COLLECT_CARS(2035,"您尚未收藏汽车"),



    /*错误*/
    DEPARTMENT_NOT_EXIST(3007, "不存在"),
    DEPARTMENT_ALREADY_EXIST(3008, "已存在"),



    /* 业务错误 */
    NO_PERMISSION(3001, "没有权限"),
    // 1. 查询汽车业务
    No_SEARCH_CAR_INFO(3002,"没有符合的车辆！请清空重新查！"),
    YES_SEARCH_CAR_INFO(3003,"加载汽车信息完成"),


    // 2. 文件上传失败
    YES_FILE_SUCCESS(3004,"文件上传成功！"),
    NO_FILE_FAIL(3005,"文件上传失败！文件为空，请重新上传"),
    No_FILE_EXCEPTION(3006,"文件上传发生异常！"),


    // 3. 地址相关
    YES_UPLOAD_ADDRESS(3301,"地址初始化成功"),
    YES_GET_ADDRESS(3302,"获取地址成功"),
    EXCEPTION_NO_ADDRESS(3303,"地址异常"),



    // 聊天列表
    YES_GET_CHATS(4000,"获取聊天列表"),
    NO_GET_CHATS(4001,"获取聊天列表失败"),





    /*运行时异常*/
    ARITHMETIC_EXCEPTION(9001,"算数异常"),
    //系统异常 Exception最大范围异常
    SYSTEM_EXCEPTION_ALL(9002,"系统繁忙,请联系管理员"),

    SYSTEM_ERROR_EXCEPTION(9000,"系统异常"),



    ;
    private Integer code;

    private String message;

    ResultCode(Integer code,String message){
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
