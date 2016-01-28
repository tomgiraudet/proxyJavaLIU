/**
 * Created by tomgiraudet on 27/01/16.
 */

import java.io.*;

public class Main {

    public static void main(String[] args) throws IOException {
        ProxyServer server = new ProxyServer();
        ProxyClient client = new ProxyClient();
        server.init();
        client.init();
        System.out.println("done");
    }
}
