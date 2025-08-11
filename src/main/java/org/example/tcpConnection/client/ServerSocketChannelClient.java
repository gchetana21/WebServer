package org.example.tcpConnection.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelClient {

    private static SocketChannel client;
    private static ByteBuffer buffer;
    private static ServerSocketChannelClient clientInstance;

    public static ServerSocketChannelClient start(){
        if(clientInstance == null){
            //open connection
            clientInstance = new ServerSocketChannelClient();
        }
        return clientInstance;
    }

    public static void stop() throws IOException {
        client.close();
        buffer = null;
    }

    private  ServerSocketChannelClient(){
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 8502));
            buffer = ByteBuffer.allocate(1024);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public String sendMessage(String message){
        buffer = ByteBuffer.wrap(message.getBytes());
        String response = null;

        try {
            client.write(buffer);
//            client.close();
            client.read(buffer);
            response = new String(buffer.array()).trim();
            System.out.println("Response : " + response );
            buffer.clear();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}
