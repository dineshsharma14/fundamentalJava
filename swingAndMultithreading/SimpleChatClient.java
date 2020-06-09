// a chat client with ability to write and read the text to and from server!

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class SimpleChatClient {

    private JTextArea incoming;
    private JTextField outgoing;
    private BufferedReader reader;
    private PrintWriter writer;
    private Socket socket;

    public static void main(String[] args) {
        new SimpleChatClient().go();
    }

    public void go() {

        // building the window and initializing the chat app
        
        JFrame frame = new JFrame("Simple Chat Client");
        JPanel panel = new JPanel();
        incoming = new JTextArea(15, 50);
        incoming.setLineWrap(true);
        incoming.setWrapStyleWord(true);
        incoming.setEditable(false);
        JScrollPane scroller = new JScrollPane(incoming);
        scroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        outgoing = new JTextField(20);
        JButton sendButton = new JButton("send");

        // adding listener to the sendButton
        sendButton.addActionListener(new SendButtonListener());

        panel.add(scroller);
        panel.add(outgoing);
        panel.add(sendButton);
        frame.getContentPane().add(BorderLayout.CENTER, panel);

        setUpNetworking();

        // starting a new thread using a inner class as Runnable for thread.
        // job - read server's socket stream, displaying any incoming messages
        Thread readerThread = new Thread(new IncomingReader());
        readerThread.start();
        
        frame.setSize(400,500);
        frame.setVisible(true);
    }

    private void setUpNetworking() {
        // code to setup Networking
        try {
            socket = new Socket("127.0.0.1", 5000);
            // we'll use this socket to read and write on to the other one coming from server!

            // reader
            InputStreamReader streamReader = new InputStreamReader(socket.getInputStream());
            reader = new BufferedReader(streamReader);

            // writer
            writer = new PrintWriter(socket.getOutputStream());
            
            System.out.println("networking extablished");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public class SendButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent ev) {
            // code to act upon press of send button
            try {
                // writer is chained to the output stream from the Socket, so
                // whenever we do a println() it goes over the network to server
                writer.println(outgoing.getText());
                writer.flush();
            } catch (Exception ex) {
            ex.printStackTrace();
            }
        outgoing.setText("");
        outgoing.requestFocus();
        }
    }

    public class IncomingReader implements Runnable {
        // it stays in the loop as long as what it gets from server is not null
        public void run() {
            String message;
            try {
                while ((message = reader.readLine()) != null) {
                    System.out.println("read " + message);
                    incoming.append(message + "\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
                    
}

    
        
