package database;

import model.Appointment;
import model.AppointmentType;

import java.util.concurrent.ConcurrentHashMap;

public class SherbrookeHashMap extends HashMap {

    public SherbrookeHashMap() {
        initializeDB();
    }

    public void initializeDB() {
        ConcurrentHashMap<AppointmentType, java.util.HashMap<String, Appointment>> hashMap = new ConcurrentHashMap<>();
        super.setAppointments(hashMap);
    }
}
