package com.chengxusheji.domain;

import java.sql.Timestamp;
public class NewsInfo {
    /*��¼���*/
    private int newsId;
    public int getNewsId() {
        return newsId;
    }
    public void setNewsId(int newsId) {
        this.newsId = newsId;
    }

    /*����*/
    private String newsTitle;
    public String getNewsTitle() {
        return newsTitle;
    }
    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    /*�Ż�����*/
    private String newsContent;
    public String getNewsContent() {
        return newsContent;
    }
    public void setNewsContent(String newsContent) {
        this.newsContent = newsContent;
    }

    /*��������*/
    private String newsDate;
    public String getNewsDate() {
        return newsDate;
    }
    public void setNewsDate(String newsDate) {
        this.newsDate = newsDate;
    }

}