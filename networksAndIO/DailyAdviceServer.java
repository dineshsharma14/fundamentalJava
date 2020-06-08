/* this program makes a ServerSocket and waits for client requests.
 * when it gets a client request (ie client said new Socket() for this app ),
 * the server makes a new Socket connection for that client. The server makes
 * a PrintWriter (using Socket's output stream) and sends message to client
 */

import java.io.*;
import java.net.*;

public class DailyAdviceServer {

    String[] adviceList = {"Take smaller bites", "No judgement day today",
        "You might want to rethink that haircut", "Go for that skinny jeans",
        "You gotta be honest today!!", "beware of wolf in sheep's clothing",
        "What can't be measured can't be managed!", "DOSDJEMSLP, DNB"};

    public static void main(String[] args) {
        DailyAdviceServer server = new DailyAdviceServer();
        server.go();
    }

    public void go() {

        try {
            // ServerSocket makes this server 'listen' for client requests on
            // port 4242 on the machine this code is running 
            ServerSocket serverSocket = new ServerSocket(4242);

            // the server goes in permanent loop waiting for and serving
            // requests
            while(true) {

                // accept method just sits there until a request comes in and
                // then it returns a Socket object (on an anonymous port) to
                // communicate with client
                Socket socket = serverSocket.accept();

                // now you have good old Socket in here as well so just usual
                // story this time sending data to Socket!
                // PrintWriter act as its own bridge! 
                PrintWriter writer = new PrintWriter(socket.getOutputStream());
                String advice = getAdvice();
                writer.println(advice);
                writer.close();
            }
         } catch (IOException ex) {
             ex.printStackTrace();
             }
    }

    private String getAdvice() {
        int random = (int) (Math.random() * adviceList.length);
        return adviceList[random];
    }
}
        
                
                

                










            
 
