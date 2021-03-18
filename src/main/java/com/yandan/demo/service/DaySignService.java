package com.yandan.demo.service;

import com.yandan.demo.VO.DaySignWorkVO;
import com.yandan.demo.dataobject.CollectInfo;
import com.yandan.demo.dataobject.DaySignWork;
import com.yandan.demo.dataobject.WorkInfo;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.form.DaySignForm;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/12  22:48
 */
public interface DaySignService {
    /**
     * 更新签到数据
     * @param daySignForm
     * @return
     */
    DaySignWorkVO updateDaySign(DaySignForm daySignForm) throws ApiException;

    /**
     * 根据工号获取 当天某员工的打卡信息
     * @param workId
     * @return
     */
    List<DaySignWorkVO>  getDaySignsByWorkId(String workId);

    /**
     * 根据部门id获取当天部门打卡信息
     *  @param departmentId
     * @return
     */
    List<DaySignWorkVO>  getDaySignByDepartmentId(String departmentId);

    /**
     * 该id的签到数据是否存在
     * @param id
     * @return
     */
    boolean exists(String id);

    /**
     * 根据daySignForm 更新 daySignWork
     * @param daySignForm
     * @return
     */
    DaySignWork updateDaySignWorkByDaySignForm(DaySignForm daySignForm,DaySignWork daySignWork) throws ApiException;

    /**
     * DaySignWork  转  DaySignWorkVO
     * @param daySignWork
     * @return
     */
    DaySignWorkVO daySignWork2DaySignWorkVO(DaySignWork daySignWork);

    /**
     * 根据信息收集id获取当天部门打卡信息
     * @param collectId
     * @return
     */
    List<DaySignWorkVO> getDaySignByCollectId(String collectId);

    /**
     * 通过日期和信息收集id获取记录和是否签到
     * @param date
     * @param collectId
     * @return
     */
    List<DaySignWorkVO> getDaySignByDateAndCollectIdAndIfSignIn(String date,String collectId,String ifSignIn);

    /**
     * 根据部门id 和 日期 获取打卡记录
     * @param departmentId
     * @param date
     * @return
     */
    List<DaySignWorkVO> getDaySignByDepartmentIdAndDate(String departmentId,String date);

    /**
     * 根据员工和收集信息id'打卡的初始数据
     * @param workInfoList
     * @param collectInfo
     * @return
     */
    List<DaySignWork> addByWorkInfoAndCollect(List<WorkInfo> workInfoList, CollectInfo collectInfo,String date);

    /**
     * 添加一条数据
     * @param daySignWork
     * @return
     */
    DaySignWork addDaySignWork(DaySignWork daySignWork) throws ApiException;

    /**
     * 通过日期获取列表
     * @param date
     * @return
     */
    List<DaySignWork> getByDate(String date);

    /**
     * 通过workId 和 date查询数据
     * @param workId
     * @param date
     * @return
     */
    List<DaySignWorkVO> getByWorkIdAndDate(String workId,String date) throws ApiException;
    List<DaySignWorkVO> getByWorkAndDepartmentAndIfSign(String workerName,String departmentName,String ifSign) throws ApiException;

}
