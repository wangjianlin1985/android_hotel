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

    private int roomTypeId;
    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
    }
    public int getRoomTypeId() {
        return roomTypeId;
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

    /*��������RoomType����*/
    private RoomType roomType;
    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }
    public RoomType getRoomType() {
        return this.roomType;
    }

    /*��ת�����RoomType��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���RoomType��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddRoomType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            roomTypeDAO.AddRoomType(roomType);
            ctx.put("message",  java.net.URLEncoder.encode("RoomType��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomType���ʧ��!"));
            return "error";
        }
    }

    /*��ѯRoomType��Ϣ*/
    public String QueryRoomType() {
        if(currentPage == 0) currentPage = 1;
        List<RoomType> roomTypeList = roomTypeDAO.QueryRoomTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        roomTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = roomTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = roomTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomTypeList",  roomTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryRoomTypeOutputToExcel() { 
        List<RoomType> roomTypeList = roomTypeDAO.QueryRoomTypeInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "RoomType��Ϣ��¼"; 
        String[] headers = { "��¼���","��������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"RoomType.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯRoomType��Ϣ*/
    public String FrontQueryRoomType() {
        if(currentPage == 0) currentPage = 1;
        List<RoomType> roomTypeList = roomTypeDAO.QueryRoomTypeInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        roomTypeDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = roomTypeDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = roomTypeDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("roomTypeList",  roomTypeList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�RoomType��Ϣ*/
    public String ModifyRoomTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������roomTypeId��ȡRoomType����*/
        RoomType roomType = roomTypeDAO.GetRoomTypeByRoomTypeId(roomTypeId);

        ctx.put("roomType",  roomType);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�RoomType��Ϣ*/
    public String FrontShowRoomTypeQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������roomTypeId��ȡRoomType����*/
        RoomType roomType = roomTypeDAO.GetRoomTypeByRoomTypeId(roomTypeId);

        ctx.put("roomType",  roomType);
        return "front_show_view";
    }

    /*�����޸�RoomType��Ϣ*/
    public String ModifyRoomType() {
        ActionContext ctx = ActionContext.getContext();
        try {
            roomTypeDAO.UpdateRoomType(roomType);
            ctx.put("message",  java.net.URLEncoder.encode("RoomType��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomType��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��RoomType��Ϣ*/
    public String DeleteRoomType() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            roomTypeDAO.DeleteRoomType(roomTypeId);
            ctx.put("message",  java.net.URLEncoder.encode("RoomTypeɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("RoomTypeɾ��ʧ��!"));
            return "error";
        }
    }

}
