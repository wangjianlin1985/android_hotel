package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.RoomInfoDAO;
import com.mobileserver.domain.RoomInfo;

import org.json.JSONStringer;

public class RoomInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���췿����Ϣҵ������*/
	private RoomInfoDAO roomInfoDAO = new RoomInfoDAO();

	/*Ĭ�Ϲ��캯��*/
	public RoomInfoServlet() {
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
			/*��ȡ��ѯ������Ϣ�Ĳ�����Ϣ*/
			String roomNumber = request.getParameter("roomNumber");
			roomNumber = roomNumber == null ? "" : new String(request.getParameter(
					"roomNumber").getBytes("iso-8859-1"), "UTF-8");
			int roomTypeObj = 0;
			if (request.getParameter("roomTypeObj") != null)
				roomTypeObj = Integer.parseInt(request.getParameter("roomTypeObj"));
			String position = request.getParameter("position");
			position = position == null ? "" : new String(request.getParameter(
					"position").getBytes("iso-8859-1"), "UTF-8");
			String introduction = request.getParameter("introduction");
			introduction = introduction == null ? "" : new String(request.getParameter(
					"introduction").getBytes("iso-8859-1"), "UTF-8");

			/*����ҵ���߼���ִ�з�����Ϣ��ѯ*/
			List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfo(roomNumber,roomTypeObj,position,introduction);

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<RoomInfos>").append("\r\n");
			for (int i = 0; i < roomInfoList.size(); i++) {
				sb.append("	<RoomInfo>").append("\r\n")
				.append("		<roomNumber>")
				.append(roomInfoList.get(i).getRoomNumber())
				.append("</roomNumber>").append("\r\n")
				.append("		<roomTypeObj>")
				.append(roomInfoList.get(i).getRoomTypeObj())
				.append("</roomTypeObj>").append("\r\n")
				.append("		<roomPrice>")
				.append(roomInfoList.get(i).getRoomPrice())
				.append("</roomPrice>").append("\r\n")
				.append("		<position>")
				.append(roomInfoList.get(i).getPosition())
				.append("</position>").append("\r\n")
				.append("		<introduction>")
				.append(roomInfoList.get(i).getIntroduction())
				.append("</introduction>").append("\r\n")
				.append("		<roomPhoto>")
				.append(roomInfoList.get(i).getRoomPhoto())
				.append("</roomPhoto>").append("\r\n")
				.append("	</RoomInfo>").append("\r\n");
			}
			sb.append("</RoomInfos>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(RoomInfo roomInfo: roomInfoList) {
				  stringer.object();
			  stringer.key("roomNumber").value(roomInfo.getRoomNumber());
			  stringer.key("roomTypeObj").value(roomInfo.getRoomTypeObj());
			  stringer.key("roomPrice").value(roomInfo.getRoomPrice());
			  stringer.key("position").value(roomInfo.getPosition());
			  stringer.key("introduction").value(roomInfo.getIntroduction());
			  stringer.key("roomPhoto").value(roomInfo.getRoomPhoto());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӷ�����Ϣ����ȡ������Ϣ�������������浽�½��ķ�����Ϣ���� */ 
			RoomInfo roomInfo = new RoomInfo();
			String roomNumber = new String(request.getParameter("roomNumber").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomNumber(roomNumber);
			int roomTypeObj = Integer.parseInt(request.getParameter("roomTypeObj"));
			roomInfo.setRoomTypeObj(roomTypeObj);
			float roomPrice = Float.parseFloat(request.getParameter("roomPrice"));
			roomInfo.setRoomPrice(roomPrice);
			String position = new String(request.getParameter("position").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setPosition(position);
			String introduction = new String(request.getParameter("introduction").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setIntroduction(introduction);
			String roomPhoto = new String(request.getParameter("roomPhoto").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomPhoto(roomPhoto);

			/* ����ҵ���ִ����Ӳ��� */
			String result = roomInfoDAO.AddRoomInfo(roomInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ��������Ϣ����ȡ������Ϣ�ķ�����*/
			String roomNumber = new String(request.getParameter("roomNumber").getBytes("iso-8859-1"), "UTF-8");
			/*����ҵ���߼���ִ��ɾ������*/
			String result = roomInfoDAO.DeleteRoomInfo(roomNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���·�����Ϣ֮ǰ�ȸ���roomNumber��ѯĳ��������Ϣ*/
			String roomNumber = new String(request.getParameter("roomNumber").getBytes("iso-8859-1"), "UTF-8");
			RoomInfo roomInfo = roomInfoDAO.GetRoomInfo(roomNumber);

			// �ͻ��˲�ѯ�ķ�����Ϣ���󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("roomNumber").value(roomInfo.getRoomNumber());
			  stringer.key("roomTypeObj").value(roomInfo.getRoomTypeObj());
			  stringer.key("roomPrice").value(roomInfo.getRoomPrice());
			  stringer.key("position").value(roomInfo.getPosition());
			  stringer.key("introduction").value(roomInfo.getIntroduction());
			  stringer.key("roomPhoto").value(roomInfo.getRoomPhoto());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���·�����Ϣ����ȡ������Ϣ�������������浽�½��ķ�����Ϣ���� */ 
			RoomInfo roomInfo = new RoomInfo();
			String roomNumber = new String(request.getParameter("roomNumber").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomNumber(roomNumber);
			int roomTypeObj = Integer.parseInt(request.getParameter("roomTypeObj"));
			roomInfo.setRoomTypeObj(roomTypeObj);
			float roomPrice = Float.parseFloat(request.getParameter("roomPrice"));
			roomInfo.setRoomPrice(roomPrice);
			String position = new String(request.getParameter("position").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setPosition(position);
			String introduction = new String(request.getParameter("introduction").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setIntroduction(introduction);
			String roomPhoto = new String(request.getParameter("roomPhoto").getBytes("iso-8859-1"), "UTF-8");
			roomInfo.setRoomPhoto(roomPhoto);

			/* ����ҵ���ִ�и��²��� */
			String result = roomInfoDAO.UpdateRoomInfo(roomInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
