package com.mobileserver.domain;

public class Evaluate {
    /*��¼���*/
    private int evaluateId;
    public int getEvaluateId() {
        return evaluateId;
    }
    public void setEvaluateId(int evaluateId) {
        this.evaluateId = evaluateId;
    }

    /*���۵ķ���*/
    private String roomObj;
    public String getRoomObj() {
        return roomObj;
    }
    public void setRoomObj(String roomObj) {
        this.roomObj = roomObj;
    }

    /*���۵��û�*/
    private String userObj;
    public String getUserObj() {
        return userObj;
    }
    public void setUserObj(String userObj) {
        this.userObj = userObj;
    }

    /*����(5����)*/
    private int evalueScore;
    public int getEvalueScore() {
        return evalueScore;
    }
    public void setEvalueScore(int evalueScore) {
        this.evalueScore = evalueScore;
    }

    /*��������*/
    private String evaluateContent;
    public String getEvaluateContent() {
        return evaluateContent;
    }
    public void setEvaluateContent(String evaluateContent) {
        this.evaluateContent = evaluateContent;
    }

    /*����ʱ��*/
    private String evaluateTime;
    public String getEvaluateTime() {
        return evaluateTime;
    }
    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }

}