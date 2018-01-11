package com.erp.zhubenerp.model;

import java.io.Serializable;
/**
 * Created by gubin on 2017/12/29.
 */

public class UserWrapper extends com.erp.zhubenerp.base.BaseObject implements Serializable {
    private User Data;

    public User getData() {
        return Data;
    }

    public void setData(User Data) {
        this.Data = Data;
    }

    public static class User implements Serializable {
        private int UserID;

        private int CompanyID;

        private String UserName;

        private String UserPass;

        private String TrueName;

        private String Gender;

        private String Tel;

        private int Status;

        private String CreateTime;

        private int DepartmentID;

        private String HeadImageUrl;

        private int GroupID;

        public int getUserID() {
            return UserID;
        }

        public void setUserID(int UserID) {
            this.UserID = UserID;
        }

        public int getCompanyID() {
            return CompanyID;
        }

        public void setCompanyID(int CompanyID) {
            this.CompanyID = CompanyID;
        }

        public String getUserName() {
            return UserName;
        }

        public void setUserName(String UserName) {
            this.UserName = UserName;
        }

        public String getUserPass() {
            return UserPass;
        }

        public void setUserPass(String UserPass) {
            this.UserPass = UserPass;
        }

        public String getTrueName() {
            return TrueName;
        }

        public void setTrueName(String TrueName) {
            this.TrueName = TrueName;
        }

        public String getGender() {
            return Gender;
        }

        public void setGender(String Gender) {
            this.Gender = Gender;
        }

        public String getTel() {
            return Tel;
        }

        public void setTel(String Tel) {
            this.Tel = Tel;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getDepartmentID() {
            return DepartmentID;
        }

        public void setDepartmentID(int DepartmentID) {
            this.DepartmentID = DepartmentID;
        }

        public String getHeadImageUrl() {
            return HeadImageUrl;
        }

        public void setHeadImageUrl(String HeadImageUrl) {
            this.HeadImageUrl = HeadImageUrl;
        }

        public int getGroupID() {
            return GroupID;
        }

        public void setGroupID(int GroupID) {
            this.GroupID = GroupID;
        }
    }
}
