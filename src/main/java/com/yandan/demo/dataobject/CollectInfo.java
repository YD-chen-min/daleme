package com.yandan.demo.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create by yandan
 * 2020/11/12  15:20
 */
@Entity
@Data
public class CollectInfo {
    @Id
    private String collectId;
    private String collectName;
    private String workId;
    private Integer ifIcon;
    private String departmentId;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String locationId;
    private String info;
    private Integer dayTimes;
}
