<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.OrderInfo" %>
<%@ page import="com.chengxusheji.domain.RoomInfo" %>
<%@ page import="com.chengxusheji.domain.UserInfo" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的RoomInfo信息
    List<RoomInfo> roomInfoList = (List<RoomInfo>)request.getAttribute("roomInfoList");
    //获取所有的UserInfo信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    OrderInfo orderInfo = (OrderInfo)request.getAttribute("orderInfo");

%>
<HTML><HEAD><TITLE>查看房间预订</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>订单编号:</td>
    <td width=70%><%=orderInfo.getOrderNumber() %></td>
  </tr>

  <tr>
    <td width=30%>预订的房间:</td>
    <td width=70%>
      <%=orderInfo.getRoomObj().getRoomNumber() %>
    </td>
  </tr>

  <tr>
    <td width=30%>预订的用户:</td>
    <td width=70%>
      <%=orderInfo.getUserObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>预订开始时间:</td>
    <td width=70%><%=orderInfo.getStartTime() %></td>
  </tr>

  <tr>
    <td width=30%>离开时间:</td>
    <td width=70%><%=orderInfo.getEndTime() %></td>
  </tr>

  <tr>
    <td width=30%>订单金额:</td>
    <td width=70%><%=orderInfo.getOrderMoney() %></td>
  </tr>

  <tr>
    <td width=30%>附加信息:</td>
    <td width=70%><%=orderInfo.getOrderMemo() %></td>
  </tr>

  <tr>
    <td width=30%>下单时间:</td>
    <td width=70%><%=orderInfo.getOrderAddTime() %></td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="返回" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
