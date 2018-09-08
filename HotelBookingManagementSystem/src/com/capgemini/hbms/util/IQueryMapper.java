package com.capgemini.hbms.util;

public interface IQueryMapper {

	//Database Connection Credentials
	public final String URL="jdbc:oracle:thin:@10.219.34.3:1521:orcl";
	public final String USERNAME="trg209";
	public final String PASSWORD="training209";
	
	//Login Check
	public final String ADMIN_LOGIN_CHECK = "SELECT user_name,password FROM Users where role='Admin'";
	public final String EMPLOYEE_LOGIN_CHECK = "SELECT user_name,password FROM Users where role='Employee'";
	public final String CUSTOMER_LOGIN_CHECK = "SELECT user_name,password FROM Users where role='Customer'";
	public final String LOGIN_CHECK = "SELECT user_id,password,role FROM Users";
	
	//Inserting Information into Database
	public final String EMPLOYEE_INSERT_QUERY = "INSERT INTO Users VALUES ('E'||TO_CHAR(Employee_id_Sequence_query.NEXTVAL,'FM00'),?,?,?,?,?,?,?)";
	public final String CUSTOMER_INSERT_QUERY = "INSERT INTO Users VALUES ('C'||TO_CHAR(Customer_id_Sequence_query.NEXTVAL,'FM00'),?,?,?,?,?,?,?)";
	public final String HOTEL_INSERT_QUERY = "INSERT INTO Hotel VALUES ('H'||TO_CHAR(Hotel_id_Sequence_query.NEXTVAL,'FM00'),?,?,?,?,?,?,?,?,?,?)";
	public final String ROOM_INSERT_QUERY = "INSERT INTO RoomDetails VALUES (?,'R'||TO_CHAR(Room_id_Sequence_query.NEXTVAL),'FM00'),?,?,?,?)";
	public final String BOOKING_INSERT_QUERY = "INSERT INTO BookingDetails VALUES ('B'||TO_CHAR(Booking_id_Sequence_query.NEXTVAL,'FM00'),?,?,?,?,?,?,?)"; 
	public final String AMOUNT_FIND_QUERY = "SELECT per_night_rate FROM RoomDetails WHERE room_id = ?";
	public final String USER_FIND_QUERY = "SELECT user_id FROM Users WHERE user_id = ?";
	
	//Generating Id using Sequence
	public final String USER_ROLE_QUERY = "SELECT role FROM Users WHERE user_id = ?";
	public final String EMPLOYEE_ID_SEQUENCE_QUERY = "SELECT 'E'||TO_CHAR(Employee_id_Sequence_Query.CURRVAL,'FM00') FROM DUAL";
	public final String CUSTOMER_ID_SEQUENCE_QUERY = "SELECT 'C'||TO_CHAR(Customer_id_Sequence_Query.CURRVAL,'FM00') FROM DUAL";
	public final String HOTEL_ID_SEQUENCE_QUERY = "SELECT 'H'||TO_CHAR(Hotel_id_Sequence_Query.CURRVAL,'FM00') FROM DUAL";
	public final String ROOM_ID_SEQUENCE_QUERY = "SELECT 'R'||TO_CHAR(Room_id_Sequence_Query.CURRVAL,'FM00') FROM DUAL";
	public final String BOOKING_ID_SEQUENCE_QUERY = "SELECT 'B'||TO_CHAR(Booking_id_Sequence_Query.CURRVAL,'FM00') FROM DUAL";
	
	//Viewing Details
	public final String VIEW_ALL_HOTELS = "SELECT * FROM Hotel";
	public final String VIEW_ALL_ROOMS = "SELECT * FROM RoomDetails";
	public final String VIEW_BOOKINGS_OF_SPECIFIC_HOTEL = "SELECT * FROM BookingDetails WHERE room_id in (SELECT room_id FROM RoomDetails WHERE hotel_id = ?)";
	public final String VIEW_BOOKINGS_OF_SPECIFIC_DATE = "SELECT booking_id,room_id,user_id,to_char(booked_from,'dd/MM/YYYY'),to_char(booked_to,'dd/MM/YYYY'),no_of_adults,no_of_children,amount FROM BookingDetails WHERE booked_from <= ? and booked_to >= ?";
	public final String VIEW_BOOKING_STATUS = "SELECT booking_id,room_id,user_id,to_char(booked_from,'dd/MM/YYYY'),to_char(booked_to,'dd/MM/YYYY'),no_of_adults,no_of_children,amount FROM BookingDetails WHERE booking_id = ? AND user_id = ?";
	public final String VIEW_ALL_BOOKING_STATUS = "SELECT booking_id,room_id,user_id,to_char(booked_from,'dd/MM/YYYY'),to_char(booked_to,'dd/MM/YYYY'),no_of_adults,no_of_children,amount FROM BookingDetails WHERE user_id = ?";
	public final String VIEW_GUESTS_OF_SPECIFIC_HOTEL = "SELECT booking_id,room_id,b.user_id,user_name,password,mobile_no,phone,address,email,to_char(booked_from,'dd/MM/YYYY'),to_char(booked_to,'dd/MM/YYYY'),no_of_adults,no_of_children,amount from Users u, BookingDetails b WHERE b.user_id=u.user_id and room_id in (SELECT room_id FROM RoomDetails WHERE hotel_id = ?)"; 
	public final String SEARCH_HOTELS = "SELECT hotel_id,city,hotel_name,address,description,avg_rate_per_night,phone_no1,phone_no2,rating,email,fax FROM Hotel where city = ?";
	public final String SEARCH_ROOMS = "SELECT * FROM RoomDetails where hotel_id = ? AND availability = 1";
	public final String SEARCH_ALL_ROOMS = "SELECT * FROM RoomDetails where hotel_id = ?";
	
	//Updating
	public final String UPDATE_ROOMS_AVAILABILITY_FALSE = "UPDATE RoomDetails SET availability = 0 WHERE room_id = ?";
	public final String UPDATE_ROOMS_AVAILABILITY_TRUE = "UPDATE RoomDetails SET availability = 1 WHERE room_id = ?";
	
	//Deleting Information from Database
	public final String DELETE_HOTEL_DETAILS = "DELETE FROM Hotel WHERE hotel_id=?";
	public final String DELETE_ROOM_DETAILS = "DELETE FROM RoomDetails WHERE room_id=?";
	public final String ROOM_EXISTENCE_QUERY = "SELECT room_id FROM RoomDetails WHERE room_id = ?";
	public final String ROOM_AVAILABILITY_QUERY = "SELECT availability FROM RoomDetails WHERE room_id = ?";
	public final String DATE_CLASH_QUERY = "SELECT room_id,to_char(booked_from,'dd/MM/YYYY'),to_char(booked_to,'dd/MM/YYYY') FROM BookingDetails";
	public final String FIND_BOOKING_ID = "SELECT booking_id FROM BookingDetails";
	public final String FIND_HOTEL = "SELECT hotel_id FROM Hotel";
	public final String FIND_CITY = "SELECT city FROM Hotel";
	public final String FIND_USER_ID = "SELECT user_id FROM BookingDetails";
	public final String VIEW_ALL_CITIES = "SELECT distinct(city) FROM Hotel";
}