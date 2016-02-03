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
        System.out.println("Asking for sending request");
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

        System.out.println("Request : "+request);
        System.out.println("Host : "+host);

        //String myURL = parsing myRequest
        String url = host.substring(6);
        System.out.println("url: "+url);
        try{
            InetAddress address = InetAddress.getByName(new URL("http://"+url+"/").getHost());
            ip = address.getHostAddress();
        }catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }
        System.out.println("ip: "+ip);



        // Sending URL


        try {
            Socket mySocket = new Socket(ip, portNumber);
            PrintWriter out = new PrintWriter(mySocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
            out.println(_request);
            out.println("");
            System.out.println("Request send");
            System.out.println("###################### RESPONSE ######################");

            // Looking for the response
            boolean finish = false;
            String res = "";
            String str = "";
            String finalMessage = "";



                char[] buffer = new char[2048];
                int charsRead = 0;
                while ((charsRead = in.read(buffer)) != -1)
                {
                    String message = new String(buffer).substring(0, charsRead);
                    System.out.println(message);
                    return message;

                }
            if(str == null || str.contains("<\\html>") || str == "0"){
                finish = true;
                System.out.println(res);
                return res;
            }

        }
        catch (IOException e) {
            System.err.println("Caught IOException: " + e.getMessage());
        }



        return "ERROR 404";
    }




}