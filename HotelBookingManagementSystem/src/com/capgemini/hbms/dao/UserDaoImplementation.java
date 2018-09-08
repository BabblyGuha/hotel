package com.capgemini.hbms.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.hbms.bean.BookingDetailsBean;
import com.capgemini.hbms.bean.HotelBean;
import com.capgemini.hbms.bean.RoomDetailsBean;
import com.capgemini.hbms.exception.BookingException;
import com.capgemini.hbms.exception.ConnectionException;
import com.capgemini.hbms.exception.RoomException;
import com.capgemini.hbms.exception.UserException;
import com.capgemini.hbms.util.DbUtil;
import com.capgemini.hbms.util.IQueryMapper;

public class UserDaoImplementation implements IUserDao{

	Connection conn = null;
	PreparedStatement preparedStatement = null;
	HotelBean hotel= null;
	List<HotelBean> hotelList = null;
	Logger logg = Logger.getRootLogger();
	UserDaoImplementation userDao = null;
	@Override
	public String addBookingDetails(BookingDetailsBean bookingDetails) throws ConnectionException, BookingException {

		userDao = new UserDaoImplementation();
		int status = 0;
		double amount = 0;
		LocalDate bookFrom;
		LocalDate bookTo;
		conn = DbUtil.getConnection();
		boolean roomAvailability, roomExistence, userExistence;
		String dateCheck;
		try 
		{
			bookFrom = bookingDetails.getBookedFrom();
			bookTo = bookingDetails.getBookedTo();
			LocalDate today = userDao.todayDate();
			
			Period intervalPeriod = Period.between(bookFrom, bookTo);
			int daysOfStay = intervalPeriod.getDays();
			
			preparedStatement = conn.prepareStatement(IQueryMapper.AMOUNT_FIND_QUERY);
			preparedStatement.setString(1, bookingDetails.getRoomId());
			ResultSet amountResultSet = preparedStatement.executeQuery();
			amountResultSet.next();
			amount = amountResultSet.getDouble(1);
			amount = amount * daysOfStay;
			
			preparedStatement = conn.prepareStatement(IQueryMapper.BOOKING_INSERT_QUERY);
			preparedStatement.setString(1, bookingDetails.getRoomId());
			preparedStatement.setString(2, bookingDetails.getUserId());
			preparedStatement.setDate(3, Date.valueOf(bookFrom));
			preparedStatement.setDate(4, Date.valueOf(bookTo));
			preparedStatement.setInt(5, bookingDetails.getNoOfAdults());
			preparedStatement.setInt(6, bookingDetails.getNoOfChildren());
			preparedStatement.setDouble(7, amount);
			
			status = preparedStatement.executeUpdate();
			if(status == 1)
			{
				updateRoomAvailability();
				if((today.equals(bookFrom)) ||(today.isAfter(bookFrom) && today.isBefore(bookTo)) || (today.equals(bookTo)))
				{
					preparedStatement = conn.prepareStatement(IQueryMapper.UPDATE_ROOMS_AVAILABILITY_FALSE);
					preparedStatement.setString(1, bookingDetails.getRoomId());
					preparedStatement.executeUpdate();
				}
				else if(today.isBefore(bookFrom) || today.isAfter(bookTo))
				{
					preparedStatement = conn.prepareStatement(IQueryMapper.UPDATE_ROOMS_AVAILABILITY_TRUE);
					preparedStatement.setString(1, bookingDetails.getRoomId());
					preparedStatement.executeUpdate();
				}
				preparedStatement = conn.prepareStatement(IQueryMapper.BOOKING_ID_SEQUENCE_QUERY);
				ResultSet resultSet = preparedStatement.executeQuery();
				resultSet.next();
				String bookingId = resultSet.getString(1);
				return bookingId;
			}	
		} 
		catch (SQLException e) 
		{
			logg.error("Error in adding booking details !!!");
			throw new BookingException("Error in adding booking details !!!" + e.getMessage());
		}
		return "error";	
		
	}
	
	private void updateRoomAvailability() {

		
		
	}

	@Override
	public ArrayList<HotelBean> searchHotels(String city) throws ConnectionException, RoomException, BookingException {
		
		conn = DbUtil.getConnection();
		ArrayList<HotelBean> hotelList = null;
		HotelBean hotel = null;
		try 
		{
			boolean stat = cityExistence(city);
			hotelList = new ArrayList<HotelBean>();
			if(stat == true)
			{
				preparedStatement = conn.prepareStatement(IQueryMapper.SEARCH_HOTELS);
				preparedStatement.setString(1, city);
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
			}
			
			return hotelList;
		}
		catch (SQLException e) 
		{
			logg.error("Error in searching for hotels !!! " + e.getMessage());
			throw new RoomException("Error in searching for hotels !!! " + e.getMessage());
		}
	}
	
