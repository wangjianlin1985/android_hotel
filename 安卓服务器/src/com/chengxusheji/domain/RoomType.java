package com.chengxusheji.domain;

import java.sql.Timestamp;
public class RoomType {
    /*��¼���*/
    private int roomTypeId;
    public int getRoomTypeId() {
        return roomTypeId;
    }
    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    /*��������*/
    private String roomTypeName;
    public String getRoomTypeName() {
        return roomTypeName;
    }
    public void setRoomTypeName(String roomTypeName) {
        this.roomTypeName = roomTypeName;
    }

}