package com.yandan.demo.service.impl;

import com.yandan.demo.VO.DaySignWorkVO;
import com.yandan.demo.config.MyConfig;
import com.yandan.demo.converter.Converter;
import com.yandan.demo.dataobject.*;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.form.DaySignForm;
import com.yandan.demo.repository.*;
import com.yandan.demo.service.DaySignService;
import com.yandan.demo.service.InService;
import com.yandan.demo.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by yandan
 * 2020/11/18  19:31
 */
@Service
public class DaySignServiceImpl implements DaySignService {
    @Autowired
    private DaySignRepository daySignRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private CollectRepository collectRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private MyConfig myConfig;
    @Autowired
    private InService inService;

    @Override
    @Transactional
    public DaySignWorkVO updateDaySign(DaySignForm daySignForm) throws ApiException {
        String daySignId=daySignForm.getDaySignId();
        DaySignWork daySignWork=daySignRepository.findById(daySignId).orElse(null);
        if(daySignWork==null) throw new ApiException(ErrorEnum.DAY_SIGN_WORK_IS_NOT_EXISTS);
        CollectInfo collectInfo=collectRepository.findById(daySignForm.getCollectId()).orElse(null);
        Location location=null;
        if(collectInfo!=null){
            location=locationRepository.findById(collectInfo.getLocationId()).orElse(null);
            if (location!=null){
                inService.setBorder(Arrays.asList(location.getLocationInfo().split(";")));
                String[] lnglat=daySignForm.getLocation().split(",");
                Point point=new Point();
                point.setLat(Double.valueOf(lnglat[1]));
                point.setLng(Double.valueOf(lnglat[0]));
                if (!inService.in(point)){
                    throw new ApiException(ErrorEnum.NOT_IN_BORDER);
                }
            }
        }
        daySignWork=updateDaySignWorkByDaySignForm(daySignForm,daySignWork);
        daySignWork=daySignRepository.save(daySignWork);
        return daySignWork2DaySignWorkVO(daySignWork);
    }

    @Override
    public List<DaySignWorkVO> getDaySignsByWorkId(String workId) {
        List<DaySignWorkVO> daySignWorkVOList=new ArrayList<DaySignWorkVO>();
        List<DaySignWork> daySignWorkList=daySignRepository.findByWorkId(workId);
        if(daySignWorkList==null||daySignWorkList.size()==0) return daySignWorkVOList;
        for (DaySignWork daySignWork:daySignWorkList){
            daySignWorkVOList.add(daySignWork2DaySignWorkVO(daySignWork));
        }
        return daySignWorkVOList;
    }

    @Override
    public List<DaySignWorkVO> getDaySignByDepartmentId(String departmentId) {
        List<DaySignWorkVO> daySignWorkVOList=new ArrayList<DaySignWorkVO>();
        List<DaySignWork> daySignWorkList=daySignRepository.findByDepartmentId(departmentId);
        if(daySignWorkList==null||daySignWorkList.size()==0) return daySignWorkVOList;
        for (DaySignWork daySignWork:daySignWorkList){
            daySignWorkVOList.add(daySignWork2DaySignWorkVO(daySignWork));
        }
        return daySignWorkVOList;
    }

    @Override
    public boolean exists(String id) {
        DaySignWork daySignWork=daySignRepository.findById(id).orElse(null);
        if(daySignWork==null) return false;
        return true;
    }

    @Override
    @Transactional
    public DaySignWork updateDaySignWorkByDaySignForm(DaySignForm daySignForm, DaySignWork daySignWork) throws ApiException {
        //TODO 怎样判断迟到
        daySignWork.setIfSign(1);
        if(daySignForm.getInfo()!=null||!"".equals(daySignForm.getInfo())) daySignWork.setInfo(daySignForm.getInfo());
        if(daySignForm.getIcon()!=null||!"".equals(daySignForm.getIcon())) daySignWork.setIcon(daySignForm.getIcon());
        daySignWork.setLocation(daySignForm.getLocation());
        String[] dateAndTime=daySignForm.getDate().split(" ");
        daySignWork.setDate(dateAndTime[0].trim());
        daySignWork.setTime(dateAndTime[1].trim());
        return daySignWork;
    }

