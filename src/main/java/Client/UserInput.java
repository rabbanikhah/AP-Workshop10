package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class UserInput extends Thread{
    private Socket socket;
    private PrintWriter writer;

    public UserInput(Socket socket, PrintWriter writer) {
        this.socket = socket;
        this.writer = writer;
    }

    @Override
    public void run (){
        try {
            BufferedReader userReader = new BufferedReader(new InputStreamReader(System.in));
            while (true){
                String message = userReader.readLine();
                if(message.equals("#exit")){
                    writer.println(message);
                    break;
                }
                writer.println(message);
            }
        }
        catch (IOException io){
            io.printStackTrace();
        }
    }
}
