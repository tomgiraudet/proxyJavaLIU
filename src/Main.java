/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int localPortnumber = 6789;

        ProxyClient client = new ProxyClient();
        KidProtection protection = new KidProtection();
        ProxyServer server = new ProxyServer(localPortnumber, client, protection);

        Thread tserveur = new Thread(server);
        Thread tclient = new Thread(client);

        // Launch the client
        tclient.start();

        // Launch the server
        tserveur.start();

        /*// Sending stuff to the browser
        HTMLtoBrowser test = new HTMLtoBrowser();
        test.sentToBrowser(localPortnumber);
        System.out.println("Sent");*/
    }
}
