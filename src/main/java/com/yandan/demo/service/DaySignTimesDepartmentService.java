package com.yandan.demo.service;

import com.yandan.demo.dataobject.DaySignTimesDepartment;
import com.yandan.demo.dataobject.DaySignTimesWork;
import com.yandan.demo.dataobject.DepartmentInfo;
import com.yandan.demo.exception.ApiException;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/19  10:37
 */
public interface DaySignTimesDepartmentService {
    /**
     * 添加一条记录
     * @param daySignTimesDepartment
     * @return
     */
    DaySignTimesDepartment addDaySignTimesDepartment(DaySignTimesDepartment daySignTimesDepartment) throws ApiException;

    /**
     * 根据List<DepartmentInfo> 添加 记录
     * @param departmentInfoList
     * @param date
     * @return
     */
    List<DaySignTimesDepartment> addByDepartmentInfo(List<DepartmentInfo> departmentInfoList, String date) throws ApiException;

    /**
     * 通过部门id获取记录列表
     * @param departmentId
     * @return
     */
    List<DaySignTimesDepartment> getDaySignTimesDepartmentByDepartmentId(String departmentId);

    /**
     * 通过日期获取记录列表
     * @param date
     * @return
     */
    List<DaySignTimesDepartment> getDaySignTimesDepartmentByDate(String date);

    /**
     * 根据员工签到数据列表和日期更新记录
     * @param daySignTimesWorkList
     * @param date
     * @return
     */
    DaySignTimesDepartment updateByDaySignTimesWork(List<DaySignTimesWork> daySignTimesWorkList,String date) throws ApiException;

    /**
     * 获取全部数据
     * @return
     */
    List<DaySignTimesDepartment> getAll();

}
