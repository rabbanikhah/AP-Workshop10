package Server;

import Common.User;
import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable{
    private Socket socket;
    private BufferedReader reader = null;
    private PrintWriter writer = null;
    private User user = null;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        }
        catch (IOException io){
            System.out.println("connection failed");
        }
    }

    @Override
    public void run(){
        try {
            writer.println("please enter your name: ");
            writer.flush();
            String name = reader.readLine();
            User u = new User(name);
            this.user = u;
            inform(name + " joined the group");
            String message = reader.readLine();
            while (true){
                if (message.startsWith("#exit")){
                    inform(user.getName() + " left the chat");
                    leaveChat();
                    break;
                }
                else{
                    inform (user.getName() + " : " + message);
                }
                message = reader.readLine();
            }
        }
        catch (IOException io){
            io.printStackTrace();
        }
    }
    public void leaveChat () throws IOException {
        reader.close();
        writer.close();
        if (!socket.isClosed()) {
            socket.close();
        }
    }
    public void inform (String message){
        for (ClientHandler c : Server.getClientHandlers()){
            if ( (c.user == null)|| (!c.user.equals(this.user))){
                c.writer.println(message);
            }
        }
    }
    public void sendMessage(String message){
        writer.println(message);
    }
}
