package com.yandan.demo.service;

import com.yandan.demo.VO.CollectInfoVO;
import com.yandan.demo.dataobject.CollectInfo;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.form.CollectForm;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/14  20:17
 */
public interface CollectionService {
    /**
     * 添加收集任务
     * @param collectForm
     * @return
     */
    CollectInfoVO addCollection(CollectForm collectForm) throws ApiException;

    /**
     * 返回所有收集信息
     * @return
     */
    List<CollectInfoVO> getCollectionList();

    /**
     * 获取特定的收集
     * @param collectionName
     * @return
     */
    CollectInfoVO  getCollection(String collectionName) throws ApiException;

    CollectInfoVO  getCollectionById(String id) throws ApiException;
    /**
     * 修改信息收集
     * @param collectForm
     * @param oldName
     * @return
     */
    CollectInfoVO  updateCollection(CollectForm collectForm,String oldName) throws ApiException;

    /**
     * 删除信息收集
     * @param names
     * @return
     */
    boolean  deleteCollections(String names) throws ApiException;

    /**
     * collectId是否已存在
     * @param collectId
     * @return
     */
    boolean exists(String collectId);

    /**
     * collectInfo 转为为collectInfoVO
     * @param collectInfo
     * @return
     */
    CollectInfoVO collectInfo2CollectInfoVO(CollectInfo collectInfo);

    /**
     * 获取所有collectInfo
     * @return
     */
    List<CollectInfo> getAllCollectInfo();

    /**
     * 通过id删除数据
     * @param id
     */
    void deleteById(String id);
}
