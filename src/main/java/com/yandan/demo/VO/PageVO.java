package com.yandan.demo.VO;

import lombok.Data;

import java.util.List;

/**
 * Create by yandan
 * 2020/11/22  15:54
 */
@Data
public class PageVO<T> {
    private int total;
    private List<T> rows;
}
