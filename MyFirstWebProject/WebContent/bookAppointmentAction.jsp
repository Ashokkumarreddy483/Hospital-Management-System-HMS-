<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.sql.*" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Appointment Confirmation</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f4;
            margin: 0;
            padding: 0;
        }
        .confirmation-container {
            width: 50%;
            margin: 50px auto;
            padding: 20px;
            background-color: #fff;
            border-radius: 8px;
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
        }
        .confirmation-container h2 {
            text-align: center;
            color: #28a745;
        }
        .confirmation-container p {
            text-align: center;
            font-size: 18px;
        }
        .button {
            display: block;
            width: 100%;
            padding: 10px;
            margin-top: 20px;
            background-color: #007BFF;
            color: #fff;
            border: none;
            border-radius: 4px;
            font-size: 16px;
            text-align: center;
        }
        .button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

<%
    String patientName = request.getParameter("patientName");
    String doctorName = request.getParameter("doctorName");
    String appointmentDate = request.getParameter("appointmentDate");
    String status = request.getParameter("status");
    String message = "";

    if (patientName != null && doctorName != null && appointmentDate != null && status != null) {
        message = "Appointment successfully booked!";
    } else {
        message = "There was an error processing your appointment.";
    }
%>

<!-- Confirmation message -->
<div class="confirmation-container">
    <h2>Appointment Confirmation</h2>
    <p><strong>Patient Name:</strong> <%= patientName %></p>
    <p><strong>Doctor Name:</strong> <%= doctorName %></p>
    <p><strong>Appointment Date:</strong> <%= appointmentDate %></p>
    <p><strong>Status:</strong> <%= status %></p>

    <p><%= message %></p>

    <button class="button" onclick="window.location.href='index.jsp';">Go Back to Home</button>
</div>

</body>
</html>
