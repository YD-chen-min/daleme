package com.yandan.demo.service.impl;

import com.yandan.demo.VO.NoticeVO;
import com.yandan.demo.config.MyConfig;
import com.yandan.demo.converter.Converter;
import com.yandan.demo.dataobject.CompanyNotice;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.repository.DepartmentRepository;
import com.yandan.demo.repository.NoticeRepository;
import com.yandan.demo.service.NoticeService;
import com.yandan.demo.util.KeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Create by yandan
 * 2020/11/16  20:22
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    @Autowired
    private NoticeRepository noticeRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private MyConfig myConfig;
    @Override
    @Transactional
    public NoticeVO addNotice(CompanyNotice notice) throws ApiException {
        if(noticeRepository.findByTitle(notice.getTitle())!=null) throw new ApiException(ErrorEnum.NOTICE_ALREADY_EXISTS);
        int n=myConfig.getNoticeIdNumber();
        String key= KeyUtil.getKey(n);
        while (exists(key)) key=KeyUtil.getKey(n);
        notice.setNoticeId(key);
        notice.setDate(new SimpleDateFormat("yyyy-MM-dd").format(Long.valueOf(notice.getDate())));
        return companyNotice2NoticeVO(noticeRepository.save(notice));
    }

    @Transactional
    @Override
    public boolean deleteNotices(String titles) throws ApiException {
        List<String> titleList= Arrays.asList(titles.split(";"));
        for (String title:titleList){
            if(noticeRepository.findByTitle(title)==null) throw new ApiException(ErrorEnum.NOTICE_IS_NOT_EXISTS);
        }
        noticeRepository.deleteByTitleIn(titleList);
        return true;
    }

    @Override
    public List<NoticeVO> getNoticeList(String departmentId) {
        List<CompanyNotice> noticeList;
        if (departmentId==null||"".equals(departmentId)){
            noticeList=noticeRepository.findAll();
        }else {
            noticeList=noticeRepository.findByDepartmentId(departmentId);
        }
        List<NoticeVO> noticeVOList=new ArrayList<NoticeVO>();
        for (CompanyNotice notice:noticeList){
            noticeVOList.add(companyNotice2NoticeVO(notice));
        }
        return noticeVOList;
    }

    @Override
    public boolean exists(String key) {
        CompanyNotice notice=noticeRepository.findById(key).orElse(null);
        if(notice==null) return false;
        return true;
    }

    @Override
    public NoticeVO companyNotice2NoticeVO(CompanyNotice notice) {
        String departmentName;
        if(notice.getDepartmentId()==null||"".equals(notice.getDepartmentId())||"null".equals(notice.getDepartmentId())) departmentName=null;
        else departmentName=departmentRepository.findById(notice.getDepartmentId()).orElse(null).getName();
        return Converter.companyNotice2NoticeVO(notice,departmentName);
    }
}
