package test;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashSet;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;

import th.ac.ku.kps.eng.cpe.soa.dao.CustomerDAO;
import th.ac.ku.kps.eng.cpe.soa.model.Customer;
import th.ac.ku.kps.eng.cpe.soa.model.Phonenumber;

public class Main {
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
	private static final String DB_CONNECTION = "jdbc:mysql://localhost:3306/soa";
	private static final String DB_USER = "µ”¡∑’Ë° ”Àπ¥";
	private static final String DB_PASSWORD = "µ”¡∑’Ë° ”Àπ¥";
	static Connection dbConnection = null;
	static Statement statement = null;

	public static void main(String[] args) throws SQLException {
// TODO Auto-generated method stub
		
	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = (Connection) DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}
}
