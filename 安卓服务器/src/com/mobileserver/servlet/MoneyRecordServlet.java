package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.MoneyRecordDAO;
import com.mobileserver.domain.MoneyRecord;

import org.json.JSONStringer;

public class MoneyRecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*�����ֵ��Ϣҵ������*/
	private MoneyRecordDAO moneyRecordDAO = new MoneyRecordDAO();

	/*Ĭ�Ϲ��캯��*/
	public MoneyRecordServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*��ȡaction����������action��ִֵ�в�ͬ��ҵ����*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*��ȡ��ѯ��ֵ��Ϣ�Ĳ�����Ϣ*/
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String happenTime = request.getParameter("happenTime");
			happenTime = happenTime == null ? "" : new String(request.getParameter(
					"happenTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�г�ֵ��Ϣ��ѯ*/
			List<MoneyRecord> moneyRecordList = moneyRecordDAO.QueryMoneyRecord(userObj,happenTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<MoneyRecords>").append("\r\n");
			for (int i = 0; i < moneyRecordList.size(); i++) {
				sb.append("	<MoneyRecord>").append("\r\n")
				.append("		<recordId>")
				.append(moneyRecordList.get(i).getRecordId())
				.append("</recordId>").append("\r\n")
				.append("		<userObj>")
				.append(moneyRecordList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<moneyValue>")
				.append(moneyRecordList.get(i).getMoneyValue())
				.append("</moneyValue>").append("\r\n")
				.append("		<memo>")
				.append(moneyRecordList.get(i).getMemo())
				.append("</memo>").append("\r\n")
				.append("		<happenTime>")
				.append(moneyRecordList.get(i).getHappenTime())
				.append("</happenTime>").append("\r\n")
				.append("	</MoneyRecord>").append("\r\n");
			}
			sb.append("</MoneyRecords>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(MoneyRecord moneyRecord: moneyRecordList) {
				  stringer.object();
			  stringer.key("recordId").value(moneyRecord.getRecordId());
			  stringer.key("userObj").value(moneyRecord.getUserObj());
			  stringer.key("moneyValue").value(moneyRecord.getMoneyValue());
			  stringer.key("memo").value(moneyRecord.getMemo());
			  stringer.key("happenTime").value(moneyRecord.getHappenTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӳ�ֵ��Ϣ����ȡ��ֵ��Ϣ�������������浽�½��ĳ�ֵ��Ϣ���� */ 
			MoneyRecord moneyRecord = new MoneyRecord();
			int recordId = Integer.parseInt(request.getParameter("recordId"));
			moneyRecord.setRecordId(recordId);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			moneyRecord.setUserObj(userObj);
			float moneyValue = Float.parseFloat(request.getParameter("moneyValue"));
			moneyRecord.setMoneyValue(moneyValue);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			moneyRecord.setMemo(memo);
			String happenTime = new String(request.getParameter("happenTime").getBytes("iso-8859-1"), "UTF-8");
			moneyRecord.setHappenTime(happenTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = moneyRecordDAO.AddMoneyRecord(moneyRecord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ����ֵ��Ϣ����ȡ��ֵ��Ϣ�ļ�¼���*/
			int recordId = Integer.parseInt(request.getParameter("recordId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = moneyRecordDAO.DeleteMoneyRecord(recordId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���³�ֵ��Ϣ֮ǰ�ȸ���recordId��ѯĳ����ֵ��Ϣ*/
			int recordId = Integer.parseInt(request.getParameter("recordId"));
			MoneyRecord moneyRecord = moneyRecordDAO.GetMoneyRecord(recordId);

			// �ͻ��˲�ѯ�ĳ�ֵ��Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("recordId").value(moneyRecord.getRecordId());
			  stringer.key("userObj").value(moneyRecord.getUserObj());
			  stringer.key("moneyValue").value(moneyRecord.getMoneyValue());
			  stringer.key("memo").value(moneyRecord.getMemo());
			  stringer.key("happenTime").value(moneyRecord.getHappenTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���³�ֵ��Ϣ����ȡ��ֵ��Ϣ�������������浽�½��ĳ�ֵ��Ϣ���� */ 
			MoneyRecord moneyRecord = new MoneyRecord();
			int recordId = Integer.parseInt(request.getParameter("recordId"));
			moneyRecord.setRecordId(recordId);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			moneyRecord.setUserObj(userObj);
			float moneyValue = Float.parseFloat(request.getParameter("moneyValue"));
			moneyRecord.setMoneyValue(moneyValue);
			String memo = new String(request.getParameter("memo").getBytes("iso-8859-1"), "UTF-8");
			moneyRecord.setMemo(memo);
			String happenTime = new String(request.getParameter("happenTime").getBytes("iso-8859-1"), "UTF-8");
			moneyRecord.setHappenTime(happenTime);

			/* ����ҵ���ִ�и��²��� */
			String result = moneyRecordDAO.UpdateMoneyRecord(moneyRecord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
