package com.yandan.demo.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 部门签到总表
 * Create by yandan
 * 2020/11/14  11:29
 */
@Entity
@Data
public class SignTimesDepartment {
    @Id
    private String departmentId;
    private Integer signInTimes;
    private Integer noSignTimes;
    private Integer lateTimes;
}
