/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class ProxyClient implements Runnable{

    public void run() {
        // Make the shit always linstening
    }


    public String writeRequest(String _request){

        KidProtection check = new KidProtection();

        System.out.println("[Client] Request received");
        char[] myRequest = _request.toCharArray();
        int portNumber = 80;
        String ip = "";

        boolean firstLineExtract = false;
        boolean secondLineExtract = false;
        int i = 0;
        String firstLine = "";
        String secondLine = "";
        String restOfRequest = "";

        while(!firstLineExtract){
            if(myRequest[i] == '\n') {
                firstLineExtract = true;
                firstLine = _request.substring(0, i);
                restOfRequest = _request.substring(i+1);
            }
            i++;
        }

        char[] myRestOfRequest = restOfRequest.toCharArray();
        i = 0;
        while(!secondLineExtract){
            if(myRestOfRequest[i] == '\n') {
                secondLineExtract = true;
                secondLine = restOfRequest.substring(0, i);
                restOfRequest = restOfRequest.substring(i+1);
            }
            i++;
        }


        String request = firstLine;
        String host = secondLine;
        System.out.println("[Client] Parsing request done");
        System.out.println("[Client] "+request);
        System.out.println("[Client] "+host);
        System.out.println("[Client] Asking the Kid protection for checking host and request ...");
        if(check.analyze(request) || check.analyze(host)){
            return "HTTP/1.1 200 OK\nContent-Type: text/html\n\n\r<p> This page is unsafe, sorry !</p>";
        }


        //String myURL = parsing myRequest
        String url = host.substring(6);
        System.out.println("[Client] url : "+url);
        try{
            InetAddress address = InetAddress.getByName(new URL("http://"+url+"/").getHost());
            ip = address.getHostAddress();
        }catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        System.out.println("[Client] ip: "+ip);



        // Sending URL


        try {
            System.out.println("[Client] Transfer : Proxy (Client side) -> Web server");
            Socket mySocket = new Socket(ip, portNumber);
            PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true);


            out.println(_request);
            out.println("");
            System.out.println("[Client] Request send ");


            // Looking for the response
            boolean finish = false;
            String res = "";
            String str = "";
            String finalMessage = "";


            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            char[] buffer = new char[2048];
            int charsRead = 0;
            String message = "";
            System.out.println("[Client] Waiting for answer ... ");
            while ((charsRead = in.read(buffer)) > 0) {
                System.out.println("[Client] Answer arrived");
                message = new String(buffer).substring(0, charsRead);
                System.out.println("[Client] Transfer : Proxy (Client side) -> Proxy (Server side)");
                if(check.analyze(message)){
                    return "HTTP/1.1 200 OK\nContent-Type: text/html\n\n\r<p> This page is unsafe, sorry !</p>";
                }else{
                    return message;
                }
            }



        }
        catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }

        return "error";
    }




}