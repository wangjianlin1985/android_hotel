package com.mobileserver.domain;

public class OrderInfo {
    /*订单编号*/
    private String orderNumber;
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /*预订的房间*/
    private String roomObj;
    public String getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(String roomObj) {
        this.roomObj = roomObj;
    }

    /*预订的用户*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*预订开始时间*/
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*离开时间*/
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /*订单金额*/
    private float orderMoney;
    public float getOrderMoney() {
        return orderMoney;
    }
    public void setOrderMoney(float orderMoney) {
        this.orderMoney = orderMoney;
    }

    /*附加信息*/
    private String orderMemo;
    public String getOrderMemo() {
        return orderMemo;
    }
    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    /*下单时间*/
    private String orderAddTime;
    public String getOrderAddTime() {
        return orderAddTime;
    }
    public void setOrderAddTime(String orderAddTime) {
        this.orderAddTime = orderAddTime;
    }

}