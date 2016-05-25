package EcoRecycle;

// Import required packages
import java.sql.*;

public class connectToDB{
	// JDBC driver name and database URL
 static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";  
 static final String DB_URL = "jdbc:mysql://localhost/OOAD";

 //  Database credentials
 public static final String USER = "root";
public  static final String PASS = "Roopa";
 public Connection conn = null;
 public Statement stmt = null;
 
public connectToDB() {
 
 try{
    //Register JDBC driver
    Class.forName("com.mysql.jdbc.Driver");

    //Open a connection
 //   System.out.println("Connecting to database...");
    conn = DriverManager.getConnection(DB_URL,USER,PASS);

 }
 catch(Exception e){
    //Handle errors for Class.forName
    e.printStackTrace();
 }
}//end main
}