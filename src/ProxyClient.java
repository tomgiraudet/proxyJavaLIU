/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.net.*;
import java.io.*;
import java.nio.ByteBuffer;
import java.util.*;

public class ProxyClient{

    private int MAXSIZEBUFFER;

    public void setSizeBuffer(int _size){
        MAXSIZEBUFFER = _size;
    }

    public ByteBuffer writeRequest(String _request) {
        KidProtection check = new KidProtection();
        ByteBuffer resBuff = ByteBuffer.allocate(MAXSIZEBUFFER);
        char[] myRequest = _request.toCharArray();
        int portNumber = 80;
        String ip = "";

        boolean firstLineExtract = false;
        boolean secondLineExtract = false;
        int i = 0;
        String firstLine = "";
        String secondLine = "";
        String restOfRequest = "";

        while (!firstLineExtract) {
            if (myRequest[i] == '\n') {
                firstLineExtract = true;
                firstLine = _request.substring(0, i);
                restOfRequest = _request.substring(i + 1);
            }
            i++;
        }

        char[] myRestOfRequest = restOfRequest.toCharArray();
        i = 0;
        while (!secondLineExtract) {
            if (myRestOfRequest[i] == '\n') {
                secondLineExtract = true;
                secondLine = restOfRequest.substring(0, i);
                restOfRequest = restOfRequest.substring(i + 1);
            }
            i++;
        }

        String request = firstLine;
        String host = secondLine;


        //String myURL = parsing myRequest
        String url = host.substring(6);
        System.out.println("[Client] url : " + url);
        try {
            InetAddress address = InetAddress.getByName(new URL("http://" + url + "/").getHost());
            ip = address.getHostAddress();
        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        System.out.println("[Client] ip: " + ip);


        // Sending URL
        try {
            System.out.println("[Client] Transfer : Proxy (Client side) -> Web server");
            Socket mySocket = new Socket(ip, portNumber);
            PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true);


            out.println(_request);
            out.println("");
            System.out.println("[Client] Request send ");


            // Looking for the response
            BufferedReader inS = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

            int charsRead = 0;
            String messageStacked = "";
            boolean isEncoded = false;


            System.out.println("[Client] Waiting for answer ... ");
            System.out.println("[Client] Answer arrived");
            while (inS.ready() || resBuff.position() < 40) {
                resBuff.put((byte) inS.read());
            }
            messageStacked = resBuff.toString();
            System.out.println(messageStacked);
            System.out.println("[Client] Transfer : Proxy (Client side) -> Proxy (Server side)");


            // Flips this buffer.  The limit is set to the current position and then
            // the position is set to zero.  If the mark is defined then it is discarded
            resBuff.flip();
            return resBuff;

        } catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }


        resBuff.flip();
        return resBuff;
    }


    public boolean checkURL(String _request){

        System.out.println("[Client] Request received");
        System.out.println("[Client] Checking URL and HOST");

        KidProtection check = new KidProtection();
        char[] myRequest = _request.toCharArray();

        boolean firstLineExtract = false;
        boolean secondLineExtract = false;
        int i = 0;
        String firstLine = "";
        String secondLine = "";
        String restOfRequest = "";

        while (!firstLineExtract) {
            if (myRequest[i] == '\n') {
                firstLineExtract = true;
                firstLine = _request.substring(0, i);
                restOfRequest = _request.substring(i + 1);
            }
            i++;
        }

        char[] myRestOfRequest = restOfRequest.toCharArray();
        i = 0;
        while (!secondLineExtract) {
            if (myRestOfRequest[i] == '\n') {
                secondLineExtract = true;
                secondLine = restOfRequest.substring(0, i);
                restOfRequest = restOfRequest.substring(i + 1);
            }
            i++;
        }


        String request = firstLine;
        String host = secondLine;
        System.out.println("[Client] Parsing request done");
        System.out.println("[Client] " + request);
        System.out.println("[Client] " + host);
        System.out.println("[Client] Asking the Kid protection for checking host and request ...");
        if (check.analyze(request) || check.analyze(host)) {
            return true;
        }
         return false;
    }


}