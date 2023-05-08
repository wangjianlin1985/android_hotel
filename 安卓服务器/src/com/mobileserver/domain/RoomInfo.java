package com.mobileserver.domain;

public class RoomInfo {
    /*房间编号*/
    private String roomNumber;
    public String getRoomNumber() {
        return roomNumber;
    }
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    /*房间类型*/
    private int roomTypeObj;
    public int getRoomTypeObj() {
        return roomTypeObj;
    }
    public void setRoomTypeObj(int roomTypeObj) {
        this.roomTypeObj = roomTypeObj;
    }

    /*价格(元/天)*/
    private float roomPrice;
    public float getRoomPrice() {
        return roomPrice;
    }
    public void setRoomPrice(float roomPrice) {
        this.roomPrice = roomPrice;
    }

    /*所处位置*/
    private String position;
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    /*房间介绍*/
    private String introduction;
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    /*房间照片*/
    private String roomPhoto;
    public String getRoomPhoto() {
        return roomPhoto;
    }
    public void setRoomPhoto(String roomPhoto) {
        this.roomPhoto = roomPhoto;
    }

}