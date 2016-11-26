package servlet;

import database.DatabaseOperation;
import com.ScenicSpot;

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
 * Created by Administrator on 2016/11/20.
 */
@WebServlet("/servlet/SearchServlet")
public class SearchServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //用于实现搜索框的查找功能
        String query=request.getParameter("search");
        DatabaseOperation databaseOperation=new DatabaseOperation();
        ArrayList<ScenicSpot> scenicSpots=new ArrayList<>();
        try{
            scenicSpots=databaseOperation.getScenicSpots();
        }catch (ClassNotFoundException e){

        }catch (SQLException e) {
        }
        int i;
        for (i=0;i<scenicSpots.size();i++){
            ScenicSpot scenicSpot=scenicSpots.get(i);
            String name=scenicSpot.getName();
            String introduce=scenicSpot.getIntroduce();
            if (name.contains(query)||introduce.contains(query)){//如果名字中有包含或者简介中有包含
                break;
            }
        }
        if (i==scenicSpots.size()){//表示没有找到匹配的
            String str = "{\"find\":\"0\"}";
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(str);
            out.flush();
        }else{//找到匹配的，返回节点名字
            String result=scenicSpots.get(i).getName();
            String str = "{\"message\":\""+result+"\",\"find\":\"1\"}";
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(str);
            out.flush();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
