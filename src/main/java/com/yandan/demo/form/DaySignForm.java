package com.yandan.demo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Create by yandan
 * 2020/11/17  23:09
 */
@Data
public class DaySignForm {
    @NotEmpty(message = "信息收集Id必填！")
    private String collectId;
    @NotEmpty(message = "员工工号必填！")
    private String workId;
    private String info;
    @NotEmpty(message = "提交时间必填！")
    private String date;
    private String icon;
    @NotEmpty(message = "记录id必填！")
    private String daySignId;
    private String location;
}
