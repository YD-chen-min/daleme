package com.yandan.demo.repository;

import com.yandan.demo.dataobject.AdminInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Create by yandan
 * 2020/11/22  14:54
 */
public interface AdminRepository extends JpaRepository<AdminInfo,String > {
}
