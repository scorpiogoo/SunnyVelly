package com.erp.zhubenerp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
/**
 * 服务器返回对象基类
 * Created by gubin on 2017/12/29.
 */

public class BaseObject implements Serializable, Parcelable {
    private int Status;

    private String Message;

    private int flag;

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "BaseObject{" +
                ", Status=" + Status +
                ", Message=" + Message + '\'' +
                ", flag=" + flag +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flag) {
        dest.writeInt(this.Status);
        dest.writeString(this.Message);
        dest.writeInt(this.flag);
    }

    public BaseObject() {

    }

    protected BaseObject(Parcel in) {
        this.Status = in.readInt();
        this.Message = in.readString();
        this.flag = in.readInt();
    }
}
