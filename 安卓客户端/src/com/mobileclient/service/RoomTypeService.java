package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.RoomType;
import com.mobileclient.util.HttpUtil;

/*房间类型管理业务逻辑层*/
public class RoomTypeService {
	/* 添加房间类型 */
	public String AddRoomType(RoomType roomType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomTypeId", roomType.getRoomTypeId() + "");
		params.put("roomTypeName", roomType.getRoomTypeName());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询房间类型 */
	public List<RoomType> QueryRoomType(RoomType queryConditionRoomType) throws Exception {
		String urlString = HttpUtil.BASE_URL + "RoomTypeServlet?action=query";
		if(queryConditionRoomType != null) {
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		RoomTypeListHandler roomTypeListHander = new RoomTypeListHandler();
		xr.setContentHandler(roomTypeListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<RoomType> roomTypeList = roomTypeListHander.getRoomTypeList();
		return roomTypeList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<RoomType> roomTypeList = new ArrayList<RoomType>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				RoomType roomType = new RoomType();
				roomType.setRoomTypeId(object.getInt("roomTypeId"));
				roomType.setRoomTypeName(object.getString("roomTypeName"));
				roomTypeList.add(roomType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomTypeList;
	}

	/* 更新房间类型 */
	public String UpdateRoomType(RoomType roomType) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomTypeId", roomType.getRoomTypeId() + "");
		params.put("roomTypeName", roomType.getRoomTypeName());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除房间类型 */
	public String DeleteRoomType(int roomTypeId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomTypeId", roomTypeId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "房间类型信息删除失败!";
		}
	}

	/* 根据记录编号获取房间类型对象 */
	public RoomType GetRoomType(int roomTypeId)  {
		List<RoomType> roomTypeList = new ArrayList<RoomType>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomTypeId", roomTypeId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomTypeServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				RoomType roomType = new RoomType();
				roomType.setRoomTypeId(object.getInt("roomTypeId"));
				roomType.setRoomTypeName(object.getString("roomTypeName"));
				roomTypeList.add(roomType);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = roomTypeList.size();
		if(size>0) return roomTypeList.get(0); 
		else return null; 
	}
}
