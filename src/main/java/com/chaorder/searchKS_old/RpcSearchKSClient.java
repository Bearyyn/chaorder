package com.chaorder.searchKS_old;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import java.io.IOException;
import java.util.List;
import java.util.ResourceBundle;

/**
 * @Description:
 * @author: Hugo Ng
 * @Date: 17-6-12
 */
public class RpcSearchKSClient {
    private static final ResourceBundle SETTING = ResourceBundle.getBundle("searchKS");
    private static final String SEARCHKS_RPC_ADDRESS = SETTING.getString("searchKS.rpc.address");
    private static final int SEARCHKS_RPC_PORT = Integer.parseInt(SETTING.getString("searchKS.rpc.port"));

    public static List<EventPayload> getKSResult(String content) throws IOException {
        List<EventPayload> eventPayload = null;
        try {
            TTransport transport = new TSocket(SEARCHKS_RPC_ADDRESS, SEARCHKS_RPC_PORT);
            transport = new TFramedTransport(transport);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            SearchKSService.Client client = new SearchKSService.Client(protocol);

            eventPayload = client.process(content);

            transport.close();
        } catch (TTransportException e) {
            e.printStackTrace();
        } catch (TException x) {
            x.printStackTrace();
        }

        return eventPayload;
    }

    public List<EventPayload> eventSearch(String args) {
    	List<EventPayload> result = null;
        try {
            result = RpcSearchKSClient.getKSResult(args);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
