<%@ page import="java.util.ArrayList" %>
<%@ page import="com.*" %>
<%@ page import="java.util.Stack" %>
<%@ page import="database.DatabaseOperation" %><%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/11/18
  Time: 18:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>景点游览系统</title>
    <%--引入自己写的css样式--%>
    <link href="css/main.css" rel="stylesheet">
    <%--引入bootstrap--%>
    <link href="css/bootstrap.css" rel="stylesheet">
    <%--引入jquery--%>
    <script src="javascript/jquery.js" rel="script"></script>
    <%--引入自己写的js代码，用于和后台交互--%>
    <script src="javascript/main.js" rel="script"></script>
    <%--引入d3--%>
    <script src="javascript/d3.v3.min.js" charset="utf-8"></script>
</head>
<body>
<%--导航栏信息--%>
<nav class="navbar navbar-default" role="navigation">
    <div class="container-fluid" >
        <div class="navbar-header">
            <a class="navbar-brand" href="#">景点管理系统</a>
        </div>
        <div class="collapse navbar-collapse " >
            <ul class="nav navbar-nav  " id="table">
                <li class="active"><a href="#">首页</a></li>
                <li><a href="#scenicspot">景点信息</a></li>
                <li><a href="#way-matrix">路线信息</a></li>
                <li><a href="#throughallpicture">导游路线</a></li>
                <li><a href="#findshortest">规划最短路线</a></li>
                <li><a href="#planning">道路规划</a></li>
                <li><a href="#sort">景点欢迎度</a></li>
                <li><a href="#parkinglot">停车场</a></li>
                <li><a href="#">景点详细信息</a></li>
            </ul>
            <%--搜索框，点击按钮之后调用javascript方法--%>
            <form class="navbar-form navbar-left" role="search" id="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="search" name="search" id="searchframe">
                </div>
                <button type="button" class="btn btn-info"  onclick="searchInDatabase(); return false;" >Search</button>
            </form>
        </div>
    </div>
</nav>

<%
    //获取数据库的点信息和边信息
    DatabaseOperation databaseOperation = new DatabaseOperation();
    Graph graph = new Graph();
    graph.setScenicSpots(databaseOperation.getScenicSpots());
    graph.setEdges(databaseOperation.getEdges());
    ArrayList<ScenicSpot> scenicSpots = graph.getScenicSpots();
    int number = scenicSpots.size();
