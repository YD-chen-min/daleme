package com.yandan.demo.service.impl;

import com.yandan.demo.config.MyConfig;
import com.yandan.demo.dataobject.DaySignTimesWork;
import com.yandan.demo.dataobject.DaySignWork;
import com.yandan.demo.dataobject.WorkInfo;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.repository.DaySignTimesWorkRepository;
import com.yandan.demo.service.DaySignTimesWorkService;
import com.yandan.demo.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by yandan
 * 2020/11/19  9:09
 */
@Service
public class DaySignTimesWorkServiceImpl implements DaySignTimesWorkService {
    @Autowired
    private DaySignTimesWorkRepository daySignTimesWorkRepository;
    @Autowired
    private MyConfig myConfig;
    @Override
    @Transactional
    public DaySignTimesWork addDaySignTimes(DaySignTimesWork daySignTimesWork) {
        if (exists(daySignTimesWork.getDate(),daySignTimesWork.getWorkId())) return null;
        return  daySignTimesWorkRepository.save(daySignTimesWork);
    }

    @Override
    public DaySignTimesWork getDaySignTimesByDateAndWorkId(String date, String workId) throws ApiException {
        DaySignTimesWork daySignTimesWork= daySignTimesWorkRepository.findByDateAndWorkId(date,workId);
        if(daySignTimesWork==null)  throw new ApiException(ErrorEnum.DAY_SIGN_WORK_IS_NOT_EXISTS);
        return daySignTimesWork;
    }

    @Override
    public List<DaySignTimesWork> getDaySignTimesByWorkId(String workId) {
        return daySignTimesWorkRepository.findByWorkId(workId);
    }

    @Override
    public List<DaySignTimesWork> getDaySignTimesByDate(String date) {
        return daySignTimesWorkRepository.findByDate(date);
    }

    @Override
    public List<DaySignTimesWork> getDaySignTimesByDepartmentId(String departmentId) {
        return daySignTimesWorkRepository.findByDepartmentId(departmentId);
    }

    @Override
    @Transactional
    public List<DaySignTimesWork> addDaySignTimesByWorkInfo(List<WorkInfo> workInfoList, String date) throws ApiException {
        List<DaySignTimesWork> daySignTimesWorkList=new ArrayList<DaySignTimesWork>();
        for (WorkInfo workInfo:workInfoList){
            DaySignTimesWork daySignTimesWork=new DaySignTimesWork();
            String id=KeyUtil.getKey(myConfig.getIdNumber());
            while (idExists(id)) id=KeyUtil.getKey(myConfig.getIdNumber());
            daySignTimesWork.setId(id);
            daySignTimesWork.setWorkId(workInfo.getWorkId());
            daySignTimesWork.setDate(date);
            daySignTimesWork.setDepartmentId(workInfo.getDepartmentId());
            daySignTimesWork.setSignInTimes(0);
            daySignTimesWork.setNoSignTimes(0);
            daySignTimesWork.setLateTimes(0);
            daySignTimesWork.setTotalTimes(0);
            daySignTimesWork=addDaySignTimes(daySignTimesWork);
            daySignTimesWorkList.add(daySignTimesWork);
        }
        return daySignTimesWorkList;
    }

    @Override
    @Transactional
    public List<DaySignTimesWork> updateDaySignTimesByDaySignWork(List<DaySignWork> daySignWorkList) throws ApiException {
        if(daySignWorkList!=null&&daySignWorkList.size()!=0){
            List<DaySignTimesWork> daySignTimesWorkList=new ArrayList<DaySignTimesWork>();
            for (DaySignWork daySignWork:daySignWorkList){
                DaySignTimesWork daySignTimesWork=getDaySignTimesByDateAndWorkId(daySignWork.getDate(),daySignWork.getWorkId());
                int signInTime=daySignTimesWork.getSignInTimes();
                int noSignTimes=daySignTimesWork.getNoSignTimes();
                int lateTimes=daySignTimesWork.getLateTimes();
                int totalTimes=daySignTimesWork.getTotalTimes();
                if(daySignWork.getIfSign()==1) signInTime++;
                else noSignTimes++;
//            if(daySignWork.getIfLate()==1) lateTimes++;
                totalTimes=signInTime;
                daySignTimesWork.setSignInTimes(signInTime);
                daySignTimesWork.setNoSignTimes(noSignTimes);
//            daySignTimesWork.setLateTimes(String.valueOf(lateTimes));
                daySignTimesWork.setTotalTimes(totalTimes);
                daySignTimesWork=daySignTimesWorkRepository.save(daySignTimesWork);
                daySignTimesWorkList.add(daySignTimesWork);
            }
            return daySignTimesWorkList;
        }
        return null;
    }

    @Override
    public List<DaySignTimesWork> getAll() {
        return daySignTimesWorkRepository.findAll();
    }

    @Override
    public List<DaySignTimesWork> getByDepartmentIdAndDate(String departmentId, String date) {
        return daySignTimesWorkRepository.findByDepartmentIdAndDate(departmentId,date);
    }
    boolean exists(String date,String workId){
        DaySignTimesWork daySignTimesWork= daySignTimesWorkRepository.findByDateAndWorkId(date,workId);
        if(daySignTimesWork==null)  return false;
        return true;
    }
    boolean idExists(String id){
        DaySignTimesWork result=daySignTimesWorkRepository.findById(id).orElse(null);
        if (result==null) return false;
        return true;
    }
}
