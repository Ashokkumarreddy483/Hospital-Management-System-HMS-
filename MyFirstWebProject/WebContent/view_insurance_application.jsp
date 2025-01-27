<%@ page import="java.sql.*, java.util.*" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<html>
<head>
    <title>Insurance Application Records</title>
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
            background-color: #f2f2f2;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
    </style>
</head>
<body>
    <h1>Insurance Application Records</h1>
    
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Full Name</th>
                <th>Date of Birth</th>
                <th>Gender</th>
                <th>Address</th>
                <th>Contact Number</th>
                <th>Email</th>
                <th>Nationality</th>
                <th>Occupation</th>
                <th>Insurance Type</th>
                <th>Coverage Amount</th>
                <th>Policy Term</th>
                <th>Beneficiaries</th>
                <th>Insurance Start Date</th>
                <th>Pre-existing Conditions</th>
                <th>Medical History</th>
                <th>Current Medications</th>
                <th>Lifestyle Details</th>
                <th>Payment Method</th>
                <th>Bank Details</th>
                <th>Billing Address</th>
                <th>Declaration</th>
                <th>Consent</th>
                <th>Signature</th>
                <th>Application Date</th>
            </tr>
        </thead>
        <tbody>
            <% 
                // Get the connection and the statement from the request
                Connection conn = null;
                Statement stmt = null;
                ResultSet rs = null;
                try {
                    // Database connection
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    conn = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:orcl1", "scott", "Tiger9");
                    stmt = conn.createStatement();
                    String sql = "SELECT * FROM insurance_application";
                    rs = stmt.executeQuery(sql);
                    
                    while (rs.next()) {
            %>
            <tr>
                <td><%= rs.getInt("ID") %></td>
                <td><%= rs.getString("FULL_NAME") %></td>
                <td><%= rs.getDate("DOB") %></td>
                <td><%= rs.getString("GENDER") %></td>
                <td><%= rs.getString("ADDRESS") %></td>
                <td><%= rs.getString("CONTACT_NUMBER") %></td>
                <td><%= rs.getString("EMAIL") %></td>
                <td><%= rs.getString("NATIONALITY") %></td>
                <td><%= rs.getString("OCCUPATION") %></td>
                <td><%= rs.getString("INSURANCE_TYPE") %></td>
                <td><%= rs.getBigDecimal("COVERAGE_AMOUNT") %></td>
                <td><%= rs.getString("POLICY_TERM") %></td>
                <td><%= rs.getString("BENEFICIARIES") %></td>
                <td><%= rs.getDate("INSURANCE_START_DATE") %></td>
                <td><%= rs.getString("PRE_EXISTING_CONDITIONS") %></td>
                <td><%= rs.getString("MEDICAL_HISTORY") %></td>
                <td><%= rs.getString("CURRENT_MEDICATIONS") %></td>
                <td><%= rs.getString("LIFESTYLE_DETAILS") %></td>
                <td><%= rs.getString("PAYMENT_METHOD") %></td>
                <td><%= rs.getString("BANK_DETAILS") %></td>
                <td><%= rs.getString("BILLING_ADDRESS") %></td>
                <td><%= rs.getString("DECLARATION") %></td>
                <td><%= rs.getString("CONSENT") %></td>
                <td><%= rs.getString("SIGNATURE") %></td>
                <td><%= rs.getDate("APPLICATION_DATE") %></td>
            </tr>
            <% 
                    }
                } catch (SQLException | ClassNotFoundException e) {
                    e.printStackTrace();
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
