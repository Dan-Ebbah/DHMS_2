package database;

import model.Appointment;
import ManagementServer.AppointmentType;
import java.util.concurrent.ConcurrentHashMap;

public class SherbrookeHashMap extends HashMapImpl {

    public SherbrookeHashMap() {
        initializeDB();
    }

    public void initializeDB() {
        ConcurrentHashMap<AppointmentType, java.util.HashMap<String, Appointment>> hashMap = new ConcurrentHashMap<>();
        super.setAppointments(hashMap);
    }
}
