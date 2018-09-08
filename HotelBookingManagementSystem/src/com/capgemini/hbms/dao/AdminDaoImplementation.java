package com.capgemini.hbms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.hbms.bean.BookingDetailsBean;
import com.capgemini.hbms.bean.HotelBean;
import com.capgemini.hbms.bean.RoomDetailsBean;
import com.capgemini.hbms.exception.AdminException;
import com.capgemini.hbms.exception.BookingException;
import com.capgemini.hbms.exception.ConnectionException;
import com.capgemini.hbms.exception.HotelException;
import com.capgemini.hbms.exception.RoomException;
import com.capgemini.hbms.util.DbUtil;
import com.capgemini.hbms.util.IQueryMapper;

public class AdminDaoImplementation implements IAdminDao{

	Connection conn = null;
	PreparedStatement preparedStatement = null;
	HotelBean hotel= null;
	List<HotelBean> hotelList = null;
	Logger logg = Logger.getRootLogger();
	
	public String addHotelDetails(HotelBean hotel) throws ConnectionException, HotelException
	{
		int status = 0;
		conn = DbUtil.getConnection();
		String stat = "error";
		try 
		{
			preparedStatement = conn.prepareStatement(IQueryMapper.HOTEL_INSERT_QUERY);
			preparedStatement.setString(1, hotel.getCity());
			preparedStatement.setString(2, hotel.getHotelName());
			preparedStatement.setString(3, hotel.getAddress());
			preparedStatement.setString(4, hotel.getDescription());
			preparedStatement.setDouble(5, hotel.getAvgRatePerNight());
			preparedStatement.setString(6, hotel.getPhone_no1());
			preparedStatement.setString(7, hotel.getPhone_no2());
			preparedStatement.setString(8, hotel.getRating());
			preparedStatement.setString(9, hotel.getEmail());
			preparedStatement.setString(10, hotel.getFax());
			
			status = preparedStatement.executeUpdate();
			if(status == 1)
			{
				preparedStatement = conn.prepareStatement(IQueryMapper.HOTEL_ID_SEQUENCE_QUERY);
				ResultSet resultSet = preparedStatement.executeQuery();
				resultSet.next();
				String hotelId = resultSet.getString(1);
				stat = hotelId;
			}
			return stat;
		} 
		catch (SQLException e) 
		{
			logg.error("Error in adding new hotel details !!! " + e.getMessage());
			throw new HotelException("Error in adding new hotel details !!! " + e.getMessage());
		}
	}

	@Override
	public String modifyHotelDetails(String modifyHotel) throws ConnectionException, HotelException {

		int status = 0;
		String stat = "error";
		conn = DbUtil.getConnection();
		try 
		{
			preparedStatement = conn.prepareStatement(modifyHotel);
			
			status = preparedStatement.executeUpdate();
			if(status == 1)
			{
				stat = "Success";
			}
			return stat;
		} 
		catch (SQLException e) 
		{
			logg.error("Error in modifying new hotel details !!! " + e.getMessage());
			throw new HotelException("Error in modifying new hotel details !!! " + e.getMessage());
		}
	}

