package database;
import com.Edge;
import com.ScenicSpot;
import database.DatabaseOperation;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpServletRequest;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Administrator on 2016/11/19.
 */
public class Upload {//用于上传新添加的景点和边
    private ScenicSpot scenicSpot;
    private Edge edge;
    private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
    private String protocol = "jdbc:derby:";
    private String dbName = "TravelSystem";

    public Upload(){
    }
    public void addScenicSpot(HttpServletRequest request) throws Exception{//添加景点
        //因为需要景点图片的上传，所以使用里common-fileupload.jar的方法
        scenicSpot = new ScenicSpot();
        int maxFileSize = 5000 * 1024 * 1024;
        String contentType = request.getContentType();
        if ((contentType.indexOf("multipart/form-data") >= 0)) {//判断是否以指定格式传入
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setSizeMax(maxFileSize);
            List fileItems = upload.parseRequest(request);
            Iterator i = fileItems.iterator();

            while (i.hasNext()) {
                FileItem fi = (FileItem) i.next();
                String fieldName = fi.getFieldName();
                String fileName = fi.getName();
                String value = fi.getString("UTF-8");

                switch (fieldName) {//依次读取上传的每一项数据
                    case "name":
                        scenicSpot.setName(value);
                        break;
                    case "introduce":
                        scenicSpot.setIntroduce(value);
                        break;
                    case "popularity":
                        scenicSpot.setPopularity(Integer.parseInt(value));
                        break;
                    case "Restarea":
                        if (value.equals("on")){
                            scenicSpot.setRestarea("有");
                        }else {
                            scenicSpot.setRestarea("无");
                        }
                        break;
                    case "Toilet":
                        if (value.equals("on")){
                            scenicSpot.setToilet("有");
                        }else {
                            scenicSpot.setToilet("无");
                        }
                        break;
                    case "picture":
                        scenicSpot.setPicture(fileName);
                        createPicture(fi, "picture/" + scenicSpot.getPicture());
                        break;
                }
            }
            //将新上传的景点利用方法写入数据库
            DatabaseOperation writer = new DatabaseOperation();
            writer.createScenicSpot(scenicSpot);
        }
    }
    public void addEdge(HttpServletRequest request) throws Exception {//读取上传的边
        //因为不存在文件的上传，所以没必要采用和上传一样的方法
            edge = new Edge();
             if (request.getCharacterEncoding() == null) {
                 request.setCharacterEncoding("UTF-8");//设置编码格式，增加对中文的支持
            }
            edge.setFirstnode(request.getParameter("firstnode"));
            edge.setSecondnode(request.getParameter("secondnode"));
            edge.setDistance(Integer.parseInt(request.getParameter("distance")));
            DatabaseOperation writer = new DatabaseOperation();
            writer.createEdge(edge);

    }
    private void createPicture(FileItem fi, String path) {//在指定的地方创建图片
        String classpath = DatabaseOperation.class.getResource("/").getPath();
        String filepath= classpath.substring(0,classpath.lastIndexOf("/WEB"));//因为项目部署在tomcat中，无法采用绝对地址
        //所以采用根据当前类的地址去获取图片文件夹地址的方法
        File file = new File(filepath+"/"+path);
        try {
            fi.write(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
