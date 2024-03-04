package server;

import ManagementServer.AppointmentType;
import database.HashMapImpl;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import static util.AppointmentTypeConverter.convertToAppointmentType;

public class UDPServerThread implements Runnable{
    private boolean running = true;
    private int portNumber;
    private DatagramSocket socket;
    private final HashMapImpl database;
    private byte[] buffer = new byte[1024];
    private String city;

    public UDPServerThread(int portNumber, HashMapImpl database, String city) throws SocketException {
        this.portNumber = portNumber;
        socket = new DatagramSocket(portNumber);
        this.database = database;
        this.city = city;
        System.out.printf("Server Started. Listening for %s Clients on port %d ....%n", city, portNumber);
    }

    @Override
    public void run() {
        System.out.println("Hello There I am waiting.....");
        running = true;
        while (running) {
            try {
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(datagramPacket);

                String s = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("I am tasked to search for appointments for " + s.toUpperCase());

                byte[] response = listCurrentAvailability(s).getBytes();

                DatagramPacket sendPacket = new DatagramPacket(response, response.length, datagramPacket.getAddress(), datagramPacket.getPort());
                socket.send(sendPacket);
            } catch (IOException e) {
                running = false;
                throw new RuntimeException(e);
            }
        }
        socket.close();
    }

    private String listCurrentAvailability(String appointmentTypeString) {
        AppointmentType appointmentType = convertToAppointmentType(appointmentTypeString);
        String s = appointmentType == null ? "" : database.getAvailability(appointmentType);
        System.out.printf("I searched through [%s] for [%s] database and got back : ->  %s", city, appointmentTypeString ,s);
        System.out.println();
        return s;
    }

}
