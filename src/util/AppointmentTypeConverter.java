package util;

import ManagementServer.AppointmentType;

public class AppointmentTypeConverter {

    public static String convertToString (AppointmentType appointmentType) {
        switch (appointmentType.value()) {
            case AppointmentType._Physician:
                return "Physician";
            case AppointmentType._Dental:
                return "Dental";
            case AppointmentType._Surgeon:
                return "Surgeon";
        }
        return null;
    }

    public static AppointmentType convertToAppointmentType (int value) {
        switch (value) {
            case AppointmentType._Physician:
                return AppointmentType.Physician;
            case AppointmentType._Dental:
                return AppointmentType.Dental;
            case AppointmentType._Surgeon:
                return AppointmentType.Surgeon;
        }
        return null;
    }

    public static AppointmentType convertToAppointmentType (String value) {
        switch (value.toLowerCase()) {
            case "physician":
                return AppointmentType.Physician;
            case "dental":
                return AppointmentType.Dental;
            case "surgeon":
                return AppointmentType.Surgeon;
        }
        return null;
    }
}