    @Override
    public DaySignWorkVO daySignWork2DaySignWorkVO(DaySignWork daySignWork) {
        DaySignWorkVO daySignWorkVO;
        CollectInfo collectInfo=collectRepository.findById(daySignWork.getCollectId()).orElse(null);
        DepartmentInfo departmentInfo=departmentRepository.findById(daySignWork.getDepartmentId()).orElse(null);
        WorkInfo workInfo=userRepository.findById(daySignWork.getWorkId()).orElse(null);
        Location location=null;
        if(collectInfo.getLocationId()!=null){
            location=locationRepository.findById(collectInfo.getLocationId()).orElse(null);
        }
        // 插入数据时对以上对象进行了null判断 因此以上对象不为空
        String work=workInfo.getName()+"("+workInfo.getWorkId()+")";
        String locationInfo;
        String address;
        if (location!=null) {
            locationInfo = location.getLocationInfo();
            address=location.getAddress();
        } else {
            locationInfo=null;
            address=null;
        }
        int ifIcon;
        if (collectInfo!=null) ifIcon=collectInfo.getIfIcon();
        else ifIcon=0;
        daySignWorkVO= Converter.daySignWork2DaySignWorkVO(daySignWork,locationInfo,
                departmentInfo.getName(),work,collectInfo.getCollectName(),ifIcon,collectInfo.getInfo(),address);
        return daySignWorkVO;
    }

    @Override
    public List<DaySignWorkVO> getDaySignByCollectId(String collectId) {
        List<DaySignWorkVO> daySignWorkVOList=new ArrayList<DaySignWorkVO>();
        List<DaySignWork> daySignWorkList=daySignRepository.findByCollectId(collectId);
        if(daySignWorkList==null||daySignWorkList.size()==0) return daySignWorkVOList;
        for (DaySignWork daySignWork:daySignWorkList){
            daySignWorkVOList.add(daySignWork2DaySignWorkVO(daySignWork));
        }
        return daySignWorkVOList;
    }

    @Override
    public List<DaySignWorkVO> getDaySignByDateAndCollectIdAndIfSignIn(String date, String collectId,String ifSignIn) {
        List<DaySignWorkVO> daySignWorkVOList=new ArrayList<DaySignWorkVO>();
        date=date.trim();
        collectId=collectId.trim();
        List<DaySignWork> daySignWorkList;
        if ("".equals(collectId)&&"".equals(ifSignIn)){
            daySignWorkList=daySignRepository.findByDate(date);
        }else if ("".equals(ifSignIn)){
            daySignWorkList= daySignRepository.findByCollectIdAndDate(collectId,date);
        }else if ("".equals(collectId)){
            Integer a=Integer.parseInt(ifSignIn);
            daySignWorkList=daySignRepository.findByDateAndIfSign(date,a);
        }else {
            Integer a=Integer.parseInt(ifSignIn);
            daySignWorkList=daySignRepository.findByDateAndCollectIdAndIfSign(date,collectId,a);
        }
        if(daySignWorkList==null||daySignWorkList.size()==0) return daySignWorkVOList;
        for (DaySignWork daySignWork:daySignWorkList){
            daySignWorkVOList.add(daySignWork2DaySignWorkVO(daySignWork));
        }
        return daySignWorkVOList;
    }

