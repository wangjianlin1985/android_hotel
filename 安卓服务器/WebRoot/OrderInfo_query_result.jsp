<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
<%@ page import="com.chengxusheji.domain.RoomInfo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<OrderInfo> orderInfoList = (List<OrderInfo>)request.getAttribute("orderInfoList");
    //��ȡ���е�RoomInfo��Ϣ
    List<RoomInfo> roomInfoList = (List<RoomInfo>)request.getAttribute("roomInfoList");
    RoomInfo roomObj = (RoomInfo)request.getAttribute("roomInfo");

    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    UserInfo userObj = (UserInfo)request.getAttribute("userInfo");

    int currentPage =  (Integer)request.getAttribute("currentPage"); //��ǰҳ
    int totalPage =   (Integer)request.getAttribute("totalPage");  //һ������ҳ
    int  recordNumber =   (Integer)request.getAttribute("recordNumber");  //һ�����ټ�¼
    String orderNumber = (String)request.getAttribute("orderNumber"); //������Ų�ѯ�ؼ���
    String startTime = (String)request.getAttribute("startTime"); //Ԥ����ʼʱ���ѯ�ؼ���
    String endTime = (String)request.getAttribute("endTime"); //�뿪ʱ���ѯ�ؼ���
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=gb2312" />
<title>����Ԥ����ѯ</title>
<style type="text/css">
<!--
body {
    margin-left: 0px;
    margin-top: 0px;
    margin-right: 0px;
    margin-bottom: 0px;
}
.STYLE1 {font-size: 12px}
.STYLE3 {font-size: 12px; font-weight: bold; }
.STYLE4 {
    color: #03515d;
    font-size: 12px;
}
-->
</style>

 <script src="<%=basePath %>calendar.js"></script>
<script>
var  highlightcolor='#c1ebff';
//�˴�clickcolorֻ����winϵͳ��ɫ������ܳɹ�,�����#xxxxxx�Ĵ���Ͳ���,��û�����Ϊʲô:(
var  clickcolor='#51b2f6';
function  changeto(){
source=event.srcElement;
if  (source.tagName=="TR"||source.tagName=="TABLE")
return;
while(source.tagName!="TD")
source=source.parentElement;
source=source.parentElement;
cs  =  source.children;
//alert(cs.length);
if  (cs[1].style.backgroundColor!=clickcolor&&source.id!="nc")
for(i=0;i<cs.length;i++){
    cs[i].style.backgroundColor=clickcolor;
}
else
for(i=0;i<cs.length;i++){
    cs[i].style.backgroundColor="";
}
}

function  changeback(){
if  (event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="nc")
return
if  (event.toElement!=source&&cs[1].style.backgroundColor!=clickcolor)
//source.style.backgroundColor=originalcolor
for(i=0;i<cs.length;i++){
	cs[i].style.backgroundColor="";
}
}

