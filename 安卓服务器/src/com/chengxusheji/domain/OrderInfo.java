package com.chengxusheji.domain;

import java.sql.Timestamp;
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
    private RoomInfo roomObj;
    public RoomInfo getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(RoomInfo roomObj) {
        this.roomObj = roomObj;
    }

    /*Ԥ�����û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
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