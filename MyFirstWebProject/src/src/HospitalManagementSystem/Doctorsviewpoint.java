package src.HospitalManagementSystem;

import java.sql.*;
import java.util.*;

public class Doctorsviewpoint {
    private Connection connection;

    public Doctorsviewpoint(Connection connection) {
        this.connection = connection;
    }

    // Method to view doctors and return the results as a List of Maps
    public List<Map<String, String>> viewDoctors() {
        String query = "SELECT DoctorId, Name, Specialization FROM doctors";
        List<Map<String, String>> doctorsList = new ArrayList<>();
        try (Statement stmt = connection.createStatement(); 
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Map<String, String> doctor = new HashMap<>();
                doctor.put("DoctorId", String.valueOf(rs.getInt("DoctorId")));
                doctor.put("Name", rs.getString("Name"));
                doctor.put("Specialization", rs.getString("Specialization"));
                doctorsList.add(doctor);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return doctorsList;
    }

    // Method to check if doctor exists by ID
    public boolean getDoctorById(int doctorId) {
        String query = "SELECT COUNT(*) FROM doctors WHERE DoctorId = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, doctorId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
