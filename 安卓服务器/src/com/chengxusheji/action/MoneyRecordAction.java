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
import com.chengxusheji.dao.MoneyRecordDAO;
import com.chengxusheji.domain.MoneyRecord;
import com.chengxusheji.dao.UserInfoDAO;
import com.chengxusheji.domain.UserInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class MoneyRecordAction extends BaseAction {

    /*界面层需要查询的属性: 充值的用户*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*界面层需要查询的属性: 充值时间*/
    private String happenTime;
    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }
    public String getHappenTime() {
        return this.happenTime;
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

    private int recordId;
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
    public int getRecordId() {
        return recordId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource MoneyRecordDAO moneyRecordDAO;

    /*待操作的MoneyRecord对象*/
    private MoneyRecord moneyRecord;
    public void setMoneyRecord(MoneyRecord moneyRecord) {
        this.moneyRecord = moneyRecord;
    }
    public MoneyRecord getMoneyRecord() {
        return this.moneyRecord;
    }

    /*跳转到添加MoneyRecord视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的UserInfo信息*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*添加MoneyRecord信息*/
    @SuppressWarnings("deprecation")
    public String AddMoneyRecord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(moneyRecord.getUserObj().getUser_name());
            moneyRecord.setUserObj(userObj);
            moneyRecordDAO.AddMoneyRecord(moneyRecord);
            ctx.put("message",  java.net.URLEncoder.encode("MoneyRecord添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MoneyRecord添加失败!"));
            return "error";
        }
    }

    /*查询MoneyRecord信息*/
    public String QueryMoneyRecord() {
        if(currentPage == 0) currentPage = 1;
        if(happenTime == null) happenTime = "";
        List<MoneyRecord> moneyRecordList = moneyRecordDAO.QueryMoneyRecordInfo(userObj, happenTime, currentPage);
        /*计算总的页数和总的记录数*/
        moneyRecordDAO.CalculateTotalPageAndRecordNumber(userObj, happenTime);
        /*获取到总的页码数目*/
        totalPage = moneyRecordDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = moneyRecordDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("moneyRecordList",  moneyRecordList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("happenTime", happenTime);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryMoneyRecordOutputToExcel() { 
        if(happenTime == null) happenTime = "";
        List<MoneyRecord> moneyRecordList = moneyRecordDAO.QueryMoneyRecordInfo(userObj,happenTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "MoneyRecord信息记录"; 
        String[] headers = { "充值的用户","充值金额","充值时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<moneyRecordList.size();i++) {
        	MoneyRecord moneyRecord = moneyRecordList.get(i); 
        	dataset.add(new String[]{moneyRecord.getUserObj().getName(),
moneyRecord.getMoneyValue() + "",moneyRecord.getHappenTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"MoneyRecord.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询MoneyRecord信息*/
    public String FrontQueryMoneyRecord() {
        if(currentPage == 0) currentPage = 1;
        if(happenTime == null) happenTime = "";
        List<MoneyRecord> moneyRecordList = moneyRecordDAO.QueryMoneyRecordInfo(userObj, happenTime, currentPage);
        /*计算总的页数和总的记录数*/
        moneyRecordDAO.CalculateTotalPageAndRecordNumber(userObj, happenTime);
        /*获取到总的页码数目*/
        totalPage = moneyRecordDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = moneyRecordDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("moneyRecordList",  moneyRecordList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("userObj", userObj);
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("happenTime", happenTime);
        return "front_query_view";
    }

    /*查询要修改的MoneyRecord信息*/
    public String ModifyMoneyRecordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键recordId获取MoneyRecord对象*/
        MoneyRecord moneyRecord = moneyRecordDAO.GetMoneyRecordByRecordId(recordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("moneyRecord",  moneyRecord);
        return "modify_view";
    }

    /*查询要修改的MoneyRecord信息*/
    public String FrontShowMoneyRecordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键recordId获取MoneyRecord对象*/
        MoneyRecord moneyRecord = moneyRecordDAO.GetMoneyRecordByRecordId(recordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("moneyRecord",  moneyRecord);
        return "front_show_view";
    }

    /*更新修改MoneyRecord信息*/
    public String ModifyMoneyRecord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(moneyRecord.getUserObj().getUser_name());
            moneyRecord.setUserObj(userObj);
            moneyRecordDAO.UpdateMoneyRecord(moneyRecord);
            ctx.put("message",  java.net.URLEncoder.encode("MoneyRecord信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MoneyRecord信息更新失败!"));
            return "error";
       }
   }

    /*删除MoneyRecord信息*/
    public String DeleteMoneyRecord() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            moneyRecordDAO.DeleteMoneyRecord(recordId);
            ctx.put("message",  java.net.URLEncoder.encode("MoneyRecord删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MoneyRecord删除失败!"));
            return "error";
        }
    }

}
