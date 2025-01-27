<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Patients</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f9fb;
            margin: 0;
            padding: 20px;
        }
        h1 {
            text-align: center;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #007bff;
            color: white;
        }
        tr:hover {
            background-color: #f1f1f1;
        }
    </style>
</head>
<body>
    <h1>Patients List</h1>
    
    <table>
        <tr>
            <th>Name</th>
            <th>Age</th>
            <th>Contact</th>
        </tr>

        <%
            // Database connection variables (replace with actual values)
            String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl1";
        String DB_USERNAME = "scott";  // Update with your DB username
        String DB_PASSWORD = "Tiger9";

            Connection conn = null;
            PreparedStatement stmt = null;
            ResultSet rs = null;

            try {
                // Load Oracle JDBC driver
                 Class.forName("oracle.jdbc.driver.OracleDriver");

                // Establish database connection
                conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                // SQL query to retrieve all patients
                String query = "SELECT * FROM ADDPATIENT";
                stmt = conn.prepareStatement(query);
                rs = stmt.executeQuery();

                // Iterate and display patient data
                while (rs.next()) {
            %>
                <tr>
                    <td><%= rs.getString("NAME") %></td>
                    <td><%= rs.getInt("AGE") %></td>
                    <td><%= rs.getString("CONTACT") %></td>
                </tr>
            <%
                }
            } catch (SQLException e) {
                out.println("<tr><td colspan='3'>SQL error: Unable to fetch patient data.</td></tr>");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                out.println("<tr><td colspan='3'>JDBC Driver not found.</td></tr>");
                e.printStackTrace();
            } finally {
                // Clean up resources
                try {
                    if (rs != null) rs.close();
                    if (stmt != null) stmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        %>
    </table>
</body>
</html>
