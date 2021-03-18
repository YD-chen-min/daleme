package com.yandan.demo.service;

import com.yandan.demo.dataobject.Location;
import com.yandan.demo.exception.ApiException;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/16  13:30
 */
public interface LocationService {
    /**
     * 获取所有打卡限制地址
     * @return
     */
    List<Location>  getLocationList();

    /**
     * 录入打卡限制地址
     * @param location
     * @return
     */
    Location  addLocation(Location location) throws ApiException;

    /**
     * 修改打卡限制地址
     * @param newLocation
     * @return
     */
    Location updateLocation(Location newLocation) throws ApiException;

    /**
     * 通过名字获取打卡限制地址
     * @param locationName
     * @return
     */
    Location getLocationByName(String locationName) throws ApiException;

    /**
     * 通过id获取打卡限制地址
     * @param locationId
     * @return
     */
    Location getLocationById(String locationId) throws ApiException;

    /**
     * 通过名称删除打卡限制地址
     * @param names
     * @return
     */
    boolean deleteLocationsByName(String names) throws ApiException;

    /**
     * 通过id删除打开限制地址
     * @param ids
     * @return
     */
    boolean deleteLocationsById(String ids) throws ApiException;

    /**
     * 是否存在该id或该名称的打卡限制地址
     * @param nameOrId
     * @return
     */
    boolean exists(String nameOrId);
}
