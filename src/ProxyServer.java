/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;


public class ProxyServer implements Runnable {

    private int portNumber;
    private int MAXSIZEBUFFER;
    private ProxyClient myClient;
    private KidProtection protection;
    public boolean shutDown = false;

    public ProxyServer(int _portNumber, ProxyClient _client, KidProtection _kidProtection, int _maxsizebuffer){
        portNumber = _portNumber;
        myClient = _client;
        protection = _kidProtection;

        MAXSIZEBUFFER = _maxsizebuffer;
        myClient.setSizeBuffer(MAXSIZEBUFFER);
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

            ServerSocket serverSocket = new ServerSocket(portNumber);
            System.out.println("[Server] Server Launched");

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
                    //Stacking the request
                    str = br.readLine();
                    requestStacked = requestStacked + str + '\n';

                    if(str.length() == 0){
                        System.out.println("[Server] Request read");
                        System.out.println("[Server] Transfer : Proxy (Server side) -> Proxy (Client side)");

                        ByteBuffer resBuff = ByteBuffer.allocate(MAXSIZEBUFFER);

                        // checking of the URL
                        if (myClient.checkURL(requestStacked)) {
                            // URL UNSAFE
                            String resUnsafe = "HTTP/1.1 302 Found\\r\\nLocation: http://www.ida.liu.se/~TDTS04/labs/2011/ass2/error1.html\\r\\nConnection: Close\\r\\n\\r\\n";
                            out = new PrintWriter(socket.getOutputStream());
                            out.write(resUnsafe);
                            out.flush();
                            out.close(); // Request is over, let inform the browser that he can display.

                        }else{
                            // URL SAFE, asking for the response
                            resBuff = myClient.writeRequest(requestStacked);
                            resBuff.flip();

                            // Checking content
                            String resBuffString;
                            resBuffString = resBuff.toString();

                            if(protection.analyze(resBuffString)){
                                // UNSAFE CONTENT
                                String resUnsafe = "HTTP/1.1 302 Found\\r\\nLocation: http://www.ida.liu.se/~TDTS04/labs/2011/ass2/error2.html\\r\\nConnection: Close\\r\\n\\r\\n";
                                out = new PrintWriter(socket.getOutputStream());
                                out.write(resUnsafe);
                                out.flush();
                                out.close(); // Request is over, let inform the browser that he can display.
                            }else{
                                // SAFE CONTENT
                                byte[] arrayRes = resBuff.array();
                                DataOutputStream dos = new DataOutputStream(os);
                                dos.write(arrayRes);

                                System.out.println("[Server] Transfer : Proxy (Server side) -> Web Browser");
                            }


                        }



                        System.out.println("[Server] Response received and processed");
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
