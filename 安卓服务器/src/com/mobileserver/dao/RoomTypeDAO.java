package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.RoomType;
import com.mobileserver.util.DB;

public class RoomTypeDAO {

	public List<RoomType> QueryRoomType() {
		List<RoomType> roomTypeList = new ArrayList<RoomType>();
		DB db = new DB();
		String sql = "select * from RoomType where 1=1";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				RoomType roomType = new RoomType();
				roomType.setRoomTypeId(rs.getInt("roomTypeId"));
				roomType.setRoomTypeName(rs.getString("roomTypeName"));
				roomTypeList.add(roomType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return roomTypeList;
	}
	/* ���뷿�����Ͷ��󣬽��з������͵����ҵ�� */
	public String AddRoomType(RoomType roomType) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����·������� */
			String sqlString = "insert into RoomType(roomTypeName) values (";
			sqlString += "'" + roomType.getRoomTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "����������ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�����������ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ���������� */
	public String DeleteRoomType(int roomTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from RoomType where roomTypeId=" + roomTypeId;
			db.executeUpdate(sqlString);
			result = "��������ɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��������ɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݼ�¼��Ż�ȡ���������� */
	public RoomType GetRoomType(int roomTypeId) {
		RoomType roomType = null;
		DB db = new DB();
		String sql = "select * from RoomType where roomTypeId=" + roomTypeId;
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				roomType = new RoomType();
				roomType.setRoomTypeId(rs.getInt("roomTypeId"));
				roomType.setRoomTypeName(rs.getString("roomTypeName"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return roomType;
	}
	/* ���·������� */
	public String UpdateRoomType(RoomType roomType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update RoomType set ";
			sql += "roomTypeName='" + roomType.getRoomTypeName() + "'";
			sql += " where roomTypeId=" + roomType.getRoomTypeId();
			db.executeUpdate(sql);
			result = "�������͸��³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "�������͸���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
