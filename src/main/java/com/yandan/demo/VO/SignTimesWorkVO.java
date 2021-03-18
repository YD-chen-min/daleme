package com.yandan.demo.VO;

import lombok.Data;

/**
 * Create by yandan
 * 2020/11/21  17:07
 */
@Data
public class SignTimesWorkVO {
    private String workName;
    private Integer signInTimes;
    private String departmentName;
    private Integer noSignTimes;
    private Integer lateTimes;
}
