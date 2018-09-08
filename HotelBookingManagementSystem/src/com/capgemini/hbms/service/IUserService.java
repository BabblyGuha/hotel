package com.capgemini.hbms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.hbms.bean.BookingDetailsBean;
import com.capgemini.hbms.bean.HotelBean;
import com.capgemini.hbms.bean.RoomDetailsBean;
import com.capgemini.hbms.exception.BookingException;
import com.capgemini.hbms.exception.ConnectionException;
import com.capgemini.hbms.exception.RoomException;
import com.capgemini.hbms.exception.UserException;

public interface IUserService {

	public ArrayList<HotelBean> searchHotels(String city) throws ConnectionException, RoomException, BookingException;
	public List<RoomDetailsBean> searchRooms(String hotelId) throws ConnectionException, RoomException, BookingException;
	public String addBookingDetails(BookingDetailsBean bookingDetails) throws ConnectionException, BookingException;
	public String viewBookingStatus(String roleId, String bookingId) throws ConnectionException, UserException;
	public boolean validateBookingId(String bookingId);
	public List<String> viewAllBookingStatus(String bookingId) throws UserException, ConnectionException;
	public boolean validateDate(String bookFrom);
	public boolean validateNoOfAdults(String adults);
	public boolean validateNoOfChildren(String childrens);
	public boolean validateUserId(String userId);
	public boolean roomExistence(String roomId) throws ConnectionException, BookingException;
	public boolean userExistence(String roomId) throws ConnectionException, BookingException;
	public String dateCheckOut(LocalDate bookingFrom, LocalDate bookingTo);
	public boolean roomAvailability(String roomId, LocalDate bookingFrom,LocalDate bookingTo) throws ConnectionException, BookingException;
	public String dateCheckIn(LocalDate bookingFrom);
	public List<String> displayCities() throws UserException, ConnectionException;
	
	
}
