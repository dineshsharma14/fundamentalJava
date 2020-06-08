/*
 * This program makes a Socket, makes a BufferedReader (with help of other
 * streams), and reads a single line from the server application (whatever is
 * running on port 4242)
 */

import java.io.*;
import java.net.*;

public class DailyAdviceClient {

    public static void main(String[] args) {
        DailyAdviceClient client = new DailyAdviceClient();
        client.go();
    }

    public void go() {
        try {
            Socket socket = new Socket("127.0.0.1", 4242);
            // ip address of localhost, and 4242 is the port because server
            // told us 

            // bridge between low level byte stream - coming from socket and
            // high level character stream (BufferedReader)
            InputStreamReader streamReader =
                new InputStreamReader(socket.getInputStream());
            BufferedReader reader = new BufferedReader(streamReader);

            String advice = reader.readLine();
            System.out.println("Hey! " + advice);

            reader.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
            
