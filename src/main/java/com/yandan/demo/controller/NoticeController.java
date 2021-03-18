package com.yandan.demo.controller;

import com.yandan.demo.VO.NoticeVO;
import com.yandan.demo.VO.PageVO;
import com.yandan.demo.VO.ResultVO;
import com.yandan.demo.converter.Converter;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.form.NoticeForm;
import com.yandan.demo.service.NoticeService;
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
 * 2020/11/16  20:50
 */
@RestController
@RequestMapping("/api/notice")
@Slf4j
public class NoticeController {
    @Autowired
    private NoticeService noticeService;

    @PostMapping("/getList")
    public ResultVO<List<NoticeVO>> getNoticeList(@RequestBody Map<String,String> param){
        String departmentId=param.get("departmentId");
        List<NoticeVO> noticeVOList=noticeService.getNoticeList(departmentId);
        if(noticeVOList.size()==0) return ResultVOUtil.fail(ErrorEnum.LIST_IS_EMPTY.getCode(),
                ErrorEnum.LIST_IS_EMPTY.getMsg());
        return ResultVOUtil.success(noticeVOList);
    }
    @PostMapping("/add")
    public ResultVO<NoticeVO> addNotice(@Valid NoticeForm noticeForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("【添加公告】code:{}  , message: {}",ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        try {
            if("".equals(noticeForm.getDepartmentId())) noticeForm.setDepartmentId(null);
            NoticeVO noticeVO=noticeService.addNotice(Converter.noticeForm2CompanyNotice(noticeForm));
            return ResultVOUtil.success(noticeVO);
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【添加公告】code:{}  , message: {}",e.getCode(),
                    e.getMessage());
            return ResultVOUtil.fail(e.getCode(),
                    e.getMessage());
        }
    }
    @PostMapping("/delete")
    public ResultVO deleteNotices(@RequestParam(value = "name",defaultValue = "")String name){
        try {
            noticeService.deleteNotices(name);
            return ResultVOUtil.success("删除成功！");
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【删除公告】code:{}  , message: {}",e.getCode(),
                    e.getMessage());
            return ResultVOUtil.fail(e.getCode(),
                    e.getMessage());
        }
    }
    @PostMapping("/delete1")
    public ResultVO deleteNotice(@RequestBody Map<String,String> param){
        try {
            String name=param.get("name");
            noticeService.deleteNotices(name);
            return ResultVOUtil.success("删除成功！");
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【删除公告】code:{}  , message: {}",e.getCode(),
                    e.getMessage());
            return ResultVOUtil.fail(e.getCode(),
                    e.getMessage());
        }
    }
    @GetMapping("getPage")
    public PageVO<NoticeVO> getPage(@RequestParam(value = "departmentId",defaultValue = "")String departmentId){
        List<NoticeVO> noticeVOList=noticeService.getNoticeList(departmentId);
       PageVO<NoticeVO> pageVO=new PageVO<NoticeVO>();
       pageVO.setTotal(noticeVOList.size());
       pageVO.setRows(noticeVOList);
       return pageVO;
    }
    @GetMapping("/getlist")
    public ResultVO<List<NoticeVO>> getNotices(@RequestParam (value = "departmentId",defaultValue = "")String departmentId){
        List<NoticeVO> noticeVOList=noticeService.getNoticeList(departmentId);
        if(noticeVOList.size()==0) return ResultVOUtil.fail(ErrorEnum.LIST_IS_EMPTY.getCode(),
                ErrorEnum.LIST_IS_EMPTY.getMsg());
        return ResultVOUtil.success(noticeVOList);
    }
}
