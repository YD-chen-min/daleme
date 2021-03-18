package com.yandan.demo.enums;

/**
 * Create by yandan
 * 2020/11/12  16:31
 */
public enum RoleEnum {
    COMMON_USER(0,"普通用户"),
    ADMIN_USER(1,"管理员用户")
    ;
    private Integer id;
    private String name;

    RoleEnum(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
