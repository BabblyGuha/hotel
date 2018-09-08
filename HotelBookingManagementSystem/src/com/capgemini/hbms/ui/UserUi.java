package com.capgemini.hbms.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

import com.capgemini.hbms.bean.BookingDetailsBean;
import com.capgemini.hbms.bean.HotelBean;
import com.capgemini.hbms.bean.RoomDetailsBean;
import com.capgemini.hbms.bean.UserBean;
import com.capgemini.hbms.exception.AdminException;
import com.capgemini.hbms.exception.BookingException;
import com.capgemini.hbms.exception.ConnectionException;
import com.capgemini.hbms.exception.CustomerException;
import com.capgemini.hbms.exception.EmployeeException;
import com.capgemini.hbms.exception.HotelException;
import com.capgemini.hbms.exception.RoomException;
import com.capgemini.hbms.exception.UserException;
import com.capgemini.hbms.service.AdminServiceImplementation;
import com.capgemini.hbms.service.AuthenticationServiceImplementation;
import com.capgemini.hbms.service.IAdminService;
import com.capgemini.hbms.service.IAuthenticationService;
import com.capgemini.hbms.service.IUserService;
import com.capgemini.hbms.service.UserServiceImplementation;

public class UserUi {

	static InputStreamReader input = null;
	static BufferedReader buffer = null;
	static HotelBean hotel = null;
	static RoomDetailsBean room  = null;
	static BookingDetailsBean bookingDetails = null;
	static IAdminService adminService = null;
	static UserUi user = null;
	static IUserService userService = null;
	static IAuthenticationService authenticationService = null;
	Logger logg = Logger.getRootLogger();
	