	//Deleting Hotel details
	@Override
	public String deleteHotelDetails(String hotelId) throws ConnectionException, HotelException {
		conn = DbUtil.getConnection();
		int status = 0;
		String stat = "error";
		try 
		{
			preparedStatement = conn.prepareStatement(IQueryMapper.DELETE_HOTEL_DETAILS);
			preparedStatement.setString(1,hotelId);
			status = preparedStatement.executeUpdate();
			if(status == 1)
			{
				stat = hotelId;
			}
			return stat;
		} 
		catch (SQLException e) 
		{
			logg.error("Error in deleting new hotel details !!! " + e.getMessage());
			throw new HotelException("Error in deleting new hotel details !!! " + e.getMessage());
		}
	}
	@Override
	public ArrayList<String> displayAllHotels() throws ConnectionException, HotelException {

		conn = DbUtil.getConnection();
		ArrayList<String> hotelList = null;
		try 
		{
			preparedStatement = conn.prepareStatement(IQueryMapper.VIEW_ALL_HOTELS);
			ResultSet resultSet = preparedStatement.executeQuery();
			
			hotelList = new ArrayList<String>();
			while(resultSet.next())
			{
				String hotelId = resultSet.getString(1);
				String city = resultSet.getString(2);
				String hotelName = resultSet.getString(3);
				String address = resultSet.getString(4);
				String description = resultSet.getString(5);
				double avgRatePerNight = resultSet.getDouble(6);
				String phoneNo1 = resultSet.getString(7);
				String phoneNo2 = resultSet.getString(8);
				String rating = resultSet.getString(9);
				String email = resultSet.getString(10);
				
				String hotel = "| "+hotelId+" | "+city+" | "+hotelName+" | "+address+" | "+description+" | "+avgRatePerNight+" | "+phoneNo1+" | "+phoneNo2+" | "+rating+" | "+email;
				hotelList.add(hotel);
			}
			return hotelList;
		}
		catch (SQLException e) 
		{
			logg.error("Error in displaying all hotel details !!! " + e.getMessage());
			throw new HotelException("Error in displaying all hotel details !!! " + e.getMessage());
		}
	}
	//Adding Room Details
	@Override
	public String addRoomDetails(RoomDetailsBean room) throws ConnectionException, RoomException {
		int status = 0;
		conn = DbUtil.getConnection();
		String stat = "error";
		try 
		{
			preparedStatement = conn.prepareStatement(IQueryMapper.ROOM_INSERT_QUERY);
			preparedStatement.setString(1, room.getHotelId());
			preparedStatement.setString(2, room.getRoomNo());
			preparedStatement.setString(3, room.getRoomType());
			preparedStatement.setDouble(4, room.getPerNightRate());
			preparedStatement.setBoolean(5, room.getAvailability());
			
			status = preparedStatement.executeUpdate();
			if(status == 1)
			{
				preparedStatement = conn.prepareStatement(IQueryMapper.ROOM_ID_SEQUENCE_QUERY);
				ResultSet resultSet = preparedStatement.executeQuery();
				resultSet.next();
				String roomId = resultSet.getString(1);
				stat = roomId;
			}
			return stat;
		} 
		catch (SQLException e) 
		{
			logg.error("Error in adding new room details !!! ");
			throw new RoomException("Error in adding new room details !!! " + e.getMessage());
		}
	}
	
	@Override
	public String modifyRoomDetails(String modifyRoom) throws ConnectionException, RoomException {
		
		int status = 0;
		conn = DbUtil.getConnection();
		String stat = "error";
		try 
		{
			System.out.println(modifyRoom);
			preparedStatement = conn.prepareStatement(modifyRoom);
			
			status = preparedStatement.executeUpdate();
			if(status == 1)
			{
				stat = "Success";
			}
			return stat;
		} 
		catch (SQLException e) 
		{
			logg.error("Error in modifying new room details !!! ");
			throw new RoomException("Error in modifying new room details !!! " + e.getMessage());
		}
	}

	//Deleting Room Details
	@Override
	public String deleteRoomDetails(String roomId) throws ConnectionException, RoomException {
		int status = 0;
		conn = DbUtil.getConnection();
		String stat = "error";
		try 
		{
			System.out.println(roomId);
			preparedStatement = conn.prepareStatement(IQueryMapper.DELETE_ROOM_DETAILS);
			preparedStatement.setString(1,roomId);
			
			status = preparedStatement.executeUpdate();
			if(status == 1)
			{
				stat = roomId;
			}
			return stat;
		} 
		catch (SQLException e) 
		{
			logg.error("Error in deleting new room details !!! ");
			throw new RoomException("Error in deleting new room details !!! " + e.getMessage());
		}
	}
	
	@Override
	public ArrayList<String> displayAllRooms() throws ConnectionException, RoomException {

		ArrayList<String> roomList = new ArrayList<String>();
		conn = DbUtil.getConnection();
		try 
		{
			preparedStatement = conn.prepareStatement(IQueryMapper.VIEW_ALL_ROOMS);
			ResultSet resultSet = preparedStatement.executeQuery();

			while(resultSet.next())
			{
				String hotelId = resultSet.getString(1);
				String roomId = resultSet.getString(2);
				String roomNumber = resultSet.getString(3);
				String roomType = resultSet.getString(4);
				double perNightRate = resultSet.getDouble(5);
				int availability = resultSet.getInt(6);
				
				String room = hotelId+" | "+roomId+" | "+roomNumber+" | "+roomType+" | "+perNightRate+" | "+availability;
				roomList.add(room);
			}
			return roomList;
		} 
		catch (SQLException e) 
		{
			logg.error("Error in displaying all rooms !!! ");
			throw new RoomException("Error in displaying all for rooms !!! " + e.getMessage());
		}
	}
	
