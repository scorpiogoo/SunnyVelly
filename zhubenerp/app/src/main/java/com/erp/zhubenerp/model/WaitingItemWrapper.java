package com.erp.zhubenerp.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author: voiceofnet
 * email: pengkun@jingzhengu.com
 * phone:18101032717
 * @time: 2016/7/2 19:38
 * @desc:
 */
public class WaitingItemWrapper extends com.erp.zhubenerp.base.BaseObject {


    /**
     * CreateDateStrYMD : 2016年07月01日
     * CreateDateStrHM : 19:17
     * LinkMan : 李白
     * CustomerName : 李白
     * FollowupId : 286
     * CustomerId : 20
     * FollowUpStatus : 预约到店
     * FollowingRecord : 客户今儿又来了。
     * ReturnVisit : null
     * ReturnVisitDate : 2016-07-02T19:44:20.01
     * ReturnVisitTime : null
     * SpecialInstruction : sdf
     * FaliureReason : 价格原因
     * OnShopDate : 2016-07-05T00:00:00
     * OnShopTime : 上午
     * FollowType : 5
     * CreateDate : 2016-07-01T19:17:13.77
     * CreateUser : admin
     * NextShopDate : 2016-07-02T19:44:20.01
     * NextShopTime : 随时
     * CreateUserID : 53
     * CustomerLevel : null
     * FollowUpItemState : 0
     */

    private int TodaySum = 1;
    private int AllSum = 1;

    public int getAllSum() {
        return AllSum;
    }

    public void setAllSum(int allSum) {
        AllSum = allSum;
    }

    public int getTodaySum() {
        return TodaySum;
    }

    public void setTodaySum(int todaySum) {
        TodaySum = todaySum;
    }

    private List<WaitingItem> Data;


    public List<WaitingItem> getData() {
        return Data;
    }

    public void setData(List<WaitingItem> Data) {
        this.Data = Data;
    }

    public static class WaitingItem implements Serializable{
        private String CreateDateStrYMD = "2018年1月6日";
        private String CreateDateStrHM = "10:40";
        private String LinkMan = "gubin";
        private String CustomerName = "gubin";
        private int FollowupId = 21;
        private int CustomerId = 30;
        private String FollowUpStatus = "正在处理";
        private String FollowingRecord = "维修进行中";
        private String SpecialInstruction = "可以";
        private String FaliureReason = "件没到";
        private String OnShopDate = "2017年12月20日";//到店日期
        private String OnShopTime = "上午";
        private String FollowType = "4";
        private String CreateDate = "2017年12月18日";
        private String CreateUser = "admin";
        private String NextShopDate = "2018年1月10日";//下次跟进日期
        private String NextShopTime = "下午";
        private int CreateUserID = 18;
        private String CustomerLevel = null;
        private int FollowUpItemState = 0;
        private String CustomerStatus = "OK";
        private int IsRead;
        private String PreCharDate;

        public String getPreCharDate() {
            return PreCharDate;
        }

        public void setPreCharDate(String preCharDate) {
            PreCharDate = preCharDate;
        }

        public int getIsRead() {
            return IsRead;
        }

        public void setIsRead(int isRead) {
            IsRead = isRead;
        }

        public String getCustomerStatus() {
            return CustomerStatus;
        }

        public void setCustomerStatus(String customerStatus) {
            CustomerStatus = customerStatus;
        }

        public String getCreateDateStrYMD() {
            return CreateDateStrYMD;
        }

        public void setCreateDateStrYMD(String CreateDateStrYMD) {
            this.CreateDateStrYMD = CreateDateStrYMD;
        }

        public String getCreateDateStrHM() {
            return CreateDateStrHM;
        }

        public void setCreateDateStrHM(String CreateDateStrHM) {
            this.CreateDateStrHM = CreateDateStrHM;
        }

        public String getLinkMan() {
            return LinkMan;
        }

        public void setLinkMan(String LinkMan) {
            this.LinkMan = LinkMan;
        }

        public String getCustomerName() {
            return CustomerName;
        }

        public void setCustomerName(String CustomerName) {
            this.CustomerName = CustomerName;
        }

        public int getFollowupId() {
            return FollowupId;
        }

        public void setFollowupId(int FollowupId) {
            this.FollowupId = FollowupId;
        }

        public int getCustomerId() {
            return CustomerId;
        }

        public void setCustomerId(int CustomerId) {
            this.CustomerId = CustomerId;
        }

        public String getFollowUpStatus() {
            return FollowUpStatus;
        }

        public void setFollowUpStatus(String FollowUpStatus) {
            this.FollowUpStatus = FollowUpStatus;
        }

        public String getFollowingRecord() {
            return FollowingRecord;
        }

        public void setFollowingRecord(String FollowingRecord) {
            this.FollowingRecord = FollowingRecord;
        }


        public String getSpecialInstruction() {
            return SpecialInstruction;
        }

        public void setSpecialInstruction(String SpecialInstruction) {
            this.SpecialInstruction = SpecialInstruction;
        }

        public String getFaliureReason() {
            return FaliureReason;
        }

        public void setFaliureReason(String FaliureReason) {
            this.FaliureReason = FaliureReason;
        }

        public String getOnShopDate() {
            return OnShopDate;
        }

        public void setOnShopDate(String OnShopDate) {
            this.OnShopDate = OnShopDate;
        }

        public String getOnShopTime() {
            return OnShopTime;
        }

        public void setOnShopTime(String OnShopTime) {
            this.OnShopTime = OnShopTime;
        }

        public String getFollowType() {
            return FollowType;
        }

        public void setFollowType(String FollowType) {
            this.FollowType = FollowType;
        }

        public String getCreateDate() {
            return CreateDate;
        }

        public void setCreateDate(String CreateDate) {
            this.CreateDate = CreateDate;
        }

        public String getCreateUser() {
            return CreateUser;
        }

        public void setCreateUser(String CreateUser) {
            this.CreateUser = CreateUser;
        }

        public String getNextShopDate() {
            return NextShopDate;
        }

        public void setNextShopDate(String NextShopDate) {
            this.NextShopDate = NextShopDate;
        }

        public String getNextShopTime() {
            return NextShopTime;
        }

        public void setNextShopTime(String NextShopTime) {
            this.NextShopTime = NextShopTime;
        }

        public int getCreateUserID() {
            return CreateUserID;
        }

        public void setCreateUserID(int CreateUserID) {
            this.CreateUserID = CreateUserID;
        }

        public String getCustomerLevel() {
            return CustomerLevel;
        }

        public void setCustomerLevel(String CustomerLevel) {
            this.CustomerLevel = CustomerLevel;
        }

        public int getFollowUpItemState() {
            return FollowUpItemState;
        }

        public void setFollowUpItemState(int FollowUpItemState) {
            this.FollowUpItemState = FollowUpItemState;
        }
    }
}
