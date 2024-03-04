package server;

import database.HashMapImpl;

import java.net.SocketException;

public class MontrealServerImpl extends ServerImpl{
    public MontrealServerImpl(HashMapImpl database) throws SocketException {
        super(database, 5052);
    }

    @Override
    public String getServerName() {
        return "Montreal";
    }
}
