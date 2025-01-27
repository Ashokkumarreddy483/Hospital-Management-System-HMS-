package src.HospitalManagementSystem;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/AddPatientServlet") // This should match the action in the HTML form
public class AddPatientServlet extends HttpServlet {
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl1";  // Update with your actual DB URL
    private static final String DB_USERNAME = "scott";  // Update with your DB username
    private static final String DB_PASSWORD = "Tiger9";  // Update with your DB password

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("patientName"); // Patient name from the form
        String ageStr = request.getParameter("patientAge"); // Patient age from the form
        String contact = request.getParameter("patientContact"); // Patient contact number from the form

        // Validate input
        if (name == null || name.trim().isEmpty() || contact == null || contact.trim().isEmpty()) {
            response.getWriter().println("Name and contact cannot be empty.");
            return;
        }

        int age = 0;
        try {
            age = Integer.parseInt(ageStr); // Convert age to integer
        } catch (NumberFormatException e) {
            response.getWriter().println("Invalid age format.");
            return;
        }

        // Validate contact number format (example: simple 10-digit check)
        if (!contact.matches("\\d{10}")) {
            response.getWriter().println("Invalid contact number. It must be a 10-digit number.");
            return;
        }

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Load JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish a database connection
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Prepare SQL insert query
            String query = "INSERT INTO ADDPATIENT (NAME, AGE, CONTACT) VALUES (?, ?, ?)";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, name); // Set the patient's name
            stmt.setInt(2, age); // Set the patient's age
            stmt.setString(3, contact); // Set the patient's contact number

            // Execute the query
            int result = stmt.executeUpdate();

            // Send a response to the client
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");

            if (result > 0) {
            	out.println("<h1 style='color: green; text-align: center;'>Patient added successfully!</h1>");
 
                // Optionally, you can redirect the user to another page:
                // response.sendRedirect("patientList.jsp");
            } else {
                out.println("<h3>Failed to add patient.</h3>");
            }
        } catch (SQLException e) {
            // Log SQL error (you might use a logging framework here)
            e.printStackTrace();
            response.getWriter().println("Error occurred while adding patient.");
        } catch (Exception e) {
            // Handle other exceptions
            e.printStackTrace();
            response.getWriter().println("Unexpected error occurred.");
        } finally {
            // Close database resources to avoid memory leaks
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
} 