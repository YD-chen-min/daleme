package com.yandan.demo.service;

import com.yandan.demo.VO.SignTimesDepartmentVO;
import com.yandan.demo.dataobject.DaySignTimesWork;
import com.yandan.demo.dataobject.DepartmentInfo;
import com.yandan.demo.dataobject.SignTimesDepartment;
import com.yandan.demo.dataobject.SignTimesWork;
import com.yandan.demo.exception.ApiException;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/21  17:43
 */
public interface SignTimesDepartmentService {
    /**
     * 添加一条数据
     * @param signTimesDepartment
     * @return
     */
    SignTimesDepartment addSignTimesDepartment(SignTimesDepartment signTimesDepartment) throws ApiException;

    /**
     * 根据部门列表添加多条数据
     * @param departmentInfoList
     * @return
     */
    List<SignTimesDepartment> addByDepartmentInfo(List<DepartmentInfo> departmentInfoList) throws ApiException;

    /**
     * 根据个人打卡次数更新数据
     * @param signTimesWorkList
     * @return
     */
    SignTimesDepartment updateBySignTimesWork(List<SignTimesWork> signTimesWorkList) throws ApiException;

    /**
     * 获取所有记录
     * @return
     */
    List<SignTimesDepartment> getAll();

    /**
     * signTimesDepartment列表  转  signTimesDepartmentVO 列表
     * @param signTimesDepartmentList
     * @return
     */
    List<SignTimesDepartmentVO> signTimesDepartment2SignTimesDepartmentVO(List<SignTimesDepartment> signTimesDepartmentList);

    /**
     * 根据部门信息添加单条数据
     * @param departmentInfo
     * @return
     */
    SignTimesDepartment addByDepartmentInfo(DepartmentInfo departmentInfo) throws ApiException;
}
