package com.chengxusheji.domain;

import java.sql.Timestamp;
public class MoneyRecord {
    /*��¼���*/
    private int recordId;
    public int getRecordId() {
        return recordId;
    }
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }

    /*��ֵ���û�*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*��ֵ���*/
    private float moneyValue;
    public float getMoneyValue() {
        return moneyValue;
    }
    public void setMoneyValue(float moneyValue) {
        this.moneyValue = moneyValue;
    }

    /*������Ϣ*/
    private String memo;
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }

    /*��ֵʱ��*/
    private String happenTime;
    public String getHappenTime() {
        return happenTime;
    }
    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }

}