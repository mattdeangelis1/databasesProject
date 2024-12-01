import java.sql.*; 
import java.util.Scanner;

public class COP3703Project {

    public static void main(String[] args) throws SQLException {
        // Hardcoded database credentials
        String uid = "G07";
        String pword = "2U6f2M9q";
        String url = "jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl";

        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
        Scanner scanner = new Scanner(System.in);

        int userChoice;

        do {
            Connection connection = DriverManager.getConnection(url, uid, pword);
            System.out.println("\n=== Hospital Management System Menu ===");
            System.out.println("1. Add a Patient");
            System.out.println("2. Add a Doctor");
            System.out.println("3. Add a Department");
            System.out.println("4. Add a Procedure");
            System.out.println("5. Add an Interaction Record");
            System.out.println("6. Generate a Patient's Health Record");
            System.out.println("7. List Procedures by Department");
            System.out.println("8. List Procedures Performed by a Doctor");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            userChoice = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline

            switch (userChoice) {
                case 1: // Add a Patient
                    PreparedStatement pstmtPatient = connection.prepareStatement(
                        "INSERT INTO PATIENT (PERSON_SSN, PATIENT_ID, CURR_PHONE, PRIMARY_DR_ID, SECONDARY_DR_ID, CONDITION, CURR_CITY, CURR_STATE, CURR_ZIP, SEX) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"
                    );
                    int userChoicePatient = 1;
                    while (userChoicePatient != 0) {
                        System.out.print("Enter Patient SSN: ");
                        String ssn = scanner.nextLine();

                        System.out.print("Enter Patient ID: ");
                        int patientId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter Current Phone: ");
                        String phone = scanner.nextLine();

                        System.out.print("Enter Primary Doctor ID: ");
                        int primaryDoctorId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter Secondary Doctor ID (or 0 if none): ");
                        int secondaryDoctorId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter Patient Condition (Critical/Stable/Fair): ");
                        String condition = scanner.nextLine();

                        System.out.print("Enter Current City: ");
                        String city = scanner.nextLine();

                        System.out.print("Enter Current State: ");
                        String state = scanner.nextLine();

                        System.out.print("Enter Current Zip: ");
                        String zip = scanner.nextLine();

                        System.out.print("Enter Patient Gender (M/F/O): ");
                        String sex = scanner.nextLine();

                        pstmtPatient.setString(1, ssn);
                        pstmtPatient.setInt(2, patientId);
                        pstmtPatient.setString(3, phone);
                        pstmtPatient.setInt(4, primaryDoctorId);
                        if (secondaryDoctorId == 0) {
                            pstmtPatient.setNull(5, Types.INTEGER);
                        } else {
                            pstmtPatient.setInt(5, secondaryDoctorId);
                        }
                        pstmtPatient.setString(6, condition);
                        pstmtPatient.setString(7, city);
                        pstmtPatient.setString(8, state);
                        pstmtPatient.setString(9, zip);
                        pstmtPatient.setString(10, sex);

                        try {
                            int rows = pstmtPatient.executeUpdate();
                            System.out.println(rows + " patient(s) added successfully.");
                        } catch (SQLException e) {
                            System.err.println("Error adding patient: " + e.getMessage());
                        }

                        System.out.print("\nHit 0 to exit or enter any other number for another insert: ");
                        userChoicePatient = scanner.nextInt();
                        scanner.nextLine();
                    }
                    pstmtPatient.close();
                    break;

                case 2: // Add a Doctor
                    PreparedStatement pstmtDoctor = connection.prepareStatement(
                        "INSERT INTO DOCTOR (PERSON_SSN, DR_ID, CONTACT_PHONE, DEPARTMENT_CODE, DEPT_HEAD_FLAG) VALUES (?, ?, ?, ?, ?)"
                    );
                    int userChoiceDoctor = 1;
                    while (userChoiceDoctor != 0) {
                        System.out.print("Enter Doctor SSN: ");
                        String ssn = scanner.nextLine();

                        System.out.print("Enter Doctor ID: ");
                        int doctorId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter Contact Phone: ");
                        String phone = scanner.nextLine();

                        System.out.print("Enter Department Code: ");
                        int deptCode = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Is the Doctor a Department Head? (1 for Yes, 0 for No): ");
                        int deptHeadFlag = scanner.nextInt();
                        scanner.nextLine();

                        pstmtDoctor.setString(1, ssn);
                        pstmtDoctor.setInt(2, doctorId);
                        pstmtDoctor.setString(3, phone);
                        pstmtDoctor.setInt(4, deptCode);
                        pstmtDoctor.setInt(5, deptHeadFlag);

                        try {
                            int rows = pstmtDoctor.executeUpdate();
                            System.out.println(rows + " doctor(s) added successfully.");
                        } catch (SQLException e) {
                            System.err.println("Error adding doctor: " + e.getMessage());
                        }

                        System.out.print("\nHit 0 to exit or enter any other number for another insert: ");
                        userChoiceDoctor = scanner.nextInt();
                        scanner.nextLine();
                    }
                    pstmtDoctor.close();
                    break;

                case 3: // Add a Department
                    PreparedStatement pstmtDept = connection.prepareStatement(
                        "INSERT INTO DEPARTMENT (DEPTCODE, DEPTNAME, DEPT_HEAD, PHONE_NUM, OFFICE_NUM) VALUES (?, ?, ?, ?, ?)"
                    );
                    System.out.print("Enter Department Code: ");
                    int deptCode = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Department Name: ");
                    String deptName = scanner.nextLine();

                    System.out.print("Enter Department Head ID: ");
                    int deptHead = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Phone Number: ");
                    String phone = scanner.nextLine();

                    System.out.print("Enter Office Number: ");
                    int office = scanner.nextInt();
                    scanner.nextLine();

                    pstmtDept.setInt(1, deptCode);
                    pstmtDept.setString(2, deptName);
                    pstmtDept.setInt(3, deptHead);
                    pstmtDept.setString(4, phone);
                    pstmtDept.setInt(5, office);

                    try {
                        int rows = pstmtDept.executeUpdate();
                        System.out.println(rows + " department(s) added successfully.");
                    } catch (SQLException e) {
                        System.err.println("Error adding department: " + e.getMessage());
                    }
                    pstmtDept.close();
                    break;

                case 4: // Add a Procedure
                    PreparedStatement pstmtProcedure = connection.prepareStatement(
                        "INSERT INTO PROCEDURE (PROCEDURENUM, PROCEDURE_NAME, PROCEDURE_DESCRIPTION, PROCEDURE_DURATION, OFFERING_DEPT) VALUES (?, ?, ?, ?, ?)"
                    );
                    int userChoiceProcedure = 1;
                    while (userChoiceProcedure != 0) {
                        System.out.print("Enter Procedure Number: ");
                        int procedureNum = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter Procedure Name: ");
                        String procedureName = scanner.nextLine();

                        System.out.print("Enter Procedure Description: ");
                        String procedureDesc = scanner.nextLine();

                        System.out.print("Enter Procedure Duration (in minutes): ");
                        int duration = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter Offering Department Code: ");
                        int offeringDeptCode = scanner.nextInt();
                        scanner.nextLine();

                        pstmtProcedure.setInt(1, procedureNum);
                        pstmtProcedure.setString(2, procedureName);
                        pstmtProcedure.setString(3, procedureDesc);
                        pstmtProcedure.setInt(4, duration);
                        pstmtProcedure.setInt(5, offeringDeptCode);

                        try {
                            int rows = pstmtProcedure.executeUpdate();
                            System.out.println(rows + " procedure(s) added successfully.");
                        } catch (SQLException e) {
                            System.err.println("Error adding procedure: " + e.getMessage());
                        }

                        System.out.print("\nHit 0 to exit or enter any other number for another insert: ");
                        userChoiceProcedure = scanner.nextInt();
                        scanner.nextLine();
                    }
                    pstmtProcedure.close();
                    break;

                case 5: // Add an Interaction Record
                    PreparedStatement pstmtInteraction = connection.prepareStatement(
                        "INSERT INTO INTERACTION_RECORD (PATIENT_ID, INTERACTION_ID, INTERACTION_TIME, INTERACTION_DESCRIPTION) VALUES (?, ?, ?, ?)"
                    );
                    int userChoiceInteraction = 1;
                    while (userChoiceInteraction != 0) {
                        System.out.print("Enter Patient ID: ");
                        int patientId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter Interaction ID: ");
                        int interactionId = scanner.nextInt();
                        scanner.nextLine();

                        System.out.print("Enter Interaction Date (YYYY-MM-DD): ");
                        String interactionDate = scanner.nextLine();

                        System.out.print("Enter Interaction Description: ");
                        String description = scanner.nextLine();

                        pstmtInteraction.setInt(1, patientId);
                        pstmtInteraction.setInt(2, interactionId);
                        pstmtInteraction.setDate(3, java.sql.Date.valueOf(interactionDate));
                        pstmtInteraction.setString(4, description);

                        try {
                            int rows = pstmtInteraction.executeUpdate();
                            System.out.println(rows + " interaction record(s) added successfully.");
                        } catch (SQLException e) {
                            System.err.println("Error adding interaction record: " + e.getMessage());
                        }

                        System.out.print("\nHit 0 to exit or enter any other number for another insert: ");
                        userChoiceInteraction = scanner.nextInt();
                        scanner.nextLine();
                    }
                    pstmtInteraction.close();
                    break;

                case 6: // Generate a Patient's Health Record
                    System.out.print("Enter Patient ID: ");
                    int patientId = scanner.nextInt();
                    scanner.nextLine();

                    PreparedStatement pstmtHealthRecord = connection.prepareStatement(
                        "SELECT * FROM PATIENT WHERE PATIENT_ID = ?"
                    );
                    pstmtHealthRecord.setInt(1, patientId);

                    ResultSet rsHealthRecord = pstmtHealthRecord.executeQuery();
                    if (rsHealthRecord.next()) {
                        System.out.println("Patient Record:");
                        System.out.println("SSN: " + rsHealthRecord.getString("PERSON_SSN"));
                        System.out.println("Phone: " + rsHealthRecord.getString("CURR_PHONE"));
                        System.out.println("Condition: " + rsHealthRecord.getString("CONDITION"));
                        System.out.println("City: " + rsHealthRecord.getString("CURR_CITY"));
                        System.out.println("State: " + rsHealthRecord.getString("CURR_STATE"));
                        System.out.println("Zip: " + rsHealthRecord.getString("CURR_ZIP"));
                    } else {
                        System.out.println("No patient record found for Patient ID: " + patientId);
                    }
                    rsHealthRecord.close();
                    pstmtHealthRecord.close();
                    break;

                case 7: // List Procedures by Department
                    System.out.print("Enter Department Code: ");
                    int departmentCode = scanner.nextInt();
                    scanner.nextLine();

                    PreparedStatement pstmtProceduresByDept = connection.prepareStatement(
                        "SELECT PROCEDURE_NAME, PROCEDURE_DURATION FROM PROCEDURE WHERE OFFERING_DEPT = ?"
                    );
                    pstmtProceduresByDept.setInt(1, departmentCode);

                    ResultSet rsProceduresByDept = pstmtProceduresByDept.executeQuery();
                    System.out.println("Procedures Offered by Department " + departmentCode + ":");
                    while (rsProceduresByDept.next()) {
                        System.out.println("Name: " + rsProceduresByDept.getString("PROCEDURE_NAME")
                                + ", Duration: " + rsProceduresByDept.getInt("PROCEDURE_DURATION") + " minutes");
                    }
                    rsProceduresByDept.close();
                    pstmtProceduresByDept.close();
                    break;

                case 8: // List Procedures Performed by a Doctor
                    System.out.print("Enter Doctor ID: ");
                    int docId = scanner.nextInt();
                    scanner.nextLine();

                    PreparedStatement pstmtProceduresByDoc = connection.prepareStatement(
                        "SELECT PROCEDURE_NAME, PROCEDURE_DURATION FROM UNDERGOES " +
                        "JOIN PROCEDURE ON UNDERGOES.PROCEDURENUM = PROCEDURE.PROCEDURENUM WHERE DR_ID = ?"
                    );
                    pstmtProceduresByDoc.setInt(1, docId);

                    ResultSet rsProceduresByDoc = pstmtProceduresByDoc.executeQuery();
                    System.out.println("Procedures Performed by Doctor " + docId + ":");
                    while (rsProceduresByDoc.next()) {
                        System.out.println("Name: " + rsProceduresByDoc.getString("PROCEDURE_NAME")
                                + ", Duration: " + rsProceduresByDoc.getInt("PROCEDURE_DURATION") + " minutes");
                    }
                    rsProceduresByDoc.close();
                    pstmtProceduresByDoc.close();
                    break;

                case 0: // Exit
                    System.out.println("Exiting the program. Goodbye!");
                    connection.close();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (userChoice != 0);
    }
}
