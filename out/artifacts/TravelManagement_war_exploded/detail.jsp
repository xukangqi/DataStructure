<%@ page import="database.DatabaseOperation" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.ScenicSpot" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/19
  Time: 18:57
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>景点详情</title>
    <link href="css/main.css" rel="stylesheet">
    <link href="css/bootstrap.css" rel="stylesheet">
</head>
<body>
<%--导航栏--%>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">景点管理系统</a>
        </div>
        <div class="collapse navbar-collapse ">
            <ul class="nav navbar-nav" id="table">
                <li><a href="index.jsp">首页</a></li>
                <li><a href="index.jsp#scenicspot">景点信息</a></li>
                <li><a href="index.jsp#way-matrix">路线信息</a></li>
                <li><a href="index.jsp#throughallpicture">导游路线</a></li>
                <li><a href="index.jsp#findshortest">规划最短路线</a></li>
                <li><a href="index.jsp#planning">道路规划</a></li>
                <li><a href="index.jsp#sort">景点欢迎度</a></li>
                <li><a href="index.jsp#parkinglot">停车场</a></li>
                <li class="active"><a href="#">景点详细信息</a></li>
            </ul>
        </div>
    </div>
</nav>

<div class="main">
    <%
        DatabaseOperation databaseOperation = new DatabaseOperation();
        ArrayList<ScenicSpot> scenicSpots = databaseOperation.getScenicSpots();
        for (int i = 0; i < scenicSpots.size(); i++) {
            ScenicSpot scenicSpot = scenicSpots.get(i);
            String name = request.getParameter("name");//获取链接传过来的景点名字
            if (scenicSpot.getName().equals(name)) {//找到那个景点

    %>
    <%--显示景点详细信息--%>
    <div style="width: 600px;float: left;margin-top: 120px">
        <p><span style="font-size: 35px"><B><%=scenicSpot.getName()%>
        </B></span></p>
        <ul>
            <li><p style="font-size: 30px"><B>景点受欢迎度 :</B></p>
                <p style="font-size: 20px;margin-left: 50px"><%=scenicSpot.getPopularity()%>%</p></li>
            <li><p style="font-size: 30px"><B>公厕 :</B></p>
                <p style="font-size: 20px;margin-left: 50px"><%=scenicSpot.getToilet()%>
                </p></li>
            <li><p style="font-size: 30px"><B>休息区 :</B></p>
                <p style="font-size: 20px;margin-left: 50px"><%=scenicSpot.getRestarea()%>
                </p></li>
            <li><p class="introduce" style="font-size: 30px"><B>景点简介:</B><br></p>
                <p class="introduce" style="font-size: 20px;margin-left: 30px"><%=scenicSpot.getIntroduce()%>
                </p></li>
        </ul>

    </div>
    <%--显示景点图片--%>
    <div style="width: 600px;float: right">
        <img src="picture/<%=scenicSpot.getPicture()%>" style="width:300px;height: 300px;margin: 150px ">
    </div>
    <%
            }
        }
    %>
</div>

</body>
</html>
