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
    //获取所有的RoomInfo信息
    List<RoomInfo> roomInfoList = (List<RoomInfo>)request.getAttribute("roomInfoList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改房间预订</TITLE>
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
/*验证表单*/
function checkForm() {
    var orderNumber = document.getElementById("orderInfo.orderNumber").value;
    if(orderNumber=="") {
        alert('请输入订单编号!');
        return false;
    }
    var startTime = document.getElementById("orderInfo.startTime").value;
    if(startTime=="") {
        alert('请输入预订开始时间!');
        return false;
    }
    var endTime = document.getElementById("orderInfo.endTime").value;
    if(endTime=="") {
        alert('请输入离开时间!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="OrderInfo/OrderInfo_ModifyOrderInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>订单编号:</td>
    <td width=70%><input id="orderInfo.orderNumber" name="orderInfo.orderNumber" type="text" value="<%=orderInfo.getOrderNumber() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>预订的房间:</td>
    <td width=70%>
      <select name="orderInfo.roomObj.roomNumber">
      <%
        for(RoomInfo roomInfo:roomInfoList) {
          String selected = "";
          if(roomInfo.getRoomNumber().equals(orderInfo.getRoomObj().getRoomNumber()))
            selected = "selected";
      %>
          <option value='<%=roomInfo.getRoomNumber() %>' <%=selected %>><%=roomInfo.getRoomNumber() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>预订的用户:</td>
    <td width=70%>
      <select name="orderInfo.userObj.user_name">
      <%
        for(UserInfo userInfo:userInfoList) {
          String selected = "";
          if(userInfo.getUser_name().equals(orderInfo.getUserObj().getUser_name()))
            selected = "selected";
      %>
          <option value='<%=userInfo.getUser_name() %>' <%=selected %>><%=userInfo.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>预订开始时间:</td>
    <td width=70%><input id="orderInfo.startTime" name="orderInfo.startTime" type="text" size="20" value='<%=orderInfo.getStartTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>离开时间:</td>
    <td width=70%><input id="orderInfo.endTime" name="orderInfo.endTime" type="text" size="20" value='<%=orderInfo.getEndTime() %>'/></td>
  </tr>

  <tr>
    <td width=30%>订单金额:</td>
    <td width=70%><input id="orderInfo.orderMoney" name="orderInfo.orderMoney" type="text" size="8" value='<%=orderInfo.getOrderMoney() %>'/></td>
  </tr>

  <tr>
    <td width=30%>附加信息:</td>
    <td width=70%><input id="orderInfo.orderMemo" name="orderInfo.orderMemo" type="text" size="20" value='<%=orderInfo.getOrderMemo() %>'/></td>
  </tr>

  <tr>
    <td width=30%>下单时间:</td>
    <td width=70%><input id="orderInfo.orderAddTime" name="orderInfo.orderAddTime" type="text" size="30" value='<%=orderInfo.getOrderAddTime() %>'/></td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
