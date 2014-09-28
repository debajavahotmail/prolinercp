package com.proline.util.mail;

public interface Constants {
	
	String SUCCESS="SUCCESS";
	String DBFAIL="FAILURE FROM DATABASE";
	String NOCONN="NO CONNECTION TO DATABASE";
	String LOGINFAILURE="ERROR IN UID OR PASSWORD TRY AGAIN OR REGISTER";
	String LOGGEDUSER="LOGGEDUSER";
	String NOTLOGGEDUSER="Not a valid user,please register";
	String DRIVERNAME="com.mysql.jdbc.Driver";
	String ORACLEDRIVERNAME="oracle.jdbc.driver.OracleDriver";
    String URL="jdbc:mysql://localhost:3306/loyalty";
    String ORACLEURL="jdbc:oracle:thin:@192.168.100.13:1521:ortd";
    String UID="root";
    String PWD="java";
    String ORACLEUID="java_ora";
    String ORACLEPWD="java_ora";
	String FAILURE = "error while inserting";
	String UPDATEFAILUE = "phot updation Failes";
	String FAILURE_TO_SENDMAIL = "error while sending mail";
	String FAILURE_TO_SENDREQUEST = "failure send friend request";
	String INSERTIONFAILURE = "insertion failure";
	String UPDATIONFAILURE = "updation failure";

}
