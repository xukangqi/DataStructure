package database;

import com.Edge;
import com.ScenicSpot;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;


public class DatabaseOperation {
	private String driver = "org.apache.derby.jdbc.EmbeddedDriver";
	private String protocol = "jdbc:derby:";
	private String dbName = "TravelSystem";

	public ArrayList<ScenicSpot> getScenicSpots() throws SQLException, ClassNotFoundException {//获取数据库中的所有景点信息
		Class.forName(driver);
		String dbUrl = protocol + dbName;
		Connection connection = DriverManager.getConnection(dbUrl);
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM ScenicSpot");//查询数据库
		ResultSet resultSet = statement.executeQuery(query);
		ArrayList<ScenicSpot> scenicSpots = new ArrayList<>();
		ScenicSpot scenicSpot;
		while (resultSet.next()) {//依次读取查询结果的每一行
			scenicSpot = new ScenicSpot();
			scenicSpot.setName(resultSet.getString("name"));
			scenicSpot.setIntroduce(resultSet.getString("introduce"));
			scenicSpot.setPopularity(resultSet.getInt("popularity"));
			scenicSpot.setPicture(resultSet.getString("picture"));
			scenicSpot.setToilet(resultSet.getString("Toilet"));
			scenicSpot.setRestarea(resultSet.getString("Restarea"));
			scenicSpots.add(scenicSpot);
		}
		statement.close();
		connection.close();//关闭数据库连接
		return scenicSpots;
	}

	public ArrayList<Edge> getEdges() throws SQLException, ClassNotFoundException {//获取数据库中的每一条边
		Class.forName(driver);
		String dbUrl = protocol + dbName;
		Connection connection = DriverManager.getConnection(dbUrl);
		Statement statement = connection.createStatement();
		String query = String.format("SELECT * FROM Edge");
		ResultSet resultSet = statement.executeQuery(query);//获取查询结果
		ArrayList<Edge> edges = new ArrayList<>();
		Edge edge;
		while (resultSet.next()) {//依次读取查询结果的每一行
			edge = new Edge();
			edge.setFirstnode(resultSet.getString("firstnode"));
			edge.setSecondnode(resultSet.getString("secondnode"));
			edge.setDistance(resultSet.getInt("distance"));
			edges.add(edge);
		}
		statement.close();
		connection.close();
		return edges;
	}

	public void createScenicSpot(ScenicSpot a) throws SQLException, ClassNotFoundException {//将新上传的景点添加到数据库
		ArrayList<ScenicSpot> scenicSpots=getScenicSpots();
		ArrayList<String> names=new ArrayList<>();
		for (int i=0;i<scenicSpots.size();i++){
			names.add(scenicSpots.get(i).getName());
		}
		if (names.contains(a.getName())){//如果数据库中已经包含该景点，不做任何操作

		}else {//如果没有该景点，则添加
			Class.forName(driver);
			String dbUrl = protocol + dbName;
			Connection connection;
			connection = DriverManager.getConnection(dbUrl);//建立连接
			Statement statement = connection.createStatement();
			String template = String.format("INSERT INTO ScenicSpot VALUES(?, ?, ?, ?, ?, ?)");
			PreparedStatement inserter = connection.prepareStatement(template);
			inserter.setString(1, a.getName());
			inserter.setString(2, a.getIntroduce());
			inserter.setInt(3, a.getPopularity());
			inserter.setString(4,a.getPicture());
			inserter.setString(5,a.getToilet());
			inserter.setString(6,a.getRestarea());
			inserter.executeUpdate();
			inserter.close();
			connection.close();
		}

	}

	public void createEdge(Edge t) throws SQLException, ClassNotFoundException {//将新上传的边加入到数据库中
		boolean repeat=false;//用于表示边是否已经存在
		ArrayList<Edge> edges=getEdges();
		for (int i=0;i<edges.size();i++){//依次去寻找是否有重复的边
			Edge edge=edges.get(i);
			if (edge.getFirstnode().equals(t.getFirstnode())&&edge.getSecondnode().equals(t.getSecondnode())){
                 repeat=true;
				 break;
			}else if (edge.getSecondnode().equals(t.getFirstnode())&&edge.getFirstnode().equals(t.getSecondnode())){
				repeat=true;
				break;
			}
		}
       if (!repeat){//如果没有找到与新加入的边重复的边
		   Class.forName(driver);
		   String dbUrl = protocol + dbName;
		   Connection connection;
		   connection = DriverManager.getConnection(dbUrl);
		   Statement statement = connection.createStatement();
		   String template = String.format("INSERT INTO Edge VALUES(?, ?, ?)");
		   PreparedStatement inserter = connection.prepareStatement(template);
		   inserter.setString(1, t.getFirstnode());
		   inserter.setString(2, t.getSecondnode());
		   inserter.setInt(3, t.getDistance());
		   inserter.executeUpdate();
		   inserter.close();
		   connection.close();
	   }
	}

}
