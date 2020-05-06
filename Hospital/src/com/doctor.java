package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class doctor {

	// db logic
	public static Connection getconnection() {
		Connection con = null;
		try {
			Class.forName("com.mysql.jdbc.Driver");

			// Provide the correct details: DBServer/DBName, username, password
			con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/doctormanagment", "root", "");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return con;
	}

	public String readDoctor() {
		String output = "";
		try {
			Connection con = getconnection();
			if (con == null) {
				return "Error while connecting to the database for reading.";
			}
			// Prepare the html table to be displayed
			output = "<table border=\"1\"><tr><th>DoctorID</th>" + "<th>Doctor Name</th><th>Specialization</th>"
					+ "<th>Contact</th>" + "<th>Address</th>" + "<th>Update</th>" + "<th>Remove</th></tr>";
			String query = "select * from doctor";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			// iterate through the rows in the result set
			while (rs.next()) {
				String DID = Integer.toString(rs.getInt("DID"));
				String DoctorID = rs.getString("DoctorID");
				String DoctorName = rs.getString("DoctorName");
				String Specialization = rs.getString("Specialization");
				String Contact = rs.getString("Contact");
				String Address = rs.getString("Address");
				// Add into the html table
				output += "<tr><td><input id='hidDIDUpdate'name='hidDIDUpdate'type='hidden'value='" + DID + "'>"
						+ DoctorID + "</td>";
				output += "<td>" + DoctorName + "</td>";
				output += "<td>" + Specialization + "</td>";
				output += "<td>" + Contact + "</td>";
				output += "<td>" + Address + "</td>";

				// buttons
				output += "<td><input name=\"btnUpdate\" type=\"button\"value=\"Update\" class=\"btn btn-secondary\"></td>"
						+ "<td><form method=\"post\" action=\"Doctor.jsp\">"
						+ "<input name=\"btnRemove\" type=\"submit\" value=\"Remove\"class=\"btn btn-danger' data-DID='"
						+ DID + "'>" + "</td></tr>";
			}
			con.close();
			// Complete the html table
			output += "</table>";
		} catch (Exception e) {
			output = "Error while reading the detaild.";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String insertDoctor(String DoctorID, String DoctorName, String Specialization, String Contact,
			String Address) {
		String output = "";
		try {
			Connection con = getconnection();
			if (con == null) {
				return "Error while connecting to the database";
			}
			// create a prepared statement
			String query = " insert into doctor (DID,'DoctorID','DoctorName','Specialization','Contact','Address')"
					+ " values (?,?, ?, ?, ?,?)";

			PreparedStatement preparedStmt = con.prepareStatement(query);

			// binding values
			preparedStmt.setInt(1, 0);
			preparedStmt.setString(2, DoctorID);
			preparedStmt.setString(3, DoctorName);
			preparedStmt.setString(4, Specialization);
			preparedStmt.setString(5, Contact);
			preparedStmt.setString(6, Address);

			// execute the statement
			preparedStmt.execute();
			con.close();
			String newDoctor = readDoctor();
			output = "{\"status\":\"success\", \"data\": \"" + newDoctor + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while inserting\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String updateDoctor(String DID, String DoctorID, String DoctorName, String Specialization, String Contact,
			String Address) {
		String output = "";
		try {
			Connection con = getconnection();
			if (con == null) {
				return "Error while connecting to the database for updating.";
			}
			// create a prepared statement
			String query = "UPDATE doctor SET DoctorID=?,DoctorName=?,Specialization=?,Contact=?,Address=?WHERE DID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setString(1, DoctorID);
			preparedStmt.setString(2, DoctorName);
			preparedStmt.setString(3, Specialization);
			preparedStmt.setString(4, Contact);
			preparedStmt.setString(5, Address);
			preparedStmt.setInt(6, Integer.parseInt(DID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newDoctor = readDoctor();
			output = "{\"status\":\"success\", \"data\": \"" + newDoctor + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while updating\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

	public String deleteDoctor(String DID) {
		String output = "";
		try {
			Connection con = getconnection();
			if (con == null) {
				return "Error while connecting to the database for deleting.";
			}
			// create a prepared statement
			String query = "delete from doctor where DID=?";
			PreparedStatement preparedStmt = con.prepareStatement(query);
			// binding values
			preparedStmt.setInt(1, Integer.parseInt(DID));
			// execute the statement
			preparedStmt.execute();
			con.close();
			String newDoctor = readDoctor();
			output = "{\"status\":\"success\", \"data\": \"" + newDoctor + "\"}";
		} catch (Exception e) {
			output = "{\"status\":\"error\", \"data\":\"Error while deleting\"}";
			System.err.println(e.getMessage());
		}
		return output;
	}

}
