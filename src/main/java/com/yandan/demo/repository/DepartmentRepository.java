package com.yandan.demo.repository;

import com.yandan.demo.dataobject.DepartmentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by yandan
 * 2020/11/12  20:36
 */
public interface DepartmentRepository extends JpaRepository<DepartmentInfo,String> {
    DepartmentInfo findByName(String name);
}
