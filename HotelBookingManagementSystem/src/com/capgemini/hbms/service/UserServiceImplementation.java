package com.capgemini.hbms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.hbms.bean.BookingDetailsBean;
import com.capgemini.hbms.bean.HotelBean;
import com.capgemini.hbms.bean.RoomDetailsBean;
import com.capgemini.hbms.dao.IUserDao;
import com.capgemini.hbms.dao.UserDaoImplementation;
import com.capgemini.hbms.exception.BookingException;
import com.capgemini.hbms.exception.ConnectionException;
import com.capgemini.hbms.exception.RoomException;
import com.capgemini.hbms.exception.UserException;

public class UserServiceImplementation implements IUserService{

	IUserDao userDao = null;
	
	@Override
	public ArrayList<HotelBean> searchHotels(String city) throws ConnectionException, RoomException, BookingException {
		userDao = new UserDaoImplementation();
		return userDao.searchHotels(city);
	}
	
	@Override
	public List<RoomDetailsBean> searchRooms(String hotelId) throws ConnectionException, RoomException, BookingException
	{
		userDao = new UserDaoImplementation();
		return userDao.searchRooms(hotelId);
	}
	@Override
	public String addBookingDetails(BookingDetailsBean bookingDetails) throws ConnectionException, BookingException {

		userDao = new UserDaoImplementation();
		return userDao.addBookingDetails(bookingDetails);
	}

	@Override
	public String viewBookingStatus(String roleId,String bookingId) throws ConnectionException, UserException {

		userDao = new UserDaoImplementation();
		return userDao.viewBookingStatus(roleId,bookingId);
		
	}

	@Override
	public boolean validateBookingId(String bookingId) {

		Pattern p = Pattern.compile("[B][0-9]{2}");
		Matcher m = p.matcher(bookingId);
		return m.matches();
	}

	@Override
	public List<String> viewAllBookingStatus(String bookingId) throws UserException, ConnectionException {

		userDao = new UserDaoImplementation();
		return userDao.viewAllBookingStatus(bookingId);
	}

	@Override
	public boolean validateDate(String date) {

		Pattern p = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{4}");
		Matcher m = p.matcher(date);
		return m.matches();
	}

	@Override
	public boolean validateNoOfAdults(String adults) {

		Pattern p = Pattern.compile("[0-9]{1,2}");
		Matcher m = p.matcher(adults);
		return m.matches();
	}

	@Override
	public boolean validateNoOfChildren(String childrens) {

		Pattern p = Pattern.compile("[0-9]{1,3}");
		Matcher m = p.matcher(childrens);
		return m.matches();	
		}

	@Override
	public boolean validateUserId(String userId) {

		Pattern p = Pattern.compile("[C][0-9]{2}");
		Matcher m = p.matcher(userId);
		return m.matches();
	}

	@Override
	public boolean roomExistence(String roomId) throws ConnectionException, BookingException {

		userDao = new UserDaoImplementation();
		return userDao.roomExistence(roomId);
	}

	@Override
	public boolean userExistence(String userId) throws ConnectionException, BookingException {

		userDao = new UserDaoImplementation();
		return userDao.userExistence(userId);
	}

	@Override
	public String dateCheckOut(LocalDate bookingFrom, LocalDate bookingTo) {

		userDao = new UserDaoImplementation();
		return userDao.dateCheckOut(bookingFrom,bookingTo);
	}

	@Override
	public boolean roomAvailability(String roomId, LocalDate bookingFrom,LocalDate bookingTo) throws ConnectionException, BookingException {

		userDao = new UserDaoImplementation();
		return userDao.roomAvailability(roomId,bookingFrom,bookingTo);
	}

	@Override
	public String dateCheckIn(LocalDate bookingFrom) {

		userDao = new UserDaoImplementation();
		return userDao.dateCheckIn(bookingFrom);
	}

	@Override
	public List<String> displayCities() throws UserException, ConnectionException {

		userDao = new UserDaoImplementation();
		return userDao.displayCities();
	}

}
