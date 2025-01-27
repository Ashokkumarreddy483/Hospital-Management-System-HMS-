package loginservlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // Database connection details
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl1";
    private static final String DB_USER = "scott";
    private static final String DB_PASSWORD = "Tiger9";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirm_password");

        // Check if password and confirm password match
        if (!password.equals(confirmPassword)) {
            // If passwords do not match, redirect back to registration page
            request.setAttribute("errorMessage", "Passwords do not match.");
            RequestDispatcher dispatcher = request.getRequestDispatcher("register.html");
            dispatcher.forward(request, response);
        } else {
            // If passwords match, insert user into the database
            try {
                // Load the Oracle JDBC driver
                Class.forName("oracle.jdbc.driver.OracleDriver");

                // Establish connection
                try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                    String sql = "INSERT INTO register (username, password) VALUES (?, ?)";
                    try (PreparedStatement pstmt = con.prepareStatement(sql)) {
                        pstmt.setString(1, username);
                        pstmt.setString(2, password); // In production, use password hashing

                        int rowsInserted = pstmt.executeUpdate();
                        if (rowsInserted > 0) {
                            // Redirect to hospital home page after successful registration
                            RequestDispatcher dispatcher = request.getRequestDispatcher("HospitalHome.html");
                            dispatcher.forward(request, response);
                        } else {
                            // If insertion fails, redirect back to registration page
                            RequestDispatcher dispatcher = request.getRequestDispatcher("Register.html");
                            dispatcher.forward(request, response);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Handle error and forward to registration page
                RequestDispatcher dispatcher = request.getRequestDispatcher("Register.html");
                dispatcher.forward(request, response);
            }
        }
    }
}
