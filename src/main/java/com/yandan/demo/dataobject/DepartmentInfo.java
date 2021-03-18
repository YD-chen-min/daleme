package com.yandan.demo.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create by yandan
 * 2020/11/12  15:10
 */
@Entity
@Data
public class DepartmentInfo {
    @Id
    private String departmentId;
    private String name;
    private Integer workerSum;
}
