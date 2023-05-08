package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.OrderInfo;
import com.mobileserver.util.DB;

public class OrderInfoDAO {

	public List<OrderInfo> QueryOrderInfo(String orderNumber,String roomObj,String userObj,String startTime,String endTime) {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		DB db = new DB();
		String sql = "select * from OrderInfo where 1=1";
		if (!orderNumber.equals(""))
			sql += " and orderNumber like '%" + orderNumber + "%'";
		if (!roomObj.equals(""))
			sql += " and roomObj = '" + roomObj + "'";
		if (!userObj.equals(""))
			sql += " and userObj = '" + userObj + "'";
		if (!startTime.equals(""))
			sql += " and startTime like '%" + startTime + "%'";
		if (!endTime.equals(""))
			sql += " and endTime like '%" + endTime + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderNumber(rs.getString("orderNumber"));
				orderInfo.setRoomObj(rs.getString("roomObj"));
				orderInfo.setUserObj(rs.getString("userObj"));
				orderInfo.setStartTime(rs.getString("startTime"));
				orderInfo.setEndTime(rs.getString("endTime"));
				orderInfo.setOrderMoney(rs.getFloat("orderMoney"));
				orderInfo.setOrderMemo(rs.getString("orderMemo"));
				orderInfo.setOrderAddTime(rs.getString("orderAddTime"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfoList;
	}
	/* 传入房间预订对象，进行房间预订的添加业务 */
	public String AddOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新房间预订 */
			String sqlString = "insert into OrderInfo(orderNumber,roomObj,userObj,startTime,endTime,orderMoney,orderMemo,orderAddTime) values (";
			sqlString += "'" + orderInfo.getOrderNumber() + "',";
			sqlString += "'" + orderInfo.getRoomObj() + "',";
			sqlString += "'" + orderInfo.getUserObj() + "',";
			sqlString += "'" + orderInfo.getStartTime() + "',";
			sqlString += "'" + orderInfo.getEndTime() + "',";
			sqlString += orderInfo.getOrderMoney() + ",";
			sqlString += "'" + orderInfo.getOrderMemo() + "',";
			sqlString += "'" + orderInfo.getOrderAddTime() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "房间预订添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间预订添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除房间预订 */
	public String DeleteOrderInfo(String orderNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from OrderInfo where orderNumber='" + orderNumber + "'";
			db.executeUpdate(sqlString);
			result = "房间预订删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间预订删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据订单编号获取到房间预订 */
	public OrderInfo GetOrderInfo(String orderNumber) {
		OrderInfo orderInfo = null;
		DB db = new DB();
		String sql = "select * from OrderInfo where orderNumber='" + orderNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				orderInfo = new OrderInfo();
				orderInfo.setOrderNumber(rs.getString("orderNumber"));
				orderInfo.setRoomObj(rs.getString("roomObj"));
				orderInfo.setUserObj(rs.getString("userObj"));
				orderInfo.setStartTime(rs.getString("startTime"));
				orderInfo.setEndTime(rs.getString("endTime"));
				orderInfo.setOrderMoney(rs.getFloat("orderMoney"));
				orderInfo.setOrderMemo(rs.getString("orderMemo"));
				orderInfo.setOrderAddTime(rs.getString("orderAddTime"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return orderInfo;
	}
	/* 更新房间预订 */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update OrderInfo set ";
			sql += "roomObj='" + orderInfo.getRoomObj() + "',";
			sql += "userObj='" + orderInfo.getUserObj() + "',";
			sql += "startTime='" + orderInfo.getStartTime() + "',";
			sql += "endTime='" + orderInfo.getEndTime() + "',";
			sql += "orderMoney=" + orderInfo.getOrderMoney() + ",";
			sql += "orderMemo='" + orderInfo.getOrderMemo() + "',";
			sql += "orderAddTime='" + orderInfo.getOrderAddTime() + "'";
			sql += " where orderNumber='" + orderInfo.getOrderNumber() + "'";
			db.executeUpdate(sql);
			result = "房间预订更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "房间预订更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
