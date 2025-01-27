<%@ page import="src.HospitalManagementSystem.MedicalHistoryPatient" %>
<%@ page import="java.util.List" %>
<%@ page import="PatientDAO.PatientDAO" %>

<%
    PatientDAO patientDAO = new PatientDAO();
    List<MedicalHistoryPatient> patients = patientDAO.getAllPatients();
%>

<h1>Patient Records</h1>
<table border="1">
    <tr>
        <th>ID</th>
        <th>Name</th>
        <th>Date of Birth</th>
        <th>Medical History</th>
    </tr>
    <% for (MedicalHistoryPatient patient : patients) { %>
    <tr>
        <td><%= patient.getId() %></td>
        <td><%= patient.getName() %></td>
        <td><%= patient.getDob() %></td>
        <td><%= patient.getMedicalHistory() %></td>
    </tr>
    <% } %>
</table>
