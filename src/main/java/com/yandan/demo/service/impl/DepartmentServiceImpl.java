package com.yandan.demo.service.impl;

import com.yandan.demo.dataobject.DepartmentInfo;
import com.yandan.demo.repository.DepartmentRepository;
import com.yandan.demo.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/12  20:37
 */
@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Override
    public List<DepartmentInfo> getDepartments() {
        return departmentRepository.findAll();
    }

    @Override
    public DepartmentInfo getDepartment(String departmentId) {
        return departmentRepository.findById(departmentId).orElse(null);
    }
    public DepartmentInfo save(DepartmentInfo departmentInfo){
        if (departmentInfo!=null){
            return departmentRepository.save(departmentInfo);
        }
        return null;
    }


}
