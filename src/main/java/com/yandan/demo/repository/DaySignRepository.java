package com.yandan.demo.repository;

import com.yandan.demo.dataobject.DaySignWork;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/12  22:45
 */
public interface DaySignRepository extends JpaRepository<DaySignWork,String> {
    List<DaySignWork> findByWorkId(String workId);
    List<DaySignWork> findByDepartmentId(String departmentId);
    List<DaySignWork> findByDate(String date);
    List<DaySignWork> findByCollectId(String collectId);
    List<DaySignWork> findByCollectIdAndDate(String collectId,String date);
    List<DaySignWork> findByDepartmentIdAndDate(String departmentId,String date);
    DaySignWork findByCollectIdAndDateAndWorkId(String collectId,String date,String workId);
    List<DaySignWork> findByWorkIdAndDate(String workId,String date);
    List<DaySignWork> findByDateAndCollectIdAndIfSign(String date,String collectId,Integer ifSign);
    List<DaySignWork> findByDateAndIfSign(String date,Integer ifSign);
    List<DaySignWork> findByWorkIdAndIfSign(String workId,Integer IfSign);
}
