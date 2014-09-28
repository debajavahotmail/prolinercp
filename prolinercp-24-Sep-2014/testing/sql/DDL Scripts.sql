-- SQL Scripts for Testing -----------
------------ Dropping Tables -------------------
drop table ProlineEmployee;
drop table ProlinePersons;
drop table ProlineSalary;
drop table ProlineProjects;
drop table ProlineAddress;
drop table ProlinePayments;
drop table ProlineCustomers;
drop table ProlineDept;
drop table ProlineWorldWide;
drop table ProlineTechnology;
drop table ProlinePartners;
drop table ProlineProduct;
drop table POPULATION;
drop table banktransaction;

------------- Table Creation -----------------------------
create table banktransaction
(
  id number(3),
  transactionName varchar2(20),
  transactionType varchar2(20),
  transactionCount number(5),
  status varchar2(40)
);

create table POPULATION 
(
  id NUMBER(3),
  cityname VARCHAR2(20),
  percapitaIncome NUMBER(3),
  educationPercent NUMBER(3),
  populationNo NUMBER(5),
  capital VARCHAR2(20) 
);

create table ProlineEmployee
(
	 id NUMBER(4,0),
	 firstName VARCHAR2(40),
	 lastName VARCHAR2(40),
	 age number(2,0),
	 hiredate date,
	 sal NUMBER(7,2),
	 deptNo NUMBER(2,0)
);

create table ProlinePersons
(
	id NUMBER(4,0),
	firstName VARCHAR2(40),
	lastName VARCHAR2(40),
	age number(2,0),
	address VARCHAR2(40),
	city VARCHAR2(40)
);

create table ProlineSalary
(
	id NUMBER(4,0),
	firstName VARCHAR2(20),
	type VARCHAR2(20),
	basic NUMBER(7,2),
	hra NUMBER(7,2),
	da NUMBER(7,2),
	extra NUMBER(7,2)
);

create table ProlineProjects
(
	id NUMBER(4,0),
	projectId NUMBER(4,0),
	projectName VARCHAR2(40),
	manager VARCHAR2(20),
	projectType VARCHAR2(10),
	cost NUMBER(4,0)
);

create table ProlineAddress
(
	id NUMBER(4,0),
	adrsId NUMBER(7,0),
	empName VARCHAR2(40),
	streetName VARCHAR2(30),
	city VARCHAR2(20),
	state VARCHAR2(10),
	pin NUMBER(8,0)
);

create table ProlinePayments
(
	id NUMBER(4,0),
	xref NUMBER(9,0),
	empName VARCHAR2(40),
	paymentType VARCHAR2(30),
	paymentCode VARCHAR2(20),
	bankName VARCHAR2(20),
	beneficiary VARCHAR2(20)
);

create table ProlineCustomers
(
	id NUMBER(4,0),
	name VARCHAR2(40),
	custType VARCHAR2(30),
	address VARCHAR2(20),
	managedBy VARCHAR2(40)
);

create table ProlineDept
(
	id NUMBER(4,0),
	deptNo NUMBER(2,0),
	empName VARCHAR2(40),
	deptType VARCHAR2(30),
	deptName VARCHAR2(20)
);	

create table ProlineWorldWide
(
	id NUMBER(4,0),
	deptNo NUMBER(2,0),
	officeName VARCHAR2(30),
	managedBy VARCHAR2(30),
	address VARCHAR2(30),
	country VARCHAR2(20)
);	

create table ProlineTechnology
(
	id NUMBER(4,0),
	deptNo NUMBER(2,0),
	name VARCHAR2(30),
	headName VARCHAR2(30),
	productName VARCHAR2(30)
);

create table ProlinePartners
(
	id NUMBER(4,0),
	partnerId NUMBER(2,0),
	partneName VARCHAR2(30),
	managerName VARCHAR2(30),
	productName VARCHAR2(30)
);

create table ProlineProduct
(
	id NUMBER(4,0),
	prodId NUMBER(4,0),
	deptNo NUMBER(2,0),
	techName VARCHAR2(30),
	headName VARCHAR2(30),
	productName VARCHAR2(30)
);	

--------------- INSERTION ----------------

