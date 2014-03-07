
import java.util.Scanner;
import java.sql.*;

public class Main {
	static Connection Connect;
	static Op Operate;
	
	public static void main (String[] args) throws SQLException {
		try{
			DriverManager.registerDriver(new oracle.jdbc.OracleDriver());
		   }
		catch(Exception e){
			e.printStackTrace();
							}
		String stConnect = "jdbc:oracle:thin:@uml.cs.ucsb.edu:1521:xe";
		String stUser = "georgebarrios";
		String stPass = "5228986";
		Connect = DriverManager.getConnection(stConnect,stUser,stPass);
		Operate = new Op(Connect);
		//Statement stmt= Connect.createStatement();
		//Scanner reader = new Scanner(System.in);
		System.out.println("Testing if this actually works currently");

	    //String sql = "INSERT INTO ship_item " +
	    	//		 "VALUES ('101','Puma','111',1,3,'Guatemala',5,8)";
	    System.out.println("test git");
	   // stmt.executeUpdate(sql);

	    String sql = "INSERT INTO ship_item " +
	    			 "VALUES ('101','Puma','111',1,3,'Guatemala',5,8)";
	   // stmt.executeUpdate(sql);

	   
		
		
	}
}
