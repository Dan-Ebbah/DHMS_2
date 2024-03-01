package client;

import ManagementServer.HealthCareSystem;
import ManagementServer.HealthCareSystemHelper;
import org.omg.CORBA.Object;
import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;


public class TestClient {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);

            Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);
            HealthCareSystem healthCareSystem = HealthCareSystemHelper.narrow(ncRef.resolve_str("TestHello"));
            System.out.println((healthCareSystem.getAppointmentSchedule("dd")));
        } catch (InvalidName | CannotProceed | NotFound | org.omg.CORBA.ORBPackage.InvalidName e) {
            throw new RuntimeException(e);
        }
    }
}
