<%@ page import="java.sql.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>View Appointments</title>
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
    List<Map<String, String>> appointmentsList = null;

    try {
        // Set up connection details
        Class.forName("oracle.jdbc.driver.OracleDriver");
        String url = "jdbc:oracle:thin:@localhost:1521:orcl1"; // Database URL
        String username = "scott"; // Database username
        String password = "Tiger9"; // Database password

        // Establish connection
        con = DriverManager.getConnection(url, username, password);

        // SQL query to fetch appointment details
        String selectQuery = "SELECT appointment_id, patient_name, doctor_name, appointment_date, status, created_at, updated_at FROM appointments";

        // Prepare and execute the query
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(selectQuery);

        // Create a list to store appointments data
        appointmentsList = new ArrayList<>();

        // Process the result set
        while (rs.next()) {
            Map<String, String> appointment = new HashMap<>();
            appointment.put("appointment_id", rs.getString("appointment_id"));
            appointment.put("patient_name", rs.getString("patient_name"));
            appointment.put("doctor_name", rs.getString("doctor_name"));
            appointment.put("appointment_date", rs.getString("appointment_date"));
            appointment.put("status", rs.getString("status"));
            appointment.put("created_at", rs.getString("created_at"));
            appointment.put("updated_at", rs.getString("updated_at"));
            appointmentsList.add(appointment);
        }
    } catch (SQLException e) {
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
    <h2>Appointments List</h2>
    <table>
        <thead>
            <tr>
                <th>Appointment ID</th>
                <th>Patient Name</th>
                <th>Doctor Name</th>
                <th>Appointment Date</th>
                <th>Status</th>
                <th>Created At</th>
                <th>Updated At</th>
            </tr>
        </thead>
        <tbody>
            <% 
                // Check if appointmentsList is not null and not empty, then loop through the list to display data
                if (appointmentsList != null && !appointmentsList.isEmpty()) {
                    for (Map<String, String> appointment : appointmentsList) {
            %>
                <tr>
                    <td><%= appointment.get("appointment_id") %></td>
                    <td><%= appointment.get("patient_name") %></td>
                    <td><%= appointment.get("doctor_name") %></td>
                    <td><%= appointment.get("appointment_date") %></td>
                    <td><%= appointment.get("status") %></td>
                    <td><%= appointment.get("created_at") %></td>
                    <td><%= appointment.get("updated_at") %></td>
                </tr>
            <% 
                    }
                } else {
            %>
                <tr>
                    <td colspan="7" style="text-align: center;">No appointments available</td>
                </tr>
            <% 
                }
            %>
        </tbody>
    </table>
</div>

</body>
</html>
