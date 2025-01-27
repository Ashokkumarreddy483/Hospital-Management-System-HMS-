package comm.sssit;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/StudentDetailsServlet")
public class StudentServlet extends HttpServlet {
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Get student details from the database
        try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl1","scott","Tiger9");

            Statement stmt = conn.createStatement();
            
            // Execute the query to retrieve student data
            String query = "SELECT * FROM STUDENTDETAILS";
            ResultSet rs = stmt.executeQuery(query);
            
            // Generate HTML output to display student data
            out.println("<html><body><h2>Student Details</h2>");
            out.println("<table border='1'>");
            out.println("<tr><th>Student ID</th><th>First Name</th><th>Last Name</th><th>Date of Birth</th><th>Email</th><th>Phone Number</th><th>Address</th><th>Gender</th><th>Qualification</th><th>Description</th><th>Enrollment Date</th></tr>");
            
            while (rs.next()) {
                out.println("<tr>");
                out.println("<td>" + rs.getInt("StudentID") + "</td>");
                out.println("<td>" + rs.getString("FirstName") + "</td>");
                out.println("<td>" + rs.getString("LastName") + "</td>");
                out.println("<td>" + rs.getDate("DateOfBirth") + "</td>");
                out.println("<td>" + rs.getString("Email") + "</td>");
                out.println("<td>" + rs.getString("PhoneNumber") + "</td>");
                out.println("<td>" + rs.getString("Address") + "</td>");
                out.println("<td>" + rs.getString("Gender") + "</td>");
                out.println("<td>" + rs.getString("Qualification") + "</td>");
                out.println("<td>" + rs.getString("Description") + "</td>");
                out.println("<td>" + rs.getDate("EnrollmentDate") + "</td>");
                out.println("</tr>");
            }
            out.println("</table></body></html>");
            
            // Close the ResultSet, Statement, and Connection
            rs.close();
            stmt.close();
            conn.close();
            
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
