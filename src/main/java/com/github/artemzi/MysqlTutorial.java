package com.github.artemzi;

import java.sql.*;

public class MysqlTutorial {
    static Connection connection = null;
    static PreparedStatement preparedStatement = null;

	public static void main(String[] argv) {

		try {
			log("-------- Simple Tutorial on how to make JDBC connection to MySQL DB locally ------------");
			makeJDBCConnection();

			log("\n---------- Adding to DB ----------");
			addDataToDB("Example, LLC.", "NYC, US", 5, "https://example.com");
			addDataToDB("Google Inc.", "Mountain View, CA, US", 50000, "https://google.com");
			addDataToDB("Apple Inc.", "Cupertino, CA, US", 30000, "http://apple.com");

			log("\n---------- Let's get Data from DB ----------");
			getDataFromDB();

			preparedStatement.close();
			connection.close(); // connection close

		} catch (SQLException e) {

			e.printStackTrace();
		}
	}

	private static void makeJDBCConnection() {

		try {
			Class.forName("com.mysql.jdbc.Driver");
			log("Congrats - Seems your MySQL JDBC Driver Registered!");
		} catch (ClassNotFoundException e) {
			log("Sorry, couldn't found JDBC driver. Make sure you have added JDBC Maven Dependency Correctly");
			e.printStackTrace();
			return;
		}

		try {
			// DriverManager: The basic service for managing a set of JDBC drivers.
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/jdbc_tutorial", "root", "secret");
			if (connection != null) {
				log("Connection Successful! Enjoy. Now it's time to push data");
			} else {
				log("Failed to make connection!");
			}
		} catch (SQLException e) {
			log("MySQL Connection Failed!");
			e.printStackTrace();
			return;
		}

	}

	private static void addDataToDB(String companyName, String address, int totalEmployee, String webSite) {

		try {
			String insertQueryStatement = "INSERT  INTO  Employee  VALUES  (?,?,?,?)";

			preparedStatement = connection.prepareStatement(insertQueryStatement);
			preparedStatement.setString(1, companyName);
			preparedStatement.setString(2, address);
			preparedStatement.setInt(3, totalEmployee);
			preparedStatement.setString(4, webSite);

			// execute insert SQL statement
			preparedStatement.executeUpdate();
			log(companyName + " added successfully");
		} catch (

		SQLException e) {
			e.printStackTrace();
		}
	}

	private static void getDataFromDB() {

		try {
			// MySQL Select Query Tutorial
			String getQueryStatement = "SELECT * FROM Employee";

			preparedStatement = connection.prepareStatement(getQueryStatement);

			// Execute the Query, and get a java ResultSet
			ResultSet rs = preparedStatement.executeQuery();

			// Let's iterate through the java ResultSet
			while (rs.next()) {
				String name = rs.getString("Name");
				String address = rs.getString("Address");
				int employeeCount = rs.getInt("EmployeeCount");
				String website = rs.getString("Website");

				// Simply Print the results
				System.out.format("%s, %s, %s, %s\n", name, address, employeeCount, website);
			}

		} catch (

		SQLException e) {
			e.printStackTrace();
		}

	}

	private static void log(String string) {
		System.out.println(string);
	}
}
