<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Evaluate" %>
<%@ page import="com.chengxusheji.domain.RoomInfo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�RoomInfo��Ϣ
    List<RoomInfo> roomInfoList = (List<RoomInfo>)request.getAttribute("roomInfoList");
    //��ȡ���е�UserInfo��Ϣ
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    Evaluate evaluate = (Evaluate)request.getAttribute("evaluate");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸�������Ϣ</TITLE>
<STYLE type=text/css>
BODY {
	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*��֤��*/
function checkForm() {
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="Evaluate/Evaluate_ModifyEvaluate.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><input id="evaluate.evaluateId" name="evaluate.evaluateId" type="text" value="<%=evaluate.getEvaluateId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>���۵ķ���:</td>
    <td width=70%>
      <select name="evaluate.roomObj.roomNumber">
      <%
        for(RoomInfo roomInfo:roomInfoList) {
          String selected = "";
          if(roomInfo.getRoomNumber().equals(evaluate.getRoomObj().getRoomNumber()))
            selected = "selected";
      %>
          <option value='<%=roomInfo.getRoomNumber() %>' <%=selected %>><%=roomInfo.getRoomNumber() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>���۵��û�:</td>
    <td width=70%>
      <select name="evaluate.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(evaluate.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>����(5����):</td>
    <td width=70%><input id="evaluate.evalueScore" name="evaluate.evalueScore" type="text" size="8" value='<%=evaluate.getEvalueScore() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%><textarea id="evaluate.evaluateContent" name="evaluate.evaluateContent" rows=5 cols=50><%=evaluate.getEvaluateContent() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>����ʱ��:</td>
    <td width=70%><input id="evaluate.evaluateTime" name="evaluate.evaluateTime" type="text" size="20" value='<%=evaluate.getEvaluateTime() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
