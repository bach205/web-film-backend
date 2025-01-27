/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.huybach.resources.Model;

import java.util.List;

/**
 *
 * @author HOME PC
 */
public class Response {
    private int status;
    private String message;
    private Object data;
    private List<Object> listData;

    public Response(int status, String message, Object data, List<Object> listData) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.listData = listData;
    }

    public Response(int status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public Response(int status, String message, List<Object> listData) {
        this.status = status;
        this.message = message;
        this.listData = listData;
    }
    
    

    public List<Object> getListData() {
        return listData;
    }

    public void setListData(List<Object> listData) {
        this.listData = listData;
    }

    

    public Response() {
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
    
}
