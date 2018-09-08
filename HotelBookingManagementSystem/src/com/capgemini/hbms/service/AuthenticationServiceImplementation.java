package com.capgemini.hbms.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.hbms.bean.UserBean;
import com.capgemini.hbms.dao.AuthenticationDaoImplementation;
import com.capgemini.hbms.dao.IAuthenticationDao;
import com.capgemini.hbms.exception.AdminException;
import com.capgemini.hbms.exception.ConnectionException;
import com.capgemini.hbms.exception.CustomerException;
import com.capgemini.hbms.exception.EmployeeException;

public class AuthenticationServiceImplementation implements IAuthenticationService{

	IAuthenticationDao authenticationDao = null;
	@Override 
	public String addUser(UserBean user) throws ConnectionException,EmployeeException 
	{
		authenticationDao = new AuthenticationDaoImplementation();
		return authenticationDao.addUser(user);
	}
	
	@Override
	public String checkLoginCredentials(String uName, String password)throws ConnectionException, AdminException {
		authenticationDao = new AuthenticationDaoImplementation();
		return authenticationDao.checkLoginCredentials(uName,password);
	}

	@Override
	public boolean validateAuthenticationChoice(String choice) {

		Pattern p = Pattern.compile("[0-9]{1,}");
		Matcher m = p.matcher(choice);
		return m.matches();
	}

	@Override
	public boolean validateAuthenticationName(String userName) 
	{
		Pattern p = Pattern.compile("^[A-Za-z\\s]{1,30}$");
		Matcher m = p.matcher(userName);
		return m.matches();
	}

	@Override
	public boolean validateAuthenticationPassword(String password) {

		Pattern p = Pattern.compile("[A-Za-z@#$%^&*!0-9]{6}+");
		Matcher m = p.matcher(password);
		return m.matches();
	}

	@Override
	public boolean validateAuthenticationMobileNo(String mobileNo) {

		Pattern p = Pattern.compile("(9|8|7|6){1}[0-9]{9}$");
		Matcher m = p.matcher(mobileNo);
		return m.matches();
	}

	@Override
	public boolean validateAuthenticationPhone(String phone) {

		Pattern p = Pattern.compile("[1-9][0-9]{5}");
		Matcher m = p.matcher(phone);
		return m.matches();
	}

	@Override
	public boolean validateAuthenticationEmail(String email) {

		Pattern p = Pattern.compile("^([_a-zA-Z0-9-]+(\\.[_a-zA-Z0-9-]+)*@[a-zA-Z0-9-]+(\\.[a-zA-Z0-9-]+)*(\\.[a-zA-Z]{1,6}))?$");
		Matcher m = p.matcher(email);
		return m.matches();
	}

}
