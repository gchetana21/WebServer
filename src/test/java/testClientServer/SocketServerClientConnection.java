package testClientServer;

import org.example.tcpConnection.client.SocketServerClient;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


import java.io.IOException;

public class SocketServerClientConnection {

    @Test
    public void testConnectionBetweenClientAndServer() throws IOException {
        SocketServerClient socketServerClient = new SocketServerClient();
        //establishing connection
        socketServerClient.startConnection("localhost", 8500);
        String response = socketServerClient.sendMessage("hello server");
        assertEquals("hello client",response);
    }


    @Test
    public void testConnectionBetweenClientAndServer1() throws IOException {
        SocketServerClient socketServerClient1 = new SocketServerClient();
        //establishing connection
        socketServerClient1.startConnection("localhost", 8600);
        String msg1 = socketServerClient1.sendMessage("hello");
        String msg2 = socketServerClient1.sendMessage("world");
        String terminate = socketServerClient1.sendMessage(".");

        assertEquals("hello", msg1);
        assertEquals( "world", msg2);
        assertEquals( "closing connection", terminate);

    }
    @Test
    public void testConnectionBetweenClientAndServer2() throws IOException {
        SocketServerClient socketServerClient2 = new SocketServerClient();
        //establishing connection
        socketServerClient2.startConnection("localhost", 8600);
        String msg1 = socketServerClient2.sendMessage("Hi");
        String msg2 = socketServerClient2.sendMessage("Chetana");
        String terminate = socketServerClient2.sendMessage(".");

        assertEquals(msg1, "Hi");
        assertEquals(msg2, "Chetana");
        assertEquals(terminate, "closing connection");

    }

}
