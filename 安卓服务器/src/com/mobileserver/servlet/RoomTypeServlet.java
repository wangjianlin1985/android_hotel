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

	/*构造房间类型业务层对象*/
	private RoomTypeDAO roomTypeDAO = new RoomTypeDAO();

	/*默认构造函数*/
	public RoomTypeServlet() {
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
			/*获取查询房间类型的参数信息*/

			/*调用业务逻辑层执行房间类型查询*/
			List<RoomType> roomTypeList = roomTypeDAO.QueryRoomType();

			/*2种数据传输格式，一种是xml文件格式：将查询的结果集通过xml格式传输给客户端
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
			//第2种采用json格式(我们用这种)： 客户端查询的图书对象，返回json数据格式
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if (action.equals("add")) {
			/* 添加房间类型：获取房间类型参数，参数保存到新建的房间类型对象 */ 
			RoomType roomType = new RoomType();
			int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
			roomType.setRoomTypeId(roomTypeId);
			String roomTypeName = new String(request.getParameter("roomTypeName").getBytes("iso-8859-1"), "UTF-8");
			roomType.setRoomTypeName(roomTypeName);

			/* 调用业务层执行添加操作 */
			String result = roomTypeDAO.AddRoomType(roomType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		} else if (action.equals("delete")) {
			/*删除房间类型：获取房间类型的记录编号*/
			int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
			/*调用业务逻辑层执行删除操作*/
			String result = roomTypeDAO.DeleteRoomType(roomTypeId);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			/*将删除是否成功信息返回给客户端*/
			out.print(result);
		} else if (action.equals("updateQuery")) {
			/*更新房间类型之前先根据roomTypeId查询某个房间类型*/
			int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
			RoomType roomType = roomTypeDAO.GetRoomType(roomTypeId);

			// 客户端查询的房间类型对象，返回json数据格式, 将List<Book>组织成JSON字符串
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
			response.setContentType("text/json; charset=UTF-8");  //JSON的类型为text/json
			response.getOutputStream().write(stringer.toString().getBytes("UTF-8"));
		} else if(action.equals("update")) {
			/* 更新房间类型：获取房间类型参数，参数保存到新建的房间类型对象 */ 
			RoomType roomType = new RoomType();
			int roomTypeId = Integer.parseInt(request.getParameter("roomTypeId"));
			roomType.setRoomTypeId(roomTypeId);
			String roomTypeName = new String(request.getParameter("roomTypeName").getBytes("iso-8859-1"), "UTF-8");
			roomType.setRoomTypeName(roomTypeName);

			/* 调用业务层执行更新操作 */
			String result = roomTypeDAO.UpdateRoomType(roomType);
			response.setCharacterEncoding("utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
		}
	}
}