	public static void main(String[] args) throws ConnectionException, BookingException, AdminException, CustomerException, RoomException, HotelException, EmployeeException, UserException, IOException {

		input = new InputStreamReader(System.in);
		buffer = new BufferedReader(input);
		Logger logg = Logger.getRootLogger();
		String roleId,loginRole;
		String login[];
		try
		{
			do
			{
				String choice;
				int aChoice = 0;
				int action = 0;
				do
				{
					action = 1;
					System.out.println("***************************************************");
					System.out.println("*        Hotel Booking Management System          *");
					System.out.println("***************************************************");
					System.out.println("         1) Register");
					System.out.println("         2) Login");
					System.out.println("         3) Exit");
					choice = buffer.readLine();
					authenticationService = new AuthenticationServiceImplementation();
					if(authenticationService.validateAuthenticationChoice(choice))
					{
						aChoice = Integer.parseInt(choice);
						if(aChoice >= 1 || aChoice <= 3)
							break;
						else
							System.err.println("Invalid Option");
					}
					else
					{
						logg.error("User has entered an incorrect option");
						System.err.println("Please Enter any of the below listed options !!!");
					}
					
				}while(action == 1);
				
				switch(aChoice)
				{
				case 1:
					user = new UserUi();
					String status = user.addUser();
					
					if(status.equals("back"))
					{
						continue;
					}
					else if(status.equals("error") ==  false)
					{
						String userId = status.substring(0, status.indexOf(','));
						String role = status.substring(status.indexOf(',')+1);
						logg.info(role + " with " + role + " id: " + userId + ", is successfully registered...");
						System.out.println(role + " with " + role + " id: " + userId + ", is successfully registered...");
					}
					else
					{
						logg.error("Error in registration !!!");
						System.err.println("Error in registration !!!");
					}
					break;	
				case 2: 
					user = new UserUi();
					roleId = user.checkLoginCredentials();
					login = roleId.split(",");
					loginRole = login[0];
					roleId = login[1];
					if(loginRole.equals("error"))
					{
						logg.error("User Name/Password is invalid");
						System.err.println("User Name/Password is invalid");
					}
					else if(!loginRole.equals("error"))
					{
						logg.info("Login Successful ...");
						System.out.println("Login Successful ...");
						System.out.println();
						if(loginRole.equals("Admin"))
						{
							int logout;
							do
							{
								String option;
								int aOption = 0;
								action = 0;
								do
								{
									action = 1;
									System.out.println();
									System.out.println();
									System.out.println("*****************************************************");
									System.out.println("*               Welcome to Admin Page               *");
									System.out.println("*****************************************************");
									System.out.println("                Choose Your Option :");
									System.out.println("                1) Perform Hotel Management");
									System.out.println("                2) Perform Room Management");
									System.out.println("                3) Generate Reports");
									System.out.println("                4) Log Out");
									System.out.println("                5) Exit");
									option = buffer.readLine();
									authenticationService = new AuthenticationServiceImplementation();
									if(authenticationService.validateAuthenticationChoice(option))
									{
										aOption = Integer.parseInt(option);
										if(aOption >=1 && aOption <= 5)
										{
											break;
										}
										else
										{
											System.err.println("Invalid Option !!!");
										}
										
									}
									else
									{
										logg.error("User has entered an incorrect option");
										System.err.println("Please Enter any of the below listed options !!!");
									}
									
								}while(action == 1);
								logout = 0;
								
								
								switch(aOption)
								{
								case 1:
									String hoption;
									int hOption=0;
									do
									{
										action = 1;
										System.out.println();
										System.out.println("*************************************************");
										System.out.println("*               Hotel Management				*");
										System.out.println("*************************************************");
										System.out.println("                Choose Your Option :");
										System.out.println("                1) Add Hotel Details");
										System.out.println("                2) Modify Hotel Details");
										System.out.println("                3) Delete Hotel Details");
										System.out.println("                4) Go Back");
										System.out.println("                5) Exit");

										hoption = buffer.readLine();
										authenticationService = new AuthenticationServiceImplementation();
										if(authenticationService.validateAuthenticationChoice(hoption))
										{
											hOption = Integer.parseInt(hoption);
											if(hOption >=1 && hOption <= 5)
											{
												break;
											}
											else
											{
												System.err.println("Invalid Option !!!");
											}
										}
										else
										{
											logg.error("User has entered an incorrect option");
											System.err.println("Please Enter any of the below listed options !!!");
										}
										
									}while(action == 1);
									
									
									String hotelId;
									switch(hOption)
									{
									case 1:
										user = new UserUi();
										hotelId = user.addHotelDetails();
										if(hotelId.equals("error") ==  false)
										{
											logg.info("Hotel with hotel id: " + hotelId + ", is successfully added...");
											System.out.println("Hotel with hotel id: " + hotelId + ", is successfully added...");
										}
										else
										{
											logg.error("Error in Adding new hotel details !!!");
											System.err.println("Error in Adding new hotel details !!!");
										}
										break;
									case 2: 
										user = new UserUi();
										hotelId = user.modifyHotelDetails();
										System.out.println("Hotel with Hotel ID " + hotelId + " is successfully modified ...");
										break;
									case 3:
										user = new UserUi();
										hotelId = user.deleteHotelDetails();
										if(hotelId.equals("error") ==  false)
										{
											logg.info("Hotel with Hotel ID " + hotelId + " is successfully deleted ...");
											System.out.println("Hotel with Hotel ID " + hotelId + " is successfully deleted ...");
										}
										else
										{
											logg.error("Error in Deleting new hotel details !!!");
											System.err.println("Error in Deleting new hotel details !!!");
										}
										
										break;
									case 4: break;
									case 5: 
										System.out.println("You Have Successfully exited !!! Thank You...");
										System.exit(0);
									default: System.err.println("Invalid Option");
									}
									break;
								case 2:
									
									int rOption = 0;
									String roption;
									do
									{
										action = 1;
										System.out.println();
										System.out.println("*************************************************");
										System.out.println("*               Room Management 				*");
										System.out.println("*************************************************");
										System.out.println("                Choose Your Option :");
										System.out.println("                1) Add Room Details");
										System.out.println("                2) Modify Room Details");
										System.out.println("                3) Delete Room Details");
										System.out.println("                4) Go Back");
										System.out.println("                5) Exit");

										roption = buffer.readLine();
										authenticationService = new AuthenticationServiceImplementation();
										if(authenticationService.validateAuthenticationChoice(roption))
										{
											rOption = Integer.parseInt(roption);
											if(rOption >= 1 && rOption<=5)
											{
												break;
											}
										}
										else
										{
											logg.error("User has entered an incorrect option");
											System.err.println("Please Enter any of the below listed options !!!");
										}
										
									}while(action == 1);
									
									
									String roomId;
									switch(rOption)
									{
									case 1:
										user = new UserUi();
										roomId = user.addRoomDetails();
										if(roomId.equals("error") ==  false)
										{
											System.out.println("Room with room id: " + roomId + ", is successfully added...");
										}
										else
										{
											System.err.println("Error in Adding new room details !!!");
										}
										break;
									case 2: 
										roomId = user.modifyRoomDetails();
										if(roomId.equals("back"))
										{
											break;
										}
										else if("noRooms".equals(roomId))
										{
											System.err.println("There is no room in this hotel yet.");
										}
										else
										{
											System.out.println("Room with Room ID " + roomId + " is successfully modified ...");
										}
										break;
									case 3:
										user = new UserUi();
										roomId = user.deleteRoomDetails();
										if(roomId.equals("error"))
										{
											logg.error("Error in Deleting new Room details !!!");
											System.err.println("Error in Deleting new Room details !!!");
										}
										else if("noRooms".equals(roomId))
										{
											System.err.println("There are no rooms in this hotel yet.");
										}
										else if(roomId.equals("error") ==  false)
										{
											logg.info("Room with Room ID " + roomId + " is successfully deleted ...");
											System.out.println("Room with Room ID " + roomId + " is successfully deleted ...");
										}

									case 4: break;
									case 5: 
										System.out.println("You Have Successfully exited !!! Thank You...");
										System.exit(0);
									default: System.err.println("Invalid Option");
									}
									break;
								case 3:
									
									int gChoice = 0;
									String gchoice;
									do
									{
										action = 1;
										System.out.println();
										System.out.println("*************************************************");
										System.out.println("*               Generate Reports				*");
										System.out.println("*************************************************");
										System.out.println("                Choose Your Option :");
										System.out.println("                1) View List of Hotels");
										System.out.println("                2) View Bookings of Specific Hotel");
										System.out.println("                3) View Guest List of Specific Hotels");
										System.out.println("                4) View Bookings for Specified Date");
										System.out.println("                5) Go Back");
										System.out.println("                6) Exit");

										gchoice = buffer.readLine();
										authenticationService = new AuthenticationServiceImplementation();
										if(authenticationService.validateAuthenticationChoice(gchoice))
										{
											gChoice = Integer.parseInt(gchoice);
											if(gChoice >= 1 && gChoice<=6)
											{
												break;
											}
											else
											{
												System.err.println("Invalid Option !!!");
											}
										}
										else
										{
											logg.error("User has entered an incorrect option");
											System.err.println("Please Enter any of the below listed options !!!");
										}
										
									}while(action == 1);
									
									switch(gChoice)
									{
									case 1:
										System.out.println("The List of Hotels :");
										user = new UserUi();
										List<HotelBean> hotelList = user.viewHotels();
										Iterator iterator = hotelList.iterator();
										System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
										System.out.println("|      Hotel ID      |     Hotel Name     |        City        |    Description     |   Rate Per Night   |   Phone Number 1   |   Phone Number 2   |       Rating       |       Email        |        Fax         |");
										System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
										while(iterator.hasNext())
										{
											HotelBean hotel = (HotelBean) iterator.next();
											System.out.println("| "+hotel.getHotelId()+"  |  "+hotel.getHotelName()+"  |  "+hotel.getCity()+"  |  "+hotel.getDescription()+"  |  "+hotel.getAvgRatePerNight()+" |  "+hotel.getPhone_no1()+"  |  "+hotel.getPhone_no2()+"  |  "+hotel.getRating()+"  |  "+hotel.getEmail()+"  |  "+hotel.getFax());
										}
										break;
									case 2:
										user = new UserUi();
										List<BookingDetailsBean> bookingList = user.viewBookingsOfSpecificHotel();
										Iterator bIterator = bookingList.iterator();
										System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
										System.out.println("|     BOOKING ID     |      ROOM ID       |      USER ID       |    BOOKED FROM     |     BOOKED TO      |  NUMBER OF ADULTS  | NUMBER OF CHILDREN |       AMOUNT       |");
										System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
										while(bIterator.hasNext())
										{
											BookingDetailsBean bookings = (BookingDetailsBean) bIterator.next();
											System.out.println("|        "+bookings.getBookingId()+"        |        "+bookings.getRoomId()+"           |        "+bookings.getUserId()+"            |       "+bookings.getBookedFrom()+"        |  "+bookings.getBookedTo()+"        |       "+bookings.getNoOfAdults()+"              |            "+bookings.getNoOfChildren()+"           |          "+bookings.getAmount()+" |");
										}
										break;
									case 3:
										user = new UserUi();
										user.viewGuestListofSpecificHotel();
										break;
									case 4:
										user = new UserUi();
										user.viewBookingsforSpecifiedDate();
										break;
									case 5: break;
									case 6: 
										System.out.println("You Have Successfully exited !!! Thank You...");
										 System.exit(0);
										 
									default: System.err.println("Invalid Option");
									}
									break;
								case 4:
									System.out.println("You have successfully Logged out !!!");
									logout = 1;
									break;
								case 5: 
									System.out.println("You Have Successfully exited !!! Thank You...");
									 System.exit(0);
									 
								default: System.err.println("Invalid Option");
								}
							}while(logout == 0);
						}
						else if(loginRole.equals("Employee"))
						{
							int logout = 0;
							do
							{
								
								int cChoice = 0;
								String cchoice;
								do
								{
									action = 1;
									System.out.println();
									System.out.println("*********************************************************");
									System.out.println("*               Welcome to Employee Page				*");
									System.out.println("*********************************************************");
									System.out.println("                Choose Your Option :");
									System.out.println("                1) Search for Hotel Rooms");
									System.out.println("                2) Book Hotel Rooms");
									System.out.println("                3) View All Booking Status");
									System.out.println("                4) Log Out");
									System.out.println("                5) Exit");
									System.out.println();
									System.out.print("Enter your choice : ");
									cchoice = buffer.readLine();
									
									authenticationService = new AuthenticationServiceImplementation();
									if(authenticationService.validateAuthenticationChoice(cchoice))
									{
										cChoice = Integer.parseInt(cchoice);
										if(cChoice >= 1 && cChoice<= 5)
										{
											break;
										}
										else
										{
											System.err.println("Invalid Option !!!");
										}
									}
									else
									{
										logg.error("User has entered an incorrect option");
										System.err.println("Please Enter any of the below listed options !!!");
									}
									
								}while(action == 1);
								
								logout = 0;
								
								switch(cChoice)
								{
								case 1:
									user = new UserUi();
									user.searchHotels();
									break;
								case 2:
									user = new UserUi();
									user.searchHotels();
									String bookingId = user.bookRoom(loginRole,roleId);
									if(bookingId.equals("error") ==  true)
									{
										logg.error("Error in Booking hotel rooms !!!");
										System.err.println("Error in Booking hotel rooms !!!");
									}
									else
									{
										logg.info("Booking with booking id: " + bookingId + ", is successfully done...");
										System.out.println("You have successfully booked a hotel room with booking ID : " + bookingId);
									}
									break;
								case 3:
									user = new UserUi();
									List<String> list = user.viewAllBookingStatus();
									if(!(list.get(0).equals("error")))
									{
										System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
										System.out.println("|     BOOKING ID     |      ROOM ID       |      USER ID       |    BOOKED FROM     |     BOOKED TO      |  NUMBER OF ADULTS  | NUMBER OF CHILDREN |       AMOUNT       |");
										System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
										for(String bList: list)
										{
											System.out.println(bList);
										}
										
									}
									break;
								case 4: 
									System.out.println("You have successfully Logged out !!!");
									logout = 1;
									break;
								case 5: 
									System.out.println("You Have Successfully exited !!! Thank You...");
									 System.exit(0);
									 
								default: System.err.println("Invalid Option");
								}
								
							}while(logout == 0);
						}
						else if(loginRole.equals("Customer"))
						{
							int logout = 0;
							do
							{
								
								int cChoice = 0;
								String cchoice;
								do
								{
									action = 1;
									System.out.println();
									System.out.println("*********************************************************");
									System.out.println("*                 Welcome to Customer Page				*");
									System.out.println("*********************************************************");
									System.out.println("                  Choose Your Option :");
									System.out.println("                  1) Search for Hotel Rooms");
									System.out.println("                  2) Book Hotel Rooms");
									System.out.println("                  3) View Booking Status");
									System.out.println("                  4) Log Out");
									System.out.println("                  5) Exit");
									System.out.println();
									System.out.print("Enter your choice : ");
									cchoice = buffer.readLine();
									
									authenticationService = new AuthenticationServiceImplementation();
									if(authenticationService.validateAuthenticationChoice(cchoice))
									{
										cChoice = Integer.parseInt(cchoice);
										if(cChoice >= 1 && cChoice<=5)
										{
											break;
										}
										else
										{
											System.err.println("Invalid Option !!!");
										}
									}
									else
									{
										logg.error("User has entered an incorrect option");
										System.err.println("Please Enter any of the below listed options !!!");
									}
									
								}while(action == 1);
								
								logout = 0;
								
								switch(cChoice)
								{
								case 1:
									user = new UserUi();
									user.searchHotels();
									break;
								case 2:
									user = new UserUi();
									user.searchHotels();
									String bookingId = user.bookRoom(loginRole,roleId);
									if(bookingId.equals("error") ==  true)
									{
										logg.error("Error in Booking hotel rooms !!!");
										System.err.println("Error in Booking hotel rooms !!!");
									}
									else
									{
										logg.info("Booking with booking id: " + bookingId + ", is successfully done...");
										System.out.println("You have successfully booked a hotel room with booking ID : " + bookingId);
									}
									break;
								case 3:
									user = new UserUi();
									String bookingStatus = user.viewBookingStatus(loginRole,roleId);
									if(bookingStatus.equals("mismatch"))
									{
										System.err.println("The requested booking ID does not belong to the user with user ID : " + roleId);
									}
									else if(!(bookingStatus.equals("error")))
									{
										System.out.println("Your Current Booking Status :");
										System.out.println(bookingStatus);
									}
									break;
								case 4: 
									System.out.println("You have successfully Logged out !!!");
									logout = 1;
									break;
								case 5: 
									System.out.println("You Have Successfully exited !!! Thank You...");
									 System.exit(0);
									 
								default: System.err.println("Invalid Option");
								}
								
							}while(logout == 0);
						}
					}
					break;
				case 3:
					System.out.println("You have successfully exited !!!");
					System.exit(0);
				default: 
					System.err.println("Invalid Option");
				}
				
			}while(true);
		}
		catch(InputMismatchException e)
		{
			logg.error("User has entered an incorrect option");
			throw new UserException("Please Enter any of the below listed options !!!");
		}
		
	}
	

