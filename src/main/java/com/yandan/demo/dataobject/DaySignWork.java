package com.yandan.demo.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create by yandan
 * 2020/11/12  22:24
 */
@Entity
@Data
public class DaySignWork {

    @Id
    private String signInId;
    private String collectId;
    private String workId;
    private String  date;
    private String time;
    private String departmentId;
    private Integer ifSign;//是否签到
    private Integer ifLate; // 是否迟到
    private String locationId;// 签到的地址
    private String info; //打卡收集的信息
    private String icon; //拍照打卡的照片url
    private String location;//打卡位置
}
