package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.OrderInfo;
import com.mobileclient.util.HttpUtil;

/*房间预订管理业务逻辑层*/
public class OrderInfoService {
	/* 添加房间预订 */
	public String AddOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNumber", orderInfo.getOrderNumber());
		params.put("roomObj", orderInfo.getRoomObj());
		params.put("userObj", orderInfo.getUserObj());
		params.put("startTime", orderInfo.getStartTime());
		params.put("endTime", orderInfo.getEndTime());
		params.put("orderMoney", orderInfo.getOrderMoney() + "");
		params.put("orderMemo", orderInfo.getOrderMemo());
		params.put("orderAddTime", orderInfo.getOrderAddTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询房间预订 */
	public List<OrderInfo> QueryOrderInfo(OrderInfo queryConditionOrderInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "OrderInfoServlet?action=query";
		if(queryConditionOrderInfo != null) {
			urlString += "&orderNumber=" + URLEncoder.encode(queryConditionOrderInfo.getOrderNumber(), "UTF-8") + "";
			urlString += "&roomObj=" + URLEncoder.encode(queryConditionOrderInfo.getRoomObj(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionOrderInfo.getUserObj(), "UTF-8") + "";
			urlString += "&startTime=" + URLEncoder.encode(queryConditionOrderInfo.getStartTime(), "UTF-8") + "";
			urlString += "&endTime=" + URLEncoder.encode(queryConditionOrderInfo.getEndTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		OrderInfoListHandler orderInfoListHander = new OrderInfoListHandler();
		xr.setContentHandler(orderInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<OrderInfo> orderInfoList = orderInfoListHander.getOrderInfoList();
		return orderInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderNumber(object.getString("orderNumber"));
				orderInfo.setRoomObj(object.getString("roomObj"));
				orderInfo.setUserObj(object.getString("userObj"));
				orderInfo.setStartTime(object.getString("startTime"));
				orderInfo.setEndTime(object.getString("endTime"));
				orderInfo.setOrderMoney((float) object.getDouble("orderMoney"));
				orderInfo.setOrderMemo(object.getString("orderMemo"));
				orderInfo.setOrderAddTime(object.getString("orderAddTime"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return orderInfoList;
	}

	/* 更新房间预订 */
	public String UpdateOrderInfo(OrderInfo orderInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNumber", orderInfo.getOrderNumber());
		params.put("roomObj", orderInfo.getRoomObj());
		params.put("userObj", orderInfo.getUserObj());
		params.put("startTime", orderInfo.getStartTime());
		params.put("endTime", orderInfo.getEndTime());
		params.put("orderMoney", orderInfo.getOrderMoney() + "");
		params.put("orderMemo", orderInfo.getOrderMemo());
		params.put("orderAddTime", orderInfo.getOrderAddTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除房间预订 */
	public String DeleteOrderInfo(String orderNumber) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNumber", orderNumber);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "房间预订信息删除失败!";
		}
	}

	/* 根据订单编号获取房间预订对象 */
	public OrderInfo GetOrderInfo(String orderNumber)  {
		List<OrderInfo> orderInfoList = new ArrayList<OrderInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("orderNumber", orderNumber);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "OrderInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				OrderInfo orderInfo = new OrderInfo();
				orderInfo.setOrderNumber(object.getString("orderNumber"));
				orderInfo.setRoomObj(object.getString("roomObj"));
				orderInfo.setUserObj(object.getString("userObj"));
				orderInfo.setStartTime(object.getString("startTime"));
				orderInfo.setEndTime(object.getString("endTime"));
				orderInfo.setOrderMoney((float) object.getDouble("orderMoney"));
				orderInfo.setOrderMemo(object.getString("orderMemo"));
				orderInfo.setOrderAddTime(object.getString("orderAddTime"));
				orderInfoList.add(orderInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = orderInfoList.size();
		if(size>0) return orderInfoList.get(0); 
		else return null; 
	}
}
