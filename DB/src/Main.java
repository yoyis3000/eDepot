
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
	   /* Operations that I have to do in Edepot so I can work on Emart
	    * which is super hard
	    * 1. Baby Steps, perform: checking quantity of a given Item number
	    */
	   int option;
	   Scanner read = new Scanner(System.in);
	   System.out.println("What would you like to do today?");
	   System.out.println("Enter 3 to check an Item quantity");
	   option =  read.nextInt();
	   switch(option){
	   case 3:
		   try{
			   String stock;
			   System.out.println("Please Insert Stock Number:");
			   stock = read.next();
			   sql = "SELECT QUANTITY FROM WAREHOUSE_ITEM WHERE '"+stock+"'= STOCK_NUM";
			   p = Connect.prepareStatement(sql);
			   rs = p.executeQuery();
			   if(rs.next())
				   System.out.println(rs.getString(1));
			   rs.close();
		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }
	   
	   
	   				 
	   				 }
	   
	   
	   		
	   
	   
	   

	

	   
		
		
	}
}
