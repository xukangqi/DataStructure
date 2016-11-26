package servlet;

import com.Car;
import com.Parkinglot;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/11/20.
 */
@WebServlet("/servlet/CarManage")
public class CarManage extends HttpServlet {
    Parkinglot parkinglot;
    public void init() throws ServletException {
        super.init();
        parkinglot=new Parkinglot(3);//初始化停车场，并且设置停车场大小为3
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String type=request.getParameter("type");//解析请求的类型
        if (type.equals("arrive")){//当为车辆到达请求时
            //获取相关信息，车牌号，到达时间
            String number=request.getParameter("number");
            String arrivetime=request.getParameter("arrivetime");
            int hour=Integer.parseInt(arrivetime.substring(0,2));
            int minute=Integer.parseInt(arrivetime.substring(3));
            String result=parkinglot.arrive(new Car(number,hour,minute));//调用方法，获取车辆到达后相关信息
            String str = "{\"message\":\""+result+"\",\"success\":\"true\"}";
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(str);//返回json数据
            out.flush();
        }else if (type.equals("leave")){//当有车辆离开
            //获取离开车辆的车牌号，离开时间
            String number=request.getParameter("number");
            String leavetime=request.getParameter("leavetime");
            int hour=Integer.parseInt(leavetime.substring(0,2));
            int minute=Integer.parseInt(leavetime.substring(3));
            String result=parkinglot.leave(number,hour,minute);
            String str = "{\"message\":\""+result+"\",\"success\":\"true\"}";
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(str);//返回json数据
            out.flush();
        }else{//当需要列出当前所有在停车场中的车辆信息
            String result=parkinglot.list();
            String str = "{\"message\":\""+result+"\",\"success\":\"true\"}";
            response.setContentType("text/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter out = response.getWriter();
            out.write(str);
            out.flush();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
