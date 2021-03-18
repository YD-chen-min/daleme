package com.yandan.demo.VO;

import lombok.Data;

/**
 * Create by yandan
 * 2020/11/16  19:57
 */
@Data
public class NoticeVO {
    private String noticeId;
    private String departmentId;
    private String departmentName;
    private String type;
    private String title;
    private String noticeContents;
    private String date;
}
