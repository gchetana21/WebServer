package testClientServer;

import org.example.tcpConnection.client.ServerSocketChannelClient;
import org.example.tcpConnection.server.ServerSocketChannelConnection;
import org.junit.jupiter.api.AfterEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class ServerSocketChannelTest {

    Process server;
    ServerSocketChannelClient client;

    @BeforeEach
    public void setup() throws IOException, InterruptedException {
//        server = ServerSocketChannelConnection.start();
        client = ServerSocketChannelClient.start();
    }

    @Test
    public void testConnection(){
        String resp1 = client.sendMessage("hello");
        String resp2 = client.sendMessage("world");
        assertEquals("hello", resp1);
        assertEquals("world", resp2);
    }


    @AfterEach
    public  void teardown() throws IOException {
        server.destroy();
        ServerSocketChannelClient.stop();
    }
}
