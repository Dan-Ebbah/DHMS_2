package server;

import ManagementServer.AppointmentType;
import ManagementServer.HealthCareSystemPOA;

public class ServerImpl extends HealthCareSystemPOA {
    @Override
    public boolean addAppointment(String appointmentID, AppointmentType appointmentType, int capacity) {
        return false;
    }

    @Override
    public boolean removeAppointment(String appointmentID, AppointmentType appointmentType) {
        return false;
    }

    @Override
    public boolean bookAppointment(String patientID, String appointmentType, String appointmentID) {
        return false;
    }

    @Override
    public boolean cancelAppointment(String patientID, String appointmentType, String appointmentID) {
        return false;
    }

    @Override
    public String getAppointmentSchedule(String patientID) {
        return "null";
    }

    @Override
    public String listAppointmentAvailability(String appointmentType) {
        return null;
    }

    @Override
    public boolean swapAppointment(String patientID, String oldAppointmentType, String oldAppointmentID, String newAppointmentType, String newAppointmentID) {
        return false;
    }

}
