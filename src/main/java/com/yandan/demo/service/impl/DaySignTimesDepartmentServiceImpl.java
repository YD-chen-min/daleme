package com.yandan.demo.service.impl;

import com.yandan.demo.config.MyConfig;
import com.yandan.demo.dataobject.*;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.repository.DaySignTimesDepartmentRepository;
import com.yandan.demo.service.DaySignTimesDepartmentService;
import com.yandan.demo.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by yandan
 * 2020/11/19  10:51
 */
@Service
public class DaySignTimesDepartmentServiceImpl implements DaySignTimesDepartmentService {
    @Autowired
    private DaySignTimesDepartmentRepository daySignTimesDepartmentRepository;
    @Autowired
    private MyConfig myConfig;
    @Override
    @Transactional
    public DaySignTimesDepartment addDaySignTimesDepartment(DaySignTimesDepartment daySignTimesDepartment)  {
        DaySignTimesDepartment result=daySignTimesDepartmentRepository.findByDepartmentIdAndDate(daySignTimesDepartment.getDepartmentId(),daySignTimesDepartment.getDate());
        if(!(result==null||"null".equals(result)))  return null;
        daySignTimesDepartment=daySignTimesDepartmentRepository.save(daySignTimesDepartment);
        return daySignTimesDepartment;
    }

    @Override
    @Transactional
    public List<DaySignTimesDepartment> addByDepartmentInfo(List<DepartmentInfo> departmentInfoList, String date) throws ApiException {
        List<DaySignTimesDepartment> daySignTimesDepartmentList=new ArrayList<DaySignTimesDepartment>();
        for (DepartmentInfo departmentInfo:departmentInfoList){
            DaySignTimesDepartment daySignTimesDepartment=new DaySignTimesDepartment();
            String id= KeyUtil.getKey(myConfig.getIdNumber());
            while (idExists(id)) id=KeyUtil.getKey(myConfig.getIdNumber());
            daySignTimesDepartment.setId(id);
            daySignTimesDepartment.setDepartmentId(departmentInfo.getDepartmentId());
            daySignTimesDepartment.setDate(date);
            daySignTimesDepartment.setSignInTimes(0);
            daySignTimesDepartment.setNoSignTimes(0);
            daySignTimesDepartment.setLateTimes(0);
            daySignTimesDepartment=addDaySignTimesDepartment(daySignTimesDepartment);
            daySignTimesDepartmentList.add(daySignTimesDepartment);
        }
        return daySignTimesDepartmentList;
    }

    @Override
    public List<DaySignTimesDepartment> getDaySignTimesDepartmentByDepartmentId(String departmentId) {
        return daySignTimesDepartmentRepository.findByDepartmentId(departmentId);
    }

    @Override
    public List<DaySignTimesDepartment> getDaySignTimesDepartmentByDate(String date) {
        return daySignTimesDepartmentRepository.findByDate(date);
    }

    @Override
    public DaySignTimesDepartment updateByDaySignTimesWork(List<DaySignTimesWork> daySignTimesWorkList, String date) throws ApiException {
       if(daySignTimesWorkList!=null&&daySignTimesWorkList.size()!=0){
           String departmentId=daySignTimesWorkList.get(0).getDepartmentId();
           DaySignTimesDepartment daySignTimesDepartment=daySignTimesDepartmentRepository.findByDepartmentIdAndDate(departmentId,date);
           if (daySignTimesDepartment==null||"null".equals(daySignTimesDepartment)) throw new ApiException(ErrorEnum.DAY_SIGN_TIMES_DEPARTMENT_IS_NOT_EXISTS);
           int signInTimes=daySignTimesDepartment.getSignInTimes();
           int noSignTimes=daySignTimesDepartment.getNoSignTimes();
//        int lateTimes=Integer.parseInt(daySignTimesDepartment.getLateTimes());
           for (DaySignTimesWork daySignTimesWork:daySignTimesWorkList){
               signInTimes+=daySignTimesWork.getSignInTimes();
               noSignTimes+=daySignTimesWork.getNoSignTimes();
//            lateTimes+=Integer.parseInt(daySignTimesWork.getLateTimes());
           }
//        daySignTimesDepartment.setLateTimes(String.valueOf(lateTimes));
           daySignTimesDepartment.setNoSignTimes(noSignTimes);
           daySignTimesDepartment.setSignInTimes(signInTimes);
           return daySignTimesDepartmentRepository.save(daySignTimesDepartment);
       }
       return null;
    }

    @Override
    public List<DaySignTimesDepartment> getAll() {
        return daySignTimesDepartmentRepository.findAll();
    }
    boolean idExists(String id){
        DaySignTimesDepartment result=daySignTimesDepartmentRepository.findById(id).orElse(null);
        if (result==null) return false;
        return true;
    }
}
