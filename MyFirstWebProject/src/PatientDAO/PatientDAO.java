package PatientDAO;
import src.HospitalManagementSystem.MedicalHistoryPatient;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatientDAO {
    // JDBC URL for Oracle DB
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl1"; // Modify accordingly
    private static final String USER = "scott"; // Replace with your username
    private static final String PASSWORD = "Tiger9"; // Replace with your password

    // Load Oracle JDBC driver (optional, but recommended)
    static {
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Connect to the database
    private Connection connect() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }

    // Method to add a patient
    public void addPatient(MedicalHistoryPatient medicalHistoryPatient) {
        String query = "INSERT INTO patients (name, dob, medical_history) VALUES (?, ?, ?)";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, medicalHistoryPatient.getName());
            pstmt.setString(2, medicalHistoryPatient.getDob());
            pstmt.setString(3, medicalHistoryPatient.getMedicalHistory());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to get all patients
    public List<MedicalHistoryPatient> getAllPatients() {
        List<MedicalHistoryPatient> patients = new ArrayList<>();
        String query = "SELECT * FROM patients";
        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                MedicalHistoryPatient patient = new MedicalHistoryPatient();
                patient.setId(rs.getInt("id"));
                patient.setName(rs.getString("name"));
                patient.setDob(rs.getString("dob"));
                patient.setMedicalHistory(rs.getString("medical_history"));
                patients.add(patient);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return patients;
    }
}