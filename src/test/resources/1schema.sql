create table resident (
 id long generated by default as identity,
 name varchar(255),
 is_free BOOLEAN,
 price_per_hour DECIMAL,
 min_price DECIMAL,
 primary key (id)
);

CREATE TABLE parking_spot (
  id long generated by default as identity,
  spot_number varchar(255),
  status varchar(32),
  resident_id long,
  primary key (id)
);

create table user_parking_reservation (
 id long generated by default as identity,
 user_id long,
 parking_spot_id long,
 book_start_time date,
 end_time date,
 primary key (id)
);


--insert values in resident
insert into resident
values(1,'resident1', true, 0.0, 0.0);

insert into resident
values(2,'resident2', false, 10.0, 5.0);

--insert values in parking_spot

insert into parking_spot
values(1,'A1', 'FREE', 1);

insert into parking_spot
values(2,'A2', 'FREE', 1);

insert into parking_spot
values(3,'A3', 'BOOKED', 1);

insert into user_parking_reservation
values(10, 2, 3, '2023-11-20', null);



