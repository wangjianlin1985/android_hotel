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
	/* �����ֵ��Ϣ���󣬽��г�ֵ��Ϣ�����ҵ�� */
	public String AddMoneyRecord(MoneyRecord moneyRecord) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����³�ֵ��Ϣ */
			String sqlString = "insert into MoneyRecord(userObj,moneyValue,memo,happenTime) values (";
			sqlString += "'" + moneyRecord.getUserObj() + "',";
			sqlString += moneyRecord.getMoneyValue() + ",";
			sqlString += "'" + moneyRecord.getMemo() + "',";
			sqlString += "'" + moneyRecord.getHappenTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��ֵ��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ֵ��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����ֵ��Ϣ */
	public String DeleteMoneyRecord(int recordId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from MoneyRecord where recordId=" + recordId;
			db.executeUpdate(sqlString);
			result = "��ֵ��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ֵ��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ����ֵ��Ϣ */
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
	/* ���³�ֵ��Ϣ */
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
			result = "��ֵ��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ֵ��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
