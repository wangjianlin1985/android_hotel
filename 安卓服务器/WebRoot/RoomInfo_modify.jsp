<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.RoomInfo" %>
<%@ page import="com.chengxusheji.domain.RoomType" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的RoomType信息
    List<RoomType> roomTypeList = (List<RoomType>)request.getAttribute("roomTypeList");
    RoomInfo roomInfo = (RoomInfo)request.getAttribute("roomInfo");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改房间信息</TITLE>
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
    var roomNumber = document.getElementById("roomInfo.roomNumber").value;
    if(roomNumber=="") {
        alert('请输入房间编号!');
        return false;
    }
    var position = document.getElementById("roomInfo.position").value;
    if(position=="") {
        alert('请输入所处位置!');
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
    <TD align="left" vAlign=top ><s:form action="RoomInfo/RoomInfo_ModifyRoomInfo.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>房间编号:</td>
    <td width=70%><input id="roomInfo.roomNumber" name="roomInfo.roomNumber" type="text" value="<%=roomInfo.getRoomNumber() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>房间类型:</td>
    <td width=70%>
      <select name="roomInfo.roomTypeObj.roomTypeId">
      <%
        for(RoomType roomType:roomTypeList) {
          String selected = "";
          if(roomType.getRoomTypeId() == roomInfo.getRoomTypeObj().getRoomTypeId())
            selected = "selected";
      %>
          <option value='<%=roomType.getRoomTypeId() %>' <%=selected %>><%=roomType.getRoomTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>价格(元/天):</td>
    <td width=70%><input id="roomInfo.roomPrice" name="roomInfo.roomPrice" type="text" size="8" value='<%=roomInfo.getRoomPrice() %>'/></td>
  </tr>

  <tr>
    <td width=30%>所处位置:</td>
    <td width=70%><input id="roomInfo.position" name="roomInfo.position" type="text" size="30" value='<%=roomInfo.getPosition() %>'/></td>
  </tr>

  <tr>
    <td width=30%>房间介绍:</td>
    <td width=70%><textarea id="roomInfo.introduction" name="roomInfo.introduction" rows=5 cols=50><%=roomInfo.getIntroduction() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>房间照片:</td>
    <td width=70%><img src="<%=basePath %><%=roomInfo.getRoomPhoto() %>" width="200px" border="0px"/><br/>
    <input type=hidden name="roomInfo.roomPhoto" value="<%=roomInfo.getRoomPhoto() %>" />
    <input id="roomPhotoFile" name="roomPhotoFile" type="file" size="50" /></td>
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
