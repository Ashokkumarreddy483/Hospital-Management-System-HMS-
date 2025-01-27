package comm.sssit;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;

@WebServlet("/PatientBillServlet")
public class PatientBillServlet extends HttpServlet {

    // Database connection details
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl1";
    private static final String DB_USERNAME = "scott";
    private static final String DB_PASSWORD = "Tiger9";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String patientName = request.getParameter("PATIENT_NAME");
        String serviceType = request.getParameter("SERVICE");
        String insuranceProvider = request.getParameter("INSURANCE");
        String policyNumber = request.getParameter("POLICY_NUMBER");
        String billDate = request.getParameter("BILL_DATE");
        double totalCost = Double.parseDouble(request.getParameter("TOTAL_COST"));
        String status = request.getParameter("STATUS");
        String notes = request.getParameter("NOTES");

        String sql = "INSERT INTO BillingClaims (patient_name, service_type, insurance_provider, policy_number, billing_date, total_cost, claim_status, additional_notes) " +
                     "VALUES (?, ?, ?, ?, TO_DATE(?, 'YYYY-MM-DD'), ?, ?, ?)";

        // Declare connection and prepared statement
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Load and initialize the JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // Establish the database connection
            conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

            // Prepare the SQL statement
            pstmt = conn.prepareStatement(sql);

            // Set values for the prepared statement
            pstmt.setString(1, patientName);
            pstmt.setString(2, serviceType);
            pstmt.setString(3, insuranceProvider);
            pstmt.setString(4, policyNumber);
            pstmt.setString(5, billDate);
            pstmt.setDouble(6, totalCost);
            pstmt.setString(7, status);
            pstmt.setString(8, notes);

            // Execute the insert query
            int rowsAffected = pstmt.executeUpdate();

            if (rowsAffected > 0) {
                PrintWriter out = response.getWriter();
            	out.println("<h1 style='color: green; text-align: center;'>Insurance Applied Successfully!!</h1>");
            } else {
                response.sendRedirect("error.jsp");    // Redirect to error page
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");  // Redirect to error page
        } finally {
            // Close resources
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}