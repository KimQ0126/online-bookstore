<%@ page contentType="text/html; charset=gb2312" %>
<%@ page session="true" %>
<jsp:useBean id="login" scope="page" class="bookshop.run.login" />
<%
String mesg = "";

if( request.getParameter("username")!=null && !request.getParameter("username").equals("")){
	String username =request.getParameter("username");
	String passwd = request.getParameter("passwd");
	username = new String(username.getBytes("ISO8859-1"));
	passwd = new String(passwd.getBytes("ISO8859-1"));
	login.setUsername(username);
	login.setPasswd(passwd);
	out.print(username+passwd);
	if (login.excute()){
		session.setAttribute("username",username);
		String userid = Long.toString(login.getUserid());
		session.setAttribute("userid",userid);
		response.sendRedirect("booklist.jsp");
		%>
<%	
	}else {
	mesg = "��¼����"	;
	}
}
%>
<%@include file="/bookshop/inc/head.inc"%>

<script language="javascript">

 function checkform() {
	if (document.form1.username.value=="" || document.form1.passwd.value==""){
		alert("�û���������Ϊ�գ�");
		return false;
	}
	return true;

  }

</script>
<div align=center>�û���¼ </div>
          <br>          
<% if (!mesg.equals("")){
						out.println("<p>" + mesg + "</p>");}%>
						
  <form name="form1" method="post" action="login.jsp">
    <table width="400" border="0" cellspacing="1" cellpadding="1" align="center">
    <tr> 
      <td width="147" align="right">�û�����<br>
      </td>
      <td width="246" valign="top">
          <input type="text" name="username" size="16" maxlength="25">
        </td>
    </tr>
    <tr> 
      <td width="147" align="right">��&nbsp;&nbsp;&nbsp;&nbsp;�룺</td>
      <td width="246" valign="top">
          <input type="password" name="passwd" maxlength="20" size="16">
        </td>
    </tr>
    <tr> 
      <td width="147" align="right">&nbsp;</td>
      <td width="246" valign="top">
          <input type="submit" name="Submit" value="��¼" onclick="javascript:return(checkform());">
          <a>&nbsp;&nbsp;&nbsp;</a>
          <input type="reset" name="Submit2" value="ȡ��">
        </td>
    </tr>
    <tr>     
      <td colspan="2" align="center">
       
       </td>
    </tr>
    <tr> 
    
      <td colspan="2" align="center">
        <p>&nbsp;</p>
        <p>����㻹���Ǳ�վ�û������ڴ�<a href="reg.jsp">ע��</a></p>
      </td>
    </tr>
  </table>
    </form>
 <%@include file="/bookshop/inc/tail.inc"%>