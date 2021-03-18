package com.yandan.demo.VO;

import lombok.Data;

/**
 * Create by yandan
 * 2020/11/14  20:29
 */
@Data
public class CollectInfoVO {
    private String collectId;
    private String collectName;
    private String work;
    private Integer ifIcon;
    private String departmentName;
    private String departmentId;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;
    private String locationName;
    private String locationId;
    private String info;
    private Integer dayTimes;
    private String address;
}
