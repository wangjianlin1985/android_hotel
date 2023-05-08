package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.RoomTypeDAO;
import com.chengxusheji.domain.RoomType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class RoomTypeAction extends BaseAction {

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    private int roomTypeId;
    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
    public int getRoomTypeId() {
        return roomTypeId;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource RoomTypeDAO roomTypeDAO;

    /*待操作的RoomType对象*/
    private RoomType roomType;
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    public RoomType getRoomType() {
        return this.roomType;
    }

    /*跳转到添加RoomType视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加RoomType信息*/
    @SuppressWarnings("deprecation")
    public String AddRoomType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            roomTypeDAO.AddRoomType(roomType);
            ctx.put("message",  java.net.URLEncoder.encode("RoomType添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomType添加失败!"));
            return "error";
        }
    }

    /*查询RoomType信息*/
    public String QueryRoomType() {
        if(currentPage == 0) currentPage = 1;
        List<RoomType> roomTypeList = roomTypeDAO.QueryRoomTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        roomTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = roomTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = roomTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomTypeList",  roomTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryRoomTypeOutputToExcel() { 
        List<RoomType> roomTypeList = roomTypeDAO.QueryRoomTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "RoomType信息记录"; 
        String[] headers = { "记录编号","房间类型"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<roomTypeList.size();i++) {
        	RoomType roomType = roomTypeList.get(i); 
        	dataset.add(new String[]{roomType.getRoomTypeId() + "",roomType.getRoomTypeName()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"RoomType.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询RoomType信息*/
    public String FrontQueryRoomType() {
        if(currentPage == 0) currentPage = 1;
        List<RoomType> roomTypeList = roomTypeDAO.QueryRoomTypeInfo(currentPage);
        /*计算总的页数和总的记录数*/
        roomTypeDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = roomTypeDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = roomTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomTypeList",  roomTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的RoomType信息*/
    public String ModifyRoomTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键roomTypeId获取RoomType对象*/
        RoomType roomType = roomTypeDAO.GetRoomTypeByRoomTypeId(roomTypeId);

        ctx.put("roomType",  roomType);
        return "modify_view";
    }

    /*查询要修改的RoomType信息*/
    public String FrontShowRoomTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键roomTypeId获取RoomType对象*/
        RoomType roomType = roomTypeDAO.GetRoomTypeByRoomTypeId(roomTypeId);

        ctx.put("roomType",  roomType);
        return "front_show_view";
    }

    /*更新修改RoomType信息*/
    public String ModifyRoomType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            roomTypeDAO.UpdateRoomType(roomType);
            ctx.put("message",  java.net.URLEncoder.encode("RoomType信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomType信息更新失败!"));
            return "error";
       }
   }

    /*删除RoomType信息*/
    public String DeleteRoomType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            roomTypeDAO.DeleteRoomType(roomTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("RoomType删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomType删除失败!"));
            return "error";
        }
    }

}
