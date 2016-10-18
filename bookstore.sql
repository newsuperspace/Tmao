create database bookstore;
use bookstore;
create table category(
	id varchar(100) primary key,
	name varchar(100) not null unique,
	description varchar(255)
);
create table book(
	id varchar(100) primary key,
	name varchar(100) not null,
	author varchar(100),
	money float(8,2),
	description varchar(255),
	path varchar(100),
	oldImageName varchar(100),
	newImageName varchar(100),
	categoryId varchar(100),
	constraint categoryId_fk foreign key(categoryId) references category(id) 
);
create table customer(
	id varchar(100) primary key,
	username varchar(100) not null unique,
	password varchar(100),
	phone varchar(100),
	address varchar(100),
	email varchar(100) not null,
	actived bit(1),
	code varchar(100) unique
);
create table orders(
	ordersnum varchar(100) primary key,
	money float(8,2),
	num int,
	status int,
	customerid varchar(100),
	constraint customerid_fk foreign key(customerid) references customer(id) 
);
create table orderitem(
	id varchar(100) primary key,
	num int,
	money float(8,2),
	bookid varchar(100),
	ordersnum varchar(100),
	constraint bookid_fk foreign key(bookid) references book(id),
	constraint ordersnum_fk foreign key(ordersnum) references orders(ordersnum) 
);