package Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public static void main(String[] args) {
        
        ExecutorService executor = Executors.newCachedThreadPool();
        try {
            ServerSocket server =  new ServerSocket(8080);
            while (true) {
                Socket connectedSocket = server.accept();
                ClientHandler ch = new ClientHandler(connectedSocket);
                clientHandlers.add(ch);
                executor.execute(ch);
            }
        } catch (IOException e) {
            System.out.println("connection failed");
        }
    }
    public static ArrayList<ClientHandler> getClientHandlers(){
        return clientHandlers;
    }

}