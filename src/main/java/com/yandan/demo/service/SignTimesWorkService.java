package com.yandan.demo.service;

import com.yandan.demo.VO.SignTimesWorkVO;
import com.yandan.demo.dataobject.DaySignTimesWork;
import com.yandan.demo.dataobject.SignTimesWork;
import com.yandan.demo.dataobject.WorkInfo;
import com.yandan.demo.exception.ApiException;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/19  23:09
 */
public interface SignTimesWorkService {
    /**
     * 添加一条记录
     * @param signTimesWork
     * @return
     */
    SignTimesWork addSignTimesWork(SignTimesWork signTimesWork) throws ApiException;

    /**
     * 根据员工列表添加记录
     * @param workInfoList
     * @return
     */
    List<SignTimesWork> addByWorkInfo(List<WorkInfo> workInfoList) throws ApiException;

    /**
     * 根据员工每日打卡次数更新数据(属于同一部门的)
     * @param daySignTimesWorkList
     * @return
     */
    List<SignTimesWork> updateByDaySignTimesWork(List<DaySignTimesWork> daySignTimesWorkList) throws ApiException;

    /**
     * 获取所有数据
     * @return
     */
    List<SignTimesWork> getAll();

    /**
     * 通过部门id获取列表
     * @param departmentId
     * @return
     */
    List<SignTimesWork> getByDepartmentId(String departmentId);

    /**
     * signTimeWork列表 转 signTimeWorkVO 列表
     * @param signTimesWorkList
     * @return
     */
    List<SignTimesWorkVO> signTimesWork2SignTimesWorkVO(List<SignTimesWork> signTimesWorkList);
    /**
     * 根据员工信息添加记录
     * @param workInfo
     * @return
     */
    SignTimesWork addByWorkInfo(WorkInfo workInfo) throws ApiException;

}
