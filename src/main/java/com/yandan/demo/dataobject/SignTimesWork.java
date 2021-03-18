package com.yandan.demo.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 个人签到总表
 * Create by yandan
 * 2020/11/14  11:33
 */
@Entity
@Data
public class SignTimesWork {
    @Id
    private String workId;
    private String departmentId;
    private Integer signInTimes;
    private Integer noSignTimes;
    private Integer lateTimes;
}
