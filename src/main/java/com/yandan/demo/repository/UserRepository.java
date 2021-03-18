package com.yandan.demo.repository;

import com.yandan.demo.dataobject.WorkInfo;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/12  15:24
 */
public interface UserRepository extends JpaRepository<WorkInfo,String> {
    List<WorkInfo> findByDepartmentIdIn(List<String> departmentIds);
    WorkInfo findByPhoneNumber(String phoneNumber);
    WorkInfo findByNameAndDepartmentId(String name,String departmentId);
}
