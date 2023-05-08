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
import com.chengxusheji.dao.RoomInfoDAO;
import com.chengxusheji.domain.RoomInfo;
import com.chengxusheji.dao.RoomTypeDAO;
import com.chengxusheji.domain.RoomType;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class RoomInfoAction extends BaseAction {

	/*图片或文件字段roomPhoto参数接收*/
	private File roomPhotoFile;
	private String roomPhotoFileFileName;
	private String roomPhotoFileContentType;
	public File getRoomPhotoFile() {
		return roomPhotoFile;
	}
	public void setRoomPhotoFile(File roomPhotoFile) {
		this.roomPhotoFile = roomPhotoFile;
	}
	public String getRoomPhotoFileFileName() {
		return roomPhotoFileFileName;
	}
	public void setRoomPhotoFileFileName(String roomPhotoFileFileName) {
		this.roomPhotoFileFileName = roomPhotoFileFileName;
	}
	public String getRoomPhotoFileContentType() {
		return roomPhotoFileContentType;
	}
	public void setRoomPhotoFileContentType(String roomPhotoFileContentType) {
		this.roomPhotoFileContentType = roomPhotoFileContentType;
	}
    /*界面层需要查询的属性: 房间编号*/
    private String roomNumber;
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getRoomNumber() {
        return this.roomNumber;
    }

    /*界面层需要查询的属性: 房间类型*/
    private RoomType roomTypeObj;
    public void setRoomTypeObj(RoomType roomTypeObj) {
        this.roomTypeObj = roomTypeObj;
    }
    public RoomType getRoomTypeObj() {
        return this.roomTypeObj;
    }

    /*界面层需要查询的属性: 所处位置*/
    private String position;
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return this.position;
    }

    /*界面层需要查询的属性: 房间介绍*/
    private String introduction;
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getIntroduction() {
        return this.introduction;
    }

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
    @Resource RoomInfoDAO roomInfoDAO;

    /*待操作的RoomInfo对象*/
    private RoomInfo roomInfo;
    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }
    public RoomInfo getRoomInfo() {
        return this.roomInfo;
    }

    /*跳转到添加RoomInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的RoomType信息*/
        List<RoomType> roomTypeList = roomTypeDAO.QueryAllRoomTypeInfo();
        ctx.put("roomTypeList", roomTypeList);
        return "add_view";
    }

    /*添加RoomInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*验证房间编号是否已经存在*/
        String roomNumber = roomInfo.getRoomNumber();
        RoomInfo db_roomInfo = roomInfoDAO.GetRoomInfoByRoomNumber(roomNumber);
        if(null != db_roomInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("该房间编号已经存在!"));
            return "error";
        }
        try {
            RoomType roomTypeObj = roomTypeDAO.GetRoomTypeByRoomTypeId(roomInfo.getRoomTypeObj().getRoomTypeId());
            roomInfo.setRoomTypeObj(roomTypeObj);
            /*处理房间照片上传*/
            String roomPhotoPath = "upload/noimage.jpg"; 
       	 	if(roomPhotoFile != null)
       	 		roomPhotoPath = photoUpload(roomPhotoFile,roomPhotoFileContentType);
       	 	roomInfo.setRoomPhoto(roomPhotoPath);
            roomInfoDAO.AddRoomInfo(roomInfo);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfo添加失败!"));
            return "error";
        }
    }

    /*查询RoomInfo信息*/
    public String QueryRoomInfo() {
        if(currentPage == 0) currentPage = 1;
        if(roomNumber == null) roomNumber = "";
        if(position == null) position = "";
        if(introduction == null) introduction = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(roomNumber, roomTypeObj, position, introduction, currentPage);
        /*计算总的页数和总的记录数*/
        roomInfoDAO.CalculateTotalPageAndRecordNumber(roomNumber, roomTypeObj, position, introduction);
        /*获取到总的页码数目*/
        totalPage = roomInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = roomInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomInfoList",  roomInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("roomNumber", roomNumber);
        ctx.put("roomTypeObj", roomTypeObj);
        List<RoomType> roomTypeList = roomTypeDAO.QueryAllRoomTypeInfo();
        ctx.put("roomTypeList", roomTypeList);
        ctx.put("position", position);
        ctx.put("introduction", introduction);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryRoomInfoOutputToExcel() { 
        if(roomNumber == null) roomNumber = "";
        if(position == null) position = "";
        if(introduction == null) introduction = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(roomNumber,roomTypeObj,position,introduction);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "RoomInfo信息记录"; 
        String[] headers = { "房间编号","房间类型","价格(元/天)","所处位置","房间介绍","房间照片"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<roomInfoList.size();i++) {
        	RoomInfo roomInfo = roomInfoList.get(i); 
        	dataset.add(new String[]{roomInfo.getRoomNumber(),roomInfo.getRoomTypeObj().getRoomTypeName(),
roomInfo.getRoomPrice() + "",roomInfo.getPosition(),roomInfo.getIntroduction(),roomInfo.getRoomPhoto()});
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
			response.setHeader("Content-disposition","attachment; filename="+"RoomInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询RoomInfo信息*/
    public String FrontQueryRoomInfo() {
        if(currentPage == 0) currentPage = 1;
        if(roomNumber == null) roomNumber = "";
        if(position == null) position = "";
        if(introduction == null) introduction = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(roomNumber, roomTypeObj, position, introduction, currentPage);
        /*计算总的页数和总的记录数*/
        roomInfoDAO.CalculateTotalPageAndRecordNumber(roomNumber, roomTypeObj, position, introduction);
        /*获取到总的页码数目*/
        totalPage = roomInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = roomInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomInfoList",  roomInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("roomNumber", roomNumber);
        ctx.put("roomTypeObj", roomTypeObj);
        List<RoomType> roomTypeList = roomTypeDAO.QueryAllRoomTypeInfo();
        ctx.put("roomTypeList", roomTypeList);
        ctx.put("position", position);
        ctx.put("introduction", introduction);
        return "front_query_view";
    }

    /*查询要修改的RoomInfo信息*/
    public String ModifyRoomInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键roomNumber获取RoomInfo对象*/
        RoomInfo roomInfo = roomInfoDAO.GetRoomInfoByRoomNumber(roomNumber);

        List<RoomType> roomTypeList = roomTypeDAO.QueryAllRoomTypeInfo();
        ctx.put("roomTypeList", roomTypeList);
        ctx.put("roomInfo",  roomInfo);
        return "modify_view";
    }

    /*查询要修改的RoomInfo信息*/
    public String FrontShowRoomInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键roomNumber获取RoomInfo对象*/
        RoomInfo roomInfo = roomInfoDAO.GetRoomInfoByRoomNumber(roomNumber);

        List<RoomType> roomTypeList = roomTypeDAO.QueryAllRoomTypeInfo();
        ctx.put("roomTypeList", roomTypeList);
        ctx.put("roomInfo",  roomInfo);
        return "front_show_view";
    }

    /*更新修改RoomInfo信息*/
    public String ModifyRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            RoomType roomTypeObj = roomTypeDAO.GetRoomTypeByRoomTypeId(roomInfo.getRoomTypeObj().getRoomTypeId());
            roomInfo.setRoomTypeObj(roomTypeObj);
            /*处理房间照片上传*/
            if(roomPhotoFile != null) {
            	String roomPhotoPath = photoUpload(roomPhotoFile,roomPhotoFileContentType);
            	roomInfo.setRoomPhoto(roomPhotoPath);
            }
            roomInfoDAO.UpdateRoomInfo(roomInfo);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除RoomInfo信息*/
    public String DeleteRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            roomInfoDAO.DeleteRoomInfo(roomNumber);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfo删除失败!"));
            return "error";
        }
    }

}
