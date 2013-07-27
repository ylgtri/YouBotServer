package edu.gatech.visualservo.server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import javax.swing.JFrame;
import javax.swing.JPanel;

import static edu.gatech.visualservo.server.ImageFrame.convert;

public class Server extends JFrame {

    private BufferedImage bf;
    private static int width = 960;
    private static int height = 600;
    private static int serverport = 9013;
    private static int port = 9102;
    public static volatile String user =  "192.168.1.102";
    public static volatile long currentFrame = 0;

    public Server() {
        DrawPane dp = new DrawPane();
        setContentPane(dp);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setVisible(true);
        for (; ; ) {
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.repaint();
            if (bf != null) {
            new Thread(new MessageSender(user, port, convert(bf))).start();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ContDraw(width, height)).start();
        setupNetwork();
        new Server();
    }

    class DrawPane extends JPanel {

        @Override
        public void paintComponent(Graphics g) {
            bf = (BufferedImage) createImage(getSize().width, getSize().height);
            Graphics2D gph=(Graphics2D) bf.getGraphics();
            gph.drawOval(ContDraw.getX(), ContDraw.getY(), 20, 20);
            g.drawImage(bf, 0, 0, this);
        }

    }

    public BufferedImage createImage(JPanel panel) {
        int w = panel.getWidth();
        int h = panel.getHeight();
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bi.createGraphics();
        panel.paint(g);
        return bi;
    }

    private static void setupNetwork() {
        ServerBootstrap bootstrap = new ServerBootstrap(
                new NioServerSocketChannelFactory(
                        Executors.newCachedThreadPool(),
                        Executors.newCachedThreadPool()));
        bootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new ServerHandler());
            }
        });
        bootstrap.bind(new InetSocketAddress(serverport));
    }

}