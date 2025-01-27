<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Claims Data</title>
    <style>
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 10px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
    <h1>Claims Data</h1>

    <table>
        <thead>
            <tr>
                <th>Claim ID</th>
                <th>Patient Name</th>
                <th>Service Type</th>
                <th>Insurance Provider</th>
                <th>Policy Number</th>
                <th>Billing Date</th>
                <th>Total Cost</th>
                <th>Status</th>
                <th>Additional Notes</th>
            </tr>
        </thead>
        <tbody>
            <%
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;

                // Database connection details
                String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl1";
                String DB_USERNAME = "scott";
                String DB_PASSWORD = "Tiger9";

                try {
                    // Load and initialize the JDBC driver
                    Class.forName("oracle.jdbc.driver.OracleDriver");

                    // Establish the connection
                    conn = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);

                    // Execute the query
                    stmt = conn.createStatement();
                    rs = stmt.executeQuery("SELECT * FROM BillingClaims");

                    // Iterate through the result set
                    while (rs.next()) {
            %>
                        <tr>
                            <td><%= rs.getInt("claim_id") %></td>
                            <td><%= rs.getString("patient_name") %></td>
                            <td><%= rs.getString("service_type") %></td>
                            <td><%= rs.getString("insurance_provider") %></td>
                            <td><%= rs.getString("policy_number") %></td>
                            <td><%= rs.getDate("billing_date") %></td>
                            <td><%= rs.getDouble("total_cost") %></td>
                            <td><%= rs.getString("claim_status") %></td>
                            <td><%= rs.getString("additional_notes") %></td>
                        </tr>
            <%
                    }
                } catch (Exception e) {
                    e.printStackTrace();
            %>
                    <tr>
                        <td colspan="9">Error fetching data</td>
                    </tr>
            <%
                } finally {
                    // Close resources
                    try {
                        if (rs != null) rs.close();
                        if (stmt != null) stmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            %>
        </tbody>
    </table>
</body>
</html>
