<%@ page import="java.sql.*" %>
<%@ page import="src.HospitalManagementSystem.Doctorsviewpoint" %>
<%@ page import="java.util.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Doctors</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f4f4f4;
        }
        .container {
            width: 80%;
            margin: 50px auto;
            background-color: #fff;
            padding: 20px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        h2 {
            text-align: center;
            color: #007BFF;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border: 1px solid #ddd;
        }
        th {
            background-color: #f4f4f4;
        }
    </style>
</head>
<body>

<%
    Connection con = null;
    List<Map<String, String>> doctorsList = null;

    try {
        // Set up connection details
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@localhost:1521:orcl1"; // Database URL
        String username = "scott"; // Database username
        String password = "Tiger9"; // Database password

        // Establish connection
        con = DriverManager.getConnection(url, username, password);

        // Create a Doctorsviewpoint object to interact with the database
        Doctorsviewpoint doctorViewpoint = new Doctorsviewpoint(con);

        // Fetch the list of doctors from the database
        doctorsList = doctorViewpoint.viewDoctors();
    } catch (SQLException | ClassNotFoundException e) {
        e.printStackTrace();
    } finally {
        try {
            if (con != null) con.close(); // Close the connection
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
%>

<div class="container">
    <h2>Doctors List</h2>
    <table>
        <thead>
            <tr>
                <th>DoctorId</th>
                <th>Name</th>
                <th>Specialization</th>
            </tr>
        </thead>
        <tbody>
            <% 
                if (doctorsList != null && !doctorsList.isEmpty()) {
                    for (Map<String, String> doctor : doctorsList) {
            %>
                <tr>
                    <td><%= doctor.get("DoctorId") %></td>
                    <td><%= doctor.get("Name") %></td>
                    <td><%= doctor.get("Specialization") %></td>
                </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="3" style="text-align: center;">No doctors available</td>
                </tr>
            <% 
                }
            %>
        </tbody>
    </table>

    <form action="" method="get">
        <h3>Check Doctor by ID</h3>
        <label for="doctorId">Doctor ID:</label>
        <input type="text" id="doctorId" name="doctorId" required>
        <button type="submit">Check</button>
    </form>

    <% 
        String doctorIdParam = request.getParameter("doctorId");
        if (doctorIdParam != null) {
            try {
                int doctorId = Integer.parseInt(doctorIdParam);
                Doctorsviewpoint doctorViewpoint = new Doctorsviewpoint(con);
                boolean exists = doctorViewpoint.getDoctorById(doctorId);
                if (exists) {
    %>
                    <p>Doctor with ID <%= doctorId %> exists.</p>
    <% 
                } else {
    %>
                    <p>Doctor with ID <%= doctorId %> does not exist.</p>
    <% 
                }
            } catch (NumberFormatException e) {
    %>
                <p>Invalid doctor ID format.</p>
    <% 
            }
        }
    %>
</div>

</body>
</html>
