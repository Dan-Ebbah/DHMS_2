package server;

import ManagementServer.AppointmentType;
import ManagementServer.HealthCareSystemPOA;
import database.HashMapImpl;
import model.Appointment;

import java.util.List;

public class ServerImpl extends HealthCareSystemPOA {
    private HashMapImpl _database;

    public ServerImpl(HashMapImpl database) {
        _database = database;
    }

    @Override
    public String addAppointment(String appointmentID, AppointmentType appointmentType, int capacity) {
        if (isAppointmentPresent(appointmentID)) {
            String msg = "Could not add appointment, because appointment seems to already exist";
//            logger.info(String.format(msg.concat(": Appointment Type = %s, Appointment ID = %s, Appointment Capacity = %d"), appointmentID, appointmentType, capacity));
            return msg;
        }

        Appointment appointment = new Appointment(appointmentID, appointmentType, capacity);
        _database.insert(appointment);
        String msg = "Successfully added the new appointment";
        return msg;
    }

    @Override
    public String removeAppointment(String appointmentID, AppointmentType appointmentType) {
        if (isAppointmentPresent(appointmentID)) {
            return _database.remove(new Appointment(appointmentID, appointmentType));
        }
        return "Appointment does not exist to be removed";
    }

    @Override
    public String bookAppointment(String patientID, AppointmentType appointmentType, String appointmentID) {
        Appointment appointment = new Appointment(appointmentID, appointmentType);
        if (isAppointmentPresent(appointmentID)) {
            return _database.book(patientID, appointment);
        }
        return "Couldn't book appointment because Appointment ID is wrong";
    }

    @Override
    public String cancelAppointment(String patientID, String appointmentType, String appointmentID) {
        if (isAppointmentPresent(appointmentID)) {
            return _database.cancel(patientID, appointmentID);
        }
        return "Patient does not have the appointment booked";
    }

    @Override
    public String getAppointmentSchedule(String patientID) {
        List<Appointment> appointmentList = _database.getByPatientId(patientID);
        String msg = appointmentList.toString();
        return msg;
    }

    @Override
    public String listAppointmentAvailability(String appointmentType) {
        return "List ";
    }

    @Override
    public boolean swapAppointment(String patientID, String oldAppointmentType, String oldAppointmentID, String newAppointmentType, String newAppointmentID) {
        return false;
    }

    private boolean isAppointmentPresent(String appointmentID) {
        return _database.findByAppointmentID(appointmentID) != null;
    }

}
