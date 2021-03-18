package com.yandan.demo.VO;

import lombok.Data;

/**
 * Create by yandan
 * 2020/11/17  20:25
 */
@Data
public class DaySignWorkVO {
    private String signInId;
    private String collectId;
    private String work;
    private String  date;
    private String time;
    private String departmentId;
    private String ifSign;//是否签到
    private String ifLate; // 是否迟到
    private String locationId;// 签到的地址
    private String info; //打卡收集的信息
    private String icon; //拍照打卡的照片url
    private String locationName;
    private String departmentName;
    private String collectName;
    private String collectType;
    private String location;//打卡位置
    private String workId;
    private String collectInfo;
    private String address;
}
