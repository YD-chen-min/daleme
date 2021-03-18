package com.yandan.demo.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create by yandan
 * 2020/11/14  10:49
 */
@Entity
@Data
public class CompanyNotice {
    @Id
    private String noticeId;
    private String departmentId;
    private String type;
    private String title;
    private String noticeContents;
    private String date;

}
