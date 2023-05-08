package com.mobileserver.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Timestamp;
import java.util.List;

import com.mobileserver.dao.RoomTypeDAO;
import com.mobileserver.domain.RoomType;

import org.json.JSONStringer;

public class RoomTypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/*���췿������ҵ������*/
	private RoomTypeDAO roomTypeDAO = new RoomTypeDAO();

	/*Ĭ�Ϲ��캯��*/
	public RoomTypeServlet() {
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
			/*��ȡ��ѯ�������͵Ĳ�����Ϣ*/

			/*����ҵ���߼���ִ�з������Ͳ�ѯ*/
			List<RoomType> roomTypeList = roomTypeDAO.QueryRoomType();

			/*2�����ݴ����ʽ��һ����xml�ļ���ʽ������ѯ�Ľ����ͨ��xml��ʽ������ͻ���
			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>").append("\r\n")
			.append("<RoomTypes>").append("\r\n");
			for (int i = 0; i < roomTypeList.size(); i++) {
				sb.append("	<RoomType>").append("\r\n")
				.append("		<roomTypeId>")
				.append(roomTypeList.get(i).getRoomTypeId())
				.append("</roomTypeId>").append("\r\n")
				.append("		<roomTypeName>")
				.append(roomTypeList.get(i).getRoomTypeName())
				.append("</roomTypeName>").append("\r\n")
				.append("	</RoomType>").append("\r\n");
			}
			sb.append("</RoomTypes>").append("\r\n");
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(sb.toString());*/
			//��2�ֲ���json��ʽ(����������)�� �ͻ��˲�ѯ��ͼ����󣬷���json���ݸ�ʽ
			JSONStringer stringer = new JSONStringer();
			try {
			  stringer.array();
			  for(RoomType roomType: roomTypeList) {
				  stringer.object();
			  stringer.key("roomTypeId").value(roomType.getRoomTypeId());
			  stringer.key("roomTypeName").value(roomType.getRoomTypeName());
				  stringer.endObject();
			  }
			  stringer.endArray();
			} catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* ��ӷ������ͣ���ȡ�������Ͳ������������浽�½��ķ������Ͷ��� */ 
			RoomType roomType = new RoomType();
			int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
			roomType.setRoomTypeId(roomTypeId);
			String roomTypeName = new String(request.getParameter("roomTypeName").getBytes("iso-8859-1"), "UTF-8");
			roomType.setRoomTypeName(roomTypeName);

			/* ����ҵ���ִ����Ӳ��� */
			String result = roomTypeDAO.AddRoomType(roomType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*ɾ���������ͣ���ȡ�������͵ļ�¼���*/
			int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
			/*����ҵ���߼���ִ��ɾ������*/
			String result = roomTypeDAO.DeleteRoomType(roomTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*��ɾ���Ƿ�ɹ���Ϣ���ظ��ͻ���*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*���·�������֮ǰ�ȸ���roomTypeId��ѯĳ����������*/
			int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
			RoomType roomType = roomTypeDAO.GetRoomType(roomTypeId);

			// �ͻ��˲�ѯ�ķ������Ͷ��󣬷���json���ݸ�ʽ, ��List<Book>��֯��JSON�ַ���
			JSONStringer stringer = new JSONStringer(); 
			try{
			  stringer.array();
			  stringer.object();
			  stringer.key("roomTypeId").value(roomType.getRoomTypeId());
			  stringer.key("roomTypeName").value(roomType.getRoomTypeName());
			  stringer.endObject();
			  stringer.endArray();
			}
			catch(Exception e){}
			response.setContentType("text/json; charset=UTF-8");  //JSON������Ϊtext/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* ���·������ͣ���ȡ�������Ͳ������������浽�½��ķ������Ͷ��� */ 
			RoomType roomType = new RoomType();
			int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
			roomType.setRoomTypeId(roomTypeId);
			String roomTypeName = new String(request.getParameter("roomTypeName").getBytes("iso-8859-1"), "UTF-8");
			roomType.setRoomTypeName(roomTypeName);

			/* ����ҵ���ִ�и��²��� */
			String result = roomTypeDAO.UpdateRoomType(roomType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
