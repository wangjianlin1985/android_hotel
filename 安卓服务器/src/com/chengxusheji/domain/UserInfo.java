package com.chengxusheji.domain;

import java.sql.Timestamp;
public class UserInfo {
    /*�û���*/
    private String user_name;
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    /*��½����*/
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    /*����*/
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    /*�Ա�*/
    private String sex;
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }

    /*����*/
    private Timestamp birthday;
    public Timestamp getBirthday() {
        return birthday;
    }
    public void setBirthday(Timestamp birthday) {
        this.birthday = birthday;
    }

    /*���֤��*/
    private String cardNumber;
    public String getCardNumber() {
        return cardNumber;
    }
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    /*����*/
    private String city;
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }

    /*��Ƭ*/
    private String photo;
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /*�˻����*/
    private float moneyValue;
    public float getMoneyValue() {
        return moneyValue;
    }
    public void setMoneyValue(float moneyValue) {
        this.moneyValue = moneyValue;
    }

    /*��Ա����*/
    private int userScore;
    public int getUserScore() {
        return userScore;
    }
    public void setUserScore(int userScore) {
        this.userScore = userScore;
    }

}