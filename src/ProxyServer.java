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

            // Declaration of variables

            Socket socket;
            OutputStream os;
            PrintWriter out;
            BufferedReader br;
            String requestStacked;
            String str = "";
            String res;

            System.out.println("[Server] Creation of Socket : Web Browser -> Proxy (Server part)");
            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("[Server] Succeed");

            while (!shutDown) {
                System.out.println("[Server] Ready to listen");
                socket = serverSocket.accept();

                System.out.println("[Server] Request detected");
                os = socket.getOutputStream();
                br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

                // Make everything clean for a new request
                str = "";
                res = "";
                requestStacked = "";

                boolean finish = false;

                while (!finish) {
                    //stacking the request
                    str = br.readLine();
                    requestStacked = requestStacked + str + '\n';


                    if(str.length() == 0){
                        System.out.println("[Server] Request read");
                        System.out.println("[Server] Transfer : Proxy (Server side) -> Proxy (Client side)");
                        res = myClient.writeRequest(requestStacked);
                        System.out.println("[Server] Response received");

                        System.out.println("[Server] Asking KidProtection for bad words ...");
                        if(!protection.analyze(res)){
                            res = "HTTP/1.1 200 OK\nContent-Type: text/html\n\n\r<p> This page is unsafe, sorry !</p>";
                            System.out.println("[Server] Content changed");
                            // Do not display
                        }
                        out = new PrintWriter(socket.getOutputStream());
                        out.println(res);
                        out.flush();


                        out.close(); // Request is over, let inform the browser that he can display.

                        System.out.println("[Server] Transfer : Proxy (Server side) -> Web Browser");
                        finish = true;
                        System.out.println("[Server] Request finished");
                        System.out.println("################### NEXT REQUEST ###################");




                    }
                }
            }
        }catch(Exception e) {
            e.printStackTrace();
        }


    }


}
