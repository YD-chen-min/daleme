package com.yandan.demo.repository;

import com.yandan.demo.dataobject.DaySignTimesWork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/19  8:49
 */
public interface DaySignTimesWorkRepository  extends JpaRepository<DaySignTimesWork,String> {
    DaySignTimesWork findByDateAndWorkId(String date,String workId);
    List<DaySignTimesWork> findByWorkId(String workId);
    List<DaySignTimesWork> findByDate(String date);
    List<DaySignTimesWork> findByDepartmentId(String departmentId);
    List<DaySignTimesWork> findByDepartmentIdAndDate(String departmentId,String date);

}
