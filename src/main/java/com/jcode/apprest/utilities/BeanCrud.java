/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jcode.apprest.utilities;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 * @author JCode
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeanCrud {

    private String message_server;
    private BeanPagination beanPagination;

    public BeanCrud() {
    }

    public BeanCrud(BeanPagination beanPagination) {
        this.beanPagination = beanPagination;
    }

    public String getMessage_server() {
        return message_server;
    }

    public void setMessage_server(String message_server) {
        this.message_server = message_server;
    }

    public BeanPagination getBeanPagination() {
        return beanPagination;
    }

    public void setBeanPagination(BeanPagination beanPagination) {
        this.beanPagination = beanPagination;
    }

}
