package com.yandan.demo.controller;

import com.yandan.demo.VO.DaySignWorkVO;
import com.yandan.demo.VO.PageVO;
import com.yandan.demo.VO.ResultVO;
import com.yandan.demo.dataobject.DaySignWork;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.form.DaySignForm;
import com.yandan.demo.service.DaySignService;
import com.yandan.demo.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * Create by yandan
 * 2020/11/18  21:05
 */
@RestController
@Slf4j
public class DaySignController {
    @Autowired
    private DaySignService daySignService;
    @PostMapping("/api/sign")
    public ResultVO<DaySignWorkVO> sign(@RequestBody  @Valid DaySignForm daySignForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【打卡签到】code:{}, message: {}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        try {
            DaySignWorkVO daySignWorkVO=daySignService.updateDaySign(daySignForm);
            return ResultVOUtil.success(daySignWorkVO);
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【打卡签到】code:{}, message: {}", e.getCode(),
                   e.getMessage());
            return ResultVOUtil.fail(e.getCode(), e.getMessage());
        }
    }
    @GetMapping("/sign/person/record")
    public ResultVO<List<DaySignWorkVO>> getPersonRecord(@RequestParam(value = "workId",defaultValue = "")String workId){
        if ("".equals(workId)){
            log.error("【获取个人打卡记录】code:{}, message: {}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        List<DaySignWorkVO> daySignWorkVOList=daySignService.getDaySignsByWorkId(workId);
        if(daySignWorkVOList.size()==0){
            log.error("【获取个人打卡记录】code:{}, message: {}", ErrorEnum.LIST_IS_EMPTY.getCode(),
                    ErrorEnum.LIST_IS_EMPTY.getMsg());
            return ResultVOUtil.fail(ErrorEnum.LIST_IS_EMPTY.getCode(),
                    ErrorEnum.LIST_IS_EMPTY.getMsg());
        }
        return ResultVOUtil.success(daySignWorkVOList);
    }
    @GetMapping("/sign/all/record")
    public ResultVO<List<DaySignWorkVO>> getByDateAndCollectId(@RequestParam(value = "date",defaultValue = "")String date,
                                                           @RequestParam(value = "collectId",defaultValue = "")String collectId,
                                                           @RequestParam(value = "ifSignIn",defaultValue = "")String ifSignIn){
        List<DaySignWorkVO> daySignWorkVOList=daySignService.getDaySignByDateAndCollectIdAndIfSignIn(date,collectId,ifSignIn);
        return ResultVOUtil.success(daySignWorkVOList);
    }
    @GetMapping("/sign/person/all")
    public ResultVO<List<DaySignWorkVO>> getByWorkerNameAndIfSign(@RequestParam(value = "workName",defaultValue = "")String workName,
                                                               @RequestParam(value = "departmentName",defaultValue = "")String departmentName,
                                                               @RequestParam(value = "ifSign",defaultValue = "")String ifSignIn) throws ApiException {
        List<DaySignWorkVO> daySignWorkVOList=daySignService.getByWorkAndDepartmentAndIfSign(workName,departmentName,ifSignIn);
        return ResultVOUtil.success(daySignWorkVOList);
    }

    @GetMapping("/sign/page/all")
    public PageVO<DaySignWorkVO> getPageByDateAndCollectId(@RequestParam(value = "date",defaultValue = "")String date,
                                                         @RequestParam(value = "collectId",defaultValue = "")String collectId,
                                                           @RequestParam(value = "ifSignIn",defaultValue = "")String ifSignIn){
        List<DaySignWorkVO> daySignWorkVOList=daySignService.getDaySignByDateAndCollectIdAndIfSignIn(date,collectId,ifSignIn);
        PageVO<DaySignWorkVO> pageVO=new PageVO<DaySignWorkVO>();
        pageVO.setRows(daySignWorkVOList);
        pageVO.setTotal(daySignWorkVOList.size());
        return pageVO;
    }

    @GetMapping("/sign/department/record")
    public ResultVO<DaySignWorkVO> getAllByDateAndDepartmentId(@RequestParam(value = "date",defaultValue = "")String date,
                                                               @RequestParam(value = "departmentId",defaultValue = "")String departmentId){
        if("".equals(date)||"".equals(departmentId)){
            log.error("【获取当天某部门所有的打卡记录】code:{}, message: {}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        List<DaySignWorkVO> daySignWorkVOList=daySignService.getDaySignByDepartmentIdAndDate(departmentId,date);
        if(daySignWorkVOList.size()==0){
            log.error("【获取当天某部门所有的打卡记录】code:{}, message: {}", ErrorEnum.LIST_IS_EMPTY.getCode(),
                    ErrorEnum.LIST_IS_EMPTY.getMsg());
            return ResultVOUtil.fail(ErrorEnum.LIST_IS_EMPTY.getCode(),
                    ErrorEnum.LIST_IS_EMPTY.getMsg());
        }
        return ResultVOUtil.success(daySignWorkVOList);
    }
    @PostMapping("/api/task/getPerson")
    public ResultVO<List<DaySignWorkVO>> getByWorkIdAndDate(@RequestBody Map<String,String> param){
        String workId=param.get("workId");
        String date=param.get("date");
        List<DaySignWorkVO> daySignWorkVOList= null;
        if(date==null){
            daySignWorkVOList=daySignService.getDaySignsByWorkId(workId);
            return ResultVOUtil.success(daySignWorkVOList);
        }else {
            try {
                daySignWorkVOList = daySignService.getByWorkIdAndDate(workId,date);
                return ResultVOUtil.success(daySignWorkVOList);
            } catch (ApiException e) {
                log.error("【getByWorkIdAndDate】 code:{} message:{}",e.getCode(),e.getMessage());
                return ResultVOUtil.fail(e.getCode(),e.getMessage());
            }
        }


    }
}