%>
<%--首页图片--%>
<div class="showimage"></div>
<%--主要内容区域--%>
<div class="main">

    <%--景点信息区域，包括添加景点表单和景点图片展示和详情页链接--%>
    <div id="scenicspot" style="height: 512px">
        <span style="font-size: 30px"><center>景点信息</center></span>
        <%--因为涉及到传输文件，所以要指定enctype用于传输，并且将表单提交到指定jsp用于上传--%>
        <form role="form" action="UploadScenicSpot.jsp" method="post" enctype="multipart/form-data"
              style="width: 300px ;float: left;height: 512px;margin-top: 60px">
            <div class="form-group">
                <label>名字</label>
                <input type="text" class="form-control" placeholder="请输入景点名字" name="name" required>
            </div>
            <div class="form-group">
                <label>景点简介</label>
                <textarea rows="5" class="form-control" placeholder="请输入景点简介" name="introduce" required></textarea>
            </div>
            <div class="form-group">
                <label>景点受欢迎度</label>
                <input type="number" min="0" max="100" class="form-control" placeholder="请输入景点受欢迎度" name="popularity" required>
            </div>
            <div>
                <label >休息区</label>
                <input type="checkbox"   name="Restarea" >
                <label style="margin-left: 60px">公厕</label>
                <input type="checkbox" name="Toilet">
            </div>
            <div class="form-group">
                <label>file</label>
                <input type="file" name="picture">
                <p class="help-block">请选择你要上传的景点图片</p>
            </div>
            <input type="submit" class="btn btn-primary" value="提交"/>
        </form>
        <%--用于景点信息展示--%>
        <div class="information">
            <%
                int node;
                if(number>8){//因为页面空间有限，所以最多只能展示8个景点信息
                    node=8;
                }else {
                    node=number;
                }
                for (int i = 0; i <node; i++) {//依次获取每一个景点信息
                    String name = scenicSpots.get(i).getName();
                    String picture=scenicSpots.get(i).getPicture();
            %>
            <%--设置连接到详情页，并且提供name参数用于识别从哪个景点点击进入--%>
            <a class="info" href="detail.jsp?name=<%=name%>">
                <img src="picture/<%=picture%>" >
                <p><%=name%>
                </p>
            </a>
            <%
                }
            %>
        </div>
    </div>
    <br>
        <%--用于展示力导向图和上传边的表单--%>
    <div style="height: 400px;clear: both" >
        <%--用于展示力导向图--%>
        <div style="width: 600px;float: left" id="chart">
        </div>
        <div style="width: 300px;float: right;margin-right: 150px">
            <%--上传边的表单--%>
            <form role="form" action="UploadEdge.jsp" method="post"
                  style="width: 300px ;float: left;margin-top: 60px">
                <div class="form-group">
                    <label>起始景点</label>
                    <input type="text" class="form-control" placeholder="请输入起始景点" name="firstnode" required>
                </div>
                <div class="form-group">
                    <label>终止景点</label>
                    <input type="text" class="form-control" placeholder="请输入终止景点" name="secondnode" required>
                </div>
                <div class="form-group">
                    <label>距离</label>
                    <input type="number" min="0" class="form-control" placeholder="请输入距离" name="distance" required>
                </div>
                <input type="submit" class="btn btn-primary" value="提交" />
            </form>
        </div>
           <script>//用于获取图中的所有点和边
               var nodes=[];
               var edges=[];
               $.ajax({
                   cache: false,
                   type: "POST",
                   url: 'servlet/NodeServlet',//提交到指定servlet
                   data: ' ',
                   async: true,
                   dataType: "json",
                   error: function (msg) {
                       alert("failure");
                   },
                   success: function (msg) {
                       nodes=eval(msg);
                       $.ajax({//如果获取节点成功，继续获取所有边
                           cache: false,
                           type: "POST",
                           url: 'servlet/LineServlet',
                           data: ' ',
                           async: true,
                           dataType: "json",
                           error: function (msg) {
                               alert("failure");
                           },
                           success: function (msg) {
                               edges=eval(msg);
                               action()//调用方法创建力导向图
                           }
                       });
                   }
               });
           </script>
    </div>
        <%--展示点到点距离的矩阵--%>
    <div id="way-matrix">
        <div>
            <table class="table table-hover table-striped" >
                <caption style="font-size: 30px"><center>景点距离</center></caption>
                <tbody>
                <tr>
                    <td></td>
                    <%
                        for (int i = 0; i < number; i++) {//依次获取每一个顶点
                            ScenicSpot scenicSpot = scenicSpots.get(i);
                    %>
                    <%--表头--%>
                    <td><%=scenicSpot.getName()%></td>
                    <%
                        }
                    %>
                </tr>
                <%
                    graph.CreatGraph();
                    int[] distance = new int[scenicSpots.size()];
                    int[][] matrix = graph.getMatrix();//获取邻接矩阵
                    for (int i = 0; i < number; i++) {
                %>
                <tr>
                    <%
                        String name = scenicSpots.get(i).getName();//显示每一行的点的名字
                    %>
                    <td><%=name%>
                    </td>
                    <%
                        for (int j = 0; j < number; j++) {
                            distance[j] = matrix[i][j];//获取矩阵中的数据
                    %>
                    <td><%=distance[j]%>
                    </td>
                    <%
                        }
                    %>
                </tr>
                <%
                    }
                %>
                </tbody>
            </table>
        </div>
    </div>
    <br>
    <br>
        <%--显示深度遍历的结果和回路信息--%>
    <div id="throughallpicture">
        <table class="table">
            <caption style="font-size: 30px"><center>导游路线图</center></caption>
            <thread>
                <tr>
                    <%
                        graph.findway(0);//从起始点开始深度遍历整张图
                        ArrayList<Integer> visitedNode = graph.getRoad();
                        Stack<Integer> deleteNode=graph.findCircular();//获取在查找回路期间删掉的点的信息
                        for (int i = 0; i < visitedNode.size(); i++) {
                            String name = scenicSpots.get(visitedNode.get(i)).getName();
                            if (!deleteNode.contains(visitedNode.get(i))){//如果在删掉的节点中没发现这个点，证明在回路之中
                    %>
                    <%--更换字体颜色表示在停车场中--%>
                    <th style="color:#3d85c6"><%=name%></th>
                    <%
                             }else{
                    %>
                    <th><%=name%></th>
                    <%
                            }
                        }
                    %>
                </tr>
            </thread>
        </table>
    </div>
    <br>
    <br>
        <%--根据所选择的起始点和结束点生成最短路径--%>
    <div id="findshortest">
        <span style="font-size: 30px"><center>最短路线规划</center></span>
        <div class="form-group">
            <label style="width:600px;float:left">
                <center>
                    <bold>选择起始景点</bold>
                </center>
            </label>
            <label style="width:600px;float:right">
                <center>
                    <bold>选择结束景点</bold>
                </center>
            </label>
            <form id="findway">
                <%--显示景点列表下拉栏用于选择--%>
                <select class="form-control" name="startnode" style="width:600px;float: left">
                    <%
                        for (int i = 0; i < number; i++) {
                            String name = scenicSpots.get(i).getName();
                    %>
                    <option><%=name%></option>
                    <%
                        }
                    %>
                </select>
                <select class="form-control" name="endnode" style="width:600px;float:right">
                    <%
                        for (int i = 0; i < number; i++) {
                            String name = scenicSpots.get(i).getName();
                    %>
                    <option><%=name%>
                    </option>
                    <%
                        }
                    %>
                </select>
                    <%--提交选项之后调用方法传递数据到servlet生成结果--%>
                <button class="btn btn-primary" type="button"  onclick="findshortest(); return false;"
                        style="width: 80px;margin-left: 600px">规划</button>
            </form>
        </div>
    </div>
    <br>
    <br>
        <%--显示最小生成树的结果--%>
    <div id="planning">
        <table class="table table-hover table-striped" style="width: 1280px">
            <caption style="font-size: 30px">
                <center>景点道路规划</center>
            </caption>
            <thead>
            <tr>
                <th>开始景点</th>
                <th>结束景点</th>
                <th>距离</th>
            </tr>
            </thead>
            <tbody>
            <%
                Edge[] edges = graph.kruskal();//调用kruskal方法返回最小生成树的每条边形成的数组
