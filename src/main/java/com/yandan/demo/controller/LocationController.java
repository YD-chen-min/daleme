package com.yandan.demo.controller;

import com.yandan.demo.VO.PageVO;
import com.yandan.demo.VO.ResultVO;
import com.yandan.demo.dataobject.Location;
import com.yandan.demo.enums.ErrorEnum;
import com.yandan.demo.exception.ApiException;
import com.yandan.demo.service.LocationService;
import com.yandan.demo.util.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/16  15:07
 */
@RestController
@RequestMapping("/api/location")
@Slf4j
public class LocationController {
    @Autowired
    private LocationService locationService;

    @PostMapping("/add")
    public ResultVO<Location> addLocation(@RequestParam(value = "locationInfo",defaultValue = "")String locationInfo,
                                          @RequestParam(value = "name",defaultValue = "")String name,
                                          @RequestParam(value = "address",defaultValue = "")String address){
        if("".equals(locationInfo)||"".equals(name)){
            log.error("【录入打卡限制地址】code={}, message:{}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        Location location=new Location();
        location.setLocationName(name);
        location.setLocationInfo(locationInfo);
        location.setAddress(address);
        try {
            location=locationService.addLocation(location);
            return ResultVOUtil.success(location);
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【录入打卡限制地址】code={}, message:{}",e.getCode(),e.getMessage());
            return ResultVOUtil.fail(e.getCode(),e.getMessage());
        }
    }
    @PostMapping("/modify")
    public ResultVO updateLocation(@RequestParam(value = "locationId",defaultValue = "")String locationId,
                                   @RequestParam(value = "locationName",defaultValue = "")String locationName,
                                   @RequestParam(value = "locationInfo",defaultValue = "")String locationInfo){
        if("".equals(locationId)||"".equals(locationInfo)||"".equals(locationName)) {
            log.error("【修改打卡限制地址】code={}, message:{}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        Location location=new Location();
        location.setLocationInfo(locationInfo);
        location.setLocationName(locationName);
        location.setLocationId(locationId);
        try {
           location= locationService.updateLocation(location);
           return ResultVOUtil.success("修改成功！");
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【修改打卡限制地址】code={}, message:{}",e.getCode(),e.getMessage());
            return ResultVOUtil.fail(e.getCode(),e.getMessage());
        }
    }
    @PostMapping("/delete")
    public ResultVO deleteLocations(@RequestParam(value = "name",defaultValue = "")String names){
        if("".equals(names)){
            log.error("【删除打卡限制地址】code={}, message:{}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        try {
            locationService.deleteLocationsByName(names);
            return ResultVOUtil.success("删除成功！");
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【删除打卡限制地址】code={}, message:{}",e.getCode(),e.getMessage());
            return ResultVOUtil.fail(e.getCode(),e.getMessage());
        }
    }
    @GetMapping("/getList")
    public ResultVO<List<Location>> getLocationList(){
        List<Location> locationList=locationService.getLocationList();
        if (locationList==null||locationList.size()==0)  return ResultVOUtil.fail(ErrorEnum.LIST_IS_EMPTY.getCode(),
                ErrorEnum.LIST_IS_EMPTY.getMsg());
        return ResultVOUtil.success(locationList);
    }
    @GetMapping("/get")
    public ResultVO<Location> getLocation(@RequestParam(value = "name",defaultValue = "")String name){
        if("".equals(name)){
            log.error("【获取打卡限制地址】code={}, message:{}", ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
            return ResultVOUtil.fail(ErrorEnum.PARAM_ERROR.getCode(),
                    ErrorEnum.PARAM_ERROR.getMsg());
        }
        try {
            Location location=locationService.getLocationByName(name);
            return ResultVOUtil.success(location);
        } catch (ApiException e) {
            e.printStackTrace();
            log.error("【获取打卡限制地址】code={}, message:{}",e.getCode(),e.getMessage());
            return ResultVOUtil.fail(e.getCode(),e.getMessage());
        }
    }
    @GetMapping("/getPage")
    public PageVO<Location> getPage(){
        List<Location> locationList=locationService.getLocationList();
        PageVO<Location> pageVO=new PageVO<Location>();
        pageVO.setRows(locationList);
        pageVO.setTotal(locationList.size());
        return pageVO;
    }
}
