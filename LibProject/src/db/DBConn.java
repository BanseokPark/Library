package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConn {

	public static void initConnect(){ // 어디에서든 호출할 수 있도록 static
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			System.out.println("[DB] Driver Loading Success!");
		} catch (ClassNotFoundException e) {e.printStackTrace();}
	}


	public static Connection getConnection() throws SQLException{ // 어디에서든 호출할 수 있도록 static
		Connection conn = null;
//		conn = DriverManager.getConnection("jdbc:oracle:thin:@192.168.10.20:1521:xe", "hr", "hr");
		conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "hr", "hr");
		System.out.println("[DB] Connection Success!");
		return conn;

	}

	public static void close(ResultSet rs, Statement stmt, Connection conn) throws SQLException{ // 어디에서든 호출할 수 있도록 static
		if(rs != null) rs.close();
		if(stmt != null) stmt.close();
		if(conn != null) conn.close();
	}

}
