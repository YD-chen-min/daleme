package com.yandan.demo.controller;

import com.yandan.demo.VO.PageVO;
import com.yandan.demo.VO.ResultVO;
import com.yandan.demo.VO.UserMsg;
import com.yandan.demo.VO.UserVO;
import com.yandan.demo.config.MyConfig;
import com.yandan.demo.converter.Converter;
import com.yandan.demo.dataobject.WorkInfo;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.form.UserForm;
import com.yandan.demo.service.DepartmentService;
import com.yandan.demo.service.UserService;
import com.yandan.demo.util.KeyUtil;
import com.yandan.demo.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Create by yandan
 * 2020/11/14  15:00
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    private MyConfig myConfig;
    @GetMapping("/getlist")
    public ResultVO<List<WorkInfo>> getUserList(@RequestParam(value = "departmentId",defaultValue = "") String departmentId){
        List<WorkInfo> workInfoList;
        if(departmentId==null||"".equals(departmentId)) workInfoList=userService.getAll();
        else workInfoList=userService.getUsersByDepartmentId(departmentId);
        if(workInfoList.size()==0) return ResultVOUtil.fail(ErrorEnum.LIST_IS_EMPTY.getCode(),
                ErrorEnum.LIST_IS_EMPTY.getMsg());
        return ResultVOUtil.success(workInfoList);
    }

    @PostMapping("/add")
    public ResultVO<UserMsg> addUser(@Valid UserForm userForm,BindingResult bindingResult)  {
        if(bindingResult.hasErrors()){
            log.error("【添加用户】 参数错误");
            return ResultVOUtil.fail(ErrorEnum.USER_FORM_PARAM_ERROR.getCode(),
                    ErrorEnum.USER_FORM_PARAM_ERROR.getMsg());
        }
        int n=myConfig.getWorkIdNumber();
        String workId=userForm.getDepartmentId()+ KeyUtil.getKey(n);
        while(userService.workIdExists(workId)){
            workId=userForm.getDepartmentId()+KeyUtil.getKey(n);
        }
        try {
            WorkInfo userInfo=userService.addUser(workId,userForm.getName(),userForm.getDepartmentId(),
                    Integer.parseInt(userForm.getRoleId()),myConfig.getPrePassword(),myConfig.getIcon());
            UserMsg userMsg=new UserMsg();
            userMsg.setWorkId(userInfo.getWorkId());
            String department=departmentService.getDepartment(userInfo.getDepartmentId()).getName();
            userMsg.setDepartment(department);
            userMsg.setPassword("123456");
            userMsg.setRole(userInfo.getIsAdmin()==1? "管理员":"普通用户");
            userMsg.setName(userInfo.getName());
            return ResultVOUtil.success(userMsg);
        } catch (ApiException e) {
            log.error("【添加用户】code:{} , message:{}",e.getCode(),e.getMessage());
            return ResultVOUtil.fail(e.getCode(),e.getMessage());
        }
    }
    @PostMapping("/delete")
    public ResultVO deleteUsers(@RequestParam(value = "workIds",defaultValue = "") String workIds){
        if("".equals(workIds)){
            log.error("【删除用户】code:{}  ,  message: {}",
                    ErrorEnum.WORK_ID_IS_EMPTY.getCode(),
                    ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
            return ResultVOUtil.fail(ErrorEnum.WORK_ID_IS_EMPTY.getCode(),
                    ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
        }
        try {
            userService.deleteUsers(workIds);
        } catch (ApiException e) {
            log.error("【用户删除】code:{}  message:{}",e.getCode(),e.getMessage());
            e.printStackTrace();
            return ResultVOUtil.fail(e.getCode(),e.getMessage());
        }
        return ResultVOUtil.success("删除成功！");
    }
    @PostMapping("update")
    public ResultVO<UserVO> updateUserBaseInfo(@RequestBody Map<String,String> param){
        String workId=param.get("workId");
        String name=param.get("name");
        String phoneNumber=param.get("phoneNumber");
        String icon=param.get("avatar");
        String password=param.get("password");
        String mac=param.get("mac");
        if(workId==null||"".equals(workId)) {
            log.error("【修改用户基本信息】code: {},message: {}",ErrorEnum.WORK_ID_IS_EMPTY.getCode(),
                    ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
            return   ResultVOUtil.fail(ErrorEnum.WORK_ID_IS_EMPTY.getCode(),ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
        }
        WorkInfo workInfo=new WorkInfo();
        workInfo.setWorkId(workId);
        workInfo.setPhoneNumber(phoneNumber);
        workInfo.setPassword(password);
        workInfo.setMac(mac);
        workInfo.setName(name);
        workInfo.setIcon(icon);
        try {
           workInfo= userService.updateUserBaseInfo(workInfo);
           UserVO userVO= Converter.user2UserVO(workInfo,
                   departmentService.getDepartment(workInfo.getDepartmentId()).getName());
           return ResultVOUtil.success(userVO);
        } catch (ApiException e) {
            log.error("【修改用户基本信息】code: {},message: {}】",e.getCode(),e.getMessage());
            e.printStackTrace();
            return  ResultVOUtil.fail(e.getCode(),e.getMessage());

        }
    }
    @PostMapping("/role/update")
    public ResultVO updateUserRole(@RequestParam(value = "workId",defaultValue = "") String workId,
                                   @RequestParam(value = "role",defaultValue = "")  Integer role){
        if("".equals(workId)||"".equals(role)){
            log.error("【修改用户权限】code:{ }, message: {}",ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        try {
            userService.updateUserRole(workId,role);
        } catch (ApiException e) {
            log.error("【修改用户权限】code: {}, message: {}",e.getCode(),e.getMessage());
            e.printStackTrace();
            return ResultVOUtil.fail(e.getCode(),e.getMessage());
        }
        return ResultVOUtil.success("修改成功！");
    }
    @PostMapping("/icon/update")
    public ResultVO updateUserIcon(@RequestBody Map<String,String> param){
        String workId=param.get("workId");
        String icon=param.get("icon");
        if("".equals(workId)||"".equals(icon)){
            log.error("【更换头像】code:{} , message:{}",ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        try {
            userService.updateUserIcon(workId,icon);
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【更换头像】code:{} , message:{}",e.getCode(),e.getMessage());
            return ResultVOUtil.fail(e.getCode(),e.getMessage());
        }
        return ResultVOUtil.success("更换成功！");
    }

    @GetMapping("/get")
    public ResultVO<UserVO> getUser(@RequestParam(value = "workId",defaultValue = "") String workId){
        if("".equals(workId)){
            log.error("【获取用户信息】code: {}, message: {}",
                    ErrorEnum.WORK_ID_IS_EMPTY.getCode(),
                    ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
            return ResultVOUtil.fail(ErrorEnum.WORK_ID_IS_EMPTY.getCode(),
                    ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
        }
        WorkInfo workInfo=userService.getUserByWorkId(workId);
        UserVO userVO=Converter.user2UserVO(workInfo,
                departmentService.getDepartment(workInfo.getDepartmentId()).getName());
        return ResultVOUtil.success(userVO);
    }
    @PostMapping("/login")
    public ResultVO<UserVO> login(@RequestBody Map<String,String> param){
        String workId=param.get("workId");
        String password=param.get("password");
        String mac=param.get("mac");
        if ("".equals(workId)||"".equals(password)||"".equals(mac)){
            log.error("【用户登录】code:{},  message: {}",ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        WorkInfo workInfo=userService.getUserByWorkId(workId);
        if(workInfo==null) {
            log.error("【用户登录】code:{},  message: {}",ErrorEnum.USER_PASSWORD_ERROR.getCode(),
                    ErrorEnum.USER_PASSWORD_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.USER_PASSWORD_ERROR.getCode(),
                    ErrorEnum.USER_PASSWORD_ERROR.getMsg());
        }
        if(!(workInfo.getMac()==null||"null".equals(workInfo.getMac())||"".equals(workInfo.getMac()))){
            if(!mac.equals(workInfo.getMac())){
                log.error("【用户登录】code:{},  message: {}",ErrorEnum.USER_MAC_ERROR.getCode(),
                        ErrorEnum.USER_MAC_ERROR.getMsg());
                return ResultVOUtil.fail(ErrorEnum.USER_MAC_ERROR.getCode(),
                        ErrorEnum.USER_MAC_ERROR.getMsg());
            }
        }
        if(!password.equals(workInfo.getPassword())){
            log.error("【用户登录】code:{},  message: {}",ErrorEnum.USER_PASSWORD_ERROR.getCode(),
                    ErrorEnum.USER_PASSWORD_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.USER_PASSWORD_ERROR.getCode(),
                    ErrorEnum.USER_PASSWORD_ERROR.getMsg());
        }
        if (!(mac==null||"".equals(mac))) {
            try {
                userService.setMac(workId,mac);
            } catch (ApiException e) {
                log.error("【用户登录】code:{},  message: {}",ErrorEnum.USER_PASSWORD_ERROR.getCode(),
                        ErrorEnum.USER_PASSWORD_ERROR.getMsg());
                return ResultVOUtil.fail(ErrorEnum.USER_PASSWORD_ERROR.getCode(),
                        ErrorEnum.USER_PASSWORD_ERROR.getMsg());
            }
        }
        UserVO  userVO=Converter.user2UserVO(workInfo,
                departmentService.getDepartment(workInfo.getDepartmentId()).getName());
        userVO.setOldPassword(false);
        userVO.setMessageFinish(true);
        if(workInfo.getPhoneNumber()==null||"null".equals(workInfo.getPhoneNumber())||"".equals(workInfo.getPhoneNumber())){
            userVO.setMessageFinish(false);
        }
        if(workInfo.getIcon()==null||"null".equals(workInfo.getIcon())||"".equals(workInfo.getIcon())){
            userVO.setMessageFinish(false);
        }
        if(workInfo.getName()==null||"null".equals(workInfo.getName())||"".equals(workInfo.getName())){
            userVO.setMessageFinish(false);
        }
        if(workInfo.getPassword().equals(myConfig.getPrePassword())){
            userVO.setOldPassword(true);
        }
        return ResultVOUtil.success(userVO);
    }
    @GetMapping("/getPage")
    public PageVO<UserVO> getPage(@RequestParam(value = "departmentId",defaultValue = "")String departmentId){
        List<WorkInfo> workInfoList;
        if(departmentId==null||"".equals(departmentId)) workInfoList=userService.getAll();
        else workInfoList=userService.getUsersByDepartmentId(departmentId);
        List<UserVO> userVOList=new ArrayList<UserVO>();
        for (WorkInfo workInfo:workInfoList){
            userVOList.add(Converter.user2UserVO(workInfo,
                    departmentService.getDepartment(workInfo.getDepartmentId()).getName()));
        }
        PageVO<UserVO> pageVO=new PageVO<UserVO>();
        pageVO.setTotal(userVOList.size());
        pageVO.setRows(userVOList);
        return pageVO;
    }
    @PostMapping("/gget")
    public ResultVO<UserVO> getUsergg(@RequestBody Map<String,String> param){
        String workId=param.get("workId");
        if("".equals(workId)){
            log.error("【获取用户信息】code: {}, message: {}",
                    ErrorEnum.WORK_ID_IS_EMPTY.getCode(),
                    ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
            return ResultVOUtil.fail(ErrorEnum.WORK_ID_IS_EMPTY.getCode(),
                    ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
        }
        WorkInfo workInfo=userService.getUserByWorkId(workId);
        UserVO userVO=Converter.user2UserVO(workInfo,
                departmentService.getDepartment(workInfo.getDepartmentId()).getName());
        return ResultVOUtil.success(userVO);
    }
    @GetMapping("/getList")
    public ResultVO<List<UserVO>> getList(@RequestParam(value = "departmentId",defaultValue = "")String departmentId){
        List<WorkInfo> workInfoList;
        if(departmentId==null||"".equals(departmentId)) workInfoList=userService.getAll();
        else workInfoList=userService.getUsersByDepartmentId(departmentId);
        List<UserVO> userVOList=new ArrayList<UserVO>();
        for (WorkInfo workInfo:workInfoList){
            userVOList.add(Converter.user2UserVO(workInfo,
                    departmentService.getDepartment(workInfo.getDepartmentId()).getName()));
        }

        return ResultVOUtil.success(userVOList);
    }
    @PostMapping("/modify")
    public ResultVO<UserVO> modifyUserInfo(@RequestBody Map<String,String> param){
        String workId=param.get("workId");
        String name=param.get("name");
        String phoneNumber=param.get("phoneNumber");
        String departmentId=param.get("departmentId");
        if(workId==null||"".equals(workId)) {
            log.error("【修改用户基本信息】code: {},message: {}",ErrorEnum.WORK_ID_IS_EMPTY.getCode(),
                    ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
            return   ResultVOUtil.fail(ErrorEnum.WORK_ID_IS_EMPTY.getCode(),ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
        }
        WorkInfo workInfo=new WorkInfo();
        workInfo.setWorkId(workId);
        workInfo.setPhoneNumber(phoneNumber);
        workInfo.setName(name);
        workInfo.setDepartmentId(departmentId);
        try {
            workInfo= userService.updateUserBaseInfo(workInfo);
            UserVO userVO= Converter.user2UserVO(workInfo,
                    departmentService.getDepartment(workInfo.getDepartmentId()).getName());
            return ResultVOUtil.success(userVO);
        } catch (ApiException e) {
            log.error("【修改用户基本信息】code: {},message: {}】",e.getCode(),e.getMessage());
            e.printStackTrace();
            return  ResultVOUtil.fail(e.getCode(),e.getMessage());

        }
    }
    @PostMapping("/resetPassword")
    public ResultVO<UserVO> resetPassword(@RequestBody Map<String,String> param){
        String workId=param.get("workId");
        String password=myConfig.getPrePassword();
        if(workId==null||"".equals(workId)) {
            log.error("【修改用户基本信息】code: {},message: {}",ErrorEnum.WORK_ID_IS_EMPTY.getCode(),
                    ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
            return   ResultVOUtil.fail(ErrorEnum.WORK_ID_IS_EMPTY.getCode(),ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
        }
        WorkInfo workInfo=userService.getUserByWorkId(workId);
        workInfo.setPassword(password);
        try {
            workInfo= userService.updateUserBaseInfo(workInfo);
            UserVO userVO= Converter.user2UserVO(workInfo,
                    departmentService.getDepartment(workInfo.getDepartmentId()).getName());
            return ResultVOUtil.success(userVO);
        } catch (ApiException e) {
            log.error("【重置密码】code: {},message: {}】",e.getCode(),e.getMessage());
            e.printStackTrace();
            return  ResultVOUtil.fail(e.getCode(),e.getMessage());

        }
    }
    @PostMapping("/resetMac")
    public ResultVO<UserVO> setMac(@RequestBody Map<String,String> param){
        String workId=param.get("workId");
        if(workId==null||"".equals(workId)) {
            log.error("【修改用户基本信息】code: {},message: {}",ErrorEnum.WORK_ID_IS_EMPTY.getCode(),
                    ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
            return   ResultVOUtil.fail(ErrorEnum.WORK_ID_IS_EMPTY.getCode(),ErrorEnum.WORK_ID_IS_EMPTY.getMsg());
        }
        WorkInfo workInfo=userService.getUserByWorkId(workId);
        workInfo.setMac(null);
        try {
            workInfo= userService.updateUserBaseInfo(workInfo);
            UserVO userVO= Converter.user2UserVO(workInfo,
                    departmentService.getDepartment(workInfo.getDepartmentId()).getName());
            return ResultVOUtil.success(userVO);
        } catch (ApiException e) {
            log.error("【解除绑定】code: {},message: {}】",e.getCode(),e.getMessage());
            e.printStackTrace();
            return  ResultVOUtil.fail(e.getCode(),e.getMessage());

        }
    }
}