	//Generate Reports
	//Displaying List of Hotels
		@Override
		public List<HotelBean> viewHotels() throws ConnectionException, HotelException {
			conn = DbUtil.getConnection();
			hotelList = new ArrayList<HotelBean>();
			
			try 
			{
				preparedStatement = conn.prepareStatement(IQueryMapper.VIEW_ALL_HOTELS);
				ResultSet resultSet = preparedStatement.executeQuery();

				while(resultSet.next())
				{
					hotel = new HotelBean();
					hotel.setHotelId(resultSet.getString(1));
					hotel.setCity(resultSet.getString(2));
					hotel.setHotelName(resultSet.getString(3));
					hotel.setAddress(resultSet.getString(4));
					hotel.setDescription(resultSet.getString(5));
					hotel.setAvgRatePerNight(resultSet.getDouble(6));
					hotel.setPhone_no1(resultSet.getString(7));
					hotel.setPhone_no2(resultSet.getString(8));
					hotel.setRating(resultSet.getString(9));
					hotel.setEmail(resultSet.getString(10));
					hotel.setFax(resultSet.getString(11));
					
					hotelList.add(hotel);
				}
				return hotelList;

			} 
			catch (SQLException e) 
			{
				logg.error("Error in viewing new hotel details !!! " + e.getMessage());
				e.printStackTrace();
				//throw new HotelException("Error in viewing hotel details !!! " + e.getMessage());
			}
			return hotelList;
		}
		@Override
		public List<BookingDetailsBean> viewBookingsOfSpecificHotel(String hotelId) throws ConnectionException, BookingException {

			conn = DbUtil.getConnection();
			ArrayList<BookingDetailsBean> bookingList = null;
			BookingDetailsBean booking = null;
			try 
			{
				boolean stat = hotelExistence(hotelId);
				if(stat == true)
				{
					preparedStatement = conn.prepareStatement(IQueryMapper.VIEW_BOOKINGS_OF_SPECIFIC_HOTEL);
					preparedStatement.setString(1, hotelId);
					ResultSet resultSet = preparedStatement.executeQuery();
					bookingList = new ArrayList<BookingDetailsBean>();
					while(resultSet.next())
					{
						booking = new BookingDetailsBean();
						booking.setBookingId(resultSet.getString(1));
						booking.setRoomId(resultSet.getString(2));
						booking.setUserId(resultSet.getString(3));
						booking.setBookedFrom(resultSet.getDate(4).toLocalDate());
						booking.setBookedTo(resultSet.getDate(5).toLocalDate());
						booking.setNoOfAdults(resultSet.getInt(6));
						booking.setNoOfChildren(resultSet.getInt(7));
						booking.setAmount(resultSet.getDouble(8));
						
						bookingList.add(booking);
					}
				}
				return bookingList;
			}
			catch (SQLException e) 
			{
				logg.error("Error viewing the bookings of specific hotel !!!");
				throw new BookingException("Error in viewing the bookings of specific hotel !!!" + e.getMessage());
			}
		}
		@Override
		public List<String> viewGuestListofSpecificHotel(String hotelId) throws ConnectionException, BookingException {

			conn = DbUtil.getConnection();
			try 
			{
				List<String> guestList = new ArrayList<String>();
				boolean stat = hotelExistence(hotelId);
				if(stat == true)
				{
					preparedStatement = conn.prepareStatement(IQueryMapper.VIEW_GUESTS_OF_SPECIFIC_HOTEL);
					preparedStatement.setString(1, hotelId);
					ResultSet resultSet = preparedStatement.executeQuery();
					while(resultSet.next())
					{	
						String bookingId = resultSet.getString(1);
						String roomId = resultSet.getString(2);
						String userId = resultSet.getString(3);
						String userName = resultSet.getString(4);
						String password = resultSet.getString(5);
						String mobile = resultSet.getString(6);
						String phoneNumber = resultSet.getString(7);
						String address = resultSet.getString(8);
						String email = resultSet.getString(9);	
						LocalDate fromDate = stringToLocalDate(resultSet.getString(10));
						LocalDate toDate = stringToLocalDate(resultSet.getString(11));
						int noOfAdults = resultSet.getInt(12);
						int noOfChildren = resultSet.getInt(13);
						double amount = resultSet.getDouble(14);
						String guest = bookingId+" | "+roomId+" | "+userId+" | "+userName+" | "+password+" | "+mobile+" | "+phoneNumber+" | "+address+" | "+email+" | "+fromDate+" | "+toDate+" | "+noOfAdults+" | "+noOfChildren+" | "+amount;
						guestList.add(guest);
					}
				}
				return guestList;
			}
			catch (SQLException e) 
			{
				logg.error("Error viewing the guest list of specific hotel !!!");
				e.printStackTrace();
				//throw new BookingException("Error in viewing the guest list of specific hotel !!!" + e.getMessage());
			}
			return null;
		}
		@Override
		public List<BookingDetailsBean> viewBookingsforSpecifiedDate(LocalDate bookOfDate) throws ConnectionException, BookingException {
			
			conn = DbUtil.getConnection();
			ArrayList<BookingDetailsBean> bookingList = null;
			BookingDetailsBean booking = null;
			try 
			{
				preparedStatement = conn.prepareStatement(IQueryMapper.VIEW_BOOKINGS_OF_SPECIFIC_DATE);
				preparedStatement.setDate(1, Date.valueOf(bookOfDate));
				preparedStatement.setDate(2, Date.valueOf(bookOfDate));
				
				ResultSet resultSet = preparedStatement.executeQuery();
				bookingList = new ArrayList<BookingDetailsBean>();
				while(resultSet.next())
				{
					booking = new BookingDetailsBean();
					booking.setBookingId(resultSet.getString(1));
					booking.setRoomId(resultSet.getString(2));
					booking.setUserId(resultSet.getString(3));
					booking.setBookedFrom(stringToLocalDate(resultSet.getString(4)));
					booking.setBookedTo(stringToLocalDate(resultSet.getString(5)));
					booking.setNoOfAdults(resultSet.getInt(6));
					booking.setNoOfChildren(resultSet.getInt(7));
					booking.setAmount(resultSet.getDouble(8));
					
					bookingList.add(booking);
				}
			}
			catch (SQLException e) 
			{
				logg.error("Error viewing the bookings for specified date !!!");
				throw new BookingException("Error in viewing the bookings for specified date !!!" + e.getMessage());
			}
			return bookingList;
			
		}
	 
