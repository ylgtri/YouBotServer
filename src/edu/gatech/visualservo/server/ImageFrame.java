package edu.gatech.visualservo.server;

import org.apache.commons.codec.binary.Base64;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Date;

import javax.imageio.ImageIO;

import static javax.xml.bind.DatatypeConverter.parseHexBinary;
import static javax.xml.bind.DatatypeConverter.printHexBinary;

public class ImageFrame {

    private int w;
    private int h;
    private BufferedImage c;
    private static byte[] bmp;
    private static String end = "ENDOFJPEGFILE";

    public ImageFrame(int w, int h, BufferedImage c) {
        this.w = w;
        this.h = h;
        this.c = c;
    }

    public static byte[] convert(BufferedImage c) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(c, "png", baos);
            baos.flush();
            byte[] img = baos.toByteArray();
            baos.close();
            byte[] ending = end.getBytes("US-ASCII");
            byte[] time = String.valueOf(System.currentTimeMillis()).getBytes("US-ASCII");
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
            outputStream.write(time);
            outputStream.write(img);
            outputStream.write(ending);
            return outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] intToByte(int i) {
        return ByteBuffer.allocate(4).putInt(i).array();
    }

    private static int wrap(int x, int y, int val) {
        return 3 * (((y - 1) * x) + x - 1) + val;
    }

    public int getW() {
        return w;
    }

    public int getH() {
        return h;
    }

    public byte[] getBmp() {
        return bmp;
    }

    public static String ascii(String hex) {
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < hex.length(); i+=2) {
            String str = hex.substring(i, i+2);
            output.append((char)Integer.parseInt(str, 16));
        }
        return output.toString();
    }

}