	private List<String> viewAllBookingStatus() throws UserException, ConnectionException, IOException {
		int action = 1;
		String userId;
		userService = new UserServiceImplementation();
		do
		{
			System.out.print("Enter the User ID whose status you want to view : ");
			userId = buffer.readLine();
			
			if(userService.validateUserId(userId))
			{
				break;
			}
			System.out.println("Customer ID should start with 'C' followed by two numbers.");
		}while(action == 1);
		
		return userService.viewAllBookingStatus(userId);	
	}

	private String checkLoginCredentials() throws ConnectionException, AdminException, IOException {

		System.out.print("Enter User ID : ");
		String uId = buffer.readLine();
		System.out.print("Enter Password : ");
		String password = buffer.readLine();

		authenticationService = new AuthenticationServiceImplementation();
		String loginRole = authenticationService.checkLoginCredentials(uId,password);	
		loginRole = loginRole + "," + uId;
		return loginRole;
	}

	private String addUser() throws ConnectionException, CustomerException, EmployeeException, IOException {
		String choice;
		int aChoice=0;
		int action;
		String role="";
		do
		{
			action = 1;
			System.out.println("Want to register as : ");
			System.out.println("1) Employee");
			System.out.println("2) Customer");
			System.out.println("3) Go Back");
			choice = buffer.readLine();
			authenticationService = new AuthenticationServiceImplementation();
			if(authenticationService.validateAuthenticationChoice(choice))
			{
				aChoice = Integer.parseInt(choice);
				if(aChoice == 1 || aChoice == 2)
				{
					if(aChoice == 1)
					{
						do
						{
							System.out.println("Enter the Employee Code to get registered");
							String code = buffer.readLine();
							if(code.equals("Emp123"))
							{
								role = "Employee";
								break;
							}
							else
							{
								logg.error("User has entered an invalid Employee Code !.!.");
								System.err.println("Please enter a valid Employee Code !.!.");
							}
						}while(action == 1);
						
					}
					else
						role = "Customer";
					if(!role.equals(""))
					{
						break;
					}
				}
				else if(aChoice == 3)
				{
					return "back";
				}
				else
				{
					System.err.println("Invalid Option !!!");
				}
			}
			else
			{
				logg.error("User has entered an incorrect option");
				System.err.println("Please Enter any of the below listed options !!!");
			}
			
		}while(action == 1);
		String userName="",password,mobileNo,phone,email;
		do
		{
			System.out.print("Enter User Name: ");
			String usrName = (buffer.readLine()).trim();
			if(authenticationService.validateAuthenticationName(usrName))
			{
				userName = initCap(usrName);
				break;
			}
			System.err.println("User Name should contain only alphabates and spaces.");
		}while(action == 1);
		System.out.println();
		System.out.println();
		System.out.println("Password should have atleast one :");
		System.out.println("--> 1) Upper Case Character ");
		System.out.println("--> 2) Lower Case Character");
		System.out.println("--> 3) Number");
		System.out.println("--> 4) Special Symbol");
		System.out.println();
		do
		{
			System.out.print("Enter Password: ");
			password = buffer.readLine();
			if(authenticationService.validateAuthenticationPassword(password))
			{
				break;
			}
			System.out.println();
			System.err.println("Password must be minimum 6 and maximum 7 characters long and must have atleast one :");
			System.err.println("--> Upper Case Character ");
			System.err.println("--> Lower Case Character");
			System.err.println("--> Number");
			System.err.println("--> Special Symbol");
			System.out.println();
		}while(action == 1);
		do
		{
			System.out.print("Enter Mobile Number: ");
			mobileNo = buffer.readLine();
			if(authenticationService.validateAuthenticationMobileNo(mobileNo))
			{
				break;
			}
			System.err.println("Mobile Number should contain only digits and should start with (9|8|7|6) and should be 10 digits long.");
		}while(action == 1);
		do
		{
			System.out.print("Enter Phone Number: ");
			phone = buffer.readLine();
			if(authenticationService.validateAuthenticationPhone(phone))
			{
				break;
			}
			System.err.println("Phone Number should contain only digits and should be 6 digits long.");
		}while(action == 1);
		String address;
		adminService = new AdminServiceImplementation();
		do
		{
			System.out.print("Enter Address : ");
			address = buffer.readLine();
			if(adminService.validateAddress(address))
			{
				break;
			}
			System.err.println("Address cannot contain only numbers.");
		}while(action == 1);
		
		do
		{
			System.out.print("Enter Email: ");
			email = buffer.readLine();
			if(authenticationService.validateAuthenticationEmail(email))
			{
				break;
			}
			System.err.println("Please Enter a valid email.");
		}while(action == 1);

		UserBean user = new UserBean();
		user.setUsername(userName);
		user.setPassword(password);
		user.setRole(role);
		user.setMobileNo(mobileNo);
		user.setPhone(phone);
		user.setAddress(address);
		user.setEmail(email);
		
		authenticationService = new AuthenticationServiceImplementation();

		String status = authenticationService.addUser(user);
		return status;
	}

	private void displayAllHotels() throws ConnectionException, HotelException {
		user = new UserUi();
		List<HotelBean> hotelList = user.viewHotels();
		Iterator iterator = hotelList.iterator();
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println("|      Hotel ID      |     Hotel Name     |        City        |    Description     |   Rate Per Night   |   Phone Number 1   |   Phone Number 2   |       Rating       |       Email        |        Fax         |");
		System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
		System.out.println();
		while(iterator.hasNext())
		{
			HotelBean hotel = (HotelBean) iterator.next();
			System.out.println("|        " + hotel.getHotelId()+"         |        "+hotel.getHotelName()+"         |         "+hotel.getCity()+"         |         "+hotel.getDescription()+"         |  "+hotel.getAvgRatePerNight()+"        |        "+hotel.getPhone_no1()+"       |  "+hotel.getPhone_no2()+"       |          "+hotel.getRating()+"          |  "+hotel.getEmail()+"         |         "+hotel.getFax()+"        |");
		} 
		
	}

