/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.io.*;
import java.net.ServerSocket;
import java.nio.charset.*;
import java.net.*;
import java.util.Date;

public class ProxyClient {

    public void init() throws IOException {
        final int portNumber = 5678;

        final String servername = "www.google.fr";
        //InetAddress address = InetAddress.getByName(servername);
        InetAddress address = InetAddress.getLocalHost();
        System.out.println(address);

        final String request = "GET http://www.google.fr/ HTTP/1.1";
        final byte[] requestBytes = request.getBytes(StandardCharsets.UTF_8);

        System.out.println("bytes done");

        Socket connection = new Socket(address, portNumber);

        BufferedOutputStream bos = new BufferedOutputStream(connection.getOutputStream());
        BufferedWriter bufOut = new BufferedWriter( new OutputStreamWriter(bos));

        System.out.println("Creating server socket on port " + portNumber);



        bufOut.write(request, 0, request.length());
        bufOut.newLine();

        System.out.println("Finish");
    }

}
