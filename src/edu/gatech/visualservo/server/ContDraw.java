package edu.gatech.visualservo.server;

public class ContDraw implements Runnable {

    private int w;
    private int h;
    private static int x;
    private static int y;

    public ContDraw(int x, int y) {
        this.w = x;
        this.h = y;
    }

    @Override
    public void run() {
        int xd = 1;
        int yd = 1;
        x = 0;
        y = 0;
        for (; ; ) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (x == (w - 20) || x < 0) {
                xd = -1 * xd;
            }
            if (y == (h - 20) || y < 0) {
                yd = -1 * yd;
            }
            if (xd == 1) {
                x++;
            } else {
                x--;
            }
            if (yd == 1) {
                y++;
            } else {
                y--;
            }
            // System.out.println(x + ", " + y);
        }
    }

    public static int getX() {
        return x;
    }

    public static int getY() {
        return y;
    }

}
