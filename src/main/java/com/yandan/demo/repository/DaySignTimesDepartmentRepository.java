package com.yandan.demo.repository;

import com.yandan.demo.dataobject.DaySignTimesDepartment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/19  10:32
 */
public interface DaySignTimesDepartmentRepository   extends JpaRepository<DaySignTimesDepartment,String>  {
    DaySignTimesDepartment findByDepartmentIdAndDate(String departmentId,String date);
    List<DaySignTimesDepartment> findByDepartmentId(String departmentId);
    List<DaySignTimesDepartment> findByDate(String date);
}
