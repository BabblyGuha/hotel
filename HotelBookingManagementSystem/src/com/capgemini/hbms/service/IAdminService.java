package com.capgemini.hbms.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.capgemini.hbms.bean.BookingDetailsBean;
import com.capgemini.hbms.bean.HotelBean;
import com.capgemini.hbms.bean.RoomDetailsBean;
import com.capgemini.hbms.exception.AdminException;
import com.capgemini.hbms.exception.BookingException;
import com.capgemini.hbms.exception.ConnectionException;
import com.capgemini.hbms.exception.HotelException;
import com.capgemini.hbms.exception.RoomException;

public interface IAdminService {

	public String addHotelDetails(HotelBean hotel) throws ConnectionException, HotelException;
	public String modifyHotelDetails(String modifyHotel) throws ConnectionException, HotelException;
	public String deleteHotelDetails(String hotelId) throws ConnectionException, HotelException;
	public ArrayList<String> displayAllHotels() throws ConnectionException, HotelException;

	public String addRoomDetails(RoomDetailsBean room) throws ConnectionException, HotelException, RoomException;
	public String modifyRoomDetails(String modifyRoom) throws ConnectionException, RoomException;
	public String deleteRoomDetails(String roomId) throws ConnectionException, RoomException;
	public ArrayList<String> displayAllRooms() throws ConnectionException, RoomException;
	
	public List<HotelBean> viewHotels() throws ConnectionException, HotelException;
	public List<BookingDetailsBean> viewBookingsOfSpecificHotel(String hotelId) throws ConnectionException, BookingException;
	public List<String> viewGuestListofSpecificHotel(String hotelId) throws ConnectionException, BookingException;
	public List<BookingDetailsBean> viewBookingsforSpecifiedDate(LocalDate bookOfDate) throws ConnectionException, BookingException;
	
	public boolean validateHotelId(String hotelId);
	public boolean validateFax(String fax);
	public boolean validateRating(String rating);
	public boolean validateAverageRatePerNight(String averageRatePerNight);
	public boolean validateDescription(String description);
	public boolean validateAddress(String address);
	public boolean validateRoomId(String roomId);
	public boolean validateRoomNo(String roomNo);
	public boolean validateRoomType(String roomType);
	public boolean validateAvailability(String availability);
	public boolean validateUserId(String userId);
	public boolean validateDate(String bookTo);
	public List<RoomDetailsBean> searchRoom(String hotelId) throws ConnectionException, BookingException, RoomException;

}
