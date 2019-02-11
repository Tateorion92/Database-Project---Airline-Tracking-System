import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class PassengersByFlight extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response) 
    						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String flightID = request.getParameter("flightID");
        String query =  "Select * "+
						"From Passengers "+
						"Where arrID IN (Select arrID From Arrivals Where arrID ="+flightID+") OR "+
								"depID IN (Select depID From Departures Where depID ="+flightID+")";
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
			Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(
				query);
				//out.println(query);
			out.println("<table>");
			while(rset.next()){
				out.println("<tr>");
				out.print(
							"<td>Passenger ID: "+rset.getInt(1)+"; </td>"+
							"<td>Name: "+rset.getString(2)+"; </td>"+
							"<td>Passport/DL number: "+rset.getInt(3)+"; </td>"+
							"<td>Place of birth: "+rset.getString(4)+"; </td>"+
							"<td>Date of birth: "+rset.getObject(5)+"</td>");
							out.println("</tr>");
			}
			out.println("</table>");
            stmt.close();
        }
        catch(SQLException e) { out.println(e); }
        ConnectionManager.getInstance().returnConnection(conn);
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