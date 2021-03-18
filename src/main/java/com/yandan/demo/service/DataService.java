package com.yandan.demo.service;

import com.yandan.demo.exception.ApiException;

/**
 * Create by yandan
 * 2020/11/21  21:50
 */
public interface DataService {
    /**
     * 发布打卡任务(往day_sign_work里添加数据)
     */
    void taskService() throws ApiException;

    /**
     * 打卡次数统计服务
     */
    void countsService() throws ApiException;
}
