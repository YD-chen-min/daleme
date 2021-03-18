package com.yandan.demo.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Create by yandan
 * 2020/11/14  10:52
 */
@Entity
@Data
public class DaySignTimesDepartment{
    @Id
    private String id;
    private String departmentId;
    private String date;
    private Integer signInTimes;//签到次数
    private Integer noSignTimes;//未签到次数
    private Integer lateTimes;//迟到次数
}
