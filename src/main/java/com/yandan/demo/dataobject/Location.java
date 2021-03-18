package com.yandan.demo.dataobject;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Create by yandan
 * 2020/11/14  11:26
 */
@Entity
@Data
public class Location {
    @Id
    private String locationId;
    private String locationName;
    private String locationInfo;
    private String address;

}
