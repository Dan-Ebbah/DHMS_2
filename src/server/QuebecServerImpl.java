package server;

import database.HashMapImpl;
import model.UDPServerInfo;

import java.net.SocketException;

public class QuebecServerImpl extends ServerImpl{
    public QuebecServerImpl(HashMapImpl database) throws SocketException {
        super(database, 5051);
    }

    @Override
    public String getServerName() {
        return "Quebec";
    }

    @Override
    protected UDPServerInfo[] getOtherServersInfo() {
        UDPServerInfo quebecServerAddress = new UDPServerInfo("MontrealServerAddress", 5052);
        UDPServerInfo sherbrookeServerAddress = new UDPServerInfo("SherbrookeServerAddress", 5053);

        return new UDPServerInfo[]{quebecServerAddress, sherbrookeServerAddress};
    }
}
