package com.yandan.demo.service;


import com.yandan.demo.VO.NoticeVO;
import com.yandan.demo.dataobject.CompanyNotice;
import com.yandan.demo.exception.ApiException;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/16  20:13
 */
public interface NoticeService {
    /**
     * 添加公司公告
     * @param notice
     * @return
     */
    NoticeVO addNotice(CompanyNotice notice) throws ApiException;

    /**
     * 删除公告
     * @param titles
     * @return
     */
    boolean deleteNotices(String titles) throws ApiException;

    /**
     * 获取公告列表
     * @param departmentId
     * @return
     */
    List<NoticeVO> getNoticeList(String departmentId);

    /**
     * Id为tid的公告是否存在
     * @param id
     * @return
     */
    boolean exists(String id);

    /**
     * CompanyNotice  转 NoticeVO
     * @param notice
     * @return
     */
    NoticeVO companyNotice2NoticeVO(CompanyNotice notice);
}
