import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InsertDepartures extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response) 
    						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String depID = request.getParameter("depID");
		String depT = request.getParameter("depT");
        String rnum = request.getParameter("rnum");
        String acode = request.getParameter("acode");
        String gate = request.getParameter("gate");
		depT= "to_date('"+depT+"','YYYY-MM-DD hh24:mi')";
		
        String statementString = 
		"INSERT INTO Departures(depID, depT, rnum, acode, gate) " +
        "VALUES( " + depID + "," + depT + "," + rnum + ",'" + acode + "'," + gate + ")";
        
        Connection conn = ConnectionManager.getInstance().getConnection();
        try {
            Statement stmt = conn.createStatement();
            stmt.executeUpdate(statementString);
            stmt.close();
            out.println("Insertion Successful!");
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
