package server;

import ManagementServer.HealthCareSystem;
import ManagementServer.HealthCareSystemHelper;
import database.MontrealHashMap;
import database.QuebecHashMap;
import database.SherbrookeHashMap;
import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.PortableServer.POA;
import org.omg.PortableServer.POAHelper;

public class Server {
    public static void main(String[] args) {
        try {
            ORB orb = ORB.init(args, null);


            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            Object objRef = orb.resolve_initial_references("NameService");
            NamingContextExt ncRef = NamingContextExtHelper.narrow(objRef);

            MontrealServerImpl montrealServer = new MontrealServerImpl(new MontrealHashMap());
            QuebecServerImpl quebecServer = new QuebecServerImpl(new QuebecHashMap());
            SherbrookeServerImpl sherbrookeServer = new SherbrookeServerImpl(new SherbrookeHashMap());
            ServerImpl[] servers = { montrealServer, quebecServer, sherbrookeServer};

            for(ServerImpl server : servers) {
                Object ref = rootpoa.servant_to_reference(server);
                HealthCareSystem healthCareSystem = HealthCareSystemHelper.narrow(ref);
                NameComponent[] path = ncRef.to_name(server.getServerName() + "_Server");
                ncRef.rebind(path, healthCareSystem);
            }

            System.out.println("All Servers are running ...");

            orb.run();


        } catch (Exception e) {
            System.err.println("ERROR: " + e);
            e.printStackTrace(System.out);
        }
    }
}
