package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.OrderInfoDAO;
import com.mobileserver.domain.OrderInfo;

import org.json.JSONStringer;

public class OrderInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���췿��Ԥ��ҵ������*/
	private OrderInfoDAO orderInfoDAO = new OrderInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public OrderInfoServlet() {
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
			/*��ȡ��ѯ����Ԥ���Ĳ�����Ϣ*/
			String orderNumber = request.getParameter("orderNumber");
			orderNumber = orderNumber == null ? "" : new String(request.getParameter(
					"orderNumber").getBytes("iso-8859-1"), "UTF-8");
			String roomObj = "";
			if (request.getParameter("roomObj") != null)
				roomObj = request.getParameter("roomObj");
			String userObj = "";
			if (request.getParameter("userObj") != null)
				userObj = request.getParameter("userObj");
			String startTime = request.getParameter("startTime");
			startTime = startTime == null ? "" : new String(request.getParameter(
					"startTime").getBytes("iso-8859-1"), "UTF-8");
			String endTime = request.getParameter("endTime");
			endTime = endTime == null ? "" : new String(request.getParameter(
					"endTime").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�з���Ԥ����ѯ*/
			List<OrderInfo> orderInfoList = orderInfoDAO.QueryOrderInfo(orderNumber,roomObj,userObj,startTime,endTime);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<OrderInfos>").append("\r\n");
			for (int i = 0; i < orderInfoList.size(); i++) {
				sb.append("	<OrderInfo>").append("\r\n")
				.append("		<orderNumber>")
				.append(orderInfoList.get(i).getOrderNumber())
				.append("</orderNumber>").append("\r\n")
				.append("		<roomObj>")
				.append(orderInfoList.get(i).getRoomObj())
				.append("</roomObj>").append("\r\n")
				.append("		<userObj>")
				.append(orderInfoList.get(i).getUserObj())
				.append("</userObj>").append("\r\n")
				.append("		<startTime>")
				.append(orderInfoList.get(i).getStartTime())
				.append("</startTime>").append("\r\n")
				.append("		<endTime>")
				.append(orderInfoList.get(i).getEndTime())
				.append("</endTime>").append("\r\n")
				.append("		<orderMoney>")
				.append(orderInfoList.get(i).getOrderMoney())
				.append("</orderMoney>").append("\r\n")
				.append("		<orderMemo>")
				.append(orderInfoList.get(i).getOrderMemo())
				.append("</orderMemo>").append("\r\n")
				.append("		<orderAddTime>")
				.append(orderInfoList.get(i).getOrderAddTime())
				.append("</orderAddTime>").append("\r\n")
				.append("	</OrderInfo>").append("\r\n");
			}
			sb.append("</OrderInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(OrderInfo orderInfo: orderInfoList) {
				  stringer.object();
			  stringer.key("orderNumber").value(orderInfo.getOrderNumber());
			  stringer.key("roomObj").value(orderInfo.getRoomObj());
			  stringer.key("userObj").value(orderInfo.getUserObj());
			  stringer.key("startTime").value(orderInfo.getStartTime());
			  stringer.key("endTime").value(orderInfo.getEndTime());
			  stringer.key("orderMoney").value(orderInfo.getOrderMoney());
			  stringer.key("orderMemo").value(orderInfo.getOrderMemo());
			  stringer.key("orderAddTime").value(orderInfo.getOrderAddTime());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӷ���Ԥ������ȡ����Ԥ���������������浽�½��ķ���Ԥ������ */ 
			OrderInfo orderInfo = new OrderInfo();
			String orderNumber = new String(request.getParameter("orderNumber").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderNumber(orderNumber);
			String roomObj = new String(request.getParameter("roomObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setRoomObj(roomObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setUserObj(userObj);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setEndTime(endTime);
			float orderMoney = Float.parseFloat(request.getParameter("orderMoney"));
			orderInfo.setOrderMoney(orderMoney);
			String orderMemo = new String(request.getParameter("orderMemo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderMemo(orderMemo);
			String orderAddTime = new String(request.getParameter("orderAddTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderAddTime(orderAddTime);

			/* ����ҵ���ִ����Ӳ��� */
			String result = orderInfoDAO.AddOrderInfo(orderInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ������Ԥ������ȡ����Ԥ���Ķ������*/
			String orderNumber = new String(request.getParameter("orderNumber").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = orderInfoDAO.DeleteOrderInfo(orderNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���·���Ԥ��֮ǰ�ȸ���orderNumber��ѯĳ������Ԥ��*/
			String orderNumber = new String(request.getParameter("orderNumber").getBytes("iso-8859-1"), "UTF-8");
			OrderInfo orderInfo = orderInfoDAO.GetOrderInfo(orderNumber);

			// �ͻ��˲�ѯ�ķ���Ԥ�����󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("orderNumber").value(orderInfo.getOrderNumber());
			  stringer.key("roomObj").value(orderInfo.getRoomObj());
			  stringer.key("userObj").value(orderInfo.getUserObj());
			  stringer.key("startTime").value(orderInfo.getStartTime());
			  stringer.key("endTime").value(orderInfo.getEndTime());
			  stringer.key("orderMoney").value(orderInfo.getOrderMoney());
			  stringer.key("orderMemo").value(orderInfo.getOrderMemo());
			  stringer.key("orderAddTime").value(orderInfo.getOrderAddTime());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���·���Ԥ������ȡ����Ԥ���������������浽�½��ķ���Ԥ������ */ 
			OrderInfo orderInfo = new OrderInfo();
			String orderNumber = new String(request.getParameter("orderNumber").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderNumber(orderNumber);
			String roomObj = new String(request.getParameter("roomObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setRoomObj(roomObj);
			String userObj = new String(request.getParameter("userObj").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setUserObj(userObj);
			String startTime = new String(request.getParameter("startTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setStartTime(startTime);
			String endTime = new String(request.getParameter("endTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setEndTime(endTime);
			float orderMoney = Float.parseFloat(request.getParameter("orderMoney"));
			orderInfo.setOrderMoney(orderMoney);
			String orderMemo = new String(request.getParameter("orderMemo").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderMemo(orderMemo);
			String orderAddTime = new String(request.getParameter("orderAddTime").getBytes("iso-8859-1"), "UTF-8");
			orderInfo.setOrderAddTime(orderAddTime);

			/* ����ҵ���ִ�и��²��� */
			String result = orderInfoDAO.UpdateOrderInfo(orderInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
