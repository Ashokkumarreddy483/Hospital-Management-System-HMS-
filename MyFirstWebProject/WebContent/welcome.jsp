<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Register</title>
</head>
<body>
    <div class="register-container">
        <h2>Create Account</h2>
        <%
            String username = request.getParameter("username");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String CONFIRM_PASSWORD = request.getParameter("CONFIRM_PASSWORD");

            if (username != null && email != null && password != null && CONFIRM_PASSWORD != null) {
                if (password.equals(CONFIRM_PASSWORD)) {
                    try {
                        // Load Oracle JDBC driver
                        Class.forName("oracle.jdbc.driver.OracleDriver");

                        // Database connection details
                        Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl1", "scott", "Tiger9");

                        // Insert query
                        String sql = "INSERT INTO register (username, email, password, CONFIRM_PASSWORD) VALUES (?, ?, ?, ?)";
                        PreparedStatement pstmt = con.prepareStatement(sql);
                        pstmt.setString(1, username);
                        pstmt.setString(2, email);
                        pstmt.setString(3, password);
                        pstmt.setString(4, CONFIRM_PASSWORD);

                        int rowsInserted = pstmt.executeUpdate();
                        if (rowsInserted > 0) {
                            // Successful registration, redirect to login page
                            response.sendRedirect("login.html");
                        } else {
                            // Registration failed, redirect to error page
                            response.sendRedirect("Register.html");
                        }

                        pstmt.close();
                        con.close();
                    } catch (Exception e) {
                        // On error, redirect to error page
                        response.sendRedirect("registrationError.jsp?error=" + e.getMessage());
                        e.printStackTrace();
                    }
                } else {
                    // Passwords do not match, redirect to registration page with error
                    response.sendRedirect("registrationError.jsp?error=Passwords do not match.");
                }
            }
        %>
    </div>
</body>
</html>
