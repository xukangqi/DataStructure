<%@ page import="database.Upload" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/19
  Time: 11:24
  To change this template use File | Settings | File Templates.
--%>
<%--上传景点信息--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Result</title>
</head>
<body>
     <%
         Upload upload=new Upload();
         upload.addScenicSpot(request);
         response.sendRedirect("index.jsp#scenicspot");
     %>
</body>
</html>
