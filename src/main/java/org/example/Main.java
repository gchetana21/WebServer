package org.example;

import org.example.tcpConnection.server.ServerSocketChannelConnection;
import org.example.tcpConnection.server.SocketServerMultiThreaded;
import org.example.tcpConnection.server.SocketServerSingle;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //for single client
        SocketServerSingle webServer = new SocketServerSingle();
//        webServer.start(8500);
        //for multiple clients
        SocketServerMultiThreaded multiServer = new SocketServerMultiThreaded();

        ServerSocketChannelConnection channelServer = new ServerSocketChannelConnection();
        channelServer.startConnection(8502);
//        multiServer.start(8600);
    }
}
