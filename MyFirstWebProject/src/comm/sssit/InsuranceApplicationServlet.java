package comm.sssit;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.sql.*;
import java.text.*;
import java.math.BigDecimal;

@WebServlet("/InsuranceApplicationServlet")
public class InsuranceApplicationServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form values
        String fullName = request.getParameter("full_name");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String address = request.getParameter("address");
        String contactNumber = request.getParameter("contact_number");
        String email = request.getParameter("email");
        String nationality = request.getParameter("nationality");
        String occupation = request.getParameter("occupation");
        String insuranceType = request.getParameter("insurance_type");
        String coverageAmount = request.getParameter("coverage_amount");
        String policyTerm = request.getParameter("policy_term");
        String beneficiaries = request.getParameter("beneficiaries");
        String insuranceStartDate = request.getParameter("insurance_start_date");
        String preExistingConditions = request.getParameter("pre_existing_conditions");
        String medicalHistory = request.getParameter("medical_history");
        String currentMedications = request.getParameter("current_medications");
        String lifestyleDetails = request.getParameter("lifestyle_details");
        String paymentMethod = request.getParameter("payment_method");
        String bankDetails = request.getParameter("bank_details");
        String billingAddress = request.getParameter("billing_address");
        String declaration = request.getParameter("declaration");
        String consent = request.getParameter("consent");
        String signature = request.getParameter("signature");
        String applicationDate = request.getParameter("application_date");

        // Database connection variables
        String url = "jdbc:oracle:thin:@localhost:1521:orcl1"; // Externalize this in a properties file
        String dbUsername = "scott"; // Externalize this
        String dbPassword = "Tiger9"; // Externalize this
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            // Load and initialize the JDBC driver
            Class.forName("oracle.jdbc.driver.OracleDriver");
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);

            // SQL query to insert data into the database
            String sql = "INSERT INTO insurance_application (ID, FULL_NAME, DOB, GENDER, ADDRESS, CONTACT_NUMBER, EMAIL, NATIONALITY, OCCUPATION, INSURANCE_TYPE, COVERAGE_AMOUNT, POLICY_TERM, BENEFICIARIES, INSURANCE_START_DATE, PRE_EXISTING_CONDITIONS, MEDICAL_HISTORY, CURRENT_MEDICATIONS, LIFESTYLE_DETAILS, PAYMENT_METHOD, BANK_DETAILS, BILLING_ADDRESS, DECLARATION, CONSENT, SIGNATURE, APPLICATION_DATE) " +
                    "VALUES (insurance_application_seq.NEXTVAL, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fullName);  // Full name is the first parameter
            pstmt.setDate(2, Date.valueOf(dob)); // Date of birth
            pstmt.setString(3, gender);  // Gender
            pstmt.setString(4, address);  // Address
            pstmt.setString(5, contactNumber);  // Contact number
            pstmt.setString(6, email);  // Email
            pstmt.setString(7, nationality);  // Nationality
            pstmt.setString(8, occupation);  // Occupation
            pstmt.setString(9, insuranceType);  // Insurance type
            pstmt.setBigDecimal(10, new BigDecimal(coverageAmount));  // Coverage amount
            pstmt.setString(11, policyTerm);  // Policy term
            pstmt.setString(12, beneficiaries);  // Beneficiaries
            pstmt.setDate(13, Date.valueOf(insuranceStartDate));  // Insurance start date
            pstmt.setString(14, preExistingConditions);  // Pre-existing conditions
            pstmt.setString(15, medicalHistory);  // Medical history
            pstmt.setString(16, currentMedications);  // Current medications
            pstmt.setString(17, lifestyleDetails);  // Lifestyle details
            pstmt.setString(18, paymentMethod);  // Payment method
            pstmt.setString(19, bankDetails);  // Bank details
            pstmt.setString(20, billingAddress);  // Billing address
            pstmt.setString(21, declaration);  // Declaration
            pstmt.setString(22, consent);  // Consent
            pstmt.setString(23, signature);  // Signature
            pstmt.setDate(24, Date.valueOf(applicationDate));  // Application date

            int result = pstmt.executeUpdate();
            if (result > 0) {
            	response.sendRedirect("Insurance Successfully.html");
            } else {
                response.getWriter().println("Error submitting the application.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
