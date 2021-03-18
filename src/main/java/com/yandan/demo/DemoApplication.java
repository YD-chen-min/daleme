package com.yandan.demo;

import com.yandan.demo.exception.ApiException;
import com.yandan.demo.service.DataService;
import com.yandan.demo.service.impl.DataServiceImpl;
import com.yandan.demo.util.BeanUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.Serializable;
import java.text.ParseException;

@SpringBootApplication
@Slf4j
public class DemoApplication  {

    public static void main(String[] args) throws ParseException {
       BeanUtil.applicationContext= SpringApplication.run(DemoApplication.class, args);
       DataServiceImpl dataService=BeanUtil.getBean(DataServiceImpl.class);
       dataService.taskService();
       dataService.signTimesService();
       dataService.countsService();

    }

}
