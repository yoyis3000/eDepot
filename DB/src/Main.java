
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
		Statement stmt= Connect.createStatement();
		//Scanner reader = new Scanner(System.in);
		System.out.println("Testing if this actually works currently");

	   String sql = "INSERT INTO ship_item " +
	    			 "VALUES ('101','Puma','12309',8)";
	   /*stmt.executeUpdate(sql);
	   //------butters shipping comany 
	   sql = "INSERT INTO Shipping_Notice " +
			   "VALUES ('333','Butters')";
	   stmt.executeUpdate(sql);
	   
	   sql = "INSERT INTO Shipping_List " + 
			   "VALUES('101','Puma','12309','333',4)";
	   stmt.executeUpdate(sql);
	   
	   sql = "INSERT INTO Shipping_List " +
			   "VALUES('101','FakeStuff','777','333',7)";
	   stmt.executeUpdate(sql);
	   
	   sql = "INSERT INTO Shipping_List " +
			   "VALUES('101','Made_in_China','696','333',1)"; 
	   stmt.executeUpdate(sql);
	   			//-------butters company end Stan Starts
	   sql = "INSERT INTO SHIPPING_NOTICE " + 
			   "VALUES ('313','Stan')";
	   stmt.executeUpdate(sql);
	   
	   sql = "INSERT INTO Shipping_List " + 
			   "VALUES('101','Adidas','123','313',2)";
	   stmt.executeUpdate(sql);
	   
	   sql = "INSERT INTO Shipping_List " +
			   "VALUES('101','FakeStuff','787','313',2)";
	   stmt.executeUpdate(sql);*/
	   sql = "SELECT SHIPPING_LIST.MANUFACTURER FROM SHIPPING_NOTICE JOIN SHIPPING_LIST"
	   		+ " ON SHIPPING_NOTICE.SNI = SHIPPING_LIST.SNI WHERE SHIPPING_LIST.SNI = '333'";
	   int random = 500;
	   PreparedStatement p = Connect.prepareStatement(sql);
	   //p.setInt(1,random);
	   
	   ResultSet rs = p.executeQuery();
	   
	   while(rs.next()){
	   System.out.println(rs.getString(1));
	   }		
	   
	   
	   

	

	   
		
		
	}
}
