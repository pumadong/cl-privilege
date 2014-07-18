<%@ page contentType="text/html;charset=GB2312" %>
<%@page import="com.cl.privilege.utils.SpringContextHolder"%>
<%@page import="com.cl.privilege.mapper.RoleMapper"%>
<html>
<body>
<h2>Hello World!</h2>

<%
//测试MyBatis:Success
RoleMapper r = SpringContextHolder.getBean(RoleMapper.class);
out.print(r.selectByPrimaryKey(1).getName());
%>

</body>
</html>
