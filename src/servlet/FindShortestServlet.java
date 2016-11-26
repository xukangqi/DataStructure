package servlet;

import database.DatabaseOperation;
import com.Graph;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.*;
import java.sql.SQLException;

/**
 * Created by Administrator on 2016/11/19.
 */
@WebServlet("/servlet/FindShortestServlet")
public class FindShortestServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String startnode=request.getParameter("startnode");
        String endnode=request.getParameter("endnode");
        DatabaseOperation databaseOperation=new DatabaseOperation();
        Graph graph=new Graph();
        try{//连接数据库，获取所有景点信息和边信息
            graph.setScenicSpots(databaseOperation.getScenicSpots());
            graph.setEdges(databaseOperation.getEdges());
        }catch (ClassNotFoundException e){

        }catch (SQLException e){

        }
        graph.CreatGraph();//调用方法创建邻接表和邻接矩阵，为后面要用的方法服务
        String result=graph.findMiniDistance(startnode,endnode);//迪杰斯特拉算法
//        String result=graph.Floyd(startnode,endnode);//弗洛伊德算法
        String str = "{\"message\":\""+result+"\",\"success\":\"true\"}";
        response.setContentType("text/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.write(str);//返回结果
        out.flush();

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