	private void displayAllRooms(String hotelId) throws ConnectionException, RoomException, BookingException {
		user = new UserUi();
		List<RoomDetailsBean> roomList = user.searchRoom(hotelId);
		System.out.println("    The rooms in the hotel");
		System.out.println("-------------------------------");
		System.out.println();
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println("|      Room ID       |    Room Number     |     Room Type      |   Per Night Rate   |");
		System.out.println("-------------------------------------------------------------------------------------");

		for(RoomDetailsBean room : roomList)
		{
			
			System.out.println("|        " + room.getRoomId() + "        |         " + room.getRoomNo() + "        |        " + room.getRoomType() + "        |        " + room.getPerNightRate() + "        |         ");
		}
	}

	private String viewBookingStatus(String loginRole, String roleId) throws ConnectionException, BookingException, UserException, IOException {

		int action = 1;
		String bookingId;
		userService = new UserServiceImplementation();
		do
		{
			System.out.print("Enter the booking ID whose status you want to view : ");
			bookingId = buffer.readLine();
			
			if(userService.validateBookingId(bookingId))
			{
				break;
			}
			System.out.println("Booking ID should start with 'B' followed by two numbers.");
		}while(action == 1);
		
		
		return userService.viewBookingStatus(roleId,bookingId);	
		
	}

	private void viewBookingsforSpecifiedDate() throws ConnectionException, BookingException, IOException {
		
		adminService = new AdminServiceImplementation();
		String date;
		String datearr[] = new String[10];
		String to[] = new String[10];
		int action = 1,year=0,month=0,day=0;
		LocalDate bookOfDate = null;
		int flag = 0;
		List<BookingDetailsBean> bookingDateList = new ArrayList<>();
		
		do
		{
			flag = 0;
			System.out.print("Enter the Date(Enter date in the form of dd/mm/yyyy) on which you want to view the Bookings : ");
			date = buffer.readLine();
			if(adminService.validateDate(date))
			{
				datearr = date.split("/");
				year = Integer.parseInt(datearr[2]);
				month = Integer.parseInt(datearr[1]);
				day = Integer.parseInt(datearr[0]);
				try
				{
					bookOfDate = LocalDate.of(year, month, day);
				}
				catch(DateTimeException e)
				{
					flag = 1;
					System.err.println("Invalid Date !!!");
				}
				finally
				{
					if(flag == 0)
					{
						adminService = new AdminServiceImplementation();
						bookingDateList = adminService.viewBookingsforSpecifiedDate(bookOfDate);	
						System.out.println("The Bookings of Specific Date :");
						Iterator bDateIterator = bookingDateList.iterator();
						System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
						System.out.println("|     BOOKING ID     |      ROOM ID       |      USER ID       |    BOOKED FROM     |     BOOKED TO      |  NUMBER OF ADULTS  | NUMBER OF CHILDREN |       AMOUNT       |");
						System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
						while(bDateIterator.hasNext())
						{
							BookingDetailsBean bookings = (BookingDetailsBean) bDateIterator.next();
							System.out.println("|        "+bookings.getBookingId()+"         |         "+bookings.getRoomId()+"         |  "+bookings.getUserId()+"         |        "+bookings.getBookedFrom()+"        |        "+bookings.getBookedTo()+"        |        "+bookings.getNoOfAdults()+"          |  "+bookings.getNoOfChildren()+"  |  "+bookings.getAmount());
						}
					}
				}
				break;
			}
			else
			{
				System.err.println("Please enter the date in the format specified.");
			}
		}while(action == 1);
		
	}

	private void viewGuestListofSpecificHotel() throws ConnectionException, BookingException, HotelException, IOException 
	{
		String hotelId;
		int action = 1;
		adminService = new AdminServiceImplementation();
		List<HotelBean> hotelList = user.viewHotels();
		displayAllHotels();
		Iterator iterator;
		do
		{
			System.out.print("Enter the Hotel ID of the Hotel whose Bookings you want to view : ");
			hotelId = buffer.readLine();
			int flag = 0;
			if(adminService.validateHotelId(hotelId))
			{
				iterator = hotelList.iterator();
				while(iterator.hasNext())
				{
					HotelBean hotel = (HotelBean) iterator.next();
					if(hotel.getHotelId().equals(hotelId))
					{
						System.out.println(hotel.getHotelId()+"    "+hotelId);
						flag = 1;
						break;
					}
				}
				if(flag == 1)
				{
					break;
				}
				else
				{
					System.err.println("Hotel does not exist !!!");
				}
			}
			else
			{
				System.err.println("Hotel ID should start with 'H' followed by two numbers.");
			}
		}while(action == 1);
		
		adminService = new AdminServiceImplementation();
		List<String> guestList = adminService.viewGuestListofSpecificHotel(hotelId);	
		if(guestList.size() == 0)
		{
			System.err.println("There are no guests in the specified hotel");
		}
		else
		{
			System.out.println("The guest List of the hotel with hotel ID : " + hotelId);
			for(String guest: guestList)
			{
				System.out.println(guest);
			}
		}
	}

	private List<BookingDetailsBean> viewBookingsOfSpecificHotel() throws ConnectionException, BookingException, HotelException, IOException {
		String hotelId;
		int action = 1;
		adminService = new AdminServiceImplementation();
		List<HotelBean> hotelList = user.viewHotels();
		displayAllHotels();
		Iterator iterator;
		do
		{
			System.out.print("Enter the Hotel ID of the Hotel whose Bookings you want to view : ");
			hotelId = buffer.readLine();
			int flag = 0;
			if(adminService.validateHotelId(hotelId))
			{
				iterator = hotelList.iterator();
				while(iterator.hasNext())
				{
					HotelBean hotel = (HotelBean) iterator.next();
					if(hotel.getHotelId().equals(hotelId))
					{
						flag = 1;
						break;
					}
				}
				if(flag == 1)
				{
					break;
				}
				else
				{
					System.err.println("Hotel does not exist !!!");
				}
			}
			else
			{
				System.err.println("Hotel ID should start with 'H' followed by two numbers.");
			}
		}while(action == 1);
		List<BookingDetailsBean> bookingList = adminService.viewBookingsOfSpecificHotel(hotelId);	
		if(bookingList.size() == 0)
		{
			System.err.println("There are no bookings in the specified hotel");
		}
		else
		{
			System.out.println("The bookings of the hotel with hotel ID : " + hotelId);
		}
		
		adminService = new AdminServiceImplementation();
		return bookingList;
	}

