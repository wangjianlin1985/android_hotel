<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.RoomType" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�RoomType��Ϣ
    List<RoomType> roomTypeList = (List<RoomType>)request.getAttribute("roomTypeList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>��ӷ�����Ϣ</TITLE> 
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
    var roomNumber = document.getElementById("roomInfo.roomNumber").value;
    if(roomNumber=="") {
        alert('�����뷿����!');
        return false;
    }
    var position = document.getElementById("roomInfo.position").value;
    if(position=="") {
        alert('����������λ��!');
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
    <TD align="left" vAlign=top >
    <s:form action="RoomInfo/RoomInfo_AddRoomInfo.action" method="post" id="roomInfoAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>������:</td>
    <td width=70%><input id="roomInfo.roomNumber" name="roomInfo.roomNumber" type="text" /></td>
  </tr>

  <tr>
    <td width=30%>��������:</td>
    <td width=70%>
      <select name="roomInfo.roomTypeObj.roomTypeId">
      <%
        for(RoomType roomType:roomTypeList) {
      %>
          <option value='<%=roomType.getRoomTypeId() %>'><%=roomType.getRoomTypeName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�۸�(Ԫ/��):</td>
    <td width=70%><input id="roomInfo.roomPrice" name="roomInfo.roomPrice" type="text" size="8" /></td>
  </tr>

  <tr>
    <td width=30%>����λ��:</td>
    <td width=70%><input id="roomInfo.position" name="roomInfo.position" type="text" size="30" /></td>
  </tr>

  <tr>
    <td width=30%>�������:</td>
    <td width=70%><textarea id="roomInfo.introduction" name="roomInfo.introduction" rows="5" cols="50"></textarea></td>
  </tr>

  <tr>
    <td width=30%>������Ƭ:</td>
    <td width=70%><input id="roomPhotoFile" name="roomPhotoFile" type="file" size="50" /></td>
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