    @Override
    public List<DaySignWorkVO> getDaySignByDepartmentIdAndDate(String departmentId, String date) {
        List<DaySignWorkVO> daySignWorkVOList=new ArrayList<DaySignWorkVO>();
        date=date.trim();
        departmentId=departmentId.trim();
        List<DaySignWork> daySignWorkList=daySignRepository.findByDepartmentIdAndDate(departmentId,date);
        if(daySignWorkList==null||daySignWorkList.size()==0) return daySignWorkVOList;
        for (DaySignWork daySignWork:daySignWorkList){
            daySignWorkVOList.add(daySignWork2DaySignWorkVO(daySignWork));
        }
        return daySignWorkVOList;
    }

    @Override
    @Transactional
    public List<DaySignWork> addByWorkInfoAndCollect(List<WorkInfo> workInfoList, CollectInfo collectInfo,String date) {
        List<DaySignWork> daySignWorkList=new ArrayList<DaySignWork>();
        int n=myConfig.getSignInIdNumber();
        for (WorkInfo workInfo:workInfoList){
            if(daySignRepository.findByCollectIdAndDateAndWorkId(collectInfo.getCollectId(),date,workInfo.getWorkId())!=null){continue;}
            DaySignWork daySignWork=new DaySignWork();
            String key=KeyUtil.getKey(n);
            while (exists(key)) key=KeyUtil.getKey(n);
            daySignWork.setSignInId(key);
            daySignWork.setCollectId(collectInfo.getCollectId());
            daySignWork.setWorkId(workInfo.getWorkId());
            daySignWork.setDepartmentId(workInfo.getDepartmentId());
            daySignWork.setDate(date);
            daySignWork.setIfSign(0);
            daySignWork.setIfLate(0);
            daySignWork.setLocationId(collectInfo.getLocationId());
            daySignWorkList.add(daySignRepository.save(daySignWork));
        }
        return daySignWorkList;
    }

    @Override
    @Transactional
    public DaySignWork addDaySignWork(DaySignWork daySignWork) throws ApiException {
        DaySignWork result=daySignRepository.findById(daySignWork.getSignInId()).orElse(null);
        if (result!=null) throw new ApiException(ErrorEnum.DAY_SIGN_WORK_ALREADY_EXISTS);
        return daySignRepository.save(daySignWork);
    }

    @Override
    public List<DaySignWork> getByDate(String date) {
        return daySignRepository.findByDate(date);
    }

    @Override
    public List<DaySignWorkVO> getByWorkIdAndDate(String workId, String date) throws ApiException {
        List<DaySignWork> daySignWorkList=daySignRepository.findByWorkIdAndDate(workId,date);
        if (daySignWorkList==null) throw  new ApiException(ErrorEnum.LIST_IS_EMPTY);
        List<DaySignWorkVO> daySignWorkVOList=new ArrayList<DaySignWorkVO>();
        for (DaySignWork daySignWork:daySignWorkList){
            DaySignWorkVO daySignWorkVO=daySignWork2DaySignWorkVO(daySignWork);
            daySignWorkVOList.add(daySignWorkVO);
        }
        return daySignWorkVOList;
    }
    @Override
    public List<DaySignWorkVO> getByWorkAndDepartmentAndIfSign(String workerName,String departmentName,String ifSign) throws ApiException {
        DepartmentInfo departmentInfo=departmentRepository.findByName(departmentName);
        WorkInfo workInfo=userRepository.findByNameAndDepartmentId(workerName,departmentInfo.getDepartmentId());
        List<DaySignWork> daySignWorkList=daySignRepository.findByWorkIdAndIfSign(workInfo.getWorkId(),Integer.parseInt(ifSign));
        if (daySignWorkList==null) throw  new ApiException(ErrorEnum.LIST_IS_EMPTY);
        List<DaySignWorkVO> daySignWorkVOList=new ArrayList<DaySignWorkVO>();
        for (DaySignWork daySignWork:daySignWorkList){
            DaySignWorkVO daySignWorkVO=daySignWork2DaySignWorkVO(daySignWork);
            daySignWorkVOList.add(daySignWorkVO);
        }
        return daySignWorkVOList;
    }



}
