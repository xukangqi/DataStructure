package servlet;

import database.DatabaseOperation;
import com.Edge;
import com.Graph;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by Administrator on 2016/11/23.
 */
@WebServlet("/servlet/LineServlet")
public class LineServlet extends HttpServlet {
    //因为要绘制力导向图的需要，所以需要建一个servlet返回json数据
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseOperation databaseOperation=new DatabaseOperation();
        Graph graph=new Graph();
        try{//操作数据库，获取所有点和边的数据
            graph.setEdges(databaseOperation.getEdges());
            graph.setScenicSpots(databaseOperation.getScenicSpots());
        }catch (ClassNotFoundException e){

        }catch (SQLException e){

        }
        JSONArray jsonArray=new JSONArray();
        ArrayList<Edge> edges=graph.getEdges();
        for (int i=0;i<edges.size();i++){//依次读取每条边的数据，找到索引，并且加入到jsonarray中
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("source",graph.findindex(edges.get(i).getFirstnode()));
            jsonObject.put("target",graph.findindex(edges.get(i).getSecondnode()));
            jsonArray.add(jsonObject);
        }
        PrintWriter out = response.getWriter();
        out.print(jsonArray);
        out.flush();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
