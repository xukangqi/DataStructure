<%@ page import="database.Upload" %>
<%@ page import="database.DatabaseOperation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ScenicSpot" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/20
  Time: 16:30
  To change this template use File | Settings | File Templates.
--%>
<%--上传边的信息--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%
    //在上传边之前需要判断该边的两个节点是否存在
    DatabaseOperation databaseOperation=new DatabaseOperation();
    ArrayList<ScenicSpot> scenicSpots=databaseOperation.getScenicSpots();
    ArrayList<String> names=new ArrayList<String>();
    for (int i=0;i<scenicSpots.size();i++){
       names.add(scenicSpots.get(i).getName());
    }
    request.setCharacterEncoding("UTF-8");
    if (names.contains(request.getParameter("firstnode"))&&names.contains(request.getParameter("secondnode"))){//判断加入的边的两个点是否存在
    Upload upload=new Upload();
    upload.addEdge(request);
    }
    response.sendRedirect("index.jsp#scenicspot");
%>
</body>
</html>
