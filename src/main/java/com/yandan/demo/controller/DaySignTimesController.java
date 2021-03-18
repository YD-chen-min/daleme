package com.yandan.demo.controller;

import com.yandan.demo.service.DaySignTimesDepartmentService;
import com.yandan.demo.service.DaySignTimesWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Create by yandan
 * 2020/11/22  21:07
 */
@RestController
@RequestMapping("/daySignTime")
public class DaySignTimesController {
    @Autowired
    private DaySignTimesWorkService daySignTimesWorkService;
    @Autowired
    private DaySignTimesDepartmentService daySignTimesDepartmentService;

}
