package database;

import model.Appointment;
import model.AppointmentType;

import java.util.concurrent.ConcurrentHashMap;

public class MontrealHashMap extends HashMap {

    public MontrealHashMap() {
        initializeDB();
    }

    public void initializeDB() {
        ConcurrentHashMap<AppointmentType, java.util.HashMap<String, Appointment>> hashMap = new ConcurrentHashMap<>();
        java.util.HashMap<String, Appointment> value = new java.util.HashMap<>();
        value.put("MTLA101124", new Appointment("MTLA101124", AppointmentType.Physician, 1));
        hashMap.put(AppointmentType.Physician, value);
        hashMap.put(AppointmentType.Surgeon, new java.util.HashMap<>());

        super.setAppointments(hashMap);
    }
}
