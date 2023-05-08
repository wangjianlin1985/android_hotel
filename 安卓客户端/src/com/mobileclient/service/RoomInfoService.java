package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.RoomInfo;
import com.mobileclient.util.HttpUtil;

/*房间信息管理业务逻辑层*/
public class RoomInfoService {
	/* 添加房间信息 */
	public String AddRoomInfo(RoomInfo roomInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomNumber", roomInfo.getRoomNumber());
		params.put("roomTypeObj", roomInfo.getRoomTypeObj() + "");
		params.put("roomPrice", roomInfo.getRoomPrice() + "");
		params.put("position", roomInfo.getPosition());
		params.put("introduction", roomInfo.getIntroduction());
		params.put("roomPhoto", roomInfo.getRoomPhoto());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询房间信息 */
	public List<RoomInfo> QueryRoomInfo(RoomInfo queryConditionRoomInfo) throws Exception {
		String urlString = HttpUtil.BASE_URL + "RoomInfoServlet?action=query";
		if(queryConditionRoomInfo != null) {
			urlString += "&roomNumber=" + URLEncoder.encode(queryConditionRoomInfo.getRoomNumber(), "UTF-8") + "";
			urlString += "&roomTypeObj=" + queryConditionRoomInfo.getRoomTypeObj();
			urlString += "&position=" + URLEncoder.encode(queryConditionRoomInfo.getPosition(), "UTF-8") + "";
			urlString += "&introduction=" + URLEncoder.encode(queryConditionRoomInfo.getIntroduction(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		RoomInfoListHandler roomInfoListHander = new RoomInfoListHandler();
		xr.setContentHandler(roomInfoListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<RoomInfo> roomInfoList = roomInfoListHander.getRoomInfoList();
		return roomInfoList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<RoomInfo> roomInfoList = new ArrayList<RoomInfo>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				RoomInfo roomInfo = new RoomInfo();
				roomInfo.setRoomNumber(object.getString("roomNumber"));
				roomInfo.setRoomTypeObj(object.getInt("roomTypeObj"));
				roomInfo.setRoomPrice((float) object.getDouble("roomPrice"));
				roomInfo.setPosition(object.getString("position"));
				roomInfo.setIntroduction(object.getString("introduction"));
				roomInfo.setRoomPhoto(object.getString("roomPhoto"));
				roomInfoList.add(roomInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return roomInfoList;
	}

	/* 更新房间信息 */
	public String UpdateRoomInfo(RoomInfo roomInfo) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomNumber", roomInfo.getRoomNumber());
		params.put("roomTypeObj", roomInfo.getRoomTypeObj() + "");
		params.put("roomPrice", roomInfo.getRoomPrice() + "");
		params.put("position", roomInfo.getPosition());
		params.put("introduction", roomInfo.getIntroduction());
		params.put("roomPhoto", roomInfo.getRoomPhoto());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除房间信息 */
	public String DeleteRoomInfo(String roomNumber) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomNumber", roomNumber);
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "房间信息信息删除失败!";
		}
	}

	/* 根据房间编号获取房间信息对象 */
	public RoomInfo GetRoomInfo(String roomNumber)  {
		List<RoomInfo> roomInfoList = new ArrayList<RoomInfo>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("roomNumber", roomNumber);
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "RoomInfoServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				RoomInfo roomInfo = new RoomInfo();
				roomInfo.setRoomNumber(object.getString("roomNumber"));
				roomInfo.setRoomTypeObj(object.getInt("roomTypeObj"));
				roomInfo.setRoomPrice((float) object.getDouble("roomPrice"));
				roomInfo.setPosition(object.getString("position"));
				roomInfo.setIntroduction(object.getString("introduction"));
				roomInfo.setRoomPhoto(object.getString("roomPhoto"));
				roomInfoList.add(roomInfo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = roomInfoList.size();
		if(size>0) return roomInfoList.get(0); 
		else return null; 
	}
}
