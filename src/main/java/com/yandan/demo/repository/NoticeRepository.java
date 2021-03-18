package com.yandan.demo.repository;

import com.yandan.demo.dataobject.CompanyNotice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/16  20:10
 */
public interface NoticeRepository  extends JpaRepository<CompanyNotice,String> {
    CompanyNotice findByTitle(String title);
    Integer deleteByTitleIn(List<String> titles);
    List<CompanyNotice> findByDepartmentId(String departmentId);
}
