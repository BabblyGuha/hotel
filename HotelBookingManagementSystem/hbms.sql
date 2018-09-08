select * from roomdetails;
select * from bookingdetails;
select * from hotel;
select * from users;
select hotel_id from hotel;

drop sequence room_id_sequence_query;
create sequence room_id_sequence_query start with 1 increment by 1;
select room_id_sequence_query.NEXTVAL from dual;

create sequence booking_id_sequence_query start with 1 increment by 1;
select booking_id_sequence_query.NEXTVAL from dual;

create sequence Employee_id_Sequence_query start with 1 increment by 1;
select Employee_id_Sequence_query.NEXTVAL from dual;

create sequence Customer_id_Sequence_query start with 1 increment by 1;
select Customer_id_Sequence_query.NEXTVAL from dual;

create sequence Hotel_id_Sequence_query start with 1 increment by 1;
select Hotel_id_Sequence_query.NEXTVAL from dual;

alter table roomdetails add foreign key(hotel_id) references hotel(hotel_id);
alter table bookingdetails add foreign key(room_id) references roomdetails(room_id);
alter table bookingdetails add foreign key(user_id) references users(user_id);
alter table roomdetails add constraint hu unique(room_no);

delete from roomdetails;
delete from hotel where rating = '***';

SELECT booking_id,room_id,b.user_id,user_name,password,mobile_no,phone,address,email,to_char(booked_from,'dd/MM/YYYY'),to_char(booked_to,'dd/MM/YYYY'),no_of_adults,no_of_children,amount from Users u, BookingDetails b WHERE b.user_id=u.user_id and room_id in (SELECT room_id FROM RoomDetails WHERE hotel_id = 'H41')
SELECT 'E'||TO_CHAR(Employee_id_Sequence_Query.CURRVAL,'FM00'),role FROM Users;

insert into USERS values ('A01','Aa@123','Admin','Admin','9852014763','552360','Kharagpur','Ad@gmail.com');

update roomdetails set room_id = 'R09' where room_id = 'R9';









