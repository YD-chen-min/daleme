package com.yandan.demo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Create by yandan
 * 2020/11/14  20:10
 */
@Data
public class CollectForm {
    @NotEmpty(message = "打卡任务名称必填！")
    private String name;
    private String locationId="";
    private String health;
    @NotEmpty(message = "开始日期必填！")
    private String startDate;
    @NotEmpty(message = "开始时间必填！")
    private String startTime;
    @NotEmpty(message = "结束日期必填！")
    private String endDate;
    @NotEmpty(message = "结束时间必填！")
    private String endTime;
    private String workId="";
    private String departmentId="";
    @NotEmpty(message = "打卡方式必填！")
    private String ifIcon;
    private String dayTimes;
    private String address;
}
