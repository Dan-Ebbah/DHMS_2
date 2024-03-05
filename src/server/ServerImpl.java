package server;

import ManagementServer.AppointmentType;
import ManagementServer.HealthCareSystemPOA;
import com.sun.xml.internal.ws.util.StringUtils;
import database.HashMapImpl;
import model.Appointment;
import model.HospitalType;
import model.UDPServerInfo;
import util.AppointmentTypeConverter;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServerImpl extends HealthCareSystemPOA {
    private HashMapImpl _database;

    public ServerImpl(HashMapImpl database, int portNum) throws SocketException {
        _database = database;
        createAndStartThread(portNum, getServerName());
    }

    public String getServerName() {
        return null;
    }

    private void createAndStartThread(int portNum, String serverName) throws SocketException {
        new Thread(new UDPServerThread(portNum, _database, serverName)).start();
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
    public String cancelAppointment(String patientID, String appointmentID) {
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
        AppointmentType convertedAppointmentType = AppointmentTypeConverter.convertToAppointmentType(appointmentType);
        StringBuilder stringBuilder = new StringBuilder(appointmentType).append(" - ");
        stringBuilder.append(_database.getAvailability(convertedAppointmentType));
        stringBuilder.append(" ");
        stringBuilder.append(getOthersAvailability(appointmentType));
        stringBuilder.append(" ");
        String msg = stringBuilder.toString();
//        logger.info(msg);
        return msg;
    }

    public Boolean isBookableAndBooked(UDPServerInfo serverInfo, String newAppointmentID, String patientId) {
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress byAddress = InetAddress.getByName("localhost");
            String toSend = String.format("%d,%s/%s", 2, newAppointmentID, patientId);
            byte[] sendData = toSend.getBytes();
            DatagramPacket datagramPacket = new DatagramPacket(sendData, sendData.length, byAddress, serverInfo.getPort());

            socket.send(datagramPacket);

            byte[] receiveData = new byte[1024];
            DatagramPacket datagramPacket1 = new DatagramPacket(receiveData, receiveData.length);
            socket.receive(datagramPacket1);

            String s = new String(datagramPacket1.getData(), 0, datagramPacket1.getLength());
            return Boolean.valueOf(s);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getOthersAvailability(String appointmentType) {
        StringBuilder stringBuilder = new StringBuilder();
        for(UDPServerInfo serverInfo : getOtherServersInfo()) {
            try (DatagramSocket socket = new DatagramSocket()) {
                InetAddress byAddress = InetAddress.getByName("localhost");
                String toSend = String.format("%d,%s", 1, appointmentType);
                byte[] sendData = toSend.getBytes();
                DatagramPacket datagramPacket = new DatagramPacket(sendData, sendData.length, byAddress, serverInfo.getPort());

                socket.send(datagramPacket);

                byte[] receiveData = new byte[1024];
                DatagramPacket datagramPacket1 = new DatagramPacket(receiveData, receiveData.length);
                socket.receive(datagramPacket1);

                String s = new String(datagramPacket1.getData(), 0, datagramPacket1.getLength());
                stringBuilder.append(" ");
                stringBuilder.append(s);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return stringBuilder.toString();
    }

    protected UDPServerInfo[] getOtherServersInfo() {
        UDPServerInfo sherbrookeServerAddress = new UDPServerInfo("SherbrookeServerAddress", 5053);
        UDPServerInfo quebecServerAddress = new UDPServerInfo("QuebecServerAddress", 5051);

        return new UDPServerInfo[]{sherbrookeServerAddress, quebecServerAddress};
    }

    @Override
    public boolean swapAppointment(String patientID, String oldAppointmentType, String oldAppointmentID, String newAppointmentType, String newAppointmentID) {
        if (!isAppointmentPresent(oldAppointmentID)) {
            return false;
        }

        HospitalType hospitalTypeInfo = extractHospitalInfo(newAppointmentID);
        if (!isBookableAndBooked(hospitalTypeInfo.getHospitalServerAddress(), newAppointmentID, patientID)) {
            return  false;
        }

        _database.cancel(patientID, oldAppointmentID);
        return true;
    }

    private HospitalType extractHospitalInfo(String newAppointmentID) {
        String substring = newAppointmentID.substring(0, 3).toUpperCase();
        return HospitalType.findHospital(substring);
    }

    private boolean isAppointmentPresent(String appointmentID) {
        return _database.findByAppointmentID(appointmentID) != null;
    }

}
