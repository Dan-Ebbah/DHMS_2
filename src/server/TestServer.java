package server;

import ManagementServer.AppointmentType;
import ManagementServer.HealthCareSystem;
import ManagementServer.HealthCareSystemHelper;
import database.HashMapImpl;
import model.Appointment;
import org.omg.CORBA.Object;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

public class TestServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);


            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            ConcurrentHashMap<AppointmentType, HashMap<String, Appointment>> appointments = new ConcurrentHashMap<>();
            HashMapImpl hashMap = new HashMapImpl(appointments);

            ServerImpl server = new ServerImpl(hashMap);

            Object ref = rootpoa.servant_to_reference(server);

            HealthCareSystem healthCareSystem = HealthCareSystemHelper.narrow(ref);

            Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            NameComponent[] path = ncRef.to_name("TestHello");
            ncRef.rebind(path, healthCareSystem);

            System.out.println("Test Server is running ...");

            orb.run();


        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}
