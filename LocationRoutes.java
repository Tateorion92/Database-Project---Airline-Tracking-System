import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class LocationRoutes extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response) 
    						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String location = request.getParameter("location");
        
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
			Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(
				"Select * " +
				"From IncomingRoutes "+
				"Where source='"+location+"' "+
				"Union "+
				"Select * "+
				"From OutgoingRoutes "+
				"Where destination='"+location+"'");
			out.println("<table>");
			while(rset.next()){
				out.println("<tr>");
				out.print(
							"<td>Route number: "+rset.getInt("rnum")+"; </td>"+
							"<td>Airline code: "+rset.getString("acode")+"; </td>"+
							"<td>Arrival/departure time: "+rset.getObject(4)+"</td>");
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