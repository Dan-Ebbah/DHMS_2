package client;

import ManagementServer.AppointmentType;
import ManagementServer.HealthCareSystem;
import ManagementServer.HealthCareSystemHelper;
import model.User;
import org.omg.CORBA.Object;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import util.AppointmentTypeConverter;

import java.util.Scanner;


public class AdminClient {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);

            User user = logIn();
            if (hasAccess(user)) {
                HealthCareSystem healthCareSystem = connectToRightServer(orb, user);

                Scanner scanner = new Scanner(System.in);
                getOperation(scanner, healthCareSystem);
            } else {
                System.out.println("Wrong ID entered");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static HealthCareSystem connectToRightServer(ORB orb, User user) throws org.omg.CORBA.ORBPackage.InvalidName, NotFound, CannotProceed, InvalidName {
        String hospitalName = user.getHospitalType().getHospitalName();
        String serverName = hospitalName + "_Server";
        Object objRef = orb.resolve_initial_references("NameService");
        NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
        HealthCareSystem healthCareSystem = HealthCareSystemHelper.narrow(ncRef.resolve_str(serverName));
        return healthCareSystem;
    }

    private static void getOperation(Scanner scanner, HealthCareSystem managementServer) throws Exception {
        while (true) {
            System.out.println("\nAdmin Operations:");
            System.out.println("\t1. Add Appointment");
            System.out.println("\t2. Remove Appointment");
            System.out.println("\t3. List Appointment Availability");
            System.out.println("\t4. Exit");
            System.out.print("Choose an operation: ");
            int choice = scanner.nextInt();
            scanner.nextLine();

            AppointmentType appointmentType;
            String appointmentID;
            switch (choice) {
                case 1:
                    appointmentID = getAppointmentID(scanner);
                    appointmentType = selectAppointmentType(scanner);

                    System.out.print("Enter Capacity: ");
                    int capacity = scanner.nextInt();
                    System.out.println(managementServer.addAppointment(appointmentID, appointmentType, capacity));
                    break;
                case 2:
                    appointmentID = getAppointmentID(scanner);
                    appointmentType = selectAppointmentType(scanner);

                    System.out.println(managementServer.removeAppointment(appointmentID, appointmentType));
                    break;
                case 3:
                    appointmentType = selectAppointmentType(scanner);
                    System.out.println(managementServer.listAppointmentAvailability(AppointmentTypeConverter.convertToString(appointmentType)));
                    break;
                case 4: // Exit
                    scanner.close();
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static boolean hasAccess(User user) {
        return user != null && user.isAdmin();
    }

    private static String getAppointmentID(Scanner scanner) throws Exception {
        System.out.print("Enter Appointment ID: ");
        String appointmentIDAdd = scanner.nextLine();
        validateAppointmentID(appointmentIDAdd);
        return appointmentIDAdd;
    }

    static AppointmentType selectAppointmentType(Scanner scan) {
        System.out.println("Enter Appointment Type: ");
        System.out.println("\t1. Physician");
        System.out.println("\t2. Dental");
        System.out.println("\t3. Surgeon");

        System.out.print("Select a type: ");
        int choice = scan.nextInt();
        scan.nextLine();

        switch (choice) {
            case 1:
                return AppointmentType.Physician;
            case 2:
                return AppointmentType.Dental;
            case 3:
                return AppointmentType.Surgeon;
        }
        return null;
    }

    private static void validateAppointmentID(String appointmentIDAdd) throws Exception {
        if (appointmentIDAdd.length() != 10) {
            System.out.println("INVALID APPOINTMENT ID");
            throw new Exception("INVALID APPOINTMENT ID");
        }
    }

    private static User logIn() {
        System.out.print("\nHello please enter your ID number: ");
        Scanner scanner = new Scanner(System.in);
        String clientID = scanner.nextLine().toUpperCase();
        User user = validateAndExtractIdInfo(clientID);
        return user;
    }

    static User validateAndExtractIdInfo(String clientID) {
        if( isValidId(clientID) ) {
            return extractInfo(clientID);

        }
        return null;
    }

    private static User extractInfo(String clientID) {

        return new User(clientID);
    }

    private static boolean isValidId(String clientID) {
        return clientID.length() == 8;
    }

}