	private String bookRoom(String loginRole, String roleId) throws ConnectionException, BookingException, IOException, CustomerException, EmployeeException {
		
		String roomId,userId;
		String bookFrom,bookTo;
		String from[] = new String[10];
		String to[] = new String[10];
		int action = 1,fromYear,fromMonth,fromDay,toYear,toMonth,toDay;
		LocalDate bookingFrom = null,bookingTo = null;
		adminService = new AdminServiceImplementation();
		userService = new UserServiceImplementation();
		do
		{
			System.out.print("Enter Room ID : ");
			roomId = buffer.readLine();
			if(adminService.validateRoomId(roomId))
			{
				if(userService.roomExistence(roomId))
				{
					break;
				}
				else
				{
					logg.error("The Room Requested for does not exist !!!");
					System.err.println("The Room Requested for does not exist !!!");
				}
			}
			else
			{
				System.err.println("Room ID should start with 'R' followed by two numbers.");
			}
		}while(action == 1);
		if(loginRole.equals("Employee"))
		{
			do
			{
				System.out.print("Enter Customer ID : ");
				userId = buffer.readLine();
				if(adminService.validateUserId(userId))
				{
					if(userService.userExistence(userId))
					{
						break;
					}
					else
					{
						logg.error("User does not exist");
						System.err.println("You are not a registered user. Please register first !!!");
						String choice;
						int aChoice = 0;
						do
						{
							System.out.println("1) Try with another customer ID");
							System.out.println("2) Register the user First");
							System.out.println("Select your Option");
							choice = buffer.readLine();
							authenticationService = new AuthenticationServiceImplementation();
							if(authenticationService.validateAuthenticationChoice(choice))
							{
								aChoice = Integer.parseInt(choice);
								if(aChoice >= 1 || aChoice <= 2)
									break;
								else
									System.err.println("Invalid Option");
							}
							else
							{
								logg.error("User has entered an incorrect option");
								System.err.println("Please Enter any of the below listed options !!!");
							}
							
						}while(action == 1);
						
						switch(aChoice)
						{
						case 1:
							break;
						case 2:
							user = new UserUi();
							String status = user.addUser();
							
							if(status.equals("back"))
							{
								continue;
							}
							else if(status.equals("error") ==  false)
							{
								userId = status.substring(0, status.indexOf(','));
								String role = "Customer";
								logg.info(role + " with " + role + " id: " + userId + ", is successfully registered...");
								System.out.println(role + " with " + role + " id: " + userId + ", is successfully registered...");
							}
							else
							{
								logg.error("Error in registration !!!");
								System.err.println("Error in registration !!!");
							}
							break;	
						}
					}
				}
				else
				{
					System.err.println("User ID should start with 'C' followed by two numbers.");
				}
			}while(action == 1);
		}
		else
		{
			userId = roleId;
		}
		
		do
		{
			System.out.print("Book from (Enter date in the form of dd/mm/yyyy) : ");
			bookFrom = buffer.readLine();
			if(userService.validateDate(bookFrom))
			{
				int flag = 0;
				from = bookFrom.split("/");
				fromYear = Integer.parseInt(from[2]);
				fromMonth = Integer.parseInt(from[1]);
				fromDay = Integer.parseInt(from[0]);
				try
				{
					bookingFrom = LocalDate.of(fromYear, fromMonth, fromDay);
				}
				catch(DateTimeException e)
				{
					flag = 1;
					System.err.println("Invalid Date !!!");
				}
				if(flag == 0)
				{
					String dateMessage = userService.dateCheckIn(bookingFrom);
					if(dateMessage.equals("check in date error"))
					{
						logg.error("The Check in Date should be present Day or any day from Next Day, not before Present Day");
						System.err.println("The Check in Date should be present Day or any day from Next Day, not before Present Day");
					}
					else
					{
						do
						{
							System.out.print("Book to (Enter date in the form of dd/mm/yyyy) : ");
							bookTo = buffer.readLine();
							if(userService.validateDate(bookTo))
							{
								flag = 0;
								to = bookTo.split("/");
								toYear = Integer.parseInt(to[2]);
								toMonth = Integer.parseInt(to[1]);
								toDay = Integer.parseInt(to[0]);
								try
								{
									bookingTo = LocalDate.of(toYear,toMonth,toDay);
								}
								catch(DateTimeException e)
								{
									flag = 1;
									System.err.println("Invalid Date !!!");
								}
								finally
								{
									if(flag == 0)
									{
										dateMessage = userService.dateCheckOut(bookingFrom,bookingTo);
										if(dateMessage.equals("check date error"))
										{
											logg.error("The Check out Date was entered as a date which is before the check in date");
											System.err.println("The Check out Date should be after the check in date");
										}
										else if(dateMessage.equals("check out date error"))
										{
											logg.error("The Check out Date should be any day from Next Day and cannot be Present Day or a day before Present Day");
											System.err.println("The Check out Date should be any day from Next Day and cannot be Present Day or a day before Present Day");
										}
										else
										{
											boolean roomAvailability = userService.roomAvailability(roomId,bookingFrom,bookingTo);
											if(roomAvailability == false)
											{
												flag = 1;
												logg.error("The Room Requested for is not available for the requested date !!!");
												System.err.println("The Room Requested for is not available for the requested date !!!");
											}
											break;
											
										}
									}
								}
							}
							else
							{
								System.err.println("Please enter the date in the format specified.");
							}
						}while(action == 1);
						if(flag == 0)
						{
							break;
						}
					}
				}
			}
			else
			{
				System.err.println("Please enter the date in the format specified.");
			}
		}while(action == 1);
		
		String adults,childrens;
		int noOfAdults = 0, noOfChildren = 0;
		do
		{
			System.out.print("Enter Number of Adults : ");
			adults = buffer.readLine();
			if(userService.validateNoOfAdults(adults))
			{
				noOfAdults = Integer.parseInt(adults);
				if(noOfAdults > 2)
				{
					System.err.println("Number of Adults should should be less than 3.");
					System.err.println("For more Adults you have to book another room.");
				}
				else
				{
					break;
				}
			}
			else
			{
				System.err.println("Number of Adults should contain only numbers.");
			}
			
		}while(action == 1);
		do
		{
			System.out.print("Enter Number of Children : ");
			childrens = buffer.readLine();
			if(userService.validateNoOfChildren(childrens))
			{
				noOfChildren = Integer.parseInt(childrens);
				if(noOfChildren > 2)
				{
					System.err.println("Number of Children should should be less than 3.");
					System.err.println("For more Children you have to book another room.");
				}
				else
				{
					break;
				}
			}
			else
			{
				System.err.println("Number of Children should contain only numbers.");
			}
		}while(action == 1);
		
		bookingDetails = new BookingDetailsBean();
		bookingDetails.setRoomId(roomId);
		bookingDetails.setUserId(userId);
		bookingDetails.setBookedFrom(bookingFrom);
		bookingDetails.setBookedTo(bookingTo);
		bookingDetails.setNoOfAdults(noOfAdults);
		bookingDetails.setNoOfChildren(noOfChildren);		
		
		userService = new UserServiceImplementation();
		return userService.addBookingDetails(bookingDetails);
		
	}

	//Search For Hotel
		private void searchHotels() throws ConnectionException, RoomException, BookingException, IOException, UserException {
			authenticationService = new AuthenticationServiceImplementation();
			userService = new UserServiceImplementation();
			user = new UserUi();
			user.displayCities();
			String city = "";
			int action = 1;
			do
			{
				System.out.print("Enter the City where you want to Search for Hotel Rooms : ");
				String cty = buffer.readLine();
				if(authenticationService.validateAuthenticationName(cty))
				{
					int len = cty.length();
					city = city + Character.toUpperCase(cty.charAt(0));
					for(int i=1;i<len;i++)
					{
						char ch = cty.charAt(i);
						if(ch == ' ')
						{
							city = city + " " + Character.toUpperCase(cty.charAt(i+1));
							i=i+1;
						}
						else
						{
							city = city + ch;
						}
					}
					break;
				}
				System.out.println("City should contain only alphabates and spaces.");
			}while(action == 1);
			ArrayList<HotelBean> hotelList = new ArrayList<>();
			hotelList = userService.searchHotels(city);
			if(hotelList.size() == 0)
			{
				System.err.println("None of our hotels are in the requested city.");
			}
			else
			{
				for(HotelBean hotel : hotelList)
				{
					System.out.println(hotel.getHotelId()+"  |  "+hotel.getHotelName()+"  |  "+hotel.getCity()+"  |  "+hotel.getDescription()+"  |  "+hotel.getAvgRatePerNight()+" |  "+hotel.getPhone_no1()+"  |  "+hotel.getPhone_no2()+"  |  "+hotel.getRating()+"  |  "+hotel.getEmail()+"  |  "+hotel.getFax());
				}
				System.out.println();
				user = new UserUi();
				user.searchRooms(hotelList);
			}
		}

	private void displayCities() throws UserException, ConnectionException {

			userService = new UserServiceImplementation();
			List<String> cities = userService.displayCities();
			System.out.println("The cities where our hotel facilities are available :");
			for(String city: cities)
			{
				System.out.println(city);
			}
		}


	//Search For Hotel Rooms
	private void searchRooms(ArrayList<HotelBean> hotelList) throws ConnectionException, RoomException, BookingException, IOException {
		userService = new UserServiceImplementation();
		String rHotelId;
		int action = 1;
		int flag = 0;
		adminService = new AdminServiceImplementation();
		List<RoomDetailsBean> roomList = null;
		do
		{
			System.out.print("Enter the hotel ID of the Hotel which you want to view : ");
			rHotelId = buffer.readLine();
			if(adminService.validateHotelId(rHotelId))
			{
				Iterator iterator = hotelList.iterator();
				while(iterator.hasNext())
				{
					HotelBean hotel = (HotelBean) iterator.next();
					if(hotel.getHotelId().equals(rHotelId))
					{
						flag = 1;
						break;
					}
				}
				if(flag == 1)
				{
					roomList = userService.searchRooms(rHotelId);
					if(roomList.size() == 0)
					{
						System.err.println("No Rooms are available in this hotel.");
					}
					else
					{
						break;
					}
				}
				else
				{
					System.err.println("Hotel does not exist !!!");
				}
			}
			else
			{
				System.err.println("Hotel ID should start with 'H' followed by two numbers.");
			}
		}while(action == 1);
		
		System.out.println("The Available rooms in hotel");
		System.out.println("-------------------------------------------------------------------------------------");
		System.out.println("|      Room ID       |    Room Number     |     Room Type      |   Per Night Rate   |");
		System.out.println("-------------------------------------------------------------------------------------");
		for(RoomDetailsBean room : roomList)
		{
			System.out.println("|          "+room.getRoomId() + "        |          " + room.getRoomNo() + "       | " + room.getRoomType() + "        |        " + room.getPerNightRate()+"       |");
		}
	}

