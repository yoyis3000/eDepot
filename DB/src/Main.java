
import java.util.Scanner;
import java.sql.*;
import java.util.*;
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
	   stmt.executeUpdate(sql);
	   
	   *
	   							*/
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
	   System.out.println("Enter 4 If you are the manager");
	   System.out.println("Enter 5 if you work at eDepot"
	   		+ " and want to see if any shipments arrived");
	   
	   
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
				   System.out.println("Quantity is :"+rs.getString(1));
			   
			   else
				   System.out.println("Sorry, your item doesn't exist");
			   rs.close();
				   
		   }
		   catch(Exception e){
			   e.printStackTrace();
		   }
		   break;
	   case 4:
		   //Going to send a shipping Notice
			   System.out.println("Sup bro what would you like to do today?");
			   System.out.println("Enter 1 if you want order some stock");
			   System.out.println("Enter 2 if you want to change a price"
			   		+ "of an item");
			   option =  read.nextInt();
			   switch(option){
			   
			   case 1:
			   	   String Manu,Model,ShipName,yn;
				   String quantity;
				  
				   Random r = new Random();
				   int new_sni = r.nextInt((9999-1000)+1) + 1000;
				   Integer.toString(new_sni);
				   
				   System.out.println("Please Insert Shipping Comany Name:");
				   ShipName = read.next();
				   try{
					   sql = "INSERT INTO SHIPPING_NOTICE "//try catch
				   		+ "VALUES ('"+new_sni+"','"+ShipName+"')";
					 //  String sql1 = "INSERT INTO SHIPPING_NOTICE "
					 //  		+ "VALUES ('34234','Pollos')";
					   System.out.println(sql);
					  stmt.executeQuery(sql);
				   }
				   catch(Exception e){
					   e.printStackTrace();
				   }
				   
				   
				   System.out.println("For each item, please input Manufacturer Name,");
				   System.out.println("Model Number,");
				   System.out.println("and Quantity when prompted");
				   
				   System.out.println("Please Insert Manufacturer Name:");
				   Manu = read.next();
				   
				   System.out.println("Please Insert Model Number:");
				   Model = read.next();
				   
				   System.out.println("Please Insert Desired Quantity:");
				   quantity = read.next();
				   //sql statement goes here
				   try{
					   sql = "INSERT INTO Shipping_List " + //try catch
							   "VALUES('"+Manu+"','"+Model+"','"+new_sni+"',"+quantity+")";
					   stmt.executeUpdate(sql);
				   		}
		   catch(Exception e){
			   e.printStackTrace();
		   				}
				 
				   
				   do{
					   System.out.println("Would you like to enter an additional item?");
					   yn = read.next();
					   
					   if(yn.equals("no"))
						   break;
					   else{
						   System.out.println("Please Insert Manufacturer Name:");
						   Manu = read.next();
						   
						   System.out.println("Please Insert Model Number:");
						   Model = read.next();
						   
						   System.out.println("Please Insert Current Quantity:");
						   quantity = read.next();
						   try{
							   sql = "INSERT INTO Shipping_List " + //try catch
									   "VALUES('"+Manu+"','"+Model+"','"+new_sni+"',"+quantity+")";
							   stmt.executeUpdate(sql);
						   		}
				   catch(Exception e){
					   e.printStackTrace();
				   				}
						   //sql statement goes here
					   	   }
					   
				   }while(1==1);
				   System.out.println("Order has been sent!");
				   ResultSet res = Operate.Send_Ship_Notice(Integer.toString(new_sni),Model,Manu,quantity);
				 
				   if(res ==null){
					   r = new Random();
					   int new_stock_num = r.nextInt((9999-1000)+1) + 1000;
					   Integer.toString(new_stock_num);
					   sql = "INSERT INTO Warehouse_item " + //try catch
							   "VALUES('"+Model+"','"+Manu+"','"+new_stock_num+"','"+quantity+"','8','NULL','"+quantity+
							   "',NULL)";
					   stmt.executeUpdate(sql);
							   } 
				   else{
					   sql = "SELECT REPLENISHMENT FROM WAREHOUSE_ITEM WHERE MANUFACTURER='"+Manu+
							   "' AND MODEL_NUM='"+Model+"'";
					   try{
						   PreparedStatement pe = Connect.prepareStatement(sql);
						   ResultSet s = pe.executeQuery();
						   if(s.next()){
							   int rep = Integer.valueOf(s.getString(1));
							   rep = rep + Integer.valueOf(quantity);
							sql = "UPDATE WAREHOUSE_ITEM"
									+ " SET REPLENISHMENT = "+rep+""
											+ " WHERE MANUFACTURER='"+Manu+
							   "' AND MODEL_NUM='"+Model+"'";
							stmt.executeUpdate(sql);
						   }
					   }catch(Exception e){
						   e.printStackTrace();
					   }
				   }
				   break;
			   case 2:
				   String nprice;
				   String snum;
				   System.out.println("Insert Stock Number:");
				   snum = read.next();
				   System.out.println("Insert New Price:");
				   nprice = read.next();
				   sql = "UPDATE Catalog_Items SET PRICE= "+nprice+" WHERE"
				   		+ " STOCKNUMBER='"+snum+"'";
				   System.out.println(sql);
				   try{
						stmt.executeUpdate(sql);
						System.out.println("Price has succesfully changed!");
				   }
				   catch(Exception e){
					   e.printStackTrace();
				   }
				   break;
				   

					   
			   }
			   break;
	   case 5:
		
			//select fname from MyTbl where rownum = 1
		    sql = "SELECT SNI FROM SHIPPING_LIST WHERE ROWNUM = 1";
		 
		   String sni;
		   ResultSet r = Operate.checkShipping(sql);
		   if(r==null){
			   System.out.println("Sorry No Shipments Today");
			   break;
		   }
		   else{
			   sni = r.getString(1);
			   System.out.println(sni+"ahhh----hh");
			   String tempsql,shipquant,warequant, man,mod;
			   shipquant = "the";
			   warequant = "the";
			   man = "the";
			   mod = "the";
			   
			   int butters;
			   while(r!=null){
				   tempsql = "SELECT QUANTITY FROM SHIPPING_LIST "
				   		+ "WHERE SNI = '"+sni+"'";
				   try{
					   PreparedStatement pe = Connect.prepareStatement(tempsql);
					   r = pe.executeQuery();
					   if(r.next()){
						   shipquant = r.getString(1);
						   System.out.println(shipquant);
					   }
						   
					   r.close();
					   
					   
					   tempsql = "SELECT MANUFACTURER FROM SHIPPING_LIST WHERE SNI = '"+sni+"'";
					   pe = Connect.prepareStatement(tempsql);
					   r = pe.executeQuery();
					   if(r.next())
						   man = r.getString(1);
					   r.close();
					  
					   
					   tempsql = "SELECT MODEL_NUM FROM SHIPPING_LIST WHERE SNI = '"+sni+"'";
					   pe = Connect.prepareStatement(tempsql);
					   r = pe.executeQuery();
					   if(r.next()){
						   mod = r.getString(1);
						   System.out.println("mod is " + mod);
						   
					   }
						   
					   r.close();
					   tempsql = "SELECT QUANTITY FROM WAREHOUSE_ITEM WHERE MANUFACTURER = '"+man+"' "
					   		+ "AND MODEL_NUM='"+mod+"'";
					   pe = Connect.prepareStatement(tempsql);
					   r = pe.executeQuery();
					   if(r.next()){
						   warequant = r.getString(1);
						   System.out.println("--------ahhhhhh"+warequant);
					   }
						   
					   r.close();
					   int samount = Integer.valueOf(shipquant) + Integer.valueOf(warequant);
					   
					   
					   tempsql = "UPDATE WAREHOUSE_ITEM"
								+ " SET REPLENISHMENT = 0, QUANTITY ="+samount+""
										+ " WHERE MANUFACTURER='"+man+
						   "' AND MODEL_NUM='"+mod;
					   
					   tempsql = "DELETE FROM SHIPPING_LIST WHERE SNI = '"+sni+"'";
					   stmt.executeUpdate(tempsql);
					  
					   
				   }catch(Exception e){
					   e.printStackTrace();
				   }
			   }
			   tempsql = "DELETE * FROM SHIPPING_NOTICE";
			   stmt.executeUpdate(tempsql);
			   r = Operate.checkShipping(sql);
		   }
		  // while (r.next())
		  /* {
		      System.out.print("Column 1 returned ");
		      System.out.println(rs.getString(1));
		   }*/
			   
		   r.close();
		   break;
	   
	   				 
	   				 }
	   
	   
	   		
	   
	   
	   

	

	   
		
		
	}
}
