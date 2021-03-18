package com.yandan.demo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Create by yandan
 * 2020/11/14  15:09
 */
@Data
public class UserForm {
    @NotEmpty(message = "部门必填！")
    private String departmentId;
    @NotEmpty(message = "员工姓名必填！")
    private String name;
    @NotEmpty(message = "权限必填！")
    private String roleId;
    @NotEmpty(message = "密码必填！")
    private String password;
}