	//Adding Room Details
	private String addRoomDetails() throws ConnectionException, HotelException, RoomException, IOException 
	{
		displayAllHotels();
		int action = 1;
		String hotelId,roomNo,roomType,perNightRate,availability;
		double rate = 0;
		boolean available=true;
		adminService = new AdminServiceImplementation();
		do
		{
			System.out.println();
			System.out.print("Enter Hotel Id for which you want to add a room: ");
			
			hotelId = buffer.readLine();
			if(adminService.validateHotelId(hotelId))
			{
				break;
			}
			System.out.println("Hotel ID should start with 'H' followed by two numbers.");
		}while(action == 1);
		do
		{
			System.out.print("Enter Room Number: ");
			roomNo = buffer.readLine();
			if(adminService.validateRoomNo(roomNo))
			{
				break;
			}
			System.err.println("Room Number should contain only numbers.");
		}while(action == 1);
		do
		{
			System.out.print("Enter Room Type: ");
			roomType = buffer.readLine();
			if(adminService.validateRoomType(roomType))
			{
				break;
			}
			System.err.println("Room Type should not contain only numbers.");
		}while(action == 1);
		
		do
		{
			System.out.print("Enter Rate Per Night: ");
			perNightRate = buffer.readLine();
			if(adminService.validateAverageRatePerNight(perNightRate))
			{
				rate = Double.parseDouble(perNightRate);
				break;
			}
			System.err.println("Rate Per Night should contain only numbers.");
		}while(action == 1);
		do
		{
			System.out.print("Enter the new availability : ");
			availability = buffer.readLine();
			if(adminService.validateAvailability(availability))
			{
				if(availability.equals("1"))
					available = true;
				else
					available = false;
				break;
			}
			System.err.println("Availability should be either 1 or 0.");
			System.err.println("1 for Available, 0 for unavailable.");
		}while(action == 1);
		
		room = new RoomDetailsBean();
		room.setHotelId(hotelId);
		room.setRoomNo(roomNo);
		room.setRoomType(roomType);
		room.setPerNightRate(rate);
		room.setAvailability(available);
		
		adminService = new AdminServiceImplementation();
		String roomId = adminService.addRoomDetails(room);
		return roomId;
		
	}
	
	private String modifyRoomDetails() throws ConnectionException, RoomException, BookingException, HotelException, IOException {
		
		adminService = new AdminServiceImplementation();
		userService = new UserServiceImplementation();
		List<HotelBean> hotelList = user.viewHotels();
		displayAllHotels();
		Iterator iterator;
		String hotelId;
		int action = 1;
		do
		{
			System.out.println();
			System.out.print("Enter the hotel ID for which you want to modify a room: ");
			hotelId = buffer.readLine();
			int flag = 0;
			if(adminService.validateHotelId(hotelId))
			{
				iterator = hotelList.iterator();
				while(iterator.hasNext())
				{
					HotelBean hotel = (HotelBean) iterator.next();
					if(hotel.getHotelId().equals(hotelId))
					{
						flag = 1;
						break;
					}
				}
				if(flag == 1)
				{
					break;
				}
				else
				{
					System.err.println("Hotel does not exist !!!");
				}
			}
			else
			{
				System.err.println("Hotel ID should start with 'H' followed by two numbers.");
			}
		}while(action == 1);
		System.out.println();
		List<RoomDetailsBean> roomList = user.searchRoom(hotelId);
		if(roomList.size() != 0)
		{
			displayAllRooms(hotelId);
			String roomId;
			String modifyRoom = "UPDATE RoomDetails SET ";
			String roomNo,roomType,perNightRate,availability,sure;
			double rate = 0;
			int available = 0,sureChoice = 0;
			String choice;
			int aChoice = 0;
			adminService = new AdminServiceImplementation();
			authenticationService = new AuthenticationServiceImplementation();
			user = new UserUi();
			
			do
			{
				System.out.println();
				System.out.print("Enter the room ID you want to modify : ");
				roomId = buffer.readLine();
				if(adminService.validateRoomId(roomId))
				{
					int flag = 0;
					for(RoomDetailsBean room : roomList)
					{
						if(room.getRoomId().equals(roomId))
						{
							flag = 1;
							break;
						}
					}
					if(flag == 1)
					{
						break;
					}
					else
					{
						System.err.println("Room does not exist !!!");
					}
				}
				else
				{
					System.out.println("Room ID should start with a 'R' followed by two numbers.");
				}
			}while(action == 1);
			int c=0;
			List<Integer> choices = new ArrayList<>();
			int flag = 0;
			do
			{
				do
				{
					action = 1;
					System.out.println();
					System.out.println("Press 1 to modify Room Number");
					System.out.println("Press 2 to modify Room Type");
					System.out.println("Press 3 to modify Rate of Room per night");
					System.out.println("Press 4 to modify Availability");
					System.out.println("Press 5 to Go Back");
					if(c > 0)
					{
						System.out.println("Press 6 if you have completed selecting the columns for modification : ");
					}
					System.out.print("Enter the option for the column you want to modify : ");
					choice = buffer.readLine();
					if(authenticationService.validateAuthenticationChoice(choice))
					{
						aChoice = Integer.parseInt(choice);
						if(choices.contains(aChoice))
						{
							System.err.println("You have already modified this field in this turn.");
							System.err.println("Please cancel the modification from Option 5 or try again in next turn.");
							continue;
						}
						if(aChoice >= 1 && aChoice <= 5)
						{
							c++;
							break;
						}
						else if(aChoice > 6  || aChoice <= 0 || (aChoice == 6 && c == 0))
						{
							System.err.println("Invalid Option !!!");
						}
						else if(aChoice==6)
						{
							break;
						}
					}
					else
					{
						logg.error("User has entered an incorrect option");
						System.err.println("Please Enter any of the below listed options !!!");
					}
					
				}while(action == 1);
				choices.add(aChoice);
				switch(aChoice)
				{
				case 1:
					
					do
					{
						System.out.print("Enter the new Room Number : ");
						roomNo = buffer.readLine();
						if(adminService.validateRoomNo(roomNo))
						{
							break;
						}
						System.err.println("Room Number should contain only numbers.");
					}while(action == 1);
					
					modifyRoom = modifyRoom + "room_no = '" + roomNo + "',"; break;
				case 2: 
					do
					{
						System.out.print("Enter the new Room Type : ");
						roomType = buffer.readLine();
						if(adminService.validateRoomType(roomType))
						{
							break;
						}
						System.err.println("Room Type should not contain only numbers.");
					}while(action == 1);
					
					modifyRoom = modifyRoom + "room_type = '" + roomType + "',"; break;
				case 3: 
					do
					{
						System.out.print("Enter the new per Night Rate : ");
						perNightRate = buffer.readLine();
						if(adminService.validateAverageRatePerNight(perNightRate))
						{
							rate = Double.parseDouble(perNightRate);
							break;
						}
						System.err.println("Rate Per Night should contain only numbers.");
					}while(action == 1);
					
					modifyRoom = modifyRoom + "per_night_rate = " + rate + ","; break;
				case 4: 
					do
					{
						System.out.print("Enter the new availability : ");
						availability = buffer.readLine();
						if(adminService.validateAvailability(availability))
						{
							available = Integer.parseInt(availability);
							break;
						}
						System.err.println("Availability should be either 1 or 0.");
						System.err.println("1 for Available, 0 for unavailable.");
					}while(action == 1);
					
					modifyRoom = modifyRoom + "availability = " + available + ","; break;
				case 5: 
					flag = 1;
					break;
				case 6:
					do
					{
						System.out.println();
						System.out.println("Are you sure you want to modify the above details?");
						System.out.println("Press 1 to Modify");
						System.out.println("Press 0 to Cancel");
						System.out.println("Press 2 to Go Back");
						sure = buffer.readLine();
						if(sure.equals("1") || sure.equals("0") || sure.equals("2"))
						{
							sureChoice = Integer.parseInt(sure);
							if(sureChoice == 0 || sureChoice == 1)
							{
								action = 0;
								break;
							}
							else
							{
								System.err.println("Invalid Option !!!");
							}
						}
						else
						{
							logg.error("User has entered an incorrect option");
							System.err.println("Please Enter any of the below listed options !!!");
						}
					}while(action == 1);
					
					if(sureChoice == 1)
					{
						modifyRoom = modifyRoom.substring(0,modifyRoom.length()-1) + " WHERE room_id = '" + roomId + "'";
						adminService = new AdminServiceImplementation();
						return adminService.modifyRoomDetails(modifyRoom);
					}
					else if(sureChoice == 0)
					{
						System.out.println();
						System.out.println("The Last modification has been cancelled");
						choices = new ArrayList<Integer>();
						modifyRoom = "UPDATE RoomDetails SET ";
						break;
					}
				}
				if(flag == 1)
					break;
			}while(true);
			return "back";
		}
		else if(roomList.size() == 0)
		{
			return "noRooms";
		}
		return hotelId;
	}
	
