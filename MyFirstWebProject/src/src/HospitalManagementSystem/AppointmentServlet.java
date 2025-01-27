package src.HospitalManagementSystem;

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.text.*;
@WebServlet("/AppointmentServlet")
public class AppointmentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Get form parameters
        String appointmentId = request.getParameter("appointment_id");
        String patientName = request.getParameter("patient_name");
        String doctorName = request.getParameter("doctor_name");
        String appointmentDate = request.getParameter("appointment_date");
        String status = request.getParameter("status");
        String createdAt = request.getParameter("created_at");
        String updatedAt = request.getParameter("updated_at");

        // Database connection
        Connection conn = null;
        PreparedStatement pstmt = null;
        try {
            // Load database driver
            Class.forName("oracle.jdbc.OracleDriver");
            conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl1", "scott", "Tiger9");

            // Ensure created_at and updated_at are in the correct format
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

            // Parse the createdAt and updatedAt values into the correct format
            java.util.Date createdAtDate = formatter.parse(createdAt);
            java.util.Date updatedAtDate = formatter.parse(updatedAt);

            // SQL query to insert appointment data
            String sql = "INSERT INTO appointments (appointment_id, patient_name, doctor_name, appointment_date, status, created_at, updated_at) VALUES (?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, appointmentId);
            pstmt.setString(2, patientName);
            pstmt.setString(3, doctorName);
            pstmt.setDate(4, java.sql.Date.valueOf(appointmentDate));  // Correct format for Date
            pstmt.setString(5, status);
            pstmt.setTimestamp(6, new java.sql.Timestamp(createdAtDate.getTime()));  // Correct format for Timestamp
            pstmt.setTimestamp(7, new java.sql.Timestamp(updatedAtDate.getTime()));  // Correct format for Timestamp

            // Execute update
            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                response.getWriter().write("<h1 style='color: green; text-align: center;'>Appointment Applied Successfully!!</h1>");
            } else {
                response.getWriter().write("Failed to add appointment.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().write("Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
