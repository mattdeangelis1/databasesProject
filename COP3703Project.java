import java.util.*;
import java.sql.*;
import java.io.*;
import java.util.Scanner;



public class COP3703Project {

    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        // Get database connection details from the user
        System.out.println("Enter your database username (UID): ");
        String uid = scanner.nextLine(); //G07

        System.out.println("Enter your database password (PWORD): ");
        String pword = scanner.nextLine(); //2U6f2M9q

        System.out.println("Enter your database URL: ");
        String url = scanner.nextLine(); //jdbc:oracle:thin:@cisvm-oracle.unfcsd.unf.edu:1521:orcl

        // Register the Oracle JDBC driver
        DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());

        int userChoice;
        do {
            Connection connection = DriverManager.getConnection(url, uid, pword);
            connection.setAutoCommit(true);


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
            scanner.nextLine(); // Consume the newline

            switch (userChoice) {
                case 1:
                    addPatient(connection);
                    break;

                case 2:
                    addDoctor(connection);
                    break;

                case 3:
                    addDepartment(connection);
                    break;

                case 4:
                    addProcedure(connection);
                    break;

                case 5:
                    addInteraction(connection);
                    break;

                case 6:
                    generatePatientHealthRecord(connection);
                    break;

                case 7:
                    listProceduresByDepartment(connection);
                    break;

                case 8:
                    listProceduresByDoctor(connection);
                    break;

                case 0:
                    System.out.println("Exiting the program. Goodbye!");
                    connection.close();
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            } 
        } while (userChoice != 0);
    }
    private static void addPatient(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Add a Patient ===");
    
        String sql = "INSERT INTO PATIENT (PERSON_SSN, PATIENT_ID, CURR_PHONE, PRIMARY_DR_ID, SECONDARY_DR_ID, "
                   + "CONDITION, CURR_CITY, CURR_STATE, CURR_ZIP, SEX) "
                   + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
        PreparedStatement pstmt = connection.prepareStatement(sql);
    
        System.out.print("Enter Patient SSN: ");
        String ssn = scanner.nextLine();
        System.out.println("Debug: SSN = " + ssn);
    
        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Debug: Patient ID = " + patientId);
    
        System.out.print("Enter Current Phone: ");
        String phone = scanner.nextLine();
        System.out.println("Debug: Phone = " + phone);
    
        System.out.print("Enter Primary Doctor ID: ");
        int primaryDoctorId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Debug: Primary Doctor ID = " + primaryDoctorId);
    
        System.out.print("Enter Secondary Doctor ID (or 0 if none): ");
        int secondaryDoctorId = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.println("Debug: Secondary Doctor ID = " + secondaryDoctorId);
    
        System.out.print("Enter Patient Condition (Critical/Stable/Fair): ");
        String condition = scanner.nextLine();
        System.out.println("Debug: Condition = " + condition);
    
        System.out.print("Enter Current City: ");
        String city = scanner.nextLine();
        System.out.println("Debug: City = " + city);
    
        System.out.print("Enter Current State: ");
        String state = scanner.nextLine();
        System.out.println("Debug: State = " + state);
    
        System.out.print("Enter Current Zip: ");
        String zip = scanner.nextLine();
        System.out.println("Debug: Zip = " + zip);
    
        System.out.print("Enter Patient Gender (M/F/O): ");
        String sex = scanner.nextLine();
        System.out.println("Debug: SEX = " + sex);
    
        pstmt.setString(1, ssn);
        pstmt.setInt(2, patientId);
        pstmt.setString(3, phone);
        pstmt.setInt(4, primaryDoctorId);
        if (secondaryDoctorId == 0) {
            pstmt.setNull(5, Types.INTEGER);
        } else {
            pstmt.setInt(5, secondaryDoctorId);
        }
        pstmt.setString(6, condition);
        pstmt.setString(7, city);
        pstmt.setString(8, state);
        pstmt.setString(9, zip);
        pstmt.setString(10, sex);
    
        System.out.println("Debug: Ready to execute query");
        System.out.println("Debug: Connection is active? " + !connection.isClosed());
        int rows = pstmt.executeUpdate();
        System.out.println(rows + " patient(s) added successfully.");
        pstmt.close();
    }
    
    private static void addDoctor(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Add a Doctor ===");

        String sql = "INSERT INTO DOCTOR (PERSON_SSN, DR_ID, CONTACT_PHONE, DEPARTMENT_CODE, DEPT_HEAD_FLAG) "
                   + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstmt = connection.prepareStatement(sql);

        System.out.print("Enter Doctor SSN: ");
        String ssn = scanner.nextLine();

        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Contact Phone: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Department Code: ");
        int departmentCode = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Is the Doctor a Department Head? (1 for Yes, 0 for No): ");
        int deptHeadFlag = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        pstmt.setString(1, ssn);
        pstmt.setInt(2, doctorId);
        pstmt.setString(3, phone);
        pstmt.setInt(4, departmentCode);
        pstmt.setInt(5, deptHeadFlag);

        int rows = pstmt.executeUpdate();
        System.out.println(rows + " doctor(s) added successfully.");
        pstmt.close();
    }

    private static void addDepartment(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Add a Department ===");

        String sql = "INSERT INTO DEPARTMENT (DEPTCODE, DEPTNAME, DEPT_HEAD, PHONE_NUM, OFFICE_NUM) "
                   + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement pstmt = connection.prepareStatement(sql);

        System.out.print("Enter Department Code: ");
        int deptCode = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Department Name: ");
        String deptName = scanner.nextLine();

        System.out.print("Enter Department Head ID: ");
        int deptHead = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Phone Number: ");
        String phone = scanner.nextLine();

        System.out.print("Enter Office Number: ");
        int office = scanner.nextInt();

        pstmt.setInt(1, deptCode);
        pstmt.setString(2, deptName);
        pstmt.setInt(3, deptHead);
        pstmt.setString(4, phone);
        pstmt.setInt(5, office);

        int rows = pstmt.executeUpdate();
        System.out.println(rows + " department(s) added successfully.");
        pstmt.close();
    }
    private static void addProcedure(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Add a Procedure ===");
    
        String sql = "INSERT INTO PROCEDURE (PROCEDURENUM, PROCEDURE_NAME, PROCEDURE_DESCRIPTION, "
                   + "PROCEDURE_DURATION, OFFERING_DEPT) VALUES (?, ?, ?, ?, ?)";
    
        PreparedStatement pstmt = connection.prepareStatement(sql);
    
        System.out.print("Enter Procedure Number: ");
        int procedureNum = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        System.out.print("Enter Procedure Name: ");
        String procedureName = scanner.nextLine();
    
        System.out.print("Enter Procedure Description: ");
        String description = scanner.nextLine();
    
        System.out.print("Enter Procedure Duration (in minutes): ");
        int duration = scanner.nextInt();
        scanner.nextLine(); // Consume newline
    
        System.out.print("Enter Offering Department Code: ");
        int deptCode = scanner.nextInt();
        scanner.nextLine();
    
        pstmt.setInt(1, procedureNum);
        pstmt.setString(2, procedureName);
        pstmt.setString(3, description);
        pstmt.setInt(4, duration);
        pstmt.setInt(5, deptCode);
    
        System.out.println("Executing SQL: " + sql); // Debugging log
        if (connection.isClosed()) {
            System.err.println("Error: Database connection is closed.");
        } else {
            System.out.println("Debug: Database connection is active.");
        }        
        int rows = pstmt.executeUpdate();
        System.out.println(rows + " procedure(s) added successfully."); // Debugging log
        pstmt.close();
    }
    
    private static void addInteraction(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Add an Interaction Record ===");

        String sql = "INSERT INTO INTERACTION_RECORD (PATIENT_ID, INTERACTION_ID, INTERACTION_TIME, INTERACTION_DESCRIPTION) "
                   + "VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = connection.prepareStatement(sql);

        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Interaction ID: ");
        int interactionId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter Interaction Date (YYYY-MM-DD): ");
        String date = scanner.nextLine();

        System.out.print("Enter Interaction Description: ");
        String description = scanner.nextLine();

        pstmt.setInt(1, patientId);
        pstmt.setInt(2, interactionId);
        pstmt.setDate(3, java.sql.Date.valueOf(date));
        pstmt.setString(4, description);

        int rows = pstmt.executeUpdate();
        System.out.println(rows + " interaction(s) added successfully.");
        pstmt.close();
    }

    private static void generatePatientHealthRecord(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== Generate a Patient's Health Record ===");

        System.out.print("Enter Patient ID: ");
        int patientId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sql = "SELECT * FROM PATIENT WHERE PATIENT_ID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, patientId);

        ResultSet rs = pstmt.executeQuery();

        if (rs.next()) {
            System.out.println("Patient Information:");
            System.out.println("SSN: " + rs.getString("PERSON_SSN"));
            System.out.println("Phone: " + rs.getString("CURR_PHONE"));
            System.out.println("Condition: " + rs.getString("CONDITION"));
            System.out.println("City: " + rs.getString("CURR_CITY"));
            System.out.println("State: " + rs.getString("CURR_STATE"));
            System.out.println("Zip: " + rs.getString("CURR_ZIP"));
        } else {
            System.out.println("Patient not found.");
        }
        rs.close();
        pstmt.close();
    }

    private static void listProceduresByDepartment(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== List Procedures by Department ===");

        System.out.print("Enter Department Name or Code: ");
        String deptInput = scanner.nextLine();

        String sql = "SELECT PROCEDURE_NAME, PROCEDURE_DURATION FROM PROCEDURE WHERE OFFERING_DEPT = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setString(1, deptInput);

        ResultSet rs = pstmt.executeQuery();

        System.out.println("Procedures Offered:");
        while (rs.next()) {
            System.out.println("Name: " + rs.getString("PROCEDURE_NAME") +
                               ", Duration: " + rs.getInt("PROCEDURE_DURATION") + " minutes");
        }
        rs.close();
        pstmt.close();
    }

    private static void listProceduresByDoctor(Connection connection) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("=== List Procedures Performed by a Doctor ===");

        System.out.print("Enter Doctor ID: ");
        int doctorId = scanner.nextInt();

        String sql = "SELECT PROCEDURE_NAME, PROCEDURE_DURATION FROM UNDERGOES "
                   + "JOIN PROCEDURE ON UNDERGOES.PROCEDURENUM = PROCEDURE.PROCEDURENUM "
                   + "WHERE DR_ID = ?";
        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, doctorId);

        ResultSet rs = pstmt.executeQuery();

        System.out.println("Procedures Performed:");
        while (rs.next()) {
            System.out.println("Name: " + rs.getString("PROCEDURE_NAME") +
                               ", Duration: " + rs.getInt("PROCEDURE_DURATION") + " minutes");
        }
        rs.close();
        pstmt.close();
    }
}
