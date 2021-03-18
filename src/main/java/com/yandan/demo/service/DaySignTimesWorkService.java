package com.yandan.demo.service;

import com.yandan.demo.dataobject.DaySignTimesWork;
import com.yandan.demo.dataobject.DaySignWork;
import com.yandan.demo.dataobject.WorkInfo;
import com.yandan.demo.exception.ApiException;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/19  8:51
 */
public interface DaySignTimesWorkService {
    /**
     * 添加用户每日签到数据
     * @param daySignTimesWork
     * @return
     */
    DaySignTimesWork addDaySignTimes(DaySignTimesWork daySignTimesWork) throws ApiException;

    /**
     * 根据日期和工号获取记录
     * @param date
     * @param workId
     * @return
     */
    DaySignTimesWork getDaySignTimesByDateAndWorkId(String date,String workId) throws ApiException;

    /**
     * 根据工号获取记录列表
     * @param workId
     * @return
     */
    List<DaySignTimesWork> getDaySignTimesByWorkId(String workId);

    /**
     * 根据日期获取记录列表
     * @param date
     * @return
     */
    List<DaySignTimesWork> getDaySignTimesByDate(String date);

    /**
     * 根据部门Id获取记录列表
     * @param departmentId
     * @return
     */
    List<DaySignTimesWork> getDaySignTimesByDepartmentId(String departmentId);

    /**
     * 根据用户列表和date插入记录
     * @param workInfoList
     * @return
     */
    List<DaySignTimesWork> addDaySignTimesByWorkInfo(List<WorkInfo> workInfoList,String date) throws ApiException;

    /**
     * 根据签到数据更新记录
     * @param daySignWorkList
     * @return
     */
    List<DaySignTimesWork> updateDaySignTimesByDaySignWork(List<DaySignWork> daySignWorkList) throws ApiException;

    /**
     * 获取全部数据
     * @return
     */
    List<DaySignTimesWork> getAll();

    /**
     * 通过部门编号和日期获取列表
     * @param departmentId
     * @param date
     * @return
     */
    List<DaySignTimesWork> getByDepartmentIdAndDate(String departmentId,String date);
}
