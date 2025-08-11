package org.example.tcpConnection.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocketServerSingle {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void start(int port){
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("Server listening on port : " + port);
            while(true){
                clientSocket = serverSocket.accept();
                System.out.println("Client connected : " + clientSocket.getInetAddress());
                out = new PrintWriter(clientSocket.getOutputStream(),true);
                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                String input ;
                while ((input = in.readLine()) != null){
                    if("hello server".equals(input)){
                        out.println("hello client");
                    } else if(".".equals(input)){
                        out.println("closing connection");
                        break;
                    } else {
                        out.println(input);
                    }
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
        serverSocket.close();
    }
}
