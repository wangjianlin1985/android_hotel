package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.NewsInfo;
import com.mobileserver.util.DB;

public class NewsInfoDAO {

	public List<NewsInfo> QueryNewsInfo(String newsTitle,String newsDate) {
		List<NewsInfo> newsInfoList = new ArrayList<NewsInfo>();
		DB db = new DB();
		String sql = "select * from NewsInfo where 1=1";
		if (!newsTitle.equals(""))
			sql += " and newsTitle like '%" + newsTitle + "%'";
		if (!newsDate.equals(""))
			sql += " and newsDate like '%" + newsDate + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				NewsInfo newsInfo = new NewsInfo();
				newsInfo.setNewsId(rs.getInt("newsId"));
				newsInfo.setNewsTitle(rs.getString("newsTitle"));
				newsInfo.setNewsContent(rs.getString("newsContent"));
				newsInfo.setNewsDate(rs.getString("newsDate"));
				newsInfoList.add(newsInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsInfoList;
	}
	/* 传入优惠信息对象，进行优惠信息的添加业务 */
	public String AddNewsInfo(NewsInfo newsInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新优惠信息 */
			String sqlString = "insert into NewsInfo(newsTitle,newsContent,newsDate) values (";
			sqlString += "'" + newsInfo.getNewsTitle() + "',";
			sqlString += "'" + newsInfo.getNewsContent() + "',";
			sqlString += "'" + newsInfo.getNewsDate() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "优惠信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "优惠信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除优惠信息 */
	public String DeleteNewsInfo(int newsId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from NewsInfo where newsId=" + newsId;
			db.executeUpdate(sqlString);
			result = "优惠信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "优惠信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到优惠信息 */
	public NewsInfo GetNewsInfo(int newsId) {
		NewsInfo newsInfo = null;
		DB db = new DB();
		String sql = "select * from NewsInfo where newsId=" + newsId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				newsInfo = new NewsInfo();
				newsInfo.setNewsId(rs.getInt("newsId"));
				newsInfo.setNewsTitle(rs.getString("newsTitle"));
				newsInfo.setNewsContent(rs.getString("newsContent"));
				newsInfo.setNewsDate(rs.getString("newsDate"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return newsInfo;
	}
	/* 更新优惠信息 */
	public String UpdateNewsInfo(NewsInfo newsInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update NewsInfo set ";
			sql += "newsTitle='" + newsInfo.getNewsTitle() + "',";
			sql += "newsContent='" + newsInfo.getNewsContent() + "',";
			sql += "newsDate='" + newsInfo.getNewsDate() + "'";
			sql += " where newsId=" + newsInfo.getNewsId();
			db.executeUpdate(sql);
			result = "优惠信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "优惠信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
