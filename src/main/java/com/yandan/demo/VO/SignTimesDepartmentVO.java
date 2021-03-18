package com.yandan.demo.VO;

import lombok.Data;

/**
 * Create by yandan
 * 2020/11/21  17:09
 */
@Data
public class SignTimesDepartmentVO {
    private String departmentName;
    private Integer signInTimes;
    private Integer noSignTimes;
    private Integer lateTimes;
}
