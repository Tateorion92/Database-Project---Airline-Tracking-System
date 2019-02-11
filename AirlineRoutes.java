import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class AirlineRoutes extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response) 
    						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String acode = request.getParameter("acode");
        
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
			Statement stmt = conn.createStatement();
            ResultSet rset = stmt.executeQuery(
				"Select *" +
				"From Routes"+
				" Where acode='"+acode+"'");
			out.println("<table>");
			while(rset.next()){
				out.println("<tr>");
				out.print(
							"<td>Route number: "+rset.getInt("rnum")+"; </td>"+
							"<td>Plane model: "+rset.getString("planeModel")+"</td>");
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