package com.yandan.demo.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Create by yandan
 * 2020/11/16  21:07
 */
@Data
public class NoticeForm {
    @NotEmpty(message = "标题必填！")
    private String title;
    @NotEmpty(message = "内容必填！")
    private String content;
    private String departmentId;
    @NotEmpty(message = "日期必填！")
    private String date;
    @NotEmpty(message = "类型必填！")
    private String type;
}
