package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.MoneyRecord;
import com.mobileclient.util.HttpUtil;

/*充值信息管理业务逻辑层*/
public class MoneyRecordService {
	/* 添加充值信息 */
	public String AddMoneyRecord(MoneyRecord moneyRecord) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("recordId", moneyRecord.getRecordId() + "");
		params.put("userObj", moneyRecord.getUserObj());
		params.put("moneyValue", moneyRecord.getMoneyValue() + "");
		params.put("memo", moneyRecord.getMemo());
		params.put("happenTime", moneyRecord.getHappenTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MoneyRecordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询充值信息 */
	public List<MoneyRecord> QueryMoneyRecord(MoneyRecord queryConditionMoneyRecord) throws Exception {
		String urlString = HttpUtil.BASE_URL + "MoneyRecordServlet?action=query";
		if(queryConditionMoneyRecord != null) {
			urlString += "&userObj=" + URLEncoder.encode(queryConditionMoneyRecord.getUserObj(), "UTF-8") + "";
			urlString += "&happenTime=" + URLEncoder.encode(queryConditionMoneyRecord.getHappenTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		MoneyRecordListHandler moneyRecordListHander = new MoneyRecordListHandler();
		xr.setContentHandler(moneyRecordListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<MoneyRecord> moneyRecordList = moneyRecordListHander.getMoneyRecordList();
		return moneyRecordList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<MoneyRecord> moneyRecordList = new ArrayList<MoneyRecord>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				MoneyRecord moneyRecord = new MoneyRecord();
				moneyRecord.setRecordId(object.getInt("recordId"));
				moneyRecord.setUserObj(object.getString("userObj"));
				moneyRecord.setMoneyValue((float) object.getDouble("moneyValue"));
				moneyRecord.setMemo(object.getString("memo"));
				moneyRecord.setHappenTime(object.getString("happenTime"));
				moneyRecordList.add(moneyRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return moneyRecordList;
	}

	/* 更新充值信息 */
	public String UpdateMoneyRecord(MoneyRecord moneyRecord) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("recordId", moneyRecord.getRecordId() + "");
		params.put("userObj", moneyRecord.getUserObj());
		params.put("moneyValue", moneyRecord.getMoneyValue() + "");
		params.put("memo", moneyRecord.getMemo());
		params.put("happenTime", moneyRecord.getHappenTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MoneyRecordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除充值信息 */
	public String DeleteMoneyRecord(int recordId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("recordId", recordId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MoneyRecordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "充值信息信息删除失败!";
		}
	}

	/* 根据记录编号获取充值信息对象 */
	public MoneyRecord GetMoneyRecord(int recordId)  {
		List<MoneyRecord> moneyRecordList = new ArrayList<MoneyRecord>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("recordId", recordId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "MoneyRecordServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				MoneyRecord moneyRecord = new MoneyRecord();
				moneyRecord.setRecordId(object.getInt("recordId"));
				moneyRecord.setUserObj(object.getString("userObj"));
				moneyRecord.setMoneyValue((float) object.getDouble("moneyValue"));
				moneyRecord.setMemo(object.getString("memo"));
				moneyRecord.setHappenTime(object.getString("happenTime"));
				moneyRecordList.add(moneyRecord);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = moneyRecordList.size();
		if(size>0) return moneyRecordList.get(0); 
		else return null; 
	}
}