//                Edge[] edges = graph.prim();//使用prim方法返回最小生成树的每条边形成的数组
                for (int i = 0; i < edges.length; i++) {
                    if (edges[i] != null) {
                        Edge edge = edges[i];
            %>
            <tr>
                <td><%=edge.getFirstnode()%>
                </td>
                <td><%=edge.getSecondnode()%>
                </td>
                <td><%=edge.getDistance()%>
                </td>
            </tr>
            <%
                    }
                }
            %>
            </tbody>
        </table>
    </div>
    <br>
    <br>
    <%--显示欢迎度排序结果--%>
    <div id="sort">
        <span style="font-size: 30px"><center>景点受欢迎度排序</center></span>
        <%
            int[] sort = graph.SortbyPopularity();
            for (int i = 0; i < number; i++) {//获取景点名字和欢迎度
                String name = scenicSpots.get(sort[i]).getName();
                int popularity = scenicSpots.get(sort[i]).getPopularity();
        %>
        <%--条形图展示--%>
        <div class="skillbar clearfix " data-percent="<%=popularity%>%">
            <div class="skillbar-title"><span><%=name%></span></div>
            <div class="skillbar-bar"></div>
            <div class="skill-bar-percent"><%=popularity%>%</div>
        </div>
        <%
            }
        %>
    </div>
    <br>
    <br>
        <%--停车场信息--%>
    <div id="parkinglot">
        <span style="font-size: 30px"><center>停车场</center></span>
        <br>
        <%--车辆到达--%>
        <form class="form-horizontal" role="form" id="arrive" style="width: 600px;float: left">
            <input type="hidden" name="type" value="arrive" />
            <div class="form-group">
                <label class="col-sm-2 control-label">车牌号</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" placeholder="请输入车牌号" name="number"  required>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">到达时间</label>
                <div class="col-sm-10">
                    <input type="time" class="form-control" placeholder="请输入到达时间" name="arrivetime" required>
                </div>
            </div>
        </form>
        <%--车辆离开--%>
        <form class="form-horizontal" role="form" id="leave" style="width: 600px;float: right">
            <input type="hidden" name="type" value="leave" />
            <div class="form-group">
                <label class="col-sm-2 control-label">车牌号</label>
                <div class="col-sm-10">
                    <input type="text" class="form-control" placeholder="请输入车牌号" name="number" required>
                </div>
            </div>
            <div class="form-group">
                <label class="col-sm-2 control-label">离开时间</label>
                <div class="col-sm-10">
                    <input type="time" class="form-control" placeholder="请输入离开时间" name="leavetime" required>
                </div>
            </div>
        </form>
        <%--提交车辆到达信息--%>
        <div style="float: left;margin-left: 200px">
            <div >
                <button type="button" class="btn btn-default" onclick="arrive();return false;">到达</button>
            </div>
        </div>
        <%--提交车辆离开信息--%>
        <div style="float: right;margin-right: 200px">
            <div >
                <button type="button" class="btn btn-default" onclick="leave();return false;">离开</button>
            </div>
        </div>
        <%--提交查询--%>
        <div style="margin-left: 600px;margin-right:400px">
            <button type="button" class="btn btn-default" onclick="query();return false;">查询当前停车场车辆</button>
        </div>
       </div>
    <br>
    <br>
    <br>
    <br>
    <%--为欢迎度排序条形图添加动作，使进度条有动画效果--%>
    <script type="text/javascript">
        $(document).ready(function () {
            $('.skillbar').each(function () {
                $(this).find('.skillbar-bar').animate({
                    width: $(this).attr('data-percent')
                }, 6000);
            });
        });
    </script>
</div>
</body>
</html>
