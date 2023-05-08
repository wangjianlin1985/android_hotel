package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.MoneyRecord;
import com.mobileserver.util.DB;

public class MoneyRecordDAO {

	public List<MoneyRecord> QueryMoneyRecord(String userObj,String happenTime) {
		List<MoneyRecord> moneyRecordList = new ArrayList<MoneyRecord>();
		DB db = new DB();
		String sql = "select * from MoneyRecord where 1=1";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!happenTime.equals(""))
			sql += " and happenTime like '%" + happenTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				MoneyRecord moneyRecord = new MoneyRecord();
				moneyRecord.setRecordId(rs.getInt("recordId"));
				moneyRecord.setUserObj(rs.getString("userObj"));
				moneyRecord.setMoneyValue(rs.getFloat("moneyValue"));
				moneyRecord.setMemo(rs.getString("memo"));
				moneyRecord.setHappenTime(rs.getString("happenTime"));
				moneyRecordList.add(moneyRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return moneyRecordList;
	}
	/* 传入充值信息对象，进行充值信息的添加业务 */
	public String AddMoneyRecord(MoneyRecord moneyRecord) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新充值信息 */
			String sqlString = "insert into MoneyRecord(userObj,moneyValue,memo,happenTime) values (";
			sqlString += "'" + moneyRecord.getUserObj() + "',";
			sqlString += moneyRecord.getMoneyValue() + ",";
			sqlString += "'" + moneyRecord.getMemo() + "',";
			sqlString += "'" + moneyRecord.getHappenTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "充值信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "充值信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除充值信息 */
	public String DeleteMoneyRecord(int recordId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from MoneyRecord where recordId=" + recordId;
			db.executeUpdate(sqlString);
			result = "充值信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "充值信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到充值信息 */
	public MoneyRecord GetMoneyRecord(int recordId) {
		MoneyRecord moneyRecord = null;
		DB db = new DB();
		String sql = "select * from MoneyRecord where recordId=" + recordId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				moneyRecord = new MoneyRecord();
				moneyRecord.setRecordId(rs.getInt("recordId"));
				moneyRecord.setUserObj(rs.getString("userObj"));
				moneyRecord.setMoneyValue(rs.getFloat("moneyValue"));
				moneyRecord.setMemo(rs.getString("memo"));
				moneyRecord.setHappenTime(rs.getString("happenTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return moneyRecord;
	}
	/* 更新充值信息 */
	public String UpdateMoneyRecord(MoneyRecord moneyRecord) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update MoneyRecord set ";
			sql += "userObj='" + moneyRecord.getUserObj() + "',";
			sql += "moneyValue=" + moneyRecord.getMoneyValue() + ",";
			sql += "memo='" + moneyRecord.getMemo() + "',";
			sql += "happenTime='" + moneyRecord.getHappenTime() + "'";
			sql += " where recordId=" + moneyRecord.getRecordId();
			db.executeUpdate(sql);
			result = "充值信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "充值信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
