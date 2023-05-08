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

	/*构造房间信息业务层对象*/
	private RoomInfoDAO roomInfoDAO = new RoomInfoDAO();

	/*默认构造函数*/
	public RoomInfoServlet() {
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
			/*获取查询房间信息的参数信息*/
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

			/*调用业务逻辑层执行房间信息查询*/
			List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfo(roomNumber,roomTypeObj,position,introduction);

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加房间信息：获取房间信息参数，参数保存到新建的房间信息对象 */ 
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

			/* 调用业务层执行添加操作 */
			String result = roomInfoDAO.AddRoomInfo(roomInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除房间信息：获取房间信息的房间编号*/
			String roomNumber = new String(request.getParameter("roomNumber").getBytes("iso-8859-1"), "UTF-8");
			/*调用业务逻辑层执行删除操作*/
			String result = roomInfoDAO.DeleteRoomInfo(roomNumber);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新房间信息之前先根据roomNumber查询某个房间信息*/
			String roomNumber = new String(request.getParameter("roomNumber").getBytes("iso-8859-1"), "UTF-8");
			RoomInfo roomInfo = roomInfoDAO.GetRoomInfo(roomNumber);

			// 客户端查询的房间信息对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新房间信息：获取房间信息参数，参数保存到新建的房间信息对象 */ 
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

			/* 调用业务层执行更新操作 */
			String result = roomInfoDAO.UpdateRoomInfo(roomInfo);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
