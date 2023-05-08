package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Evaluate;
import com.mobileserver.util.DB;

public class EvaluateDAO {

	public List<Evaluate> QueryEvaluate(String roomObj,String userObj,String evaluateTime) {
		List<Evaluate> evaluateList = new ArrayList<Evaluate>();
		DB db = new DB();
		String sql = "select * from Evaluate where 1=1";
		if (!roomObj.equals(""))
			sql += " and roomObj = '" + roomObj + "'";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!evaluateTime.equals(""))
			sql += " and evaluateTime like '%" + evaluateTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Evaluate evaluate = new Evaluate();
				evaluate.setEvaluateId(rs.getInt("evaluateId"));
				evaluate.setRoomObj(rs.getString("roomObj"));
				evaluate.setUserObj(rs.getString("userObj"));
				evaluate.setEvalueScore(rs.getInt("evalueScore"));
				evaluate.setEvaluateContent(rs.getString("evaluateContent"));
				evaluate.setEvaluateTime(rs.getString("evaluateTime"));
				evaluateList.add(evaluate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return evaluateList;
	}
	/* ����������Ϣ���󣬽���������Ϣ�����ҵ�� */
	public String AddEvaluate(Evaluate evaluate) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����������Ϣ */
			String sqlString = "insert into Evaluate(roomObj,userObj,evalueScore,evaluateContent,evaluateTime) values (";
			sqlString += "'" + evaluate.getRoomObj() + "',";
			sqlString += "'" + evaluate.getUserObj() + "',";
			sqlString += evaluate.getEvalueScore() + ",";
			sqlString += "'" + evaluate.getEvaluateContent() + "',";
			sqlString += "'" + evaluate.getEvaluateTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "������Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��������Ϣ */
	public String DeleteEvaluate(int evaluateId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Evaluate where evaluateId=" + evaluateId;
			db.executeUpdate(sqlString);
			result = "������Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ��������Ϣ */
	public Evaluate GetEvaluate(int evaluateId) {
		Evaluate evaluate = null;
		DB db = new DB();
		String sql = "select * from Evaluate where evaluateId=" + evaluateId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				evaluate = new Evaluate();
				evaluate.setEvaluateId(rs.getInt("evaluateId"));
				evaluate.setRoomObj(rs.getString("roomObj"));
				evaluate.setUserObj(rs.getString("userObj"));
				evaluate.setEvalueScore(rs.getInt("evalueScore"));
				evaluate.setEvaluateContent(rs.getString("evaluateContent"));
				evaluate.setEvaluateTime(rs.getString("evaluateTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return evaluate;
	}
	/* ����������Ϣ */
	public String UpdateEvaluate(Evaluate evaluate) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Evaluate set ";
			sql += "roomObj='" + evaluate.getRoomObj() + "',";
			sql += "userObj='" + evaluate.getUserObj() + "',";
			sql += "evalueScore=" + evaluate.getEvalueScore() + ",";
			sql += "evaluateContent='" + evaluate.getEvaluateContent() + "',";
			sql += "evaluateTime='" + evaluate.getEvaluateTime() + "'";
			sql += " where evaluateId=" + evaluate.getEvaluateId();
			db.executeUpdate(sql);
			result = "������Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "������Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
