/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class ProxyServer {

    public void init() throws IOException {
        final int portNumber = 5678;
        System.out.println("Creating server socket on port " + portNumber);
        ServerSocket serverSocket = new ServerSocket(portNumber);

        while (true) {
            Socket socket = serverSocket.accept();
            OutputStream os = socket.getOutputStream();


            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String str = br.readLine();

            socket.close();


            System.out.println(str);
        }
    }

}

