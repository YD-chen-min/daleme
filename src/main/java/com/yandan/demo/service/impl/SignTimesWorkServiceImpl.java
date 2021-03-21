package com.yandan.demo.service.impl;

import com.yandan.demo.VO.SignTimesWorkVO;
import com.yandan.demo.dataobject.DaySignTimesWork;
import com.yandan.demo.dataobject.DepartmentInfo;
import com.yandan.demo.dataobject.SignTimesWork;
import com.yandan.demo.dataobject.WorkInfo;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.repository.DepartmentRepository;
import com.yandan.demo.repository.SignTimesWorkRepository;
import com.yandan.demo.repository.UserRepository;
import com.yandan.demo.service.SignTimesWorkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by yandan
 * 2020/11/19  23:27
 */
@Service
public class SignTimesWorkServiceImpl implements SignTimesWorkService {

    @Autowired
    private SignTimesWorkRepository signTimesWorkRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public SignTimesWork addSignTimesWork(SignTimesWork signTimesWork) throws ApiException {
        SignTimesWork result=signTimesWorkRepository.findByWorkId(signTimesWork.getWorkId());
        if(result==null||"null".equals(result)) throw new ApiException(ErrorEnum.SIGN_TIMES_WORK_ALREADY_EXISTS);
        return signTimesWorkRepository.save(signTimesWork);
    }

    @Override
    public List<SignTimesWork> addByWorkInfo(List<WorkInfo> workInfoList) throws ApiException {
        List<SignTimesWork> signTimesWorkList=new ArrayList<SignTimesWork>();
        for (WorkInfo workInfo:workInfoList){
            SignTimesWork signTimesWork=new SignTimesWork();
            signTimesWork.setWorkId(workInfo.getWorkId());
            signTimesWork.setDepartmentId(workInfo.getDepartmentId());
            signTimesWork.setSignInTimes(0);
            signTimesWork.setNoSignTimes(0);
            signTimesWork.setLateTimes(0);
            signTimesWork= addSignTimesWork(signTimesWork);
            signTimesWorkList.add(signTimesWork);
        }
        return signTimesWorkList;
    }

    @Override
    public List<SignTimesWork> updateByDaySignTimesWork(List<DaySignTimesWork> daySignTimesWorkList) throws ApiException {
      if(daySignTimesWorkList!=null&&daySignTimesWorkList.size()!=0){
          List<SignTimesWork> signTimesWorkList=new ArrayList<SignTimesWork>();
          for (DaySignTimesWork daySignTimesWork:daySignTimesWorkList){
              int signInTimes=daySignTimesWork.getSignInTimes();
              int noSignTimes=daySignTimesWork.getNoSignTimes();
//            int lateTimes=Integer.parseInt(daySignTimesWork.getLateTimes());
              SignTimesWork signTimesWork=signTimesWorkRepository.findByWorkId(daySignTimesWork.getWorkId());
              if(signTimesWork==null||"null".equals(signTimesWork) ) throw new ApiException(ErrorEnum.SIGN_TIMES_WORK_IS_NOT_EXISTS);
              signInTimes+=signTimesWork.getSignInTimes();
              noSignTimes+=signTimesWork.getNoSignTimes();
//            lateTimes+=Integer.parseInt(signTimesWork.getLateTimes());
//            signTimesWork.setLateTimes(String.valueOf(lateTimes));
              signTimesWork.setSignInTimes(signInTimes);
              signTimesWork.setNoSignTimes(noSignTimes);
              signTimesWork=signTimesWorkRepository.save(signTimesWork);
              signTimesWorkList.add(signTimesWork);
          }
          return signTimesWorkList;
      }
      return null;
    }

    @Override
    public List<SignTimesWork> getAll() {
        return signTimesWorkRepository.findAll();
    }

    @Override
    public List<SignTimesWork> getByDepartmentId(String departmentId) {
        return signTimesWorkRepository.findByDepartmentId(departmentId);
    }

    @Override
    public List<SignTimesWorkVO> signTimesWork2SignTimesWorkVO(List<SignTimesWork> signTimesWorkList) {
        List<SignTimesWorkVO> signTimesWorkVOList=new ArrayList<SignTimesWorkVO>();
        for (SignTimesWork signTimesWork:signTimesWorkList){
            SignTimesWorkVO signTimesWorkVO=new SignTimesWorkVO();
            WorkInfo workInfo=userRepository.findById(signTimesWork.getWorkId()).orElse(null);
            DepartmentInfo departmentInfo=departmentRepository.findById(signTimesWork.getDepartmentId()).orElse(null);
            BeanUtils.copyProperties(signTimesWork,signTimesWorkVO);
            signTimesWorkVO.setDepartmentName(departmentInfo.getName());
            signTimesWorkVO.setWorkName(workInfo.getName());
            signTimesWorkVOList.add(signTimesWorkVO);
        }
        return signTimesWorkVOList;
    }

    @Override
    public SignTimesWork addByWorkInfo(WorkInfo workInfo) throws ApiException {
        SignTimesWork signTimesWork=signTimesWorkRepository.findById(workInfo.getWorkId()).orElse(null);
        if(signTimesWork!=null) return null;  
        SignTimesWork signTimesWork1=new SignTimesWork();
        signTimesWork1.setDepartmentId(workInfo.getDepartmentId());
        signTimesWork1.setWorkId(workInfo.getWorkId());
        signTimesWork1.setNoSignTimes(0);
        signTimesWork1.setSignInTimes(0);
        signTimesWork1.setLateTimes(0);
        return signTimesWorkRepository.save(signTimesWork1);
    }
}
