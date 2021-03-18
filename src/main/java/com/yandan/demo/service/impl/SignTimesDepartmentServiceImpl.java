package com.yandan.demo.service.impl;

import com.yandan.demo.VO.SignTimesDepartmentVO;
import com.yandan.demo.VO.SignTimesWorkVO;
import com.yandan.demo.dataobject.DaySignTimesDepartment;
import com.yandan.demo.dataobject.DepartmentInfo;
import com.yandan.demo.dataobject.SignTimesDepartment;
import com.yandan.demo.dataobject.SignTimesWork;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.repository.DepartmentRepository;
import com.yandan.demo.repository.SignTimesDepartmentRepository;
import com.yandan.demo.service.SignTimesDepartmentService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by yandan
 * 2020/11/21  17:50
 */
@Service
public class SignTimesDepartmentServiceImpl implements SignTimesDepartmentService {
    @Autowired
    private SignTimesDepartmentRepository signTimesDepartmentRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    @Transactional
    public SignTimesDepartment addSignTimesDepartment(SignTimesDepartment signTimesDepartment) throws ApiException {
        SignTimesDepartment result=signTimesDepartmentRepository.findById(signTimesDepartment.getDepartmentId()).orElse(null);
        if(result!=null) return null;
        return signTimesDepartmentRepository.save(signTimesDepartment);
    }

    @Override
    @Transactional
    public List<SignTimesDepartment> addByDepartmentInfo(List<DepartmentInfo> departmentInfoList) throws ApiException {
        List<SignTimesDepartment> signTimesDepartmentList=new ArrayList<SignTimesDepartment>();
        for (DepartmentInfo departmentInfo:departmentInfoList){
            SignTimesDepartment signTimesDepartment=new SignTimesDepartment();
            signTimesDepartment.setDepartmentId(departmentInfo.getDepartmentId());
            signTimesDepartment.setLateTimes(0);
            signTimesDepartment.setNoSignTimes(0);
            signTimesDepartment.setSignInTimes(0);
            signTimesDepartment=addSignTimesDepartment(signTimesDepartment);
            signTimesDepartmentList.add(signTimesDepartment);
        }
        return signTimesDepartmentList;
    }

    @Override
    @Transactional
    public SignTimesDepartment updateBySignTimesWork(List<SignTimesWork> signTimesWorkList) throws ApiException {
      if(signTimesWorkList!=null&&signTimesWorkList.size()!=0){
          SignTimesDepartment signTimesDepartment=signTimesDepartmentRepository.findById(signTimesWorkList.get(0).getDepartmentId()).orElse(null);
          if (signTimesDepartment==null) throw new ApiException(ErrorEnum.SIGN_TIMES_DEPARTMENT_IS_NOT_EXISTS);
//        int lateTimes=Integer.parseInt(signTimesDepartment.getLateTimes());
          int signInTimes=signTimesDepartment.getSignInTimes();
          int noSignTimes=signTimesDepartment.getNoSignTimes();
          for (SignTimesWork signTimesWork:signTimesWorkList){
//            lateTimes+=Integer.parseInt(signTimesWork.getLateTimes());
              signInTimes+=signTimesDepartment.getSignInTimes();
              noSignTimes+=signTimesDepartment.getNoSignTimes();
          }
          signTimesDepartment.setSignInTimes(signInTimes);
          signTimesDepartment.setNoSignTimes(noSignTimes);
//        signTimesDepartment.setLateTimes(String.valueOf(lateTimes));
          return signTimesDepartmentRepository.save(signTimesDepartment);
      }
      return null;
    }

    @Override
    public List<SignTimesDepartment> getAll() {
        return signTimesDepartmentRepository.findAll();
    }

    @Override
    public List<SignTimesDepartmentVO> signTimesDepartment2SignTimesDepartmentVO(List<SignTimesDepartment> signTimesDepartmentList) {
        List<SignTimesDepartmentVO> signTimesDepartmentVOList=new ArrayList<SignTimesDepartmentVO>();
        for (SignTimesDepartment signTimesDepartment:signTimesDepartmentList){
            SignTimesDepartmentVO signTimesDepartmentVO=new SignTimesDepartmentVO();
            BeanUtils.copyProperties(signTimesDepartment,signTimesDepartmentVO);
            DepartmentInfo departmentInfo=departmentRepository.findById(signTimesDepartment.getDepartmentId()).orElse(null);
            signTimesDepartmentVO.setDepartmentName(departmentInfo.getName());
            signTimesDepartmentVOList.add(signTimesDepartmentVO);
        }
        return signTimesDepartmentVOList;
    }

    @Override
    @Transactional
    public SignTimesDepartment addByDepartmentInfo(DepartmentInfo departmentInfo) throws ApiException {
        SignTimesDepartment result=signTimesDepartmentRepository.findById(departmentInfo.getDepartmentId()).orElse(null);
        if(result!=null) return null;
        SignTimesDepartment signTimesDepartment=new SignTimesDepartment();
        signTimesDepartment.setDepartmentId(departmentInfo.getDepartmentId());
        signTimesDepartment.setLateTimes(0);
        signTimesDepartment.setNoSignTimes(0);
        signTimesDepartment.setSignInTimes(0);
        return signTimesDepartmentRepository.save(signTimesDepartment);
    }
}
