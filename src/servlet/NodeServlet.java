package servlet;

import database.DatabaseOperation;
import com.ScenicSpot;
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
@WebServlet("/servlet/NodeServlet")
public class NodeServlet extends HttpServlet {
    //因为要绘制力导向图的需要，所以需要建一个servlet返回json数据
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DatabaseOperation databaseOperation=new DatabaseOperation();
        try {
            ArrayList<ScenicSpot> scenicSpots=databaseOperation.getScenicSpots();
            JSONArray jsonArray=new JSONArray();
            for (int i=0;i<scenicSpots.size();i++){//获取每一个节点的名字，加入到jsonarray中
                JSONObject jsonObject=new JSONObject();
                jsonObject.put("name",scenicSpots.get(i).getName());
                jsonArray.add(i,jsonObject);
            }
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.print(jsonArray);//返回数据
            out.flush();
        }catch (ClassNotFoundException e){

        }catch (SQLException e){

        }



    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
