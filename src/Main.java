/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int localPortnumber = 6789;

        ProxyServer server = new ProxyServer(localPortnumber);
        ProxyClient client = new ProxyClient();

        Thread tserveur = new Thread(server);
        Thread tclient = new Thread(client);

        // Launch the server
        tserveur.start();

        // Launch the client
        //tclient.start();



    }
}
