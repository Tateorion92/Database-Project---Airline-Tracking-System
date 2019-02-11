import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class FlightsByTime extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response) 
    						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String date = request.getParameter("date");
        String query = "Select * "+
				"From Departures "+
				"Where 24*ABS(depT-to_date('"+date+"', 'YYYY-MM-DD hh24:mi') ) <= 1 "+
				"Union "+
				"Select * "+
				"From Arrivals "+
				"Where 24*ABS(arrT-to_date('"+date+"', 'YYYY-MM-DD hh24:mi') ) <= 1";
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
							"<td>Flight number: "+rset.getInt(1)+"; </td>"+
							"<td> Route number: "+rset.getInt(3)+"; </td>"+
							"<td>Airline code: "+rset.getObject(4)+"; </td>"+
							"<td>Gate number: "+rset.getInt(5)+"</td>");
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