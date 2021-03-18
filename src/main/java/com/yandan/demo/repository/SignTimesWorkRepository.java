package com.yandan.demo.repository;

import com.yandan.demo.dataobject.SignTimesWork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/19  23:06
 */
public interface SignTimesWorkRepository extends JpaRepository<SignTimesWork,String> {
    SignTimesWork findByWorkId(String workId);
    List<SignTimesWork> findByDepartmentId(String departmentId);

}
