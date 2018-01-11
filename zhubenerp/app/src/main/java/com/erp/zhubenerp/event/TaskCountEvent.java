package com.erp.zhubenerp.event;

/**
 * Created by gubin on 2018/1/8.
 */

public class TaskCountEvent {
    private int id;
    private int allSum;
    private int todaySum;

    public TaskCountEvent() {

    }

    public TaskCountEvent(int id, int allSum, int todaySum) {
        this.id = id;
        this.allSum = allSum;
        this.todaySum = todaySum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAllSum() {
        return allSum;
    }

    public void setAllSum(int allSum) {
        this.allSum = allSum;
    }

    public int getTodaySum() {
        return todaySum;
    }

    public void setTodaySum(int todaySum) {
        this.todaySum = todaySum;
    }
}
