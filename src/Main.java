/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {

        System.out.println("[Main] Initialization ...");

        // Configuration
        int localPortnumber = 6789;
        int maxsizebuffer = 1048576;

        ProxyClient client = new ProxyClient();
        KidProtection protection = new KidProtection();
        System.out.println("[Main] Initialization done");

        // Launching server
        System.out.println("[Main] Server ready to be launched ...");
        ProxyServer server = new ProxyServer(localPortnumber, client, protection, maxsizebuffer);
        server.run();
    }
}
