package testClientServer;

import org.example.tcpConnection.client.ServerSocketChannelClient;
import org.example.tcpConnection.server.ServerSocketChannelConnection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SimpleHttpParserTest {


    Process server;
    ServerSocketChannelClient client;

    @BeforeEach
    public void setup() throws IOException, InterruptedException {
//        server = ServerSocketChannelConnection.start();
        client = ServerSocketChannelClient.start();
    }

    @Test
    void testHelloEndpoint() throws Exception {
        URL url = new URL("http://localhost:8502/hello");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        int status = conn.getResponseCode();
        String response = readResponse(conn);

        assertEquals(200, status);
        assertEquals("Hello from custom HTTP server!", response.trim());
    }

    private String readResponse(HttpURLConnection conn) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = in.readLine()) != null) {
            content.append(line);
        }

        in.close();
        return content.toString();
    }
}
