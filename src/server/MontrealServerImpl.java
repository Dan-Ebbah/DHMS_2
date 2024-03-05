package server;

import database.HashMapImpl;
import util.LoggerUtil;

import java.net.SocketException;

public class MontrealServerImpl extends ServerImpl{
    public MontrealServerImpl(HashMapImpl database) throws SocketException {
        super(database, 5052, LoggerUtil.getLogger(MontrealServerImpl.class.getName(), "Montreal"));
    }

    @Override
    public String getServerName() {
        return "Montreal";
    }
}