	private List<RoomDetailsBean> searchRoom(String rHotelId) throws ConnectionException, BookingException, RoomException {
		userService = new UserServiceImplementation();
		adminService = new AdminServiceImplementation();
		List<RoomDetailsBean> roomList = adminService.searchRoom(rHotelId);
		return roomList;
	}


	//Deleting Room Details
	private String deleteRoomDetails() throws ConnectionException, RoomException, BookingException, HotelException, IOException {
		
		adminService = new AdminServiceImplementation();
		userService = new UserServiceImplementation();
		List<HotelBean> hotelList = user.viewHotels();
		displayAllHotels();
		Iterator iterator;
		String hotelId;
		int action = 1;
		do
		{
			System.out.println();
			System.out.print("Enter the hotel ID for which you want to delete a room: ");
			hotelId = buffer.readLine();
			int flag = 0;
			if(adminService.validateHotelId(hotelId))
			{
				iterator = hotelList.iterator();
				while(iterator.hasNext())
				{
					HotelBean hotel = (HotelBean) iterator.next();
					if(hotel.getHotelId().equals(hotelId))
					{
						flag = 1;
						break;
					}
				}
				if(flag == 1)
				{
					break;
				}
				else
				{
					System.err.println("Hotel does not exist !!!");
				}
			}
			else
			{
				System.err.println("Hotel ID should start with 'H' followed by two numbers.");
			}
		}while(action == 1);
		List<RoomDetailsBean> roomList = user.searchRoom(hotelId);
		if(roomList.size() != 0)
		{
			System.out.println();
			displayAllRooms(hotelId);
			String roomId;
			user = new UserUi();
			
			do
			{
				System.out.println("Enter the Room Id of the Room to be deleted");
				roomId = buffer.readLine();
				if(adminService.validateRoomId(roomId))
				{
					int flag = 0;
					for(RoomDetailsBean room : roomList)
					{
						if(room.getRoomId().equals(roomId))
						{
							flag = 1;
							break;
						}
					}
					if(flag == 1)
					{
						break;
					}
					else
					{
						System.err.println("Room does not exist !!!");
					}
				}
				else
				{
					System.out.println("Room ID should start with a 'R' followed by two numbers.");
				}
			}while(action == 1);
			
			adminService = new AdminServiceImplementation();
			return adminService.deleteRoomDetails(roomId);
		}
		
		else
		{
			return "noRooms"; 
		}
	}

	//Viewing List of Hotels
	private List<HotelBean> viewHotels() throws ConnectionException, HotelException {
		adminService = new AdminServiceImplementation();
		return adminService.viewHotels();	
	}

	//Adding Hotel Details
	private String addHotelDetails() throws ConnectionException, HotelException, IOException {

		adminService = new AdminServiceImplementation();
		int action = 1;
		String city,hotelName,address,description,averageRatePerNight,phoneNumber1,phoneNumber2,rating,email,fax;
		double rate = 0;
		do
		{
			System.out.print("Enter City : ");
			city = buffer.readLine();
			if(authenticationService.validateAuthenticationName(city))
			{
				city = initCap(city);
				break;
			}
			System.err.println("City should contain only alphabates and spaces.");
		}while(action == 1);
		do
		{
			System.out.print("Enter Hotel Name : ");
			hotelName = buffer.readLine();
			if(authenticationService.validateAuthenticationName(hotelName))
			{
				hotelName = initCap(hotelName);
				break;
			}
			System.err.println("Hotel Name should contain only alphabates and spaces.");
		}while(action == 1);
		
		do
		{
			System.out.print("Enter Address : ");
			address = buffer.readLine();
			if(adminService.validateAddress(address))
			{
				break;
			}
			System.err.println("Address cannot contain only numbers.");
		}while(action == 1);
		
		do
		{
			System.out.print("Enter the new Description : ");
			description = buffer.readLine();
			if(adminService.validateDescription(description))
			{
				break;
			}
			System.err.println("Description cannot contain only numbers.");
		}while(action == 1);
		
		do
		{
			System.out.print("Enter Average Rate Per Night : ");
			averageRatePerNight = buffer.readLine();
			if(adminService.validateAverageRatePerNight(averageRatePerNight))
			{
				rate = Double.parseDouble(averageRatePerNight);
				break;
			}
			System.err.println("Average Rate Per Night should contain only numbers.");
		}while(action == 1);
		do
		{
			System.out.print("Enter Phone Number 1 : ");
			phoneNumber1 = buffer.readLine();
			if(authenticationService.validateAuthenticationMobileNo(phoneNumber1))
			{
				break;
			}
			System.err.println("Phone Number should be 10 digits long and should contain only digits.");
		}while(action == 1);
		do
		{
			System.out.print("Enter Phone Number 2 : ");
			phoneNumber2 = buffer.readLine();
			if(authenticationService.validateAuthenticationMobileNo(phoneNumber2))
			{
				break;
			}
			System.err.println("Phone Number should be 10 digits long and should contain only digits.");
		}while(action == 1);
		do
		{
			System.out.print("Enter Rating : ");
			rating = buffer.readLine();
			if(adminService.validateRating(rating))
			{
				break;
			}
			System.err.println("Should contain one number and one star");
		}while(action == 1);
		do
		{
			System.out.print("Enter Email : ");
			email = buffer.readLine();
			if(authenticationService.validateAuthenticationEmail(email))
			{
				break;
			}
			System.err.println("Please enter valid email ID.");
		}while(action == 1);
		do
		{
			System.out.print("Enter Fax : ");
			fax = buffer.readLine();
			if(adminService.validateFax(fax))
			{
				break;
			}
			System.err.println("Fax should contain only numbers and should be 12 digits long.");
		}while(action == 1);
		
		
		hotel = new HotelBean();
		hotel.setCity(city);
		hotel.setHotelName(hotelName);
		hotel.setAddress(address);
		hotel.setDescription(description);
		hotel.setAvgRatePerNight(rate);
		hotel.setPhone_no1(phoneNumber1);
		hotel.setPhone_no2(phoneNumber2);
		hotel.setRating(rating);
		hotel.setEmail(email);
		hotel.setFax(fax);
		
		adminService = new AdminServiceImplementation();
		return adminService.addHotelDetails(hotel);
		
	}	
	
