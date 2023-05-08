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

    /*�������Ҫ��ѯ������: ��ֵ���û�*/
    private UserInfo userObj;
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }
    public UserInfo getUserObj() {
        return this.userObj;
    }

    /*�������Ҫ��ѯ������: ��ֵʱ��*/
    private String happenTime;
    public void setHappenTime(String happenTime) {
        this.happenTime = happenTime;
    }
    public String getHappenTime() {
        return this.happenTime;
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

    private int recordId;
    public void setRecordId(int recordId) {
        this.recordId = recordId;
    }
    public int getRecordId() {
        return recordId;
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
    @Resource UserInfoDAO userInfoDAO;
    @Resource MoneyRecordDAO moneyRecordDAO;

    /*��������MoneyRecord����*/
    private MoneyRecord moneyRecord;
    public void setMoneyRecord(MoneyRecord moneyRecord) {
        this.moneyRecord = moneyRecord;
    }
    public MoneyRecord getMoneyRecord() {
        return this.moneyRecord;
    }

    /*��ת�����MoneyRecord��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�UserInfo��Ϣ*/
        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        return "add_view";
    }

    /*���MoneyRecord��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddMoneyRecord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(moneyRecord.getUserObj().getUser_name());
            moneyRecord.setUserObj(userObj);
            moneyRecordDAO.AddMoneyRecord(moneyRecord);
            ctx.put("message",  java.net.URLEncoder.encode("MoneyRecord��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MoneyRecord���ʧ��!"));
            return "error";
        }
    }

    /*��ѯMoneyRecord��Ϣ*/
    public String QueryMoneyRecord() {
        if(currentPage == 0) currentPage = 1;
        if(happenTime == null) happenTime = "";
        List<MoneyRecord> moneyRecordList = moneyRecordDAO.QueryMoneyRecordInfo(userObj, happenTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        moneyRecordDAO.CalculateTotalPageAndRecordNumber(userObj, happenTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = moneyRecordDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryMoneyRecordOutputToExcel() { 
        if(happenTime == null) happenTime = "";
        List<MoneyRecord> moneyRecordList = moneyRecordDAO.QueryMoneyRecordInfo(userObj,happenTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "MoneyRecord��Ϣ��¼"; 
        String[] headers = { "��ֵ���û�","��ֵ���","��ֵʱ��"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"MoneyRecord.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯMoneyRecord��Ϣ*/
    public String FrontQueryMoneyRecord() {
        if(currentPage == 0) currentPage = 1;
        if(happenTime == null) happenTime = "";
        List<MoneyRecord> moneyRecordList = moneyRecordDAO.QueryMoneyRecordInfo(userObj, happenTime, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        moneyRecordDAO.CalculateTotalPageAndRecordNumber(userObj, happenTime);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = moneyRecordDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�MoneyRecord��Ϣ*/
    public String ModifyMoneyRecordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������recordId��ȡMoneyRecord����*/
        MoneyRecord moneyRecord = moneyRecordDAO.GetMoneyRecordByRecordId(recordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("moneyRecord",  moneyRecord);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�MoneyRecord��Ϣ*/
    public String FrontShowMoneyRecordQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������recordId��ȡMoneyRecord����*/
        MoneyRecord moneyRecord = moneyRecordDAO.GetMoneyRecordByRecordId(recordId);

        List<UserInfo> userInfoList = userInfoDAO.QueryAllUserInfoInfo();
        ctx.put("userInfoList", userInfoList);
        ctx.put("moneyRecord",  moneyRecord);
        return "front_show_view";
    }

    /*�����޸�MoneyRecord��Ϣ*/
    public String ModifyMoneyRecord() {
        ActionContext ctx = ActionContext.getContext();
        try {
            UserInfo userObj = userInfoDAO.GetUserInfoByUser_name(moneyRecord.getUserObj().getUser_name());
            moneyRecord.setUserObj(userObj);
            moneyRecordDAO.UpdateMoneyRecord(moneyRecord);
            ctx.put("message",  java.net.URLEncoder.encode("MoneyRecord��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MoneyRecord��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��MoneyRecord��Ϣ*/
    public String DeleteMoneyRecord() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            moneyRecordDAO.DeleteMoneyRecord(recordId);
            ctx.put("message",  java.net.URLEncoder.encode("MoneyRecordɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("MoneyRecordɾ��ʧ��!"));
            return "error";
        }
    }

}
