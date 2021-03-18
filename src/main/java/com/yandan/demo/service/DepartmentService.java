package com.yandan.demo.service;

import com.yandan.demo.dataobject.DepartmentInfo;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/12  20:37
 */
public interface DepartmentService {
    List<DepartmentInfo> getDepartments();
    DepartmentInfo getDepartment(String departmentId);
}