insert into ProlineEmployee values(1,'Swamy','Rao',23,to_date('23-01-13','DD-MM-RR'),3000,10);
insert into ProlineEmployee values(2,'Sneha','Patil',25,to_date('23-01-13','DD-MM-RR'),4000,20);
insert into ProlineEmployee values(3,'Arun','Murugesan',28,to_date('23-01-13','DD-MM-RR'),5000,30);
insert into ProlineEmployee values(4,'Srinath','Kunchipudi',20,to_date('23-01-13','DD-MM-RR'),6000,40);
insert into ProlineEmployee values(5,'Tofique','Khan',21,to_date('23-01-13','DD-MM-RR'),7000,50);
insert into ProlineEmployee values(6,'Prashant','Somsundar',22,to_date('23-01-13','DD-MM-RR'),8000,60);
insert into ProlineEmployee values(7,'Saurabh','Sahay',25,to_date('23-01-13','DD-MM-RR'),9000,70);
insert into ProlineEmployee values(8,'John','Abraham',40,to_date('23-01-13','DD-MM-RR'),1000,80);
insert into ProlineEmployee values(9,'Vidya','Balan',37,to_date('23-01-13','DD-MM-RR'),2000,90);
insert into ProlineEmployee values(10,'Rani','Mukherjee',39,to_date('23-01-13','DD-MM-RR'),3000,10);
commit;	


insert into ProlinePersons values(1,'Swamy','Rao',23,'Karnatak','Bengaluru');
insert into ProlinePersons values(2,'Sneha','Patil',23,'Maharasta','Mumbai');
insert into ProlinePersons values(3,'Arun','Murugesan',23,'TamilNadu','Chennai');
insert into ProlinePersons values(4,'Srinath','Kunchipudi',23,'Karnatak','Bengaluru');
insert into ProlinePersons values(5,'Tofique','Khan',23,'Karnatak','Belgaon');
insert into ProlinePersons values(6,'Prashant','Somsundar',23,'Karnatak','Bengaluru');
insert into ProlinePersons values(7,'Saurabh','Sahay',23,'Bihar','Patna');
insert into ProlinePersons values(8,'John','Abraham',23,'Kerala','Kotayam');
insert into ProlinePersons values(9,'Vidya','Balan',23,'Maharasta','Mumbai');
insert into ProlinePersons values(10,'Rani','Mukherjee',23,'West Bengal','Kolkatta');	
commit;		


insert into ProlineSalary values(1,'Rani','Contract',1000,500,200,10);	
insert into ProlineSalary values(2,'Swamy','Permanent',1000,500,200,10);
insert into ProlineSalary values(3,'Sneha','Permanent',1000,500,200,10);
insert into ProlineSalary values(4,'Arun','Permanent',1000,500,200,10);
insert into ProlineSalary values(5,'Srinath','Permanent',1000,500,200,10);
insert into ProlineSalary values(6,'Tofique','Permanent',1000,500,200,10);
insert into ProlineSalary values(7,'Prashant','Permanent',1000,500,200,10);
insert into ProlineSalary values(8,'Saurabh','Permanent',1000,500,200,10);
insert into ProlineSalary values(9,'John','Contract',1000,500,200,10);
insert into ProlineSalary values(10,'Vidya','Contract',1000,500,200,10);	
commit;		


insert into ProlineProjects values(1,1,'Project-1','Vidya','Fixed Cost',5000);
insert into ProlineProjects values(2,2,'Project-2','Swamy','Fixed Cost',5000);	
insert into ProlineProjects values(3,3,'Project-3','John','Time based',5000);	
insert into ProlineProjects values(4,4,'Project-4','Sneha','Fixed Cost',5000);	
insert into ProlineProjects values(5,5,'Project-5','Srinath','Fixed Cost',5000);	
insert into ProlineProjects values(6,6,'Project-6','Arun','Fixed Cost',5000);	
insert into ProlineProjects values(7,7,'Project-7','Prashant','Time based',5000);	
insert into ProlineProjects values(8,8,'Project-8','Saurabh','Fixed Cost',5000);	
insert into ProlineProjects values(9,9,'Project-9','Tofique','Time based',5000);	
insert into ProlineProjects values(10,10,'Project-10','Rani','Fixed Cost',5000);		
commit;	


