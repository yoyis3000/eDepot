import java.sql.*;
import java.text.SimpleDateFormat;

public class Op {
	public Connection con;
	public Op(Connection con_1){
		con = con_1;
	}
	public ResultSet Send_Ship_Notice(String sni, String mod, String man, String q){
		/*Receive a shipping notice from a manufacturer; each notice 
		 * includes a unique shipping notice identiﬁer,
		  the shipping company name, and a list of items 
		  (manufacturer, model number, quantity). In processing a
		  shipping notice, the warehouse either ﬁnds its stock number, 
		  or assign a new one if it is a new product.
		  The quantities in the shipping notice need to add to the items’ 
		  replenishment entries to indicate future
		  availability.
*/
		String st = "SELECT STOCK_NUM FROM WAREHOUSE_ITEM WHERE MANUFACTURER='"
				+ ""+man+"' AND MODEL_NUM='"+mod+"'";
		//System.out.println(st);
		try{
			PreparedStatement p = con.prepareStatement(st);
			ResultSet rs = p.executeQuery();
			while (rs.next()){
				return rs;
			}
			rs.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
		
		
	}

}
