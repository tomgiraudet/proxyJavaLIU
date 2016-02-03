/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;


public class ProxyServer implements Runnable {

    private int portNumber;
    private ProxyClient myClient;
    private KidProtection protection;
    public boolean shutDown = false;

    public ProxyServer(int _portNumber, ProxyClient _client, KidProtection _kidProtection){
        portNumber = _portNumber;
        myClient = _client;
        protection = _kidProtection;
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


                        if(str.length() == 0){
                            System.out.println("Request finished");
                            System.out.println(requestStacked);
                            System.out.println("Request send");
                            String res = myClient.writeRequest(requestStacked);


                            System.out.println("###################### Send to browser ######################");
                            System.out.println(res);
                            PrintWriter out = new PrintWriter(socket.getOutputStream());
                            out.println(res);
                            out.flush();
                            out.close();


                            System.out.println("Send to WB");



                        }
                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }


    }


}