insert into ProlineAddress values(1,1,'Vidya','JP Nagar','Bengaluru','Karnatak',123456);
insert into ProlineAddress values(2,2,'Swamy','JP Nagar','Bengaluru','Karnatak',123456);	
insert into ProlineAddress values(3,3,'John','JP Nagar','Bengaluru','Karnatak',123456);	
insert into ProlineAddress values(4,4,'Sneha','BTM','Bengaluru','Karnatak',123456);	
insert into ProlineAddress values(5,5,'Srinath','BTM','Bengaluru','Karnatak',123456);	
insert into ProlineAddress values(6,6,'Arun','BTM','Bengaluru','Karnatak',123456);	
insert into ProlineAddress values(7,7,'Prashant','Banashankari','Bengaluru','Karnatak',123456);	
insert into ProlineAddress values(8,8,'Saurabh','Banashankari','Bengaluru','Karnatak',123456);	
insert into ProlineAddress values(9,9,'Tofique','Banashankari','Bengaluru','Karnatak',123456);	
insert into ProlineAddress values(10,10,'Rani','Majestic','Bengaluru','Karnatak',123456);		
commit;	


insert into ProlinePayments values(1,1234567,'Rani','Swift','BA007','HDFC','Swamy');
insert into ProlinePayments values(2,1234567,'John','Swift','BA007','SBI','Rani');
insert into ProlinePayments values(3,1234567,'Vidya','Swift','BA007','ICICI','Bipasha');
insert into ProlinePayments values(4,1234567,'Srinath','Swift','BA007','HDFC','Arun');
insert into ProlinePayments values(5,1234567,'Prashant','Swift','BA007','HDFC','Saurabh');
insert into ProlinePayments values(6,1234567,'Tofique','Swift','BA007','CITI','Sneha');
insert into ProlinePayments values(7,1234567,'Saurabh','Swift','BA007','Danske','Srinath');
insert into ProlinePayments values(8,1234567,'Sneha','Swift','BA007','HDFC','Manoj');
insert into ProlinePayments values(9,1234567,'Arun','Swift','BA007','DNB','Namita');
insert into ProlinePayments values(10,1234567,'Swamy','Swift','BA007','HDFC','Swamy');	
commit;	


insert into ProlineCustomers values(1,'IBM','Gold','Bengaluru','Srinath');
insert into ProlineCustomers values(2,'Microsoft','Platinum','Bengaluru','Swamy');
insert into ProlineCustomers values(3,'Google','Silver','Bengaluru','Tofique');
insert into ProlineCustomers values(4,'Accenture','Gold','Bengaluru','Arun');
insert into ProlineCustomers values(5,'Infosys','Gold','Bengaluru','John');
insert into ProlineCustomers values(6,'Mahindra','Silver','Bengaluru','Vidya');
insert into ProlineCustomers values(7,'HDFC','Diamond','Bengaluru','Rani');
insert into ProlineCustomers values(8,'SBI','Gold','Bengaluru','Saurabh');
insert into ProlineCustomers values(9,'ICICI','Platinum','Bengaluru','Sneha');
insert into ProlineCustomers values(10,'Danske','Gold','Bengaluru','Prashnat');	
commit;	


insert into ProlineDept values(1,10,'Prashnat','IT','Development');	
insert into ProlineDept values(2,20,'Swamy','Finance','Fincorp');
insert into ProlineDept values(3,30,'Arun','IT','Testing');	
insert into ProlineDept values(4,40,'Sneha','IT','RND');	
insert into ProlineDept values(5,50,'Tofique','Sales','SalesnMarketing');	
insert into ProlineDept values(6,60,'Srinath','Presale','Presale');	
insert into ProlineDept values(7,70,'Saurabh','Others','House Keeping');	
insert into ProlineDept values(8,80,'John','Others','Security');	
insert into ProlineDept values(9,90,'Vidya','Customer','Customer Dealing');	
insert into ProlineDept values(10,10,'Rani','Customer','Customer Dealing');		
commit;	


insert into ProlineWorldWide values(1,10,'Office-1','Rani','Helsinki','Finland');
insert into ProlineWorldWide values(2,20,'Office-2','John','Espoo','Finland');
insert into ProlineWorldWide values(3,30,'Office-3','Vidya','Alborg','Denmark');
insert into ProlineWorldWide values(4,40,'Office-4','Swamy','California','US');
insert into ProlineWorldWide values(5,50,'Office-5','Arun','Oslo','Norway');
insert into ProlineWorldWide values(6,60,'Office-6','Prashant','Teheran','Iran');
insert into ProlineWorldWide values(7,70,'Office-7','Saurabh','Delhi','India');
insert into ProlineWorldWide values(8,80,'Office-8','Sneha','Amsterdam','Netherland');
insert into ProlineWorldWide values(9,90,'Office-9','Srinath','Paris','France');
insert into ProlineWorldWide values(10,10,'Office-11','Tofique','Berlin','Germany');	
commit;	


