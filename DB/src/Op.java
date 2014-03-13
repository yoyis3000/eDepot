import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
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
	
	public ResultSet checkShipping(String s){
		try{
			PreparedStatement p = con.prepareStatement(s);
			ResultSet r = p.executeQuery();
			while (r.next()){
				return r;
			}
			r.close();
		
	
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	public boolean CheckAndProcess(){
		String sql = "SELECT COUNT (CART_ID) FROM CART_STUFF";
		int ref=1;
		
		String stock = "the";
		String man = "the";
		String mod = "the";
		Map<String,ArrayList<Integer>> ManCount = new HashMap<String,ArrayList<Integer>>();
		ArrayList<Integer> Qc_sn_manref = new ArrayList<Integer>(4);
		
		int table_limit = 4;
		
				try{
					Statement st= con.createStatement();
					PreparedStatement p = con.prepareStatement(sql);
					ResultSet r = p.executeQuery();
					if(r.next())
						table_limit = r.getInt(1);
					r.close();
					String cart_quantity = "the",
					ware_quantity="the";
					//Qiff is the difference between Qwarehouse - QCart (q refers to quantity)
					//OriginalQw is just an array of original Qwarehouse
					int[] Qdiff = new int[table_limit];
					int[] OriginalQw = new int[table_limit];
					int[] stock_list = new int[table_limit];
					for(int i = 1; i<= table_limit; i++){
						sql ="SELECT STOCKNUMBER FROM CART_STUFF WHERE ROWNUM = "+Integer.toString(i); 
						p = con.prepareStatement(sql);
						r = p.executeQuery();
						if(r.next())
							stock = r.getString(1);
						r.close();
						stock_list[i] = Integer.valueOf(stock);
						
						sql = "SELECT QUANTITY FROM CART_STUFF WHERE ROWNUM = "+Integer.toString(i);
						p = con.prepareStatement(sql);
						r = p.executeQuery();
						if(r.next())
							cart_quantity = r.getString(1);
						r.close();
						
						sql = "SELECT QUANTITY FROM WAREHOUSE_ITEM WHERE STOCK_NUM ="
								+ "'"+stock+"'";
						p = con.prepareStatement(sql);
						r = p.executeQuery();
						if(r.next())
							ware_quantity = r.getString(1);
						r.close();
						
						if(Integer.valueOf(cart_quantity) > Integer.valueOf(ware_quantity))
							return false;
						Qdiff[i] = Integer.valueOf(ware_quantity) - Integer.valueOf(cart_quantity);
						OriginalQw[i] = Integer.valueOf(ware_quantity);
						
						sql = "SELECT MANUFACTURER FROM WAREHOUSE_ITEM WHERE STOCK_NUM ="
								+ "'"+stock+"'";
						p = con.prepareStatement(sql);
						r = p.executeQuery();
						if(r.next())
							man = r.getString(1);
						r.close();
						
						if(ManCount.containsKey(man)){
							Qc_sn_manref = ManCount.get(man);
							ref = Qc_sn_manref.get(3);
							ref++;
							Qc_sn_manref.set(3,ref);
							ManCount.put(man, Qc_sn_manref);
							}
						else{
							
//							Qc_sn_manref.set(1,Integer.valueOf(cart_quantity));
//							Qc_sn_manref.set(2,Integer.valueOf(stock));
//							Qc_sn_manref.set(3,1);
//							Qc_sn_manref.set(4,i);
							Qc_sn_manref.set(1,Integer.valueOf(cart_quantity));
							Qc_sn_manref.set(2,Integer.valueOf(stock));
							Qc_sn_manref.set(3,1);
							Qc_sn_manref.set(4,i);
							ManCount.put(man, Qc_sn_manref);
							//Qc_sn_manref = ManCount.get(man);
							}
						
				}
				//-----If I go this far, that means that Cart is good to
				// go and now I have to update the inventory in warehouse id
					for(int i = 1; i<= table_limit; i++){
						int temp;
						stock = Integer.toString(stock_list[i]);
						sql = "SELECT QUANTITY FROM WAREHOUSE_ITEM WHERE STOCK_NUM ="
								+ "'"+stock+"'";
						p = con.prepareStatement(sql);
						r = p.executeQuery();
						if(r.next())
							ware_quantity = r.getString(1);
						r.close();
						
						sql = "SELECT QUANTITY FROM CART_STUFF WHERE STOCKNUMBER ="
								+ "'"+stock+"'";
						p = con.prepareStatement(sql);
						r = p.executeQuery();
						if(r.next())
							cart_quantity = r.getString(1);
						r.close();
						
						temp = Integer.valueOf(ware_quantity)- Integer.valueOf(cart_quantity);
						sql = "UPDATE WAREHOUSE_ITEM"
								+ " SET QUANTITY = "+temp+""
								+ " WHERE STOCK_NUM='"+stock+"'";
						st.executeQuery(sql);
						
						sql = "SELECT MANUFACTURER FROM WAREHOUSE_ITEM WHERE STOCK_NUM ="
								+ "'"+stock+"'";
						p = con.prepareStatement(sql);
						r = p.executeQuery();
						if(r.next())
							man = r.getString(1);
						r.close();
						
						Qc_sn_manref = ManCount.get(man);
						if(ManCount.containsKey(man) && Qc_sn_manref.get(3)>=3){
							   Random ran = new Random();
							   int new_sni = ran.nextInt((9999-1000)+1) + 1000;
							   String sni = Integer.toString(new_sni);
							   String ship_com = man+" Shipping Department";
							   sql = "INSERT INTO SHIPPING_NOTICE "//try catch
								   		+ "VALUES ('"+sni+"','"+ship_com+"')";
							   st.executeQuery(sql);
//							   sql = "SELECT STOCKNUMBER FROM CART_STUFF "
	//						   		+ "WHERE MANUFACTURER = '"+man+"'";
							   sql = "SELECT STOCK_NUM FROM WAREHOUSE_ITEM "
							   		+ "WHERE MANUFACTURER = '"+man+"'";
							   
							   p = con.prepareStatement(sql);
							   r = p.executeQuery();
							   ResultSet ree;
							   PreparedStatement pee;
							   while (r.next()){
									stock = r.getString(1);
									sql = "SELECT MODEL_NUM FROM WAREHOUSE_ITEM WHERE "
											+ "STOCK_NUM = '"+stock+"'";
									pee = con.prepareStatement(sql) ;
									ree = pee.executeQuery();
									if(ree.next())
										mod = ree.getString(1);
									ree.close();
									sql = "INSERT INTO Shipping_List " + //try catch
											   "VALUES('"+man+"','"+mod+"','"+sni+"',2)";
									st.executeUpdate(sql);	
								}
								r.close();
							   ManCount.remove(man);
							   
						}
						
						
						
						
						
						
						
				        
						
						//ifstatement that matches stock number with index, 
						//omg I can just make another array that keeps track of stock nums
						//easy as fuck
					}
					
					//once it exits both loops
					
				}
				catch(Exception e){
					e.printStackTrace();
				}
				return true;
	}
}
