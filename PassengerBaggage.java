import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class PassengerBaggage extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response) 
    						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
		String pID = request.getParameter("pID");
        String query =  "Select B.* "+
						"From Baggage B, Passengers P "+
						"Where B.pID="+pID+" And P.pID="+pID;
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
							"<td>Baggage tag: "+rset.getInt(1)+"; </td>"+
							"<td>Weight: "+rset.getInt(2)+" </td>");
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