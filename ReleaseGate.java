import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class ReleaseGate extends HttpServlet {
	public static int gateNum;
    void processRequest(HttpServletRequest request, HttpServletResponse response) 
    						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
		String flightID = request.getParameter("flightID");
        String query = "Select gate "+
						"From Arrivals "+
						"Where arrID="+flightID+
						" Union "+
						"Select gate "+
						"From Departures "+
						"Where depID="+flightID;
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
			Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(query);
				//out.println(query);
			out.println("<table>");
			while(rset.next()){
				out.println("<tr>");
				out.print(
							"<td>Gate number: "+rset.getInt(1)+" </td>");
							out.println("</tr>");
							gateNum = rset.getInt(1);
			}
			out.println("</table>");
            stmt.close();
			
			String query2 = "Update Gates "+
							"Set isfree=0 "+
							"Where gate="+gateNum;
							
			Statement stmt2 = conn.createStatement();
            ResultSet rset2 = stmt2.executeQuery(query2);
			
			stmt2.close();
			
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