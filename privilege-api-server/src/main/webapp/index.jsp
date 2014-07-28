<%@ page contentType="text/html;charset=GB2312" %>
<%@page import="com.cl.privilege.utils.SpringContextHolder"%>
<%@page import="com.cl.privilege.api.IPrivilegeBaseApiService"%>
<%@page import="com.cl.privilege.api.impl.PrivilegeBaseApiServiceImpl"%>

<html>
<body>

<%
//测试MyBatis:Success
IPrivilegeBaseApiService us = SpringContextHolder.getBean(IPrivilegeBaseApiService.class);
out.print(us.getModuleTree(0,"p",""));
%>

</body>
</html>