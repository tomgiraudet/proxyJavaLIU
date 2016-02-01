/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.net.*;
import java.io.*;

public class ProxyClient implements Runnable{

    public void run() {
    }


    public void writeRequest(String _request){
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
    }




}