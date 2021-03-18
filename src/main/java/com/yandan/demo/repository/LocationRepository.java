package com.yandan.demo.repository;

import com.yandan.demo.dataobject.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/15  11:29
 */
public interface LocationRepository  extends JpaRepository<Location,String> {
    Integer deleteByLocationIdIn(List<String> ids);
    Integer deleteByLocationNameIn(List<String> names);
    Location findByLocationName(String locationName);
}
