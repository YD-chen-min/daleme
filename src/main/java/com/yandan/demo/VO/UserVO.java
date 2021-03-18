package com.yandan.demo.VO;

import lombok.Data;

/**
 * Create by yandan
 * 2020/11/12  15:13
 */
@Data
public class UserVO {
    private String workId;
    private String name;
    private String phoneNumber;
    private String icon;
    private String mac;
    private Integer isAdmin;
    private String departmentId;
    private String departmentName;
    private boolean messageFinish;
    private boolean oldPassword;
}
