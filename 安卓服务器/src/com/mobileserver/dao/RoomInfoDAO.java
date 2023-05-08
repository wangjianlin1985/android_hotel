package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.RoomInfo;
import com.mobileserver.util.DB;

public class RoomInfoDAO {

	public List<RoomInfo> QueryRoomInfo(String roomNumber,int roomTypeObj,String position,String introduction) {
		List<RoomInfo> roomInfoList = new ArrayList<RoomInfo>();
		DB db = new DB();
		String sql = "select * from RoomInfo where 1=1";
		if (!roomNumber.equals(""))
			sql += " and roomNumber like '%" + roomNumber + "%'";
		if (roomTypeObj != 0)
			sql += " and roomTypeObj=" + roomTypeObj;
		if (!position.equals(""))
			sql += " and position like '%" + position + "%'";
		if (!introduction.equals(""))
			sql += " and introduction like '%" + introduction + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				RoomInfo roomInfo = new RoomInfo();
				roomInfo.setRoomNumber(rs.getString("roomNumber"));
				roomInfo.setRoomTypeObj(rs.getInt("roomTypeObj"));
				roomInfo.setRoomPrice(rs.getFloat("roomPrice"));
				roomInfo.setPosition(rs.getString("position"));
				roomInfo.setIntroduction(rs.getString("introduction"));
				roomInfo.setRoomPhoto(rs.getString("roomPhoto"));
				roomInfoList.add(roomInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return roomInfoList;
	}
	/* ���뷿����Ϣ���󣬽��з�����Ϣ�����ҵ�� */
	public String AddRoomInfo(RoomInfo roomInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����·�����Ϣ */
			String sqlString = "insert into RoomInfo(roomNumber,roomTypeObj,roomPrice,position,introduction,roomPhoto) values (";
			sqlString += "'" + roomInfo.getRoomNumber() + "',";
			sqlString += roomInfo.getRoomTypeObj() + ",";
			sqlString += roomInfo.getRoomPrice() + ",";
			sqlString += "'" + roomInfo.getPosition() + "',";
			sqlString += "'" + roomInfo.getIntroduction() + "',";
			sqlString += "'" + roomInfo.getRoomPhoto() + "'";
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
	public String DeleteRoomInfo(String roomNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from RoomInfo where roomNumber='" + roomNumber + "'";
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

	/* ���ݷ����Ż�ȡ��������Ϣ */
	public RoomInfo GetRoomInfo(String roomNumber) {
		RoomInfo roomInfo = null;
		DB db = new DB();
		String sql = "select * from RoomInfo where roomNumber='" + roomNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				roomInfo = new RoomInfo();
				roomInfo.setRoomNumber(rs.getString("roomNumber"));
				roomInfo.setRoomTypeObj(rs.getInt("roomTypeObj"));
				roomInfo.setRoomPrice(rs.getFloat("roomPrice"));
				roomInfo.setPosition(rs.getString("position"));
				roomInfo.setIntroduction(rs.getString("introduction"));
				roomInfo.setRoomPhoto(rs.getString("roomPhoto"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return roomInfo;
	}
	/* ���·�����Ϣ */
	public String UpdateRoomInfo(RoomInfo roomInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update RoomInfo set ";
			sql += "roomTypeObj=" + roomInfo.getRoomTypeObj() + ",";
			sql += "roomPrice=" + roomInfo.getRoomPrice() + ",";
			sql += "position='" + roomInfo.getPosition() + "',";
			sql += "introduction='" + roomInfo.getIntroduction() + "',";
			sql += "roomPhoto='" + roomInfo.getRoomPhoto() + "'";
			sql += " where roomNumber='" + roomInfo.getRoomNumber() + "'";
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
