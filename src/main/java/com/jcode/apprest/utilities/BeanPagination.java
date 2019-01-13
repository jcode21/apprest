/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcode.apprest.utilities;

import java.util.List;

/**
 *
 * @author JCode
 */
public class BeanPagination {

    private Integer count_filter;
    private List<?> list;

    public BeanPagination() {
    }

    public BeanPagination(Integer count_filter, List<?> list) {
        this.count_filter = count_filter;
        this.list = list;
    }

    public Integer getCount_filter() {
        return count_filter;
    }

    public void setCount_filter(Integer count_filter) {
        this.count_filter = count_filter;
    }

    public List<?> getList() {
        return list;
    }

    public void setList(List<?> list) {
        this.list = list;
    }

}
