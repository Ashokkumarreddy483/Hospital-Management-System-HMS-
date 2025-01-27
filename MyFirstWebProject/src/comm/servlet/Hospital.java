package comm.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/login")
public class Hospital extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String loginMessage = "";

        if (username != null && password != null) {
            try {
                // Load Oracle JDBC driver
                Class.forName("oracle.jdbc.driver.OracleDriver");

                // Database connection
                Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl1", "scott", "Tiger9");

                // Select query to validate login
                String sql = "SELECT * FROM register WHERE username = ? AND password = ?";
                PreparedStatement pstmt = con.prepareStatement(sql);
                pstmt.setString(1, username);
                pstmt.setString(2, password);

                ResultSet rs = pstmt.executeQuery();
                if (rs.next()) {
                    loginMessage = "Registration successful! You can now <a href='Hotel-Website-master'>Login</a>.";
                    // Redirect to the login page if successful
                    response.sendRedirect("Hotel-Website-master");
                } else {
                    loginMessage = "Invalid username or password. Please try again.";
                }

                rs.close();
                pstmt.close();
                con.close();
            } catch (Exception e) {
                loginMessage = "Error: " + e.getMessage();
                e.printStackTrace();
            }
        }

        // Send the message back to the client
        response.setContentType("text/html");
        response.getWriter().println("<p>" + loginMessage + "</p>");
    }
}
