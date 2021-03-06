import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class InsertOutgoingRoutes extends HttpServlet {
    void processRequest(HttpServletRequest request, HttpServletResponse response) 
    						throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String rnum = request.getParameter("rnum");
        String acode = request.getParameter("acode");
        String destination = request.getParameter("destination");
		String outT = request.getParameter("outT");
		//out.println(outT +"\n");
		outT= "to_date('"+outT+"','YYYY-MM-DD hh24:mi')";
		//out.println(outT +"\n");
        String statementString = 
		"INSERT INTO OutgoingRoutes(rnum, acode, destination, outT) " +
        "VALUES( " + rnum + ",'" + acode + "','" + destination + "'," + outT + ")";
       // out.println(statementString);
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
