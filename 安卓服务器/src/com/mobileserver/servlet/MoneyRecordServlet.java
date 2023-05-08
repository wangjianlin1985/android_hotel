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

	/*构造充值信息业务层对象*/
	private MoneyRecordDAO moneyRecordDAO = new MoneyRecordDAO();

	/*默认构造函数*/
	public MoneyRecordServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/*获取action参数，根据action的值执行不同的业务处理*/
		String action = request.getParameter("action");
		if (action.equals("query")) {
			/*获取查询充值信息的参数信息*/
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String happenTime = request.getParameter("happenTime");
			happenTime = happenTime == null ? "" : new String(request.getParameter(
					"happenTime").getBytes("iso-8859-1"), "UTF-8");

			/*调用业务逻辑层执行充值信息查询*/
			List<MoneyRecord> moneyRecordList = moneyRecordDAO.QueryMoneyRecord(userObj,happenTime);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加充值信息：获取充值信息参数，参数保存到新建的充值信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = moneyRecordDAO.AddMoneyRecord(moneyRecord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除充值信息：获取充值信息的记录编号*/
			int recordId = Integer.parseInt(request.getParameter("recordId"));
			/*调用业务逻辑层执行删除操作*/
			String result = moneyRecordDAO.DeleteMoneyRecord(recordId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新充值信息之前先根据recordId查询某个充值信息*/
			int recordId = Integer.parseInt(request.getParameter("recordId"));
			MoneyRecord moneyRecord = moneyRecordDAO.GetMoneyRecord(recordId);

			// 客户端查询的充值信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新充值信息：获取充值信息参数，参数保存到新建的充值信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = moneyRecordDAO.UpdateMoneyRecord(moneyRecord);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
