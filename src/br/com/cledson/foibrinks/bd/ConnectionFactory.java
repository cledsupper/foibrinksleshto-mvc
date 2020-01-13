package br.com.cledson.foibrinks.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
	public static Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("org.mariadb.jdbc.Driver");
			String HOSTNAME = "localhost";
			String DATABASE = "foibrinks_leshto";
			String USERNAME = "root";
			String PASSWORD = "";
			conn = DriverManager.getConnection("jdbc:mariadb://" + HOSTNAME +
					"/" + DATABASE, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conn;
	}
}
