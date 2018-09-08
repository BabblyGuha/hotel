package com.capgemini.hbms.dao;

import com.capgemini.hbms.bean.UserBean;
import com.capgemini.hbms.exception.AdminException;
import com.capgemini.hbms.exception.ConnectionException;
import com.capgemini.hbms.exception.EmployeeException;

public interface IAuthenticationDao {

	public String addUser(UserBean user) throws ConnectionException,EmployeeException;
	
	public String checkLoginCredentials(String uName, String password) throws ConnectionException, AdminException;

}
