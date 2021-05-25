package com.gksl.util;

import java.io.Serializable;

public class UtilResult implements Serializable {

    private int code;
    private String msg;
    private Object date;

    public UtilResult() {
    }

    public UtilResult(int code, String msg, Object date) {
        this.code = code;
        this.msg = msg;
        this.date = date;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getDate() {
        return date;
    }

    public void setDate(Object date) {
        this.date = date;
    }
}
