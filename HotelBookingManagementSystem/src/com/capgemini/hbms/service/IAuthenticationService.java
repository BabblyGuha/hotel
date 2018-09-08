package com.capgemini.hbms.service;

import com.capgemini.hbms.bean.UserBean;
import com.capgemini.hbms.exception.AdminException;
import com.capgemini.hbms.exception.ConnectionException;
import com.capgemini.hbms.exception.EmployeeException;

public interface IAuthenticationService {

	public String addUser(UserBean user) throws ConnectionException,EmployeeException;
	public String checkLoginCredentials(String uName, String password) throws ConnectionException, AdminException;
	public boolean validateAuthenticationChoice(String choice);
	public boolean validateAuthenticationName(String userName);
	public boolean validateAuthenticationPassword(String password);
	public boolean validateAuthenticationMobileNo(String mobileNo);
	public boolean validateAuthenticationPhone(String phone);
	public boolean validateAuthenticationEmail(String email);
	
}
