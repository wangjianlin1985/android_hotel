package com.mobileserver.domain;

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
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
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