	//Modifying Hotel Details
	private String modifyHotelDetails() throws ConnectionException, HotelException, IOException 
	{
		List<HotelBean> hotelList = user.viewHotels();
		displayAllHotels();
		Iterator iterator;
		String hotelId;
		int action = 1;
		do
		{
			System.out.println();
			System.out.print("Enter the hotel ID you want to modify : ");
			hotelId = buffer.readLine();
			int flag = 0;
			if(adminService.validateHotelId(hotelId))
			{
				iterator = hotelList.iterator();
				while(iterator.hasNext())
				{
					HotelBean hotel = (HotelBean) iterator.next();
					if(hotel.getHotelId().equals(hotelId))
					{
						flag = 1;
						break;
					}
				}
				if(flag == 1)
				{
					break;
				}
				else
				{
					System.err.println("Hotel does not exist !!!");
				}
			}
			else
			{
				System.out.println("Hotel ID should start with 'H' followed by two numbers.");
			}
		}while(action == 1);
		
		String modifyHotel = "UPDATE Hotel SET ";
		int c=0;
		do
		{
			String choice;
			int aChoice = 0;
			
			do
			{
				System.out.println();
				System.out.println("Press 1 to modify City");
				System.out.println("Press 2 to modify Hotel Name");
				System.out.println("Press 3 to modify Address");
				System.out.println("Press 4 to modify Description");
				System.out.println("Press 5 to modify Average Rate Per Night");
				System.out.println("Press 6 to modify Phone Number 1");
				System.out.println("Press 7 to modify Phone Number 2");
				System.out.println("Press 8 to modify Rating");
				System.out.println("Press 9 to modify Email");
				System.out.println("Press 10 to modify Fax");
				if(c>0)
				{
					System.out.println("Press 11 if you have completed selecting the columns for modification : ");
				}
				System.out.print("Enter the option for the column you want to modify : ");
				System.out.println();
				choice = buffer.readLine();
				authenticationService = new AuthenticationServiceImplementation();
				System.out.println(authenticationService.validateAuthenticationChoice(choice));
				if(authenticationService.validateAuthenticationChoice(choice))
				{
					aChoice = Integer.parseInt(choice);
					if(aChoice >= 1 && aChoice <= 10)
					{
						c=c+1;
						break;
					}
					else if(aChoice > 11 || aChoice<=0 || (aChoice == 11 && c==0))
					{
						System.err.println("Invalid Option !!!");
					}
					else if(aChoice==11)
					{
						break;
					}
				}
				else
				{
					logg.error("User has entered an incorrect option");
					System.err.println("Please Enter any of the below listed options !!!");
				}
				
			}while(action == 1);
			String city,hotelName,address,description,averageRatePerNight,phoneNumber1,phoneNumber2,rating,email,fax;
			double rate=0;
			switch(aChoice)
			{
			case 1:
				do
				{
					System.out.print("Enter New City : ");
					city = buffer.readLine();
					if(authenticationService.validateAuthenticationName(city))
					{
						city = initCap(city);
						break;
					}
					System.err.println("City should contain only alphabates and spaces and every word should start with a capital letter.");
				}while(action == 1);
				modifyHotel = modifyHotel + "city = '" + city + "',"; break;
			case 2: 
				do
				{
					System.out.print("Enter New Hotel Name : ");
					hotelName = buffer.readLine();
					if(authenticationService.validateAuthenticationName(hotelName))
					{
						hotelName = initCap(hotelName);
						break;
					}
					System.err.println("Hotel Name should contain only alphabates and spaces and every word should start with a capital letter.");
				}while(action == 1);
				modifyHotel = modifyHotel + "hotel_name = '" + hotelName + "',"; break;
			case 3: 
				
				do
				{
					System.out.print("Enter the new Address : ");
					address = buffer.readLine();
					if(adminService.validateAddress(address))
					{
						address = initCap(address);
						break;
					}
					System.err.println("Address cannot contain only numbers.");
				}while(action == 1);
				
				modifyHotel = modifyHotel + "address = '" + address + "',"; break;
			case 4: 
				
				do
				{
					System.out.print("Enter the new Description : ");
					description = buffer.readLine();
					if(adminService.validateDescription(description))
					{
						description = initCap(description);
						break;
					}
					System.err.println("Description cannot contain only numbers.");
				}while(action == 1);
				
				modifyHotel = modifyHotel + "description = '" + description + "',"; break;
			case 5: 
				do
				{
					System.out.print("Enter Average Rate Per Night : ");
					averageRatePerNight = buffer.readLine();
					if(adminService.validateAverageRatePerNight(averageRatePerNight))
					{
						rate = Double.parseDouble(averageRatePerNight);
						break;
					}
					System.err.println("Average Rate Per Night should contain only numbers.");
				}while(action == 1);
				modifyHotel = modifyHotel + "avg_rate_per_night = " + rate + ","; break;
			case 6: 
				do
				{
					System.out.print("Enter Phone Number 1 : ");
					phoneNumber1 = buffer.readLine();
					if(authenticationService.validateAuthenticationMobileNo(phoneNumber1))
					{
						break;
					}
					System.err.println("Phone Number should start with (9|8|7|6) and must be 10 digits long and should contain only digits.");
				}while(action == 1);
				modifyHotel = modifyHotel + "phone_no1 = '" + phoneNumber1 + "',"; break;
			case 7: 
				do
				{
					System.out.print("Enter Phone Number 2 : ");
					phoneNumber2 = buffer.readLine();
					if(authenticationService.validateAuthenticationMobileNo(phoneNumber2))
					{
						break;
					}
					System.err.println("Phone Number should start with (9|8|7|6) and must be 10 digits long and should contain only digits.");
				}while(action == 1);
				modifyHotel = modifyHotel + "phone_no2 = '" + phoneNumber2 + "',"; break;
			case 8: 
				do
				{
					System.out.print("Enter Rating : ");
					rating = buffer.readLine();
					if(adminService.validateRating(rating))
					{
						break;
					}
					System.err.println("Should contain a number and a star (Example: 5*)");
				}while(action == 1);
				modifyHotel = modifyHotel + "rating = '" + rating + "',"; break;
			case 9: 
				do
				{
					System.out.print("Enter Email : ");
					email = buffer.readLine();
					if(authenticationService.validateAuthenticationEmail(email))
					{
						break;
					}
					System.err.println("Please enter valid email ID.");
				}while(action == 1);
				modifyHotel = modifyHotel + "email = '" + email + "',"; break;
			case 10: 
				do
				{
					System.out.print("Enter Fax : ");
					fax = buffer.readLine();
					if(adminService.validateFax(fax))
					{
						break;
					}
					System.err.println("Fax should contain only numbers and should be 12 digits long.");
				}while(action == 1);
				modifyHotel = modifyHotel + "fax = '" + fax + "',"; break;
			case 11: 
				
				String sure;
				int mOption = 0;
				do
				{
					action = 1;
					System.out.println("Are you sure you want to modify the above details?");
					System.out.println("Press 1 to Modify");
					System.out.println("Press 0 to Cancel");
					sure = buffer.readLine();
					authenticationService = new AuthenticationServiceImplementation();
					if(authenticationService.validateAuthenticationChoice(sure))
					{
						mOption = Integer.parseInt(sure);
						if(mOption == 1 || mOption == 0)
						{	
							break;
						}
						else
						{
							System.err.println("Invalid Option !!!");
						}
					}
					else
					{
						logg.error("User has entered an incorrect option");
						System.err.println("Please Enter any of the below listed options !!!");
					}
					
				}while(action == 1);
				
				
				if(mOption == 1)
				{
					modifyHotel = modifyHotel.substring(0,modifyHotel.length()-1) + " WHERE hotel_id = '" + hotelId + "'";
					adminService = new AdminServiceImplementation();
					return adminService.modifyHotelDetails(modifyHotel);	
				}
				else
				{
					break;
				}
			}
		}while(true);
	}
	
	//Deleting Hotel Details
	private String deleteHotelDetails() throws ConnectionException, HotelException, IOException 
	{
		displayAllHotels();
		int action = 1;
		String hotelId;
		do
		{
			System.out.println();
			System.out.println("Enter the Hotel Id of the Hotel to be deleted");
			hotelId = buffer.readLine();
			if(adminService.validateHotelId(hotelId))
			{
				break;
			}
			System.out.println("Hotel ID should start with 'H' followed by two numbers.");
		}while(action == 1);
		
		adminService = new AdminServiceImplementation();
		return adminService.deleteHotelDetails(hotelId);
	}
	
	private String initCap(String usrName)
	{
		String userName= "";
		int len = usrName.length();
		userName = userName + Character.toUpperCase(usrName.charAt(0));
		for(int i=1;i<len;i++)
		{
			char ch = usrName.charAt(i);
			if(ch == ' ')
			{
				userName = userName + " " + Character.toUpperCase(usrName.charAt(i+1));
				i=i+1;
			}
			else
			{
				userName = userName + ch;
			}
		}
		return userName;
	}
}
