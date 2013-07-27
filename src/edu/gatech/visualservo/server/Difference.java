package edu.gatech.visualservo.server;

public class Difference {

    public byte[] difference(byte[] a, byte[] b) {
        if (a.length == b.length) {
            byte[] diff = new byte[a.length];
            for (int i = 0; i < a.length; i++) {
                if (a[i] != b[i]) {
                    diff[i] = b[i];
                } else {
                    diff[i] = (byte) 0;
                }
            }
            return diff;
        }
        return null;
    }

}
