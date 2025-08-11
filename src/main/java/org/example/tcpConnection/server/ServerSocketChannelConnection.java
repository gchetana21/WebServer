package org.example.tcpConnection.server;

import org.example.core.handler.HttpParser;
import org.example.core.handler.HttpRouter;
import org.example.core.handler.RouteScanner;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ServerSocketChannelConnection {

    public void startConnection(int port){
        try {

            //create selector and channel
            Selector selector = Selector.open();
            ServerSocketChannel serverSocket = ServerSocketChannel.open();
            serverSocket.bind(new InetSocketAddress("localhost", port));
            serverSocket.configureBlocking(false);
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            ByteBuffer buffer = ByteBuffer.allocate(10000);
            System.out.println("Server listening on port : " + port);
            while(true){
                //blocking until channel is ready
                selector.select();
                Set<SelectionKey> selectedKeys = selector.selectedKeys();
                Iterator<SelectionKey> iter = selectedKeys.iterator();
                while(iter.hasNext()){
                    SelectionKey key = iter.next();

                    if(key.isAcceptable()){
                        /*
                        ServerSocketChannel can only handle accept operations.When accepted connection from
                        client we can obtain socket server channel to perform read or write
                         */
                        SocketChannel socketChannel = serverSocket.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    if(key.isReadable()){
                        //write to client
                        responseToClient(buffer, key);
                    }
                    iter.remove();
                }
            }


        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void responseToClient(ByteBuffer buffer, SelectionKey key) throws ClassNotFoundException {
        try {
            SocketChannel client = (SocketChannel) key.channel();
            int r = client.read(buffer);

            //check if there is nothing to read or client sends to stop
            if (r == -1 || new String(buffer.array()).trim().equals("POISON_PILL")){
                client.close();
                System.out.println("Not accepting client messages anymore");
                return;
            }
            //write the buffer
            buffer.flip();
            String request = new String(buffer.array(), 0, buffer.limit());

            HttpRouter router = new HttpRouter();
            HttpParser httpParser = new HttpParser(router);

            String mainClassName = Thread.currentThread().getStackTrace()[Thread.currentThread().getStackTrace().length - 1].getClassName();
            Package mainPackage = Class.forName(mainClassName).getPackage();
            String basePackage = mainPackage.getName();

            RouteScanner.scanAndRegister(basePackage, router);
            client.write(ByteBuffer.wrap(httpParser.httpRequestParser(request)));
            buffer.clear();
            client.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static Process start() throws IOException, InterruptedException {
//        String javaHome = System.getProperty("java.home");
//        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
//        String classpath = System.getProperty("java.class.path");
//        String className = ServerSocketChannelConnection.class.getCanonicalName();
//
//        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);
//
//        return builder.start();
//    }
}