	// To Search for Hotel rooms based on city
		public List<RoomDetailsBean> searchRooms(String hotelId) throws ConnectionException, RoomException, BookingException
		{
			conn = DbUtil.getConnection();
			List<RoomDetailsBean> roomList = new ArrayList<RoomDetailsBean>();
			RoomDetailsBean room = null;
			try 
			{
				boolean stat = hotelExistence(hotelId);
				if(stat == true)
				{
					preparedStatement = conn.prepareStatement(IQueryMapper.SEARCH_ROOMS);
					preparedStatement.setString(1, hotelId);
					ResultSet resultSet = preparedStatement.executeQuery();
					
					roomList = new ArrayList<RoomDetailsBean>();
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
				
				return roomList;
			}
			catch (SQLException e) 
			{
				logg.error("Error in searching for rooms !!! ");
				throw new RoomException("Error in searching for rooms !!! " + e.getMessage());
			}
		}
		
		public boolean userExistence(String userId) throws ConnectionException, BookingException {

			conn = DbUtil.getConnection();
			userDao = new UserDaoImplementation();
			try 
			{
				preparedStatement = conn.prepareStatement(IQueryMapper.USER_FIND_QUERY);
				preparedStatement.setString(1, userId);
				ResultSet userResultSet = preparedStatement.executeQuery();
				
				if(userResultSet.next())
				{
					return true;
				}
			}
			catch (SQLException e) 
			{
				logg.error("Error in checking whether user exists or not !!!");
				throw new BookingException("Error in checking whether user exists or not !!!" + e.getMessage());
			}
			return false;
		}
		public boolean roomExistence(String roomId) throws ConnectionException, BookingException 
		{
			userDao = new UserDaoImplementation();
			conn = DbUtil.getConnection();
			try 
			{
				preparedStatement = conn.prepareStatement(IQueryMapper.ROOM_EXISTENCE_QUERY);
				preparedStatement.setString(1, roomId);
				ResultSet roomResultSet = preparedStatement.executeQuery();
				if(roomResultSet.next())
				{
					return true;
				}
			}
			catch (SQLException e) 
			{
				logg.error("Error in checking whether room exists or not !!!");
				throw new BookingException("Error in checking whether room exists or not !!!");
			}
			return false;
		}
		public boolean roomAvailability(String roomId, LocalDate bookFrom, LocalDate bookTo) throws ConnectionException, BookingException {
			
			conn = DbUtil.getConnection();
			userDao = new UserDaoImplementation();
			try 
			{
				/*preparedStatement = conn.prepareStatement(IQueryMapper.ROOM_AVAILABILITY_QUERY);
				preparedStatement.setString(1, roomId);
				ResultSet roomResultSet = preparedStatement.executeQuery();
				roomResultSet.next();
				if(roomResultSet.getInt(1) == 1)
				{*/
					preparedStatement = conn.prepareStatement(IQueryMapper.DATE_CLASH_QUERY);
					ResultSet dateResultSet = preparedStatement.executeQuery();
					while(dateResultSet.next())
					{
						String id = dateResultSet.getString(1);
						LocalDate from = stringToLocalDate(dateResultSet.getString(2));
						LocalDate to = stringToLocalDate(dateResultSet.getString(3));
						
						if(roomId.equals(id))
						{
							
							if((bookFrom.equals(from) || bookFrom.isAfter(from)) && (bookTo.equals(to) || bookTo.isBefore(to)))
							{
								return false;
							}
						}
					}
					return true;
				
			}
			catch (SQLException e) 
			{
				logg.error("Error in checking whether room is available or not !!!");
				throw new BookingException("Error in checking whether room is available or not !!!" + e.getMessage());
			}
		}
		
		private LocalDate todayDate()
		{
			LocalDate today = LocalDate.now();
			
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/YYYY");
			String date = formatter.format(today);
			userDao = new UserDaoImplementation();
			return userDao.stringToLocalDate(date);
		}
		@Override
		public String dateCheckOut(LocalDate bookFrom, LocalDate bookTo) {

			userDao = new UserDaoImplementation();
			LocalDate today = userDao.todayDate();
			if(bookTo.isBefore(bookFrom))
			{
				return "check date error";
			}
			else if(bookTo.isBefore(today) || bookTo.equals(today))
			{
				return "check out date error";
			}
			return "Correct Date";
		}
		
		private LocalDate stringToLocalDate(String date)
		{
			String tday[] = date.split("/");
			int tYear = Integer.parseInt(tday[2]);
			int tMonth = Integer.parseInt(tday[1]);
			int tDay = Integer.parseInt(tday[0]);
			return LocalDate.of(tYear,tMonth,tDay);
		}

		@Override
		public String viewBookingStatus(String roleId,String bookingId) throws ConnectionException, UserException {

			conn = DbUtil.getConnection();
			String bookingStatus;
			try 
			{
				boolean stat = bookingIdExist(bookingId);
				if(stat == true)
				{
					preparedStatement = conn.prepareStatement(IQueryMapper.VIEW_BOOKING_STATUS);
					preparedStatement.setString(1, bookingId);
					preparedStatement.setString(2, roleId);
					ResultSet resultSet = preparedStatement.executeQuery();
					if(resultSet.isBeforeFirst())
					{
						resultSet.next();
						
						String roomId = resultSet.getString(2);
						String userId = resultSet.getString(3);
						String bookedFrom = resultSet.getString(4);
						String bookedTo = resultSet.getString(5);
						int noOfAdults = resultSet.getInt(6);
						int  noOfChildren = resultSet.getInt(7);
						double amount = resultSet.getDouble(8);
						
						bookingStatus = bookingId+" | "+roomId+" | "+userId+" | "+bookedFrom+" | "+bookedTo+" | "+noOfAdults+" | "+noOfChildren+" | "+amount+" | "+"Confirmed";
						return bookingStatus;	
					}
					else
					{
						return "mismatch";
					}
				}
				else
				{
					logg.error("Booking ID requested for, does not exist !!!");
					System.err.println("Booking ID requested for, does not exist !!! ");
					return "error";
				}
			}
			catch (SQLException e) 
			{
				logg.error("Error viewing the booking status !!!");
				e.printStackTrace();
				//throw new UserException("Error in viewing the booking status !!!");
			}
			return null;
		}
		
		public boolean bookingIdExist(String bookingId) throws UserException, ConnectionException
		{
			conn = DbUtil.getConnection();
			try
			{
				preparedStatement = conn.prepareStatement(IQueryMapper.FIND_BOOKING_ID);
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
				{
					String id = resultSet.getString(1);
					if(id.equals(bookingId))
					{
						return true;
					}
				}
				return false;
			}
			catch (SQLException e) 
			{
				logg.error("Error viewing the booking status !!!");
				throw new UserException("Error in viewing the booking status !!!");
			}
		}
		private boolean cityExistence(String city) throws BookingException, ConnectionException
		{
			conn = DbUtil.getConnection();
			try 
			{
				preparedStatement = conn.prepareStatement(IQueryMapper.FIND_CITY);
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
				{
					String cty = resultSet.getString(1);
					if(cty.equals(city))
					{
						return true;
					}
				}
				return false;
			}
			catch (SQLException e) 
			{
				logg.error("Error viewing the guest list of specific hotel !!!");
				//e.printStackTrace();
				throw new BookingException("Error in viewing the guest list of specific hotel !!!" + e.getMessage());
			}
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
				throw new BookingException("Error in viewing the guest list of specific hotel !!!" + e.getMessage());
			}
			return false;
		}

		@Override
		public List<String> viewAllBookingStatus(String userId) throws UserException, ConnectionException {

			conn = DbUtil.getConnection();
			String bookingStatus;
			List<String> list = new ArrayList<>();
			try 
			{
				boolean stat = userIdExist(userId);
				if(stat == true)
				{
					preparedStatement = conn.prepareStatement(IQueryMapper.VIEW_ALL_BOOKING_STATUS);
					preparedStatement.setString(1, userId);
			
					ResultSet resultSet = preparedStatement.executeQuery();
					while(resultSet.next())
					{
						String bookingId = resultSet.getString(1);
						String roomId = resultSet.getString(2);
						String bookedFrom = resultSet.getString(4);
						String bookedTo = resultSet.getString(5);
						int noOfAdults = resultSet.getInt(6);
						int  noOfChildren = resultSet.getInt(7);
						double amount = resultSet.getDouble(8);
						
						bookingStatus = " | "+bookingId+" | "+roomId+" | "+userId+" | "+bookedFrom+" | "+bookedTo+" | "+noOfAdults+" | "+noOfChildren+" | "+amount+" | ";
						list.add(bookingStatus);
						
					}
					
					return list;	
				}
				else
				{
					logg.error("User ID requested for, has no bookings !!!");
					System.err.println("User ID requested for, has no bookings !!! ");
					list.add("error");
					return list;
				}
			}
			catch (SQLException e) 
			{
				logg.error("Error viewing the booking status !!!");
				throw new UserException("Error in viewing the booking status !!!");
			}
		}

		private boolean userIdExist(String userId) throws ConnectionException, UserException {

			conn = DbUtil.getConnection();
			try
			{
				preparedStatement = conn.prepareStatement(IQueryMapper.FIND_USER_ID);
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
				{
					String id = resultSet.getString(1);
					if(id.equals(userId))
					{
						return true;
					}
				}
				return false;
			}
			catch (SQLException e) 
			{
				logg.error("Error viewing the booking status !!!");
				throw new UserException("Error in viewing the booking status !!!");
			}
		}

		@Override
		public String dateCheckIn(LocalDate bookFrom) {

			userDao = new UserDaoImplementation();
			LocalDate today = userDao.todayDate();
			if(bookFrom.isBefore(today))
			{
				return "check in date error";
			}
			return "Correct Date";
		}

		@Override
		public List<String> displayCities() throws UserException, ConnectionException {

			conn = DbUtil.getConnection();
			try
			{
				List<String> cities = new ArrayList<>();
				preparedStatement = conn.prepareStatement(IQueryMapper.VIEW_ALL_CITIES);
				ResultSet resultSet = preparedStatement.executeQuery();
				while(resultSet.next())
				{
					String city = resultSet.getString(1);
					cities.add(city);
				}
				return cities;
			}
			catch (SQLException e) 
			{
				logg.error("Error viewing the booking status !!!");
				throw new UserException("Error in viewing the booking status !!!");
			}
		}
		
}
