<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.RoomInfo" %>
<%@ page import="com.chengxusheji.domain.RoomType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的RoomType信息
    List<RoomType> roomTypeList = (List<RoomType>)request.getAttribute("roomTypeList");
    RoomInfo roomInfo = (RoomInfo)request.getAttribute("roomInfo");

%>
<HTML><HEAD><TITLE>查看房间信息</TITLE>
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
    <td width=30%>房间编号:</td>
    <td width=70%><%=roomInfo.getRoomNumber() %></td>
  </tr>

  <tr>
    <td width=30%>房间类型:</td>
    <td width=70%>
      <%=roomInfo.getRoomTypeObj().getRoomTypeName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>价格(元/天):</td>
    <td width=70%><%=roomInfo.getRoomPrice() %></td>
  </tr>

  <tr>
    <td width=30%>所处位置:</td>
    <td width=70%><%=roomInfo.getPosition() %></td>
  </tr>

  <tr>
    <td width=30%>房间介绍:</td>
    <td width=70%><%=roomInfo.getIntroduction() %></td>
  </tr>

  <tr>
    <td width=30%>房间照片:</td>
    <td width=70%><img src="<%=basePath %><%=roomInfo.getRoomPhoto() %>" width="200px" border="0px"/></td>
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
