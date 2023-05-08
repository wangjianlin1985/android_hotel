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
	/* 传入房间类型对象，进行房间类型的添加业务 */
	public String AddRoomType(RoomType roomType) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新房间类型 */
			String sqlString = "insert into RoomType(roomTypeName) values (";
			sqlString += "'" + roomType.getRoomTypeName() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "房间类型添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间类型添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除房间类型 */
	public String DeleteRoomType(int roomTypeId) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from RoomType where roomTypeId=" + roomTypeId;
			db.executeUpdate(sqlString);
			result = "房间类型删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间类型删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据记录编号获取到房间类型 */
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
	/* 更新房间类型 */
	public String UpdateRoomType(RoomType roomType) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update RoomType set ";
			sql += "roomTypeName='" + roomType.getRoomTypeName() + "'";
			sql += " where roomTypeId=" + roomType.getRoomTypeId();
			db.executeUpdate(sql);
			result = "房间类型更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间类型更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
