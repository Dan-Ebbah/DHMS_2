package server;

import ManagementServer.HealthCareSystem;
import ManagementServer.HealthCareSystemHelper;
import org.omg.CORBA.Object;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class TestServer {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);


            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            ServerImpl server = new ServerImpl();

            Object ref = rootpoa.servant_to_reference(server);

            HealthCareSystem healthCareSystem = HealthCareSystemHelper.narrow(ref);

            Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            NameComponent[] path = ncRef.to_name("TestHello");
            ncRef.bind(path, healthCareSystem);

            System.out.println("Test Server is running ...");

            orb.run();


        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}