		private LocalDate stringToLocalDate(String date)
		{
			String tday[] = date.split("/");
			int tYear = Integer.parseInt(tday[2]);
			int tMonth = Integer.parseInt(tday[1]);
			int tDay = Integer.parseInt(tday[0]);
			return LocalDate.of(tYear,tMonth,tDay);
		}
		
		private boolean hotelExistence(String hotelId) throws BookingException, ConnectionException
		{
			conn = DbUtil.getConnection();
			try 
			{
				preparedStatement = conn.prepareStatement(IQueryMapper.FIND_HOTEL);
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
				{
					String id = resultSet.getString(1);
					if(id.equals(hotelId))
					{
						return true;
					}
				}
			}
			catch (SQLException e) 
			{
				logg.error("Error viewing the guest list of specific hotel !!!");
				e.printStackTrace();
				//throw new BookingException("Error in viewing the guest list of specific hotel !!!" + e.getMessage());
			}
			return false;
		}

		@Override
		public List<RoomDetailsBean> searchRoom(String hotelId) throws ConnectionException, BookingException, RoomException {

			conn = DbUtil.getConnection();
			List<RoomDetailsBean> roomList = new ArrayList<RoomDetailsBean>();
			RoomDetailsBean room = null;
			try 
			{
				boolean stat = hotelExistence(hotelId);
				if(stat == true)
				{
					preparedStatement = conn.prepareStatement(IQueryMapper.SEARCH_ALL_ROOMS);
					preparedStatement.setString(1, hotelId);
					ResultSet resultSet = preparedStatement.executeQuery();
					boolean record = resultSet.isBeforeFirst();
					if(record == true)
					{
						while(resultSet.next())
						{
							room = new RoomDetailsBean();
							room.setHotelId(resultSet.getString(1));
							room.setRoomId(resultSet.getString(2));
							room.setRoomNo(resultSet.getString(3));
							room.setRoomType(resultSet.getString(4));
							room.setPerNightRate(resultSet.getDouble(5));
							room.setAvailability(resultSet.getBoolean(6));
							
							roomList.add(room);
						}
					}
					
				}
				
				return roomList;
			}
			catch (SQLException e) 
			{
				logg.error("Error in searching for rooms !!! ");
				throw new RoomException("Error in searching for rooms !!! " + e.getMessage());
			}
		}
		
		
}
