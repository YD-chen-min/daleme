package com.yandan.demo.service.impl;

import com.yandan.demo.dataobject.WorkInfo;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.repository.UserRepository;
import com.yandan.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * Create by yandan
 * 2020/11/12  16:35
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public WorkInfo getUserByWorkId(String workId) {

        return userRepository.findById(workId).orElse(null);
    }

    @Override
    public List<WorkInfo> getUsersByDepartmentId(String departmentId) {
        List<String>departmentIds=Arrays.asList(departmentId.split(";"));
      return   userRepository.findByDepartmentIdIn(departmentIds);

    }

    @Override
    @Transactional
    public WorkInfo updateUserBaseInfo(WorkInfo user) throws ApiException {
        String workId=user.getWorkId();
        WorkInfo user1=getUserByWorkId(workId);
        if(user1==null){
            throw new ApiException(ErrorEnum.USER_IS_NOT_EXIST);
        }
        if(user.getDepartmentId()!=null&&!"".equals(user.getDepartmentId())) user1.setDepartmentId(user.getDepartmentId());
        if(user.getPhoneNumber()!=null&&!"".equals(user.getPhoneNumber())) {
            if(userRepository.findByPhoneNumber(user.getPhoneNumber())!=null&&
                    !userRepository.findByPhoneNumber(user.getPhoneNumber()).getWorkId().equals(user.getWorkId())) throw new ApiException(ErrorEnum.USER_PHONE_EXIST);
            user1.setPhoneNumber(user.getPhoneNumber());
        }
        if(user.getPassword()!=null&&!"".equals(user.getPassword())) user1.setPassword(user.getPassword());
        if (user.getIcon()!=null&&!"".equals(user.getIcon())) user1.setIcon(user.getIcon());
        if (user.getMac()!=null&&!"".equals(user.getMac())) user1.setMac(user.getMac());

        return userRepository.save(user1);
    }

    @Override
    @Transactional
    public WorkInfo updateUserIcon(String workId, String icon) throws ApiException {
        WorkInfo user=getUserByWorkId(workId);
        if(user==null)  throw new ApiException(ErrorEnum.USER_IS_NOT_EXIST);
        user.setIcon(icon);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public WorkInfo addUser(String workId, String name, String departmentId, Integer roleId, String password) throws ApiException {
        if(getUserByWorkId(workId)!=null) throw new ApiException(ErrorEnum.USER_ALREADY_EXISTS);
        WorkInfo user=new WorkInfo();
        user.setWorkId(workId);
        user.setDepartmentId(departmentId);
        user.setIsAdmin(roleId);
        user.setPassword(password);
        user.setName(name);
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public boolean deleteUsers(String workIds) throws ApiException {
        List<String> workIdList=Arrays.asList(workIds.split(";"));
        for (String workId :workIdList){
            WorkInfo workInfo=getUserByWorkId(workId);
            if(workId==null) throw new ApiException(ErrorEnum.USER_IS_NOT_EXIST);
            userRepository.deleteById(workId);
        }
        return true;
    }

    @Override
    @Transactional
    public boolean updateUserRole(String workId, Integer role) throws ApiException {
        WorkInfo user=getUserByWorkId(workId);
        if(user==null)   throw new ApiException(ErrorEnum.USER_IS_NOT_EXIST);
        user.setIsAdmin(role);
        if(userRepository.save(user)==null) return false;
        return true;
    }

    @Override
    public List<WorkInfo> getAll() {
        return userRepository.findAll();
    }

    @Override
    public boolean workIdExists(String workId) {
        WorkInfo workInfo=userRepository.findById(workId).orElse(null);
        if (workInfo==null) return false;
        return true;
    }

    @Override
    public void setMac(String workId, String mac) throws ApiException {
        if (!workIdExists(workId)) throw new ApiException(ErrorEnum.USER_IS_NOT_EXIST);
        WorkInfo workInfo=getUserByWorkId(workId);
        workInfo.setMac(mac);
        userRepository.save(workInfo);
    }
}
