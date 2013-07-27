package edu.gatech.visualservo.server;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;

import static edu.gatech.visualservo.server.ImageFrame.ascii;
import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static javax.xml.bind.DatatypeConverter.printHexBinary;

public class MessageSender implements Runnable {

    private String host;
    private int port;
    private byte[] message;

    public MessageSender(String host, int port, byte[] message) {
        this.host = host;
        this.port = port;
        this.message = message;
        Socket skt = null;
    }

    @Override
    public void run() {
        try {
            Socket skt = new Socket(host, port);
            OutputStream out = skt.getOutputStream();
            out.write(message);
            System.out.println("Sent " + message.length + " bytes");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
