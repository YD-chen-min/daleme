package com.yandan.demo.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Create by yandan
 * 2020/11/14  11:03
 */
@Entity
@Data
public class DaySignTimesWork {
    @Id
    private String id;
    private String workId;
    private String date;//当日日期
    private String departmentId;
    private Integer signInTimes;//当日签到次数
    private Integer noSignTimes;//当日没签到次数
    private Integer lateTimes;//当日迟到次数
    private Integer totalTimes;//总计签到次数
}
