package loginservlet;
import src.HospitalManagementSystem.MedicalHistoryPatient;  // Correct import for Patient class
import PatientDAO.PatientDAO;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/PatientController")
public class PatientController extends HttpServlet {
    private PatientDAO patientDAO;

    // Initialize the DAO object
    public void init() {
        patientDAO = new PatientDAO();
    }

    // Handle POST request to add a new patient
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve form data
        String name = request.getParameter("name");
        String dob = request.getParameter("dob");
        String medicalHistory = request.getParameter("medicalHistory");

        // Create a new Patient object and set the attributes
        MedicalHistoryPatient MedicalHistoryPatient = new MedicalHistoryPatient();
        MedicalHistoryPatient.setName(name);
        MedicalHistoryPatient.setDob(dob);
        MedicalHistoryPatient.setMedicalHistory(medicalHistory);

        // Call DAO to insert the patient into the database
        patientDAO.addPatient(MedicalHistoryPatient);

        // Redirect to the list of patients page
        response.sendRedirect("listPatients.jsp");
    }

    // Handle GET request to display all patients
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Retrieve all patients from the database
        request.setAttribute("patients", patientDAO.getAllPatients());

        // Forward the request to the listPatients.jsp page for display
        RequestDispatcher dispatcher = request.getRequestDispatcher("listPatients.jsp");
        dispatcher.forward(request, response);
    }
}
