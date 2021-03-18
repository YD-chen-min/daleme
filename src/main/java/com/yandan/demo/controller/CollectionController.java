package com.yandan.demo.controller;

import com.yandan.demo.VO.CollectInfoVO;
import com.yandan.demo.VO.PageVO;
import com.yandan.demo.VO.ResultVO;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.form.CollectForm;
import com.yandan.demo.service.CollectionService;
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
 * 2020/11/16  17:08
 */
@RestController
@RequestMapping("/api/task")
@Slf4j
public class CollectionController {
    @Autowired
    private CollectionService collectionService;

    @PostMapping("/add")
    public ResultVO addTask(@Valid CollectForm collectForm, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            log.error("【设置打卡任务】code:{}  ,  message:{}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        try {
            collectionService.addCollection(collectForm);
            return ResultVOUtil.success("设置成功！");
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【设置打卡任务】code:{}  ,  message:{}", e.getCode(),
                    e.getMessage());
            return ResultVOUtil.fail(e.getCode(),
                    e.getMessage());
        }
    }
    @GetMapping("/getList")
    public ResultVO<List<CollectInfoVO>> getTaskList(){
        List<CollectInfoVO> collectInfoVOList=collectionService.getCollectionList();
        if (collectInfoVOList.size()==0) return ResultVOUtil.fail(ErrorEnum.LIST_IS_EMPTY.getCode(),
                ErrorEnum.LIST_IS_EMPTY.getMsg());
        return ResultVOUtil.success(collectInfoVOList);
    }
    @GetMapping("/get")
    public ResultVO<CollectInfoVO> getTask(@RequestParam(value = "name",defaultValue = "")String name){
        if("".equals(name)){
            log.error("【获取打卡任务】code:{}  ,  message:{}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        try {
            CollectInfoVO collectInfoVO=collectionService.getCollection(name);
            return ResultVOUtil.success(collectInfoVO);
        } catch (ApiException e) {
            log.error("【获取打卡任务】code:{}  ,  message:{}", e.getCode(),
                    e.getMessage());
            return ResultVOUtil.fail(e.getCode(),
                    e.getMessage());
        }
    }
    @PostMapping("/modify")
    public ResultVO updateTask(@Valid CollectForm collectForm,BindingResult bindingResult,
                               @RequestParam(value = "oldName",defaultValue = "")String oldName){
        if("".equals(oldName)||bindingResult.hasErrors()){
            log.error("【修改打卡任务】code:{}  ,  message:{}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        try {
            collectionService.updateCollection(collectForm,oldName);
            return ResultVOUtil.success("修改成功！");
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【修改打卡任务】code:{}  ,  message:{}", e.getCode(),
                    e.getMessage());
            return ResultVOUtil.fail(e.getCode(),
                    e.getMessage());
        }
    }
    @PostMapping("/delete")
    public ResultVO deleteTasks(@RequestParam(value = "name",defaultValue = "")String names){
        if("".equals(names)){
            log.error("【删除打卡任务】code:{}  ,  message:{}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        try {
            collectionService.deleteCollections(names);
            return ResultVOUtil.success("删除成功！");
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【删除打卡任务】code:{}  ,  message:{}", e.getCode(),
                    e.getMessage());
            return ResultVOUtil.fail(e.getCode(),
                    e.getMessage());
        }
    }
    @GetMapping("/getPage")
    public PageVO<CollectInfoVO> getTaskLPage(){
        List<CollectInfoVO> collectInfoVOList=collectionService.getCollectionList();
        PageVO<CollectInfoVO> pageVO=new PageVO<CollectInfoVO>();
        pageVO.setTotal(collectInfoVOList.size());
        pageVO.setRows(collectInfoVOList);
        return pageVO;
    }
    @PostMapping("/get1")
    public ResultVO<CollectInfoVO> getTask1(@RequestBody Map<String,String> param){
        String id=param.get("id");
        if("".equals(id)){
            log.error("【获取打卡任务】code:{}  ,  message:{}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        try {
            CollectInfoVO collectInfoVO=collectionService.getCollectionById(id);
            return ResultVOUtil.success(collectInfoVO);
        } catch (ApiException e) {
            log.error("【获取打卡任务】code:{}  ,  message:{}", e.getCode(),
                    e.getMessage());
            return ResultVOUtil.fail(e.getCode(),
                    e.getMessage());
        }
    }
}
