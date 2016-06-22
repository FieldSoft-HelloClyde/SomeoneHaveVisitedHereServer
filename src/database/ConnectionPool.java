package database;

import java.util.*;
import java.sql.*;

public class ConnectionPool {
	private ArrayList<Connection> pool;
	private int poolSize = 10;
	private static ConnectionPool instance = null;
	private String ConnectionString = "jdbc:mysql://localhost:3306/someonehavevisitedhere";
	private String UserName = "root";
	private String PWD = "12345678";
	public static ConnectionPool getInstance(){
		if (instance == null){
			instance = new ConnectionPool();
		}
		return instance;
	}
	
	private Connection CreateConnection(){
		try{
			Connection conn = DriverManager.getConnection(this.ConnectionString,this.UserName,this.PWD);
			return conn;
		} catch (Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	private ConnectionPool(){
		pool = new ArrayList<Connection>(poolSize);
		Connection conn = null;
		try {
			for (int i = 0;i < poolSize;i ++){
				Class.forName("com.mysql.jdbc.Driver");
				conn = this.CreateConnection();
				pool.add(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public synchronized Connection getConnection(){
		try {
			if (pool.size() > 0){
				Connection conn = pool.get(0);
				pool.remove(conn);
				if (conn.isValid(1000)){
					return conn;
				}
				else{
					return this.CreateConnection();
				}
			}
			else{
				return this.CreateConnection();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return this.CreateConnection();
		}
	}
	
	public synchronized void release(Connection conn){
		if (pool.size() < 10){
			pool.add(conn);
		}
		else{
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
