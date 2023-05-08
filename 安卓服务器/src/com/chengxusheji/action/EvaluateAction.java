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
import com.chengxusheji.dao.EvaluateDAO;
import com.chengxusheji.domain.Evaluate;
import com.chengxusheji.dao.RoomInfoDAO;
import com.chengxusheji.domain.RoomInfo;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class EvaluateAction extends BaseAction {

    /*界面层需要查询的属性: 评价的房间*/
    private RoomInfo roomObj;
    public void setRoomObj(RoomInfo roomObj) {
        this.roomObj = roomObj;
    }
    public RoomInfo getRoomObj() {
        return this.roomObj;
    }

    /*界面层需要查询的属性: 评价的用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 评价时间*/
    private String evaluateTime;
    public void setEvaluateTime(String evaluateTime) {
        this.evaluateTime = evaluateTime;
    }
    public String getEvaluateTime() {
        return this.evaluateTime;
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

    private int evaluateId;
    public void setEvaluateId(int evaluateId) {
        this.evaluateId = evaluateId;
    }
    public int getEvaluateId() {
        return evaluateId;
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
    @Resource RoomInfoDAO roomInfoDAO;
    @Resource UserInfoDAO userInfoDAO;
    @Resource EvaluateDAO evaluateDAO;

    /*待操作的Evaluate对象*/
    private Evaluate evaluate;
    public void setEvaluate(Evaluate evaluate) {
        this.evaluate = evaluate;
    }
    public Evaluate getEvaluate() {
        return this.evaluate;
    }

    /*跳转到添加Evaluate视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的RoomInfo信息*/
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加Evaluate信息*/
    @SuppressWarnings("deprecation")
    public String AddEvaluate() {
        ActionContext ctx = ActionContext.getContext();
        try {
            RoomInfo roomObj = roomInfoDAO.GetRoomInfoByRoomNumber(evaluate.getRoomObj().getRoomNumber());
            evaluate.setRoomObj(roomObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(evaluate.getUserObj().getUser_name());
            evaluate.setUserObj(userObj);
            evaluateDAO.AddEvaluate(evaluate);
            ctx.put("message",  java.net.URLEncoder.encode("Evaluate添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluate添加失败!"));
            return "error";
        }
    }

    /*查询Evaluate信息*/
    public String QueryEvaluate() {
        if(currentPage == 0) currentPage = 1;
        if(evaluateTime == null) evaluateTime = "";
        List<Evaluate> evaluateList = evaluateDAO.QueryEvaluateInfo(roomObj, userObj, evaluateTime, currentPage);
        /*计算总的页数和总的记录数*/
        evaluateDAO.CalculateTotalPageAndRecordNumber(roomObj, userObj, evaluateTime);
        /*获取到总的页码数目*/
        totalPage = evaluateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = evaluateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("evaluateList",  evaluateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("roomObj", roomObj);
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("evaluateTime", evaluateTime);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryEvaluateOutputToExcel() { 
        if(evaluateTime == null) evaluateTime = "";
        List<Evaluate> evaluateList = evaluateDAO.QueryEvaluateInfo(roomObj,userObj,evaluateTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Evaluate信息记录"; 
        String[] headers = { "评价的房间","评价的用户","评分(5分制)","评价内容","评价时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<evaluateList.size();i++) {
        	Evaluate evaluate = evaluateList.get(i); 
        	dataset.add(new String[]{evaluate.getRoomObj().getRoomNumber(),
evaluate.getUserObj().getName(),
evaluate.getEvalueScore() + "",evaluate.getEvaluateContent(),evaluate.getEvaluateTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Evaluate.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询Evaluate信息*/
    public String FrontQueryEvaluate() {
        if(currentPage == 0) currentPage = 1;
        if(evaluateTime == null) evaluateTime = "";
        List<Evaluate> evaluateList = evaluateDAO.QueryEvaluateInfo(roomObj, userObj, evaluateTime, currentPage);
        /*计算总的页数和总的记录数*/
        evaluateDAO.CalculateTotalPageAndRecordNumber(roomObj, userObj, evaluateTime);
        /*获取到总的页码数目*/
        totalPage = evaluateDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = evaluateDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("evaluateList",  evaluateList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("roomObj", roomObj);
        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("evaluateTime", evaluateTime);
        return "front_query_view";
    }

    /*查询要修改的Evaluate信息*/
    public String ModifyEvaluateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键evaluateId获取Evaluate对象*/
        Evaluate evaluate = evaluateDAO.GetEvaluateByEvaluateId(evaluateId);

        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("evaluate",  evaluate);
        return "modify_view";
    }

    /*查询要修改的Evaluate信息*/
    public String FrontShowEvaluateQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键evaluateId获取Evaluate对象*/
        Evaluate evaluate = evaluateDAO.GetEvaluateByEvaluateId(evaluateId);

        List<RoomInfo> roomInfoList = roomInfoDAO.QueryAllRoomInfoInfo();
        ctx.put("roomInfoList", roomInfoList);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("evaluate",  evaluate);
        return "front_show_view";
    }

    /*更新修改Evaluate信息*/
    public String ModifyEvaluate() {
        ActionContext ctx = ActionContext.getContext();
        try {
            RoomInfo roomObj = roomInfoDAO.GetRoomInfoByRoomNumber(evaluate.getRoomObj().getRoomNumber());
            evaluate.setRoomObj(roomObj);
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(evaluate.getUserObj().getUser_name());
            evaluate.setUserObj(userObj);
            evaluateDAO.UpdateEvaluate(evaluate);
            ctx.put("message",  java.net.URLEncoder.encode("Evaluate信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluate信息更新失败!"));
            return "error";
       }
   }

    /*删除Evaluate信息*/
    public String DeleteEvaluate() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            evaluateDAO.DeleteEvaluate(evaluateId);
            ctx.put("message",  java.net.URLEncoder.encode("Evaluate删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Evaluate删除失败!"));
            return "error";
        }
    }

}
