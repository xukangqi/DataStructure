package database;

import javax.servlet.*;


import java.sql.SQLException;

public class DatabaseInitializer implements ServletContextListener {//此类已经设置为监听类，当tomcat启动时，先调用类中的方法建立数据库
	 public void contextInitialized(ServletContextEvent event) {
		 try{
			 new CreatDatabase().createDatabase();
		 }catch(ClassNotFoundException e){
			 
		 }catch (SQLException e) {
			
		}
		    
		  }
		  
		  public void contextDestroyed(ServletContextEvent event) {}
		
}
