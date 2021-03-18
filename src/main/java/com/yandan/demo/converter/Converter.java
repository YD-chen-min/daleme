package com.yandan.demo.converter;

import com.yandan.demo.VO.CollectInfoVO;
import com.yandan.demo.VO.DaySignWorkVO;
import com.yandan.demo.VO.NoticeVO;
import com.yandan.demo.VO.UserVO;
import com.yandan.demo.dataobject.CollectInfo;
import com.yandan.demo.dataobject.CompanyNotice;
import com.yandan.demo.dataobject.DaySignWork;
import com.yandan.demo.dataobject.WorkInfo;
import com.yandan.demo.form.CollectForm;
import com.yandan.demo.form.DaySignForm;
import com.yandan.demo.form.NoticeForm;
import org.springframework.beans.BeanUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Create by yandan
 * 2020/11/12  15:16
 */
public class Converter {
    public static UserVO user2UserVO(WorkInfo user, String departName){
        UserVO userVO=new UserVO();
        BeanUtils.copyProperties(user,userVO);
        userVO.setDepartmentName(departName);
        return userVO;
    }
    public static CollectInfoVO collectInfo2collectInfoVO(CollectInfo collectInfo,String work,String departmentName,String locationName,String address){
        CollectInfoVO collectInfoVO=new CollectInfoVO();
        BeanUtils.copyProperties(collectInfo,collectInfoVO);
        if(departmentName==null) collectInfoVO.setDepartmentName("全公司");
        else collectInfoVO.setDepartmentName(departmentName);
        if(locationName==null) collectInfoVO.setLocationName("任意地点");
        else collectInfoVO.setLocationName(locationName);
        if(address==null) collectInfoVO.setAddress("任意地点");
        else collectInfoVO.setAddress(address);
        if(work==null) {
            if("全公司".equals(collectInfoVO.getDepartmentName())) collectInfoVO.setWork("全体员工");
            else collectInfoVO.setWork("部门全体员工");
        }else{
            collectInfoVO.setWork(work);
        }
        return collectInfoVO;
    }
    public static CollectInfo collectForm2CollectInfo(CollectForm collectForm,String key){
        CollectInfo collectInfo=new CollectInfo();
        BeanUtils.copyProperties(collectForm,collectInfo);
        if ("".equals(collectForm.getDepartmentId())) collectInfo.setDepartmentId(null);
        if("".equals(collectForm.getWorkId()))  collectInfo.setWorkId(null);
        if("".equals(collectForm.getLocationId())) collectInfo.setLocationId(null);
        collectInfo.setIfIcon(Integer.parseInt(collectForm.getIfIcon()));
        collectInfo.setDayTimes(Integer.parseInt(collectForm.getDayTimes()));
        collectInfo.setCollectId(key);
        collectInfo.setCollectName(collectForm.getName());
        collectInfo.setInfo(collectForm.getHealth());
        return collectInfo;
    }
    public static NoticeVO companyNotice2NoticeVO(CompanyNotice companyNotice,String departmentName){
        NoticeVO noticeVO=new NoticeVO();
        BeanUtils.copyProperties(companyNotice,noticeVO);
        noticeVO.setDate(companyNotice.getDate());
        if(departmentName==null||"null".equals(departmentName)) noticeVO.setDepartmentName("全公司");
        else noticeVO.setDepartmentName(departmentName);
        return noticeVO;
    }
    public static CompanyNotice noticeForm2CompanyNotice(NoticeForm noticeForm){
        CompanyNotice notice=new CompanyNotice();
        BeanUtils.copyProperties(noticeForm,notice);
        notice.setNoticeContents(noticeForm.getContent());
        return notice;
    }
    public static DaySignWorkVO daySignWork2DaySignWorkVO(DaySignWork daySignWork,String locationName,String departmentName,
                                                          String work,String collectName,int ifIcon,String  collectInfo,String address){
        DaySignWorkVO daySignWorkVO=new DaySignWorkVO();
        BeanUtils.copyProperties(daySignWork,daySignWorkVO);
        if (daySignWork.getIfSign()==0) daySignWorkVO.setIfSign("未签到");
        else daySignWorkVO.setIfSign("已签到");
        if(locationName!=null) daySignWorkVO.setLocationName(locationName);
        if(address!=null) daySignWorkVO.setAddress(address);
        if(departmentName!=null) daySignWorkVO.setDepartmentName(departmentName);
        if (work!=null) daySignWorkVO.setWork(work);
        if (ifIcon==0) daySignWorkVO.setCollectType("普通签到");
        else daySignWorkVO.setCollectType("拍照签到");
        daySignWorkVO.setCollectInfo(collectInfo);
        daySignWorkVO.setCollectName(collectName);
        return daySignWorkVO;
    }

}
