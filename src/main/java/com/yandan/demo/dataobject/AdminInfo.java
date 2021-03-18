package com.yandan.demo.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create by yandan
 * 2020/11/22  14:53
 */
@Data
@Entity
public class AdminInfo {
    @Id
    private String id;
    private String password;
}
