package com.capgemini.hbms.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.capgemini.hbms.exception.ConnectionException;

public class DbUtil {

	private static Connection connection = null;

	public static Connection getConnection() throws ConnectionException{
		try 
		{
			connection = DriverManager.getConnection(IQueryMapper.URL,
					IQueryMapper.USERNAME, IQueryMapper.PASSWORD);	
		} 
		catch (SQLException e) 
		{
			System.err.println("Error in connection");
			throw new ConnectionException("Error connecting db: " + e.getMessage());
		}
		return connection;
	}
}
