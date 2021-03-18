package com.yandan.demo.controller;

import com.yandan.demo.VO.PageVO;
import com.yandan.demo.VO.ResultVO;
import com.yandan.demo.VO.SignTimesDepartmentVO;
import com.yandan.demo.VO.SignTimesWorkVO;
import com.yandan.demo.dataobject.SignTimesDepartment;
import com.yandan.demo.dataobject.SignTimesWork;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.service.SignTimesDepartmentService;
import com.yandan.demo.service.SignTimesWorkService;
import com.yandan.demo.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/21  17:23
 */
@RestController
@Slf4j
@RequestMapping("/api/counts")
public class SignTimesController {
    @Autowired
    private SignTimesWorkService signTimesWorkService;
    @Autowired
    private SignTimesDepartmentService signTimesDepartmentService;
    @GetMapping("/all")
    public ResultVO<List<SignTimesWorkVO>> getAllSignTimesWork(){
        List<SignTimesWork> signTimesWorkList=signTimesWorkService.getAll();
        if(signTimesWorkList==null||signTimesWorkList.size()==0) return ResultVOUtil.fail(ErrorEnum.LIST_IS_EMPTY.getCode(),
                ErrorEnum.LIST_IS_EMPTY.getMsg());
        List<SignTimesWorkVO> signTimesWorkVOList=signTimesWorkService.signTimesWork2SignTimesWorkVO(signTimesWorkList);
        return ResultVOUtil.success(signTimesWorkVOList);
    }
    @GetMapping("/department")
    public ResultVO<List<SignTimesWorkVO>> getSignTimesWorkByDepartmentId(@RequestParam(value = "departmentId",defaultValue = "")String departmentId){
        if("".equals(departmentId)) {
            log.error("【获取某部门个人打卡次数】code:{}  , message:{}",ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        List<SignTimesWork> signTimesWorkList=signTimesWorkService.getByDepartmentId(departmentId);
        List<SignTimesWorkVO> signTimesWorkVOList=signTimesWorkService.signTimesWork2SignTimesWorkVO(signTimesWorkList);
        return ResultVOUtil.success(signTimesWorkVOList);
    }
    @GetMapping("/allDepartment")
    public ResultVO<List<SignTimesWorkVO>> getSignTimesDepartment(){
        List<SignTimesDepartment> signTimesDepartmentList=signTimesDepartmentService.getAll();
        List<SignTimesDepartmentVO> signTimesDepartmentVOList=signTimesDepartmentService.signTimesDepartment2SignTimesDepartmentVO(signTimesDepartmentList);
        return ResultVOUtil.success(signTimesDepartmentVOList);
    }
    @GetMapping("/page/allDepartment")
    public PageVO<SignTimesDepartmentVO> getPageSignTimesDepartment(){
        List<SignTimesDepartment> signTimesDepartmentList=signTimesDepartmentService.getAll();
        List<SignTimesDepartmentVO> signTimesDepartmentVOList=signTimesDepartmentService.signTimesDepartment2SignTimesDepartmentVO(signTimesDepartmentList);
        PageVO<SignTimesDepartmentVO> pageVO=new PageVO<SignTimesDepartmentVO>();
        pageVO.setTotal(signTimesDepartmentVOList.size());
        pageVO.setRows(signTimesDepartmentVOList);
        return pageVO;
    }
    @GetMapping("/page/all")
    public PageVO<SignTimesWorkVO> getPageSignTimesWork(){
        List<SignTimesWork> signTimesWorkList=signTimesWorkService.getAll();
        List<SignTimesWorkVO> signTimesWorkVOList=signTimesWorkService.signTimesWork2SignTimesWorkVO(signTimesWorkList);
        PageVO<SignTimesWorkVO> pageVO=new PageVO<SignTimesWorkVO>();
        pageVO.setTotal(signTimesWorkVOList.size());
        pageVO.setRows(signTimesWorkVOList);
        return pageVO;
    }
    @GetMapping("/page/department")
    public PageVO<SignTimesWorkVO> getPageSignTimesWorkVOByDepartmentId(@RequestParam(value = "departmentId",defaultValue = "")String departmentId){
        List<SignTimesWork> signTimesWorkList;
        if ("".equals(departmentId)) signTimesWorkList=signTimesWorkService.getAll();
        else signTimesWorkList=signTimesWorkService.getByDepartmentId(departmentId);
        List<SignTimesWorkVO> signTimesWorkVOList=signTimesWorkService.signTimesWork2SignTimesWorkVO(signTimesWorkList);
        PageVO<SignTimesWorkVO> pageVO=new PageVO<SignTimesWorkVO>();
        pageVO.setRows(signTimesWorkVOList);
        pageVO.setTotal(signTimesWorkVOList.size());
        return pageVO;
    }

}
