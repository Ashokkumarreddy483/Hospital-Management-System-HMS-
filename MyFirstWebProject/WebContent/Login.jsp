<%@ page import="java.sql.*" %>
<%@ page import="java.io.*" %>
<%@ page import="javax.servlet.http.HttpServletResponse" %>

<html>
<head>
    <title>Registration Result</title>
</head>
<body>
    <%
        String username = request.getParameter("username");
        String password = request.getParameter("password");

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
                    // Successful login: Redirect to Hotel-Website-master (or login page)
                    response.sendRedirect("Insurance Home .html");
                } else {
                    // Invalid login: Redirect to error page
                    response.sendRedirect("errorPage.jsp");
                }

                rs.close();
                pstmt.close();
                con.close();
            } catch (Exception e) {
                // On error, redirect to error page
                response.sendRedirect("errorPage.jsp?error=" + e.getMessage());
            }
        }
    %>
</body>
</html>
