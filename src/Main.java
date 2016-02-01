/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        int localPortnumber = 6789;

        ProxyClient client = new ProxyClient();
        ProxyServer server = new ProxyServer(localPortnumber, client);


        Thread tserveur = new Thread(server);
        Thread tclient = new Thread(client);

        // Launch the client
        tclient.start();

        // Launch the server
        tserveur.start();





    }
}
