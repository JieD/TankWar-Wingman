/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wingman;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.net.URL;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author Ilmi
 */
public class gm1942 extends JApplet implements Runnable {

    private Thread thread; 
   
    private int x = 0, move = 0;
    private BufferedImage bimg;
    int speed = 1, score = 0, life = 4;
    Random generator = new Random(1234567);
    Island I1, I2, I3;
    MyPlane m;
    Enemy e1;
    GameEvents gameEvents;

    boolean gameOver;
    ImageObserver observer;

    public void init() {
        setBackground(Color.white);
        Image myPlane, island1, island2, island3, enemy;
        
        island1 = getSprite("Resources/island1.png");
        island2 = getSprite("Resources/island2.png");
        island3 = getSprite("Resources/island3.png");
        myPlane = getSprite("Resources/myplane_1.png");
 
        gameOver = false;
        observer = this;

        I1 = new Island(island1, 100, 100, speed, generator);
        I2 = new Island(island2, 200, 400, speed, generator);
        I3 = new Island(island3, 300, 200, speed, generator);
        e1 = new Enemy(speed, generator);
        
        KeyControl key = new KeyControl();
        addKeyListener(key);
        gameEvents = new GameEvents();
        m = new MyPlane(myPlane, 300, 360, 10); 
        gameEvents.addObserver(m);
        
    }

    public class Island {

        Image img;
        int x, y, speed;
        Random gen;

        Island(Image img, int x, int y, int speed, Random gen) {
            this.img = img;
            this.x = x;
            this.y = y;
            this.speed = speed;
            this.gen = gen;
        }

        public void update(int w, int h) {
            y += speed;
            if (y >= h) {
                y = -100;
                x = Math.abs(gen.nextInt() % (w - 30));
            }
        }

        public void draw(Graphics g, ImageObserver obs) {
            g.drawImage(img, x, y, obs);
        }
    }
   public class GameEvents extends Observable {
       int type;
       Object event;
       
   public void setValue(KeyEvent e) {
          type = 1; // let's assume this mean key input. Should use CONSTANT value for this
          event = e;
          setChanged();
         // trigger notification
         notifyObservers(this);  
   }

       public void setValue(String msg) {
          type = 2; // let's assume this mean key input. Should use CONSTANT value for this
          event = msg;
          setChanged();
         // trigger notification
         notifyObservers(this);  
        }
    }

    public class KeyControl extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            gameEvents.setValue(e);
        }
    }

    public class MyPlane implements Observer {
        Image img;
        int x, y, speed;
        int boom;

        MyPlane(Image img, int x, int y, int speed) {
            this.img = img;
            this.x = x;
            this.y = y;
            this.speed = speed;
            boom = 0;
        }

        public void draw(Graphics g, ImageObserver obs) {
            if(boom == 0)
                g.drawImage(img, x, y, obs);
        }
        
        public boolean collision(int x, int y, int w, int h) {
            if(y+h > this.y) { // very simple test for showing an idea -- this only checks y forwarding direction
                return true;
            }
            return false;
        }
      
        public void update(Observable obj, Object arg) {
            GameEvents ge = (GameEvents) arg;
            if(ge.type == 1) {
                KeyEvent e = (KeyEvent) ge.event;
                switch (e.getKeyCode()) {    
                    case KeyEvent.VK_LEFT:
                        System.out.println("Left");
                        x -= speed;
	        	break; 
                    case KeyEvent.VK_RIGHT:
                        System.out.println("Right");
                        x += speed;
	        	break;
                    default:
                  if(e.getKeyChar() == ' ') {
                        System.out.println("Fire");  
                  }
                }
            }
            else if(ge.type == 2) {
                String msg = (String)ge.event;
                if(msg.equals("Explosion"))
                    boom = 1;
            }
        }
    }

    public class Enemy {

        Image img;
        int x, y, sizeX, sizeY, speed;
        Random gen;
        boolean show;
   
        Enemy(int speed, Random gen) {
            this.img = getSprite("Resources/enemy1_1.png");
            this.x = Math.abs(gen.nextInt() % (600 - 30));
            this.y = -100;
            this.speed = speed;
            this.gen = gen;
            this.show = true;
            sizeX = img.getWidth(null);
            sizeY = img.getHeight(null);
            System.out.println("w:" + sizeX + " y:" + sizeY);
       }

        public void update(int w, int h) {
            y += speed;
            if(m.collision(x, y, sizeX, sizeY)) {
                show = false;
                gameEvents.setValue("Explosion");
            }
        }

        public void draw(Graphics g, ImageObserver obs) {
            if (show) {
                g.drawImage(img, x, y, obs);
            }
        }
    }
    
    public Image getSprite(String name) {
        URL url = gm1942.class.getResource(name);
        Image img = getToolkit().getImage(url);
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        } catch (Exception e) {
        }
        return img;
    }

    // generates a new color with the specified hue
    public void drawBackGroundWithTileImage(int w, int h, Graphics2D g2) {
        Image sea;
        sea = getSprite("Resources/water.png");
        int TileWidth = sea.getWidth(this);
        int TileHeight = sea.getHeight(this);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);

        Image Buffer = createImage(NumberX * TileWidth, NumberY * TileHeight);
        //Graphics BufferG = Buffer.getGraphics();


        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(sea, j * TileWidth, i * TileHeight + (move % TileHeight), TileWidth, TileHeight, this);
            }
        }
        move += speed;
    }

    public void drawDemo(int w, int h, Graphics2D g2) {

        if (!gameOver) {
            drawBackGroundWithTileImage(w, h, g2);
            I1.update(w, h);
            I1.draw(g2, this);

            I2.update(w, h);
            I2.draw(g2, this);

            I3.update(w, h);
            I3.draw(g2, this);

            m.draw(g2, this);
            
            e1.update(w, h);
            e1.draw(g2, this);
        }
  
    }

    public Graphics2D createGraphics2D(int w, int h) {
        Graphics2D g2 = null;
        if (bimg == null || bimg.getWidth() != w || bimg.getHeight() != h) {
            bimg = (BufferedImage) createImage(w, h);
        }
        g2 = bimg.createGraphics();
        g2.setBackground(getBackground());
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }

    public void paint(Graphics g) {
        Dimension d = getSize();
        Graphics2D g2 = createGraphics2D(d.width, d.height);
        drawDemo(d.width, d.height, g2);
        g2.dispose();
        g.drawImage(bimg, 0, 0, this);
    }

    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    public void run() {
    	
        Thread me = Thread.currentThread();
        while (thread == me) {
            repaint();
          
          try {
                thread.sleep(25);
            } catch (InterruptedException e) {
                break;
            }
            
        }
    	    	
       // thread = null;
    }

    private void playSound(String filename, int back) {
      
    }

    public static void main(String argv[]) {
        final gm1942 demo = new gm1942();
        demo.init();
        JFrame f = new JFrame("Scrolling Shooter");
        f.addWindowListener(new WindowAdapter() {});
        f.getContentPane().add("Center", demo);
        f.pack();
        f.setSize(new Dimension(640, 480));
        f.setVisible(true);
        f.setResizable(false);
        demo.start();
    }

    /**
     * This method is called from within the init() method to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.BorderLayout());
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
