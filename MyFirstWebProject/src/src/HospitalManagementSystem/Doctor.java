package src.HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class Doctor {
    private Connection connection;

    public Doctor(Connection connection) {
        this.connection = connection;
    }

    // Method to view doctors
    public void viewDoctors() {
        String query = "SELECT DoctorId, Name, Specialization FROM doctors";
        try (Statement stmt = connection.createStatement(); 
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                int doctorId = rs.getInt("DoctorId"); // Updated column name
                String name = rs.getString("Name");   // Updated column name
                String specialization = rs.getString("Specialization"); // Updated column name
                System.out.println("DoctorId: " + doctorId + ", Name: " + name + ", Specialization: " + specialization);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to check if doctor exists by ID
    public boolean getDoctorById(int doctorId) {
        String query = "SELECT COUNT(*) FROM doctors WHERE DoctorId = ?"; // Updated column name
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
