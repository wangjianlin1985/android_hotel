package com.chengxusheji.domain;

import java.sql.Timestamp;
public class RoomInfo {
    /*������*/
    private String roomNumber;
    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /*��������*/
    private RoomType roomTypeObj;
    public RoomType getRoomTypeObj() {
        return roomTypeObj;
    }
    public void setRoomTypeObj(RoomType roomTypeObj) {
        this.roomTypeObj = roomTypeObj;
    }

    /*�۸�(Ԫ/��)*/
    private float roomPrice;
    public float getRoomPrice() {
        return roomPrice;
    }
    public void setRoomPrice(float roomPrice) {
        this.roomPrice = roomPrice;
    }

    /*����λ��*/
    private String position;
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    /*�������*/
    private String introduction;
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /*������Ƭ*/
    private String roomPhoto;
    public String getRoomPhoto() {
        return roomPhoto;
    }
    public void setRoomPhoto(String roomPhoto) {
        this.roomPhoto = roomPhoto;
    }

}