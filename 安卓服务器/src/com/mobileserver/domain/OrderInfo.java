package com.mobileserver.domain;

public class OrderInfo {
    /*�������*/
    private String orderNumber;
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /*Ԥ���ķ���*/
    private String roomObj;
    public String getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(String roomObj) {
        this.roomObj = roomObj;
    }

    /*Ԥ�����û�*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*Ԥ����ʼʱ��*/
    private String startTime;
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /*�뿪ʱ��*/
    private String endTime;
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /*�������*/
    private float orderMoney;
    public float getOrderMoney() {
        return orderMoney;
    }
    public void setOrderMoney(float orderMoney) {
        this.orderMoney = orderMoney;
    }

    /*������Ϣ*/
    private String orderMemo;
    public String getOrderMemo() {
        return orderMemo;
    }
    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    /*�µ�ʱ��*/
    private String orderAddTime;
    public String getOrderAddTime() {
        return orderAddTime;
    }
    public void setOrderAddTime(String orderAddTime) {
        this.orderAddTime = orderAddTime;
    }

}