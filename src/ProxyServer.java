/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;


public class ProxyServer implements Runnable {


    private int portNumber;
    private ProxyClient myClient;
    public boolean shutDown = false;

    public ProxyServer(int _portNumber, ProxyClient _client){
        portNumber = _portNumber;
        myClient = _client;
    }

    public void run() {

        try {
            ServerSocket serverSocket = new ServerSocket(portNumber);
            Socket socket;

            System.out.println("Socket created");

            while (!shutDown) {
                System.out.println("Ready to listen");
                socket = serverSocket.accept();

                System.out.println("Request detected");
                OutputStream os = socket.getOutputStream();


                BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                String str = "";


                while (!shutDown) {
                    String requestStacked = "";
                    boolean finish = false;

                    while (!finish) {
                        //stacking the request
                        str = br.readLine();
                        requestStacked = requestStacked + str + '\n';
                        System.out.println(requestStacked);


                        if(str.length() == 0){
                            System.out.println("Request finished");
                            System.out.println("Request stacked");
                            myClient.writeRequest(requestStacked);

                            // Finishing
                            requestStacked = "";
                            finish = true;
                            System.out.println("Request dealded");
                        }
                    }
                }
            }
            //socket.close();

        }catch(Exception e) {
            e.printStackTrace();
        }


    }


}
