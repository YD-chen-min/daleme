package com.yandan.demo.enums;

/**
 * Create by yandan
 * 2020/11/12  16:01
 */
public enum ErrorEnum {
    USER_IS_NOT_EXIST(1,"用户不存在，请核实要操作的工号"),
    USER_ALREADY_EXISTS(2,"用户已经存在"),
    USER_FORM_PARAM_ERROR(3,"添加用户参数错误"),
    USER_DELETE_ERROR(4,"用户删除失败"),
    WORK_ID_IS_EMPTY(5,"工号为空"),
    PARAM_ERROR(6,"参数错误"),
    USER_PHONE_EXIST(7,"手机号已经绑定其他用户"),
    COLLECTION_ALREADY_EXIST(8,"该名称的信息收集任务已存在"),
    LOCATION_ALREADY_EXIST(9,"该名称的打卡限制地址已存在"),
    LOCATION_IS_NOT_EXIST(10,"该打卡限制地址不存在"),
    LOCATION_INFO_IS_EMPTY(11,"打卡限制地址信息为空"),
    COLLECT_IS_NOT_EXIST(12,"该打卡任务不存在"),
    NOTICE_ALREADY_EXISTS(13,"该名称的公告已存在"),
    NOTICE_IS_NOT_EXISTS(14,"该公告不存在"),
    LIST_IS_EMPTY(15,"列表为空"),
    USER_MAC_ERROR(16,"用户设备错误"),
    USER_PASSWORD_ERROR(17,"用户密码错误"),
    DEPARTMENT_ID_IS_EMPTY(18,"部门为空"),
    DAY_SIGN_WORK_IS_NOT_EXISTS(19,"该记录不存在"),
    DAY_SIGN_TIMES_WORK_ALREADY_EXISTS(20,"该记录已存在"),
    DAY_SIGN_TIMES_DEPARTMENT_ALREADY_EXISTS(21,"该记录已存在"),
    DAY_SIGN_TIMES_DEPARTMENT_IS_NOT_EXISTS(22,"该记录不存在"),
    SIGN_TIMES_WORK_ALREADY_EXISTS(23,"该记录已经存在"),
    SIGN_TIMES_WORK_IS_NOT_EXISTS(24,"该记录不存在"),
    SIGN_TIMES_DEPARTMENT_ALREADY_EXISTS(23,"该记录已经存在"),
    SIGN_TIMES_DEPARTMENT_IS_NOT_EXISTS(24,"该记录不存在"),
    DAY_SIGN_WORK_ALREADY_EXISTS(25,"该记录已存在"),
    NOT_IN_BORDER(26,"不在打卡范围内")
    ;
    private Integer code;
    private String msg;

    ErrorEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
