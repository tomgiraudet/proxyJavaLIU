/**
 * Created by tomgiraudet on 03/02/16.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
public class HTMLtoBrowser {

    public void sentToBrowser(int _port){
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(_port);
        } catch (IOException e)
        {
            System.err.println("Could not listen on port: "+_port);
            System.exit(1);
        }

        Socket clientSocket = null;
        try {
            clientSocket = serverSocket.accept();

            if(clientSocket != null)
                System.out.println("Connected");

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream());
            out.println("HTTP/1.1 200 OK");
            out.println("Content-Type: text/html");
            out.println("\r\n");
            out.println("<p> Hello world </p>");
            out.flush();

            out.close();

            clientSocket.close();
            serverSocket.close();

        } catch (IOException e) {
            System.err.println("Accept failed.");
            System.exit(1);
        }
    }
}
