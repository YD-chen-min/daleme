package com.yandan.demo.exception;

import com.yandan.demo.enums.ErrorEnum;

/**
 * Create by yandan
 * 2020/11/12  16:47
 */
public class ApiException extends Exception{
    private Integer code;
    public ApiException(ErrorEnum errorEnum){
        super(errorEnum.getMsg());
        code=errorEnum.getCode();
    }
    public Integer getCode(){
        return this.code;
    }
}
