package com.capgemini.hbms.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.capgemini.hbms.bean.UserBean;
import com.capgemini.hbms.exception.AdminException;
import com.capgemini.hbms.exception.ConnectionException;
import com.capgemini.hbms.exception.EmployeeException;
import com.capgemini.hbms.util.DbUtil;
import com.capgemini.hbms.util.IQueryMapper;

public class AuthenticationDaoImplementation implements IAuthenticationDao{

	Connection conn = null;
	PreparedStatement preparedstatement = null;
	Logger logg = Logger.getRootLogger();
	@Override
	public String addUser(UserBean user) throws ConnectionException, EmployeeException {

		int status = 0;
		String stat = "error";
		conn = DbUtil.getConnection();
		try 
		{
			if(user.getRole().equals("Employee"))
			{
				preparedstatement = conn.prepareStatement(IQueryMapper.EMPLOYEE_INSERT_QUERY);
			}
			else
			{
				preparedstatement = conn.prepareStatement(IQueryMapper.CUSTOMER_INSERT_QUERY);
			}
			preparedstatement.setString(1, user.getPassword());
			preparedstatement.setString(2, user.getRole());
			preparedstatement.setString(3, user.getUsername());
			preparedstatement.setString(4, user.getMobileNo());
			preparedstatement.setString(5, user.getPhone());
			preparedstatement.setString(6, user.getAddress());
			preparedstatement.setString(7, user.getEmail());
			status = preparedstatement.executeUpdate();
			if(status == 1)
			{
				if(user.getRole().equals("Employee"))
				{
					preparedstatement = conn.prepareStatement(IQueryMapper.EMPLOYEE_ID_SEQUENCE_QUERY);
				}
				else
				{
					preparedstatement = conn.prepareStatement(IQueryMapper.CUSTOMER_ID_SEQUENCE_QUERY);
				}
				ResultSet resultSet = preparedstatement.executeQuery();
				resultSet.next();
				String userId = resultSet.getString(1);
				preparedstatement = conn.prepareStatement(IQueryMapper.USER_ROLE_QUERY);
				preparedstatement.setString(1, userId);
				resultSet = preparedstatement.executeQuery();
				resultSet.next();
				stat = userId + "," + resultSet.getString(1);
			}
			return stat;
		} 
		catch (SQLException e) 
		{
			logg.error("Error in user registration !!!");
			throw new EmployeeException("Error in user registration !!!"+e.getMessage());
		}
	}
	
	@Override
	public String checkLoginCredentials(String uId, String password)throws ConnectionException, AdminException {
		String role = "error";
		conn = DbUtil.getConnection();
		try 
		{
			preparedstatement = conn.prepareStatement(IQueryMapper.LOGIN_CHECK);
			ResultSet resultSet = preparedstatement.executeQuery();

			while(resultSet.next())
			{
				String uid=resultSet.getString(1);
				String pswd=resultSet.getString(2);
				if(uid.equals(uId) && pswd.equals(password))
				{
					role = resultSet.getString(3);
					break;
				}
			}
			return role;
		} 
		catch (SQLException e) 
		{
			logg.error("Login Credentials are incorrect.");
			throw new AdminException("Login Credentials are incorrect. Please try again");
		}
	}
	
}
