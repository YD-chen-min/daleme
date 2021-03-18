package com.yandan.demo.controller;

import com.yandan.demo.VO.ResultVO;
import com.yandan.demo.dataobject.AdminInfo;
import com.yandan.demo.repository.AdminRepository;
import com.yandan.demo.util.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Create by yandan
 * 2020/11/22  14:51
 */
@Controller
public class WebLoginController {
    @Autowired
    public AdminRepository adminRepository;

    @PostMapping("/api/web/login")
    public String webLogin(@RequestParam(value = "id",defaultValue = "")String id,
                           @RequestParam(value = "password",defaultValue = "")String password,
                           HttpServletRequest request, HttpServletResponse response){
        if("".equals(id)||"".equals(password)) return "error";
        AdminInfo adminInfo=adminRepository.findById(id).orElse(null);
        if(adminInfo==null) return "error";
        if(!password.equals(adminInfo.getPassword())) return "error";
        request.getSession().setAttribute("adminInfo",adminInfo);
        return "UI";
    }
    @GetMapping("/api/login")
    public String index(){
        return "login";
    }
    @ResponseBody
    @PostMapping("/login")
    public ResultVO login(@RequestParam(value = "id",defaultValue = "")String id,
                          @RequestParam(value = "password",defaultValue = "")String password){
        if("".equals(id)||"".equals(password))  return ResultVOUtil.fail(-1,"用户名或密码错误");
        AdminInfo adminInfo=adminRepository.findById(id).orElse(null);
        if(adminInfo==null) return ResultVOUtil.fail(-1,"用户名或密码错误");
        if(!password.equals(adminInfo.getPassword()))  return ResultVOUtil.fail(-1,"用户名或密码错误");
        return ResultVOUtil.success("登陆成功!");
    }
}
