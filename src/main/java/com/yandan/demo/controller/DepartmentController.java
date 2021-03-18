package com.yandan.demo.controller;

import com.yandan.demo.VO.ResultVO;
import com.yandan.demo.dataobject.DepartmentInfo;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.service.DepartmentService;
import com.yandan.demo.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/14  14:36
 */
@RestController
@RequestMapping("/api/department")
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/getlist")
    public ResultVO<List<DepartmentInfo>> getDepartmentList(){
        List<DepartmentInfo> departmentInfoList=departmentService.getDepartments();
        if(departmentInfoList.size()==0) return ResultVOUtil.fail(ErrorEnum.LIST_IS_EMPTY.getCode(),
                ErrorEnum.LIST_IS_EMPTY.getMsg());
        return ResultVOUtil.success(departmentInfoList);
    }
    @PostMapping("/ggetlist")
    public ResultVO<List<DepartmentInfo>> ggetDepartmentList(){
        List<DepartmentInfo> departmentInfoList=departmentService.getDepartments();
        if(departmentInfoList.size()==0) return ResultVOUtil.fail(ErrorEnum.LIST_IS_EMPTY.getCode(),
                ErrorEnum.LIST_IS_EMPTY.getMsg());
        return ResultVOUtil.success(departmentInfoList);
    }
}
