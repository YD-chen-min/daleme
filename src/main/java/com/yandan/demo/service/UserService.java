package com.yandan.demo.service;

import com.yandan.demo.dataobject.WorkInfo;
import com.yandan.demo.exception.ApiException;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/12  16:08
 */
public interface UserService {
    /**
     *
     * @param workId 工号
     * @return user
     */
    WorkInfo getUserByWorkId(String workId);

    /**
     *
     * @param departmentId  部门id
     * @return  user列表
     */
    List<WorkInfo> getUsersByDepartmentId(String departmentId);

    /**
     *用户第一次进入完善自己的信息
     * @param user 装有信息的user对象
     * @return
     */
    WorkInfo updateUserBaseInfo(WorkInfo user) throws ApiException;

    /**
     *
     * @param workId 用户工号
     * @param icon  头像图片url
     * @return
     */
    WorkInfo updateUserIcon(String workId, String icon) throws ApiException;

    /**
     *
     * @param workId
     * @param name
     * @param departmentId
     * @param roleId
     * @return 写入数据库的user对象
     */
    WorkInfo addUser(String workId, String name, String departmentId, Integer roleId, String password) throws ApiException;

    /**
     *
     * @param workIds
     * @return 成功删除返回true 否则为false
     */
    boolean deleteUsers(String workIds) throws ApiException;

    /**
     *
     * @param workId
     * @param role
     * @return
     */
    boolean updateUserRole(String workId,Integer role) throws ApiException;

    List<WorkInfo> getAll();

    /**
     * 检验输入的工号是否已经存在
     * @param workId
     * @return
     */
    boolean workIdExists(String workId);

    /**
     * 保存mac
     * @param workId
     * @param mac
     */
    void setMac(String workId,String mac) throws ApiException;

}
