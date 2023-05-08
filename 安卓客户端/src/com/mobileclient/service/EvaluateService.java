package com.mobileclient.service;

import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mobileclient.domain.Evaluate;
import com.mobileclient.util.HttpUtil;

/*评价信息管理业务逻辑层*/
public class EvaluateService {
	/* 添加评价信息 */
	public String AddEvaluate(Evaluate evaluate) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("evaluateId", evaluate.getEvaluateId() + "");
		params.put("roomObj", evaluate.getRoomObj());
		params.put("userObj", evaluate.getUserObj());
		params.put("evalueScore", evaluate.getEvalueScore() + "");
		params.put("evaluateContent", evaluate.getEvaluateContent());
		params.put("evaluateTime", evaluate.getEvaluateTime());
		params.put("action", "add");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "EvaluateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 查询评价信息 */
	public List<Evaluate> QueryEvaluate(Evaluate queryConditionEvaluate) throws Exception {
		String urlString = HttpUtil.BASE_URL + "EvaluateServlet?action=query";
		if(queryConditionEvaluate != null) {
			urlString += "&roomObj=" + URLEncoder.encode(queryConditionEvaluate.getRoomObj(), "UTF-8") + "";
			urlString += "&userObj=" + URLEncoder.encode(queryConditionEvaluate.getUserObj(), "UTF-8") + "";
			urlString += "&evaluateTime=" + URLEncoder.encode(queryConditionEvaluate.getEvaluateTime(), "UTF-8") + "";
		}

		/* 2种数据解析方法，第一种是用SAXParser解析xml文件格式
		URL url = new URL(urlString);
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser sp = spf.newSAXParser();
		XMLReader xr = sp.getXMLReader();

		EvaluateListHandler evaluateListHander = new EvaluateListHandler();
		xr.setContentHandler(evaluateListHander);
		InputStreamReader isr = new InputStreamReader(url.openStream(), "UTF-8");
		InputSource is = new InputSource(isr);
		xr.parse(is);
		List<Evaluate> evaluateList = evaluateListHander.getEvaluateList();
		return evaluateList;*/
		//第2种是基于json数据格式解析，我们采用的是第2种
		List<Evaluate> evaluateList = new ArrayList<Evaluate>();
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(urlString, null, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Evaluate evaluate = new Evaluate();
				evaluate.setEvaluateId(object.getInt("evaluateId"));
				evaluate.setRoomObj(object.getString("roomObj"));
				evaluate.setUserObj(object.getString("userObj"));
				evaluate.setEvalueScore(object.getInt("evalueScore"));
				evaluate.setEvaluateContent(object.getString("evaluateContent"));
				evaluate.setEvaluateTime(object.getString("evaluateTime"));
				evaluateList.add(evaluate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return evaluateList;
	}

	/* 更新评价信息 */
	public String UpdateEvaluate(Evaluate evaluate) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("evaluateId", evaluate.getEvaluateId() + "");
		params.put("roomObj", evaluate.getRoomObj());
		params.put("userObj", evaluate.getUserObj());
		params.put("evalueScore", evaluate.getEvalueScore() + "");
		params.put("evaluateContent", evaluate.getEvaluateContent());
		params.put("evaluateTime", evaluate.getEvaluateTime());
		params.put("action", "update");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "EvaluateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/* 删除评价信息 */
	public String DeleteEvaluate(int evaluateId) {
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("evaluateId", evaluateId + "");
		params.put("action", "delete");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "EvaluateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			return result;
		} catch (Exception e) {
			e.printStackTrace();
			return "评价信息信息删除失败!";
		}
	}

	/* 根据记录编号获取评价信息对象 */
	public Evaluate GetEvaluate(int evaluateId)  {
		List<Evaluate> evaluateList = new ArrayList<Evaluate>();
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("evaluateId", evaluateId + "");
		params.put("action", "updateQuery");
		byte[] resultByte;
		try {
			resultByte = HttpUtil.SendPostRequest(HttpUtil.BASE_URL + "EvaluateServlet?", params, "UTF-8");
			String result = new String(resultByte, "UTF-8");
			JSONArray array = new JSONArray(result);
			int length = array.length();
			for (int i = 0; i < length; i++) {
				JSONObject object = array.getJSONObject(i);
				Evaluate evaluate = new Evaluate();
				evaluate.setEvaluateId(object.getInt("evaluateId"));
				evaluate.setRoomObj(object.getString("roomObj"));
				evaluate.setUserObj(object.getString("userObj"));
				evaluate.setEvalueScore(object.getInt("evalueScore"));
				evaluate.setEvaluateContent(object.getString("evaluateContent"));
				evaluate.setEvaluateTime(object.getString("evaluateTime"));
				evaluateList.add(evaluate);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int size = evaluateList.size();
		if(size>0) return evaluateList.get(0); 
		else return null; 
	}
}
