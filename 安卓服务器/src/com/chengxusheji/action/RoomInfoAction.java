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

	/*ͼƬ���ļ��ֶ�roomPhoto��������*/
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
    /*�������Ҫ��ѯ������: ������*/
    private String roomNumber;
    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }
    public String getRoomNumber() {
        return this.roomNumber;
    }

    /*�������Ҫ��ѯ������: ��������*/
    private RoomType roomTypeObj;
    public void setRoomTypeObj(RoomType roomTypeObj) {
        this.roomTypeObj = roomTypeObj;
    }
    public RoomType getRoomTypeObj() {
        return this.roomTypeObj;
    }

    /*�������Ҫ��ѯ������: ����λ��*/
    private String position;
    public void setPosition(String position) {
        this.position = position;
    }
    public String getPosition() {
        return this.position;
    }

    /*�������Ҫ��ѯ������: �������*/
    private String introduction;
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getIntroduction() {
        return this.introduction;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource RoomTypeDAO roomTypeDAO;
    @Resource RoomInfoDAO roomInfoDAO;

    /*��������RoomInfo����*/
    private RoomInfo roomInfo;
    public void setRoomInfo(RoomInfo roomInfo) {
        this.roomInfo = roomInfo;
    }
    public RoomInfo getRoomInfo() {
        return this.roomInfo;
    }

    /*��ת�����RoomInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�RoomType��Ϣ*/
        List<RoomType> roomTypeList = roomTypeDAO.QueryAllRoomTypeInfo();
        ctx.put("roomTypeList", roomTypeList);
        return "add_view";
    }

    /*���RoomInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        /*��֤�������Ƿ��Ѿ�����*/
        String roomNumber = roomInfo.getRoomNumber();
        RoomInfo db_roomInfo = roomInfoDAO.GetRoomInfoByRoomNumber(roomNumber);
        if(null != db_roomInfo) {
            ctx.put("error",  java.net.URLEncoder.encode("�÷������Ѿ�����!"));
            return "error";
        }
        try {
            RoomType roomTypeObj = roomTypeDAO.GetRoomTypeByRoomTypeId(roomInfo.getRoomTypeObj().getRoomTypeId());
            roomInfo.setRoomTypeObj(roomTypeObj);
            /*��������Ƭ�ϴ�*/
            String roomPhotoPath = "upload/noimage.jpg"; 
       	 	if(roomPhotoFile != null)
       	 		roomPhotoPath = photoUpload(roomPhotoFile,roomPhotoFileContentType);
       	 	roomInfo.setRoomPhoto(roomPhotoPath);
            roomInfoDAO.AddRoomInfo(roomInfo);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯRoomInfo��Ϣ*/
    public String QueryRoomInfo() {
        if(currentPage == 0) currentPage = 1;
        if(roomNumber == null) roomNumber = "";
        if(position == null) position = "";
        if(introduction == null) introduction = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(roomNumber, roomTypeObj, position, introduction, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        roomInfoDAO.CalculateTotalPageAndRecordNumber(roomNumber, roomTypeObj, position, introduction);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = roomInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryRoomInfoOutputToExcel() { 
        if(roomNumber == null) roomNumber = "";
        if(position == null) position = "";
        if(introduction == null) introduction = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(roomNumber,roomTypeObj,position,introduction);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "RoomInfo��Ϣ��¼"; 
        String[] headers = { "������","��������","�۸�(Ԫ/��)","����λ��","�������","������Ƭ"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"RoomInfo.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯRoomInfo��Ϣ*/
    public String FrontQueryRoomInfo() {
        if(currentPage == 0) currentPage = 1;
        if(roomNumber == null) roomNumber = "";
        if(position == null) position = "";
        if(introduction == null) introduction = "";
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryRoomInfoInfo(roomNumber, roomTypeObj, position, introduction, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        roomInfoDAO.CalculateTotalPageAndRecordNumber(roomNumber, roomTypeObj, position, introduction);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = roomInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�RoomInfo��Ϣ*/
    public String ModifyRoomInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������roomNumber��ȡRoomInfo����*/
        RoomInfo roomInfo = roomInfoDAO.GetRoomInfoByRoomNumber(roomNumber);

        List<RoomType> roomTypeList = roomTypeDAO.QueryAllRoomTypeInfo();
        ctx.put("roomTypeList", roomTypeList);
        ctx.put("roomInfo",  roomInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�RoomInfo��Ϣ*/
    public String FrontShowRoomInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������roomNumber��ȡRoomInfo����*/
        RoomInfo roomInfo = roomInfoDAO.GetRoomInfoByRoomNumber(roomNumber);

        List<RoomType> roomTypeList = roomTypeDAO.QueryAllRoomTypeInfo();
        ctx.put("roomTypeList", roomTypeList);
        ctx.put("roomInfo",  roomInfo);
        return "front_show_view";
    }

    /*�����޸�RoomInfo��Ϣ*/
    public String ModifyRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            RoomType roomTypeObj = roomTypeDAO.GetRoomTypeByRoomTypeId(roomInfo.getRoomTypeObj().getRoomTypeId());
            roomInfo.setRoomTypeObj(roomTypeObj);
            /*��������Ƭ�ϴ�*/
            if(roomPhotoFile != null) {
            	String roomPhotoPath = photoUpload(roomPhotoFile,roomPhotoFileContentType);
            	roomInfo.setRoomPhoto(roomPhotoPath);
            }
            roomInfoDAO.UpdateRoomInfo(roomInfo);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��RoomInfo��Ϣ*/
    public String DeleteRoomInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            roomInfoDAO.DeleteRoomInfo(roomNumber);
            ctx.put("message",  java.net.URLEncoder.encode("RoomInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
