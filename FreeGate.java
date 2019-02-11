import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class FreeGate extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response) 
    						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String who_knows = request.getParameter("who_knows");
		Connection conn = ConnectionManager.getInstance().getConnection();

		try{
			Statement stmt = conn.createStatement();
			ResultSet rset = stmt.executeQuery( "SELECT arrID FROM Arrivals");
			
			while (rset.next()){
				String s = rset.getString("arrID");
				//out.println("NOT QUITEINARRAIVALS");
				//out.println("wellthis is your j value"+j);
				if(s.equals(who_knows)==true){
					
					Statement stmt1=conn.createStatement();
					ResultSet rset1 = stmt1.executeQuery( "SELECT gate FROM Gates WHERE isfree=0");
					while(rset1.next()){
						
						int isfree= rset1.getInt("gate");
						out.println("FREE GATE IN ARRIVALS: " +isfree);
						stmt1.close();
						return;
					
					}
				}				
			}
			
			stmt.close();
			Statement stmt2=conn.createStatement();
			ResultSet dset = stmt2.executeQuery( "SELECT depID FROM Departures");
			while(dset.next()){
				String d = dset.getString("depID");
				if(d.equals(who_knows)==true){
					ResultSet dset1 = stmt2.executeQuery( "SELECT gate FROM Gates WHERE isfree=0");
					while(dset1.next()){
									
						int isfree1= dset1.getInt("gate");
										//out.println("THIS IS ISFREE"+isfree1);
				
						out.println("FREE GATE IN DEPARTURES: " +isfree1);
						stmt2.close();
						return;
					}
				}
			}
			
			out.println("NO FREE GATES WERE AVAILABLE");
		
		}
        catch(SQLException e) { out.println(e); }
        ConnectionManager.getInstance().returnConnection(conn);
		// ConnectionManager.getInstance().returnConnection(conn1);
    }
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
    			throws ServletException, IOException {
        processRequest(request, response);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        processRequest(request, response);
    }
	
    public String getServletInfo() {  return "Insert"; }
}