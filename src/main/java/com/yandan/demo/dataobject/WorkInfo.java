package com.yandan.demo.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create by yandan
 * 2020/11/12  12:33
 */
@Entity
@Data
public class WorkInfo {
    @Id
    private String workId;
    private String name;
    private String phoneNumber;
    private String password;
    private String icon;
    private String departmentId;
    private String mac;
    private Integer isAdmin;
}