insert into ProlineTechnology values(1,10,'Java','Tofique','Product-1');
insert into ProlineTechnology values(2,20,'Net','Swamy','Product-2');
insert into ProlineTechnology values(3,30,'C++','Sneha','Product-3');
insert into ProlineTechnology values(4,40,'Java','Srinath','Product-4');
insert into ProlineTechnology values(5,50,'Oracle','Saurabh','Product-5');
insert into ProlineTechnology values(6,60,'Mysql','Arun','Product-6');
insert into ProlineTechnology values(7,70,'SAP','John','Product-7');
insert into ProlineTechnology values(8,80,'Python','Rani','Product-8');
insert into ProlineTechnology values(9,90,'Net','Prashant','Product-99');
insert into ProlineTechnology values(10,10,'Photoshop','Vidya','Product-10');	
commit;	


insert into ProlinePartners values(1,10,'IBM','Vidya','Product-10');
insert into ProlinePartners values(2,20,'IBM','Rani','Product-10');
insert into ProlinePartners values(3,30,'IBM','John','Product-10');
insert into ProlinePartners values(4,40,'IBM','Srinath','Product-10');
insert into ProlinePartners values(5,50,'IBM','Swamy','Product-10');
insert into ProlinePartners values(6,60,'IBM','Sneha','Product-10');
insert into ProlinePartners values(7,70,'IBM','Saurabh','Product-10');
insert into ProlinePartners values(8,80,'IBM','Tofique','Product-10');
insert into ProlinePartners values(9,90,'IBM','Prashant','Product-10');
insert into ProlinePartners values(10,10,'IBM','Arun','Product-10');	
commit;	


insert into ProlineProduct values(1,1,10,'Java','Arun','Product-1');
insert into ProlineProduct values(2,2,20,'Net','Swamy','Product-3');
insert into ProlineProduct values(3,3,30,'Python','Srinath','Product-2');
insert into ProlineProduct values(4,4,40,'Oracle','Prashant','Product-4');
insert into ProlineProduct values(5,5,50,'MySQL','Tofique','Product-5');
insert into ProlineProduct values(6,6,60,'Java','Sneha','Product-6');
insert into ProlineProduct values(7,7,70,'Net','Saurabh','Product-7');
insert into ProlineProduct values(8,8,80,'SAP','John','Product-8');
insert into ProlineProduct values(9,9,90,'Java','Vidya','Product-9');
insert into ProlineProduct values(10,10,10,'C++','Rani','Product-10');
commit;	

insert into POPULATION values (1,'New Delhi',120,20,40000,'Delhi');
insert into POPULATION values (2,'Maharashtra',150,60,70000,'Mumbai');
insert into POPULATION values (3,'TamilNadu',100,30,75000,'Chennai');
insert into POPULATION values (4,'Odisha',180,15,60000,'Bhubaneswar');
insert into POPULATION values (5,'Karnataka',190,90,88000,'Bengaluru');
commit;

insert into banktransaction values(1, 'transaction-1' , 'Successfull' , 100,'Transaction Passed Succesfully');
insert into banktransaction values(1, 'transaction-2' , 'Pending' , 20,'Transaction Needs approval');
insert into banktransaction values(1, 'transaction-3' , 'Held' , 45,'Due to suspense account');
insert into banktransaction values(1, 'transaction-4' , 'Failed' , 15,'Error processing transaction');
commit;
------------ SELECT QUERIES ----------------

select * from ProlineEmployee;
select * from ProlinePersons;
select * from ProlineSalary;
select * from ProlineProjects;
select * from ProlineAddress;
select * from ProlinePayments;
select * from ProlineCustomers;
select * from ProlineDept;
select * from ProlineWorldWide;
select * from ProlineTechnology;
select * from ProlinePartners;
select * from ProlineProduct;
select * from POPULATION;
select CITYNAME,POPULATIONNO from POPULATION;
select * from banktransaction;
select TRANSACTIONTYPE,TRANSACTIONCOUNT from BANKTRANSACTION;