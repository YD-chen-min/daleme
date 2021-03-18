package com.yandan.demo.repository;

import com.yandan.demo.dataobject.CollectInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/14  20:16
 */
public interface CollectRepository extends JpaRepository<CollectInfo,String> {
    Integer deleteByCollectNameIn(List<String> names);
    CollectInfo findByCollectName(String collectName);
}
