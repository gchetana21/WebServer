package org.example.tcpConnection.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerMultiThreaded {
    private ServerSocket serverSocket;

    public void start(int port){
        try {
            /*
            // creation of severSocket instance and bind it to port
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(port));
             */
            serverSocket =new ServerSocket(port);
            System.out.println("Server listening on port : " + port);
            while(true){
                //waiting for client Connection
                Socket clientSocket = serverSocket.accept();

                //handle multiple clients simultaneously
                Thread connectionThread = new Thread(() -> handleClient(clientSocket));

                //start the connection thread
                connectionThread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void handleClient(Socket clientSocket) {
        System.out.println("Client connected : " + clientSocket.getInetAddress());
        try (
                //wrap output
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                //read input
                BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))
        ) {
            String input ;
            while ((input = in.readLine()) != null) {
                if("hello server".equals(input)){
                    out.println("hello client");
                } else if(".".equals(input)){
                    out.println("closing connection");
                    break;
                } else {
                    out.println(input);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}
