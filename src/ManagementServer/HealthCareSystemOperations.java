package ManagementServer;


/**
* ManagementServer/HealthCareSystemOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from ManagementServer.idl
* Thursday, February 29, 2024 1:42:55 o'clock PM EST
*/

public interface HealthCareSystemOperations 
{
  boolean addAppointment (String appointmentID, ManagementServer.AppointmentType appointmentType, int capacity);
  boolean removeAppointment (String appointmentID, ManagementServer.AppointmentType appointmentType);
  boolean bookAppointment (String patientID, String appointmentType, String appointmentID);
  boolean cancelAppointment (String patientID, String appointmentType, String appointmentID);
  String getAppointmentSchedule (String patientID);
  String listAppointmentAvailability (String appointmentType);
  boolean swapAppointment (String patientID, String oldAppointmentType, String oldAppointmentID, String newAppointmentType, String newAppointmentID);
} // interface HealthCareSystemOperations
