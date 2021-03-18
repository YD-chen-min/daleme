package com.yandan.demo.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



/**
 * Create by yandan
 * 2020/11/14  15:45
 */
@Data
@Component
public class MyConfig {
    @Value("${my-config.key.workIdNumber}")
    private Integer workIdNumber;
    @Value("${my-config.prePassword}")
    private String prePassword;
    @Value("${my-config.key.locationIdNumber}")
    private Integer locationNumber;
    @Value("${my-config.key.collectionIdNumber}")
    private Integer collectionNumber;
    @Value("${my-config.key.noticeIdNumber}")
    private Integer noticeIdNumber;
    @Value("${my-config.key.signInIdNumber}")
    private Integer signInIdNumber;
    @Value("${my-config.key.idNumber}")
    private Integer idNumber;
}
