package com.yandan.demo.controller;

import com.yandan.demo.VO.ResultVO;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.service.SMSService;
import com.yandan.demo.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Create by yandan
 * 2020/12/21  23:29
 */
@Slf4j
@Controller
public class SMSController {
    @Autowired
    private SMSService smsService;

    @GetMapping("/api/sendMessage")
    public ResultVO sendMessage(@RequestParam(value = "workIds",defaultValue = "")String workIds){
        if ("".equals(workIds)){
            log.error("【删除打卡任务】code:{}  ,  message:{}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        int count=smsService.sendMessage(workIds);
        return ResultVOUtil.success("成功发送"+count+"条提醒信息");
    }
}
