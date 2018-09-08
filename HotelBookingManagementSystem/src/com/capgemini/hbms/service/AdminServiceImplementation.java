package com.capgemini.hbms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.capgemini.hbms.bean.BookingDetailsBean;
import com.capgemini.hbms.bean.HotelBean;
import com.capgemini.hbms.bean.RoomDetailsBean;
import com.capgemini.hbms.dao.AdminDaoImplementation;
import com.capgemini.hbms.dao.IAdminDao;
import com.capgemini.hbms.exception.AdminException;
import com.capgemini.hbms.exception.BookingException;
import com.capgemini.hbms.exception.ConnectionException;
import com.capgemini.hbms.exception.HotelException;
import com.capgemini.hbms.exception.RoomException;

public class AdminServiceImplementation implements IAdminService{

	IAdminDao adminDao = null;
	// Hotel Management
	@Override
	public String addHotelDetails(HotelBean hotel) throws ConnectionException, HotelException {

		adminDao = new AdminDaoImplementation();
		return adminDao.addHotelDetails(hotel);
	}
	@Override
	public String modifyHotelDetails(String modifyHotel) throws ConnectionException, HotelException {
		adminDao = new AdminDaoImplementation();
		return adminDao.modifyHotelDetails(modifyHotel);
	}
	@Override
	public String deleteHotelDetails(String hotelId) throws ConnectionException, HotelException {
		adminDao = new AdminDaoImplementation();
		return adminDao.deleteHotelDetails(hotelId);
	}
	@Override
	public ArrayList<String> displayAllHotels() throws ConnectionException, HotelException {
		adminDao = new AdminDaoImplementation();
		return adminDao.displayAllHotels();
	}
	
	//Room Management
	@Override
	public String addRoomDetails(RoomDetailsBean room) throws ConnectionException, RoomException {
		adminDao = new AdminDaoImplementation();
		return adminDao.addRoomDetails(room);
	}
	@Override
	public String modifyRoomDetails(String modifyRoom) throws ConnectionException, RoomException {
		adminDao = new AdminDaoImplementation();
		return adminDao.modifyRoomDetails(modifyRoom);
	}
	@Override
	public String deleteRoomDetails(String roomId) throws ConnectionException, RoomException {
		adminDao = new AdminDaoImplementation();
		return adminDao.deleteRoomDetails(roomId);
	}
	@Override
	public ArrayList<String> displayAllRooms() throws ConnectionException, RoomException {

		adminDao = new AdminDaoImplementation();
		return adminDao.displayAllRooms();
	}

	//Generate Reports
	@Override
	public List<HotelBean> viewHotels() throws ConnectionException, HotelException {
		adminDao = new AdminDaoImplementation();
		return adminDao.viewHotels();
	}
	@Override
	public List<BookingDetailsBean> viewBookingsOfSpecificHotel(String HotelId) throws ConnectionException, BookingException {

		adminDao = new AdminDaoImplementation();
		return adminDao.viewBookingsOfSpecificHotel(HotelId);
	}
	@Override
	public List<String> viewGuestListofSpecificHotel(String HotelId) throws ConnectionException, BookingException {
	
		adminDao = new AdminDaoImplementation();
		return adminDao.viewGuestListofSpecificHotel(HotelId);
		
	}
	@Override
	public List<BookingDetailsBean> viewBookingsforSpecifiedDate(LocalDate bookOfDate) throws ConnectionException, BookingException {
		
		adminDao = new AdminDaoImplementation();
		return adminDao.viewBookingsforSpecifiedDate(bookOfDate);
	}

	@Override
	public boolean validateFax(String fax) {

		Pattern p = Pattern.compile("[1-9]{1}[0-9]{2,11}");
		Matcher m = p.matcher(fax);
		return m.matches();
	}

	@Override
	public boolean validateRating(String rating) {

		Pattern p = Pattern.compile("[0-9]{1}[*]{1}");
		Matcher m = p.matcher(rating);
		return m.matches();
	}

	@Override
	public boolean validateAverageRatePerNight(String averageRatePerNight) {

		Pattern p = Pattern.compile("[0-9]+");
		Matcher m = p.matcher(averageRatePerNight);
		return m.matches();
	}
	@Override
	public boolean validateHotelId(String hotelId) {

		Pattern p = Pattern.compile("[H][0-9]{2}");
		Matcher m = p.matcher(hotelId);
		return m.matches();
	}
	@Override
	public boolean validateDescription(String description) {

		Pattern p = Pattern.compile("^\\d*[a-zA-Z ]{1,}\\d*");
		Matcher m = p.matcher(description);
		return m.matches();
		
	}
	@Override
	public boolean validateAddress(String address) {

		Pattern p = Pattern.compile("^\\d*[a-zA-Z ]{1,}\\d*");
		Matcher m = p.matcher(address);
		return m.matches();
		
	}
	@Override
	public boolean validateRoomId(String roomId) {

		Pattern p = Pattern.compile("[R][0-9]{2}");
		Matcher m = p.matcher(roomId);
		return m.matches();
		
	}
	@Override
	public boolean validateRoomNo(String roomNo) {

		Pattern p = Pattern.compile("[0-9]{3}");
		Matcher m = p.matcher(roomNo);
		return m.matches();
	}
	@Override
	public boolean validateRoomType(String roomType) {

		Pattern p = Pattern.compile("^\\d*[a-zA-Z ]{1,}\\d*");
		Matcher m = p.matcher(roomType);
		return m.matches();
	}
	@Override
	public boolean validateAvailability(String availability) {

		Pattern p = Pattern.compile("[0-1]{1}");
		Matcher m = p.matcher(availability);
		return m.matches();
	}
	@Override
	public boolean validateUserId(String userId) {

		Pattern p = Pattern.compile("[C][0-9]{2}");
		Matcher m = p.matcher(userId);
		return m.matches();
	}
	@Override
	public boolean validateDate(String date) {

		Pattern p = Pattern.compile("[0-9]{2}/[0-9]{2}/[0-9]{4}");
		Matcher m = p.matcher(date);
		return m.matches();
	}
	@Override
	public List<RoomDetailsBean> searchRoom(String hotelId) throws ConnectionException, BookingException, RoomException {
		
		adminDao = new AdminDaoImplementation();
		return adminDao.searchRoom(hotelId);
	}
	
}
