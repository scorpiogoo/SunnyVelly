package com.erp.zhubenerp.base;

/**
 * Created by gubin on 2017/12/29.
 */

public class BaseObject {
    protected int Status;
    protected String Message;

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
}
