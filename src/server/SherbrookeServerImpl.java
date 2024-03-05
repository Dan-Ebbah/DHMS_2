package server;

import database.HashMapImpl;
import model.UDPServerInfo;
import util.LoggerUtil;

import java.net.SocketException;

public class SherbrookeServerImpl extends ServerImpl{
    public SherbrookeServerImpl(HashMapImpl database) throws SocketException {
        super(database, 5053, LoggerUtil.getLogger(SherbrookeServerImpl.class.getName(), "Sherbrooke"));
    }

    @Override
    public String getServerName() {
        return "Sherbrooke";
    }

    @Override
    protected UDPServerInfo[] getOtherServersInfo() {
        UDPServerInfo sherbrookeServerAddress = new UDPServerInfo("QuebecServerAddress", 5051);
        UDPServerInfo quebecServerAddress = new UDPServerInfo("MontrealServerAddress", 5052);

        return new UDPServerInfo[]{sherbrookeServerAddress, quebecServerAddress};
    }
}