/*��ת����ѯ�����ĳҳ*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.forms[0].currentPage.value = currentPage;
    document.forms[0].action = "<%=basePath %>/OrderInfo/OrderInfo_QueryOrderInfo.action";
    document.forms[0].submit();

}

function changepage(totalPage)
{
    var pageValue=document.bookQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('�������ҳ�볬������ҳ��!');
        return ;
    }
    document.orderInfoQueryForm.currentPage.value = pageValue;
    document.forms["orderInfoQueryForm"].action = "<%=basePath %>/OrderInfo/OrderInfo_QueryOrderInfo.action";
    document.orderInfoQueryForm.submit();
}

function QueryOrderInfo() {
	document.forms["orderInfoQueryForm"].action = "<%=basePath %>/OrderInfo/OrderInfo_QueryOrderInfo.action";
	document.forms["orderInfoQueryForm"].submit();
}

function OutputToExcel() {
	document.forms["orderInfoQueryForm"].action = "<%=basePath %>/OrderInfo/OrderInfo_QueryOrderInfoOutputToExcel.action";
	document.forms["orderInfoQueryForm"].submit(); 
}
</script>
</head>

<body>
<form action="<%=basePath %>/OrderInfo/OrderInfo_QueryOrderInfo.action" name="orderInfoQueryForm" method="post">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr>
    <td height="30" background="<%=basePath %>images/tab_05.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="30"><img src="<%=basePath %>images/tab_03.gif" width="12" height="30" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td width="46%" valign="middle"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td width="5%"><div align="center"><img src="<%=basePath %>images/tb.gif" width="16" height="16" /></div></td>
                <td width="95%" class="STYLE1"><span class="STYLE3">�㵱ǰ��λ��</span>��[����Ԥ������]-[����Ԥ����ѯ]</td>
              </tr>
            </table></td>
            <td width="54%"><table border="0" align="right" cellpadding="0" cellspacing="0">

            </table></td>
          </tr>
        </table></td>
        <td width="16"><img src="<%=basePath %>images/tab_07.gif" width="16" height="30" /></td>
      </tr>
    </table></td>
  </tr>


  <tr>
  <td>
�������:<input type=text name="orderNumber" value="<%=orderNumber %>" />&nbsp;
Ԥ���ķ��䣺<select name="roomObj.roomNumber">
 				<option value="">������</option>
 				<%
 					for(RoomInfo roomInfoTemp:roomInfoList) {
 						String selected = "";
 						if(roomObj!=null && roomInfoTemp.getRoomNumber().equals(roomObj.getRoomNumber())) selected = "selected"; 			   %>
 			   <option <%=selected %> value="<%=roomInfoTemp.getRoomNumber() %>"><%=roomInfoTemp.getRoomNumber() %></option>
 			   <%
 					}
 				%>
 			</select>
Ԥ�����û���<select name="userObj.user_name">
 				<option value="">������</option>
 				<%
 					for(UserInfo userInfoTemp:userInfoList) {
 						String selected = "";
 						if(userObj!=null && userInfoTemp.getUser_name().equals(userObj.getUser_name())) selected = "selected"; 			   %>
 			   <option <%=selected %> value="<%=userInfoTemp.getUser_name() %>"><%=userInfoTemp.getName() %></option>
 			   <%
 					}
 				%>
 			</select>
Ԥ����ʼʱ��:<input type=text name="startTime" value="<%=startTime %>" />&nbsp;
�뿪ʱ��:<input type=text name="endTime" value="<%=endTime %>" />&nbsp;
    <input type=hidden name=currentPage value="1" />
    <input type=submit value="��ѯ" onclick="QueryOrderInfo();" />
  </td>
</tr>
  <tr>
    <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="8" background="<%=basePath %>images/tab_12.gif">&nbsp;</td>
        <td><table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="b5d6e6" onmouseover="changeto()"  onmouseout="changeback()">
          <tr>
          <!-- 
            <td width="3%" height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center">
              <input type="checkbox" name="checkall" onclick="checkAll();" />
            </div></td> -->
            <td width="3%" height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">���</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">�������</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">Ԥ���ķ���</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">Ԥ�����û�</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">Ԥ����ʼʱ��</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">�뿪ʱ��</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">�������</span></div></td>
            <td  height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1">�µ�ʱ��</span></div></td>
            <td width="10%" height="22" background="<%=basePath %>images/bg.gif" bgcolor="#FFFFFF" class="STYLE1"><div align="center">��������</div></td>
          </tr>
           <%
           		/*������ʼ���*/
            	int startIndex = (currentPage -1) * 3;
            	/*������¼*/
            	for(int i=0;i<orderInfoList.size();i++) {
            		int currentIndex = startIndex + i + 1; //��ǰ��¼�����
            		OrderInfo orderInfo = orderInfoList.get(i); //��ȡ��OrderInfo����
             %>
          <tr>
            <td height="20" bgcolor="#FFFFFF"><div align="center" class="STYLE1">
              <div align="center"><%=currentIndex %></div>
            </div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getOrderNumber() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getRoomObj().getRoomNumber() %></span></div></td>
            <td bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getUserObj().getName() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getStartTime() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getEndTime() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getOrderMoney() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center"><span class="STYLE1"><%=orderInfo.getOrderAddTime() %></span></div></td>
            <td height="20" bgcolor="#FFFFFF"><div align="center">
            <span class="STYLE4">
            <span style="cursor:hand;" onclick="location.href='<%=basePath %>OrderInfo/OrderInfo_ModifyOrderInfoQuery.action?orderNumber=<%=orderInfo.getOrderNumber() %>'"><a href='#'><img src="<%=basePath %>images/edt.gif" width="16" height="16"/>�༭&nbsp; &nbsp;</a></span>
            <img src="<%=basePath %>images/del.gif" width="16" height="16"/><a href="<%=basePath  %>OrderInfo/OrderInfo_DeleteOrderInfo.action?orderNumber=<%=orderInfo.getOrderNumber() %>" onclick="return confirm('ȷ��ɾ����OrderInfo��?');">ɾ��</a></span>
            </div></td>
          </tr>
          <%	} %>
        </table></td>
        <td width="8" background="images/tab_15.gif">&nbsp;</td>
      </tr>
    </table></td>
  </tr>

  <tr>
    <td height="35" background="<%=basePath %>images/tab_19.gif"><table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td width="12" height="35"><img src="<%=basePath %>images/tab_18.gif" width="12" height="35" /></td>
        <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="STYLE4">&nbsp;&nbsp;����<%=recordNumber %>����¼����ǰ�� <%=currentPage %>/<%=totalPage %> ҳ&nbsp;&nbsp;<span style="color:red;text-decoration:underline;cursor:hand" onclick="OutputToExcel();">������ǰ��¼��excel</span></td>
            <td><table border="0" align="right" cellpadding="0" cellspacing="0">
                <tr>
                  <td width="40"><img src="<%=basePath %>images/first.gif" width="37" height="15" style="cursor:hand;" onclick="GoToPage(1,<%=totalPage %>);" /></td>
                  <td width="45"><img src="<%=basePath %>images/back.gif" width="43" height="15" style="cursor:hand;" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);"/></td>
                  <td width="45"><img src="<%=basePath %>images/next.gif" width="43" height="15" style="cursor:hand;" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);" /></td>
                  <td width="40"><img src="<%=basePath %>images/last.gif" width="37" height="15" style="cursor:hand;" onclick="GoToPage(<%=totalPage %>,<%=totalPage %>);"/></td>
                  <td width="100"><div align="center"><span class="STYLE1">ת����
                    <input name="pageValue" type="text" size="4" style="height:12px; width:20px; border:1px solid #999999;" />
                    ҳ </span></div></td>
                  <td width="40"><img src="<%=basePath %>images/go.gif" onclick="changepage(<%=totalPage %>);" width="37" height="15" /></td>
                </tr>
            </table></td>
          </tr>
        </table></td>
        <td width="16"><img src="<%=basePath %>images/tab_20.gif" width="16" height="35" /></td>
      </tr>
    </table></td>
  </tr>
</table>
  </form>
</body>
</html>
