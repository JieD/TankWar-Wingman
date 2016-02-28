/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wingman;

import BasicObject.BasicObject;
import BasicObject.Island;
import CollidableObject.CollidableObject;
import CollidableObject.Munitions;
import GameEvents.CollisionDetector;
import GameEvents.GameEvents;
import Helper.Explosion;
import Helper.Property;
import Helper.ResourceTable;
import Weapon.DumpWeapon;
import Weapon.SmartWeapon;
import Weapon.Weapon;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.net.URL;
import java.util.ArrayList;
import java.util.ListIterator;
import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import javax.swing.JApplet;
import javax.swing.JFrame;
import javax.swing.JPanel;


/**
 *
 * @author ASUS
 */
public class Wingman extends JApplet implements Runnable, Observer {
    private Thread thread; 
    private BufferedImage bimg;
    ImageObserver observer;
    public static boolean gameOver;
    private int x = 0, move = 0;
    Random generator = new Random(1234567);
    private AudioClip backgroundSound = getSound("Resources/background.wav");
    
    private ResourceTable resources;
    private CollisionDetector collisionDetector;
    private GameEvents gameEvents = new GameEvents();
    private ArrayList<BasicObject> permanentList = new ArrayList();
    
    private int frameNumber = 0;
    public static final int SPEED = 1;
    public static final int FRAME_PER_SECOND = 30;
    public static final int PROCESSING_TIME = 8;
    public static final int DELAY = 1000 / FRAME_PER_SECOND - PROCESSING_TIME;
    public static final int CUSHION = 50;
    public static final double SCALE = 0.833;
    
    private JPanel informationPanel;
    private SmartWeapon myPlane1;
    private SmartWeapon myPlane2;
    
    @Override
    public void init() {
        setBackground(Color.white);
        gameOver = false;
        observer = this;
        collisionDetector = new CollisionDetector();
        resources = new ResourceTable();
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gameEvents.setValue(e);
            }
        });
        gameEvents.addObserver(this);
        backgroundSound.loop();
        createIsland(100, 100, "Resources/island1.png");
        createIsland(200, 400, "Resources/island2.png");
        createIsland(300, 200, "Resources/island3.png");
        //createMyPlane(100, 400, 450, 400);
        myPlane1 = createSmartWeapon(400, 350, 0, "MyPlane1");
        myPlane2 = createSmartWeapon(100, 350, 0, "MyPlane2");
        informationPanel = new InformationPanel(myPlane1, myPlane2);
    }
    
    /** private Image getSprite(String spriteName) {
        String spriteName = resources.getSpriteName(className);
        return loadSprite(spriteName);
    }
    * 
     * Explosion can be 3 forms:
     * 1. no sound nor animation
     * 2. with only sound
     * 3. with sound and animation
     */
    /** private Explosion getExplosion(String className) {
        Explosion explosion;
        String explosionSoundName = resources.getExplosionSoundName(className);
        ArrayList<String> explosionSpriteName = resources.getExplosionSpriteName(className);
        if ((explosionSoundName == null) && (explosionSpriteName == null)) {
            explosion = new Explosion();
        } else {
            AudioClip explosionSound = loadSound(explosionSoundName);
            if (explosionSpriteName == null) {
                explosion = new Explosion(explosionSound);
            } else {
                ArrayList<Image> explosionSprite = loadMultiSprite(explosionSpriteName);
                explosion = new Explosion(explosionSprite, explosionSound);
            }
        }
        return explosion;
    } */
    
    private Image getSprite(String spriteName) {
        URL url = Wingman.class.getResource(spriteName);
        Image img = getToolkit().getImage(url);
        try {
            MediaTracker tracker = new MediaTracker(this);
            tracker.addImage(img, 0);
            tracker.waitForID(0);
        } catch (Exception e) {
        }
        return img;
    }
    
    private ArrayList<Image> getMultiSprite(ArrayList<String> multiSpriteName) {
            ArrayList<Image> imageList = new ArrayList();
            for(String spriteName: multiSpriteName) {
                imageList.add(getSprite(spriteName));
            }
            return imageList;
    }
    
    private Explosion getExplosion(Property property) {
        Explosion explosion;
        String explosionSoundName = property.getExplosionSoundName();
        ArrayList<String> explosionSpriteNameList = property.getExplosionSpriteNameList();
        if ((explosionSoundName == null) && (explosionSpriteNameList == null)) {
            explosion = new Explosion();
        } else {
            AudioClip explosionSound = getSound(explosionSoundName);
            if (explosionSpriteNameList == null) {
                explosion = new Explosion(explosionSound);
            } else {
                ArrayList<Image> explosionSprite = getMultiSprite(explosionSpriteNameList);
                explosion = new Explosion(explosionSprite, explosionSound);
            }
        }
        return explosion;
    }
    
    private AudioClip getSound(String soundName) {
        Class metaObject = this.getClass();
        URL url = metaObject.getResource(soundName);
        return JApplet.newAudioClip(url);
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
        move += SPEED;
    }
    
     public void draw(int w, int h, Graphics2D g2) {

        //drawBackGroundWithTileImage(w, h, g2);
        if (!gameOver) {
            drawBackGroundWithTileImage(w, h, g2);
            checkTimeLine();
            for (BasicObject obj: permanentList) {
                obj.update(w, h);
                obj.draw(g2, observer);
            }
            collisionDetector.update();
            collisionDetector.checkCollision(observer);
            /** for (CollidableObject collidable: collisionDetector.getList()) {
                if (collidable != null) {
                    collidable.update(w, h);
                    collidable.draw(g2, observer);
                }
            } */
            
            
            ArrayList<CollidableObject> original = collisionDetector.getList();
            ArrayList<CollidableObject> copy = (ArrayList<CollidableObject>)original.clone();
            ListIterator it = copy.listIterator();
            while(it.hasNext()) {
                CollidableObject object = (CollidableObject)it.next();
                object.update(w, h);
                object.draw(g2, observer);
            }  
            copy = null;
        } else {
            backgroundSound.stop();
            g2.drawImage(getSprite("Resources/score.png"), 150, 25, observer); 
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 22));
            int score = myPlane1.getScore() + myPlane2.getScore();
            g2.drawString("Your score: " + score, 250, 200);
        }
    }
    
     private void checkTimeLine() {
         if(frameNumber == 3 * FRAME_PER_SECOND) {
            createEnemyPlaneDown(80, -40);
            createEnemyPlaneDown(200, -40);
            createEnemyPlaneDown(440, -40);
            createEnemyPlaneDown(560, -40);
            createPowerImage(400, -35);
         }
         
         if(frameNumber == 6 * FRAME_PER_SECOND) {
            createEnemyPlaneDown(80, -40);
            createEnemyPlaneDown(200, -40);
            createEnemyPlaneDown(320, -40);
            createEnemyPlaneDown(440, -40);
            createEnemyPlaneDown(560, -40);
         }
         
         if(frameNumber == 9 * FRAME_PER_SECOND) {
            createEnemyPlaneRight(-40, 320);
            createEnemyPlaneRight(-40, 240);
            createEnemyPlaneLeft(680, 280);
            createEnemyPlaneLeft(680, 200);
            createPowerImage(400, -35);
         }
         
         if(frameNumber == 12 * FRAME_PER_SECOND) {
            createEnemyPlaneUp(160, 500);
            createEnemyPlaneUp(320, 500);
            createEnemyPlaneUp(480, 500);
         } 
         
         if(frameNumber == 15 * FRAME_PER_SECOND) {
            createEnemyShoot(80, -40);
            createEnemyShoot(110, -30);
            createEnemyShoot(220, -10);
            createEnemyShoot(480, -50);
            createEnemyShoot(460, 0);
         } 
         
         if(frameNumber == 18 * FRAME_PER_SECOND) {
            createEnemyPlaneDown(80, -40);
            createEnemyPlaneDown(200, -40);
            createEnemyPlaneDown(440, -40);
            createEnemyPlaneDown(560, -40);
            createPowerImage(400, -35);
         }
         
         if(frameNumber == 21 * FRAME_PER_SECOND) {
            createEnemyPlaneDown(80, -40);
            createEnemyPlaneDown(200, -40);
            createEnemyPlaneDown(320, -40);
            createEnemyPlaneDown(440, -40);
            createEnemyPlaneDown(560, -40);
         }
         
         if(frameNumber == 24 * FRAME_PER_SECOND) {
            createEnemyPlaneRight(-40, 320);
            createEnemyPlaneRight(-40, 240);
            createEnemyPlaneLeft(680, 280);
            createEnemyPlaneLeft(680, 200);
         }
         
         if(frameNumber == 27 * FRAME_PER_SECOND) {
            createEnemyPlaneUp(160, 500);
            createEnemyPlaneUp(320, 500);
            createEnemyPlaneUp(480, 500);
         } 
         
         if(frameNumber == 30 * FRAME_PER_SECOND) {
            createEnemyShoot(80, -40);
            createEnemyShoot(110, -30);
            createEnemyShoot(220, -10);
            createEnemyShoot(480, -50);
            createEnemyShoot(460, 0);
         } 
         if(frameNumber == 33 * FRAME_PER_SECOND) {
            createEnemyPlaneDown(80, -40);
            createEnemyPlaneDown(200, -40);
            createEnemyPlaneDown(440, -40);
            createEnemyPlaneDown(560, -40);
         }
         
         if(frameNumber == 36 * FRAME_PER_SECOND) {
            createEnemyPlaneDown(80, -40);
            createEnemyPlaneDown(200, -40);
            createEnemyPlaneDown(320, -40);
            createEnemyPlaneDown(440, -40);
            createEnemyPlaneDown(560, -40);
         }
         
         if(frameNumber == 39 * FRAME_PER_SECOND) {
            createEnemyPlaneRight(-40, 320);
            createEnemyPlaneRight(-40, 240);
            createEnemyPlaneLeft(680, 280);
            createEnemyPlaneLeft(680, 200);
         }
         
         if(frameNumber == 42 * FRAME_PER_SECOND) {
            createEnemyPlaneUp(160, 500);
            createEnemyPlaneUp(320, 500);
            createEnemyPlaneUp(480, 500);
         } 
         
         if(frameNumber == 45 * FRAME_PER_SECOND) {
            createEnemyShoot(80, -40);
            createEnemyShoot(110, -30);
            createEnemyShoot(220, -10);
            createEnemyShoot(480, -50);
            createEnemyShoot(460, 0);
         } 
     }
    
    private void createIsland(int x, int y, String spriteName) {
        Island island = new Island(x, y, SPEED, 270, getSprite(spriteName), generator);
        permanentList.add(island);
    }
    
    /** private void testCollision(int x, int y) {
        Property property = resources.getProperty("MyBulletUp");
        createMunition(x, y, 4 * SPEED, property);
    }*/
    
    private void createMunition(int x, int y, int speed, Property property) {
        Image image = getSprite(property.getSpriteName());
        Explosion explosion = getExplosion(property);
        Munitions munition = new Munitions(x, y, speed, property, image, explosion);
        collisionDetector.add(munition);
    }
    
    private void createMunition(Property property, Weapon owner) {
        Image image = getSprite(property.getSpriteName());
        Explosion explosion = getExplosion(property);
        Munitions munition = new Munitions(property, image, explosion, owner);
        collisionDetector.add(munition);
    }
    
    private void createDumpWeapon(int x, int y, int speed, Property property) {
        Image image = getSprite(property.getSpriteName());
        Explosion explosion = getExplosion(property);
        DumpWeapon dumpWeapon = new DumpWeapon(x, y, speed, property, image, explosion, gameEvents);
        collisionDetector.add(dumpWeapon);
    }
    
    private SmartWeapon createSmartWeapon(int x, int y, int speed, String name) {
        Property property = resources.getProperty(name);
        Image image = getSprite(property.getSpriteName());
        Explosion explosion = getExplosion(property);
        SmartWeapon smartWeapon = new SmartWeapon(x, y, speed, property, image, explosion, gameEvents);
        gameEvents.addObserver(smartWeapon);
        collisionDetector.add(smartWeapon);
        return smartWeapon;
    }
    /** private void createSmartWeapon(int x, int y, int speed, Property property) {
        Image image = getSprite(property.getSpriteName());
        Explosion explosion = getExplosion(property);
        SmartWeapon smartWeapon = new SmartWeapon(x, y, speed, property, image, explosion, gameEvents);
        gameEvents.addObserver(smartWeapon);
        collisionDetector.add(smartWeapon);
    }
    
    private void createMyPlane(int x1, int y1, int x2, int y2) {
        Property property1 = resources.getProperty("MyPlane1");
        Property property2 = resources.getProperty("MyPlane2");
        createSmartWeapon(x1, y1, 0, property1);
        createSmartWeapon(x2, y2, 0, property2);
    }*/
    
    private void createEnemyPlaneDown(int x, int y) { 
        Property property = resources.getProperty("EnemyPlaneDown");
        createMunition(x, y, 2 * SPEED, property);
    }
    
    private void createEnemyPlaneUp(int x, int y) { 
        Property property = resources.getProperty("EnemyPlaneUp");
        createMunition(x, y, 2 * SPEED, property);
    }
    
    private void createEnemyPlaneLeft(int x, int y) { 
        Property property = resources.getProperty("EnemyPlaneLeft");
        createMunition(x, y, 2 * SPEED, property);
    }
    
    private void createEnemyPlaneRight(int x, int y) { 
        Property property = resources.getProperty("EnemyPlaneRight");
        createMunition(x, y, 2 * SPEED, property);
    }
    
    private void createEnemyBullet(Weapon owner) {
        Property property = resources.getProperty("EnemyBullet");
        createMunition(property, owner);
    }
    
    private void createMyBulletUp(Weapon owner) {
        Property property = resources.getProperty("MyBulletUp");
        createMunition(property, owner);
    }
    
    private void createMyBulletLeft(Weapon owner) {
        Property property = resources.getProperty("MyBulletLeft");
        createMunition(property, owner);
    }
    
    private void createMyBulletRight(Weapon owner) {
        Property property = resources.getProperty("MyBulletRight");
        createMunition(property, owner);
    }
    
    private void createEnemyShoot(int x, int y) {
        Property property = resources.getProperty("EnemyShoot");
        createDumpWeapon(x, y, 2 * SPEED, property);
    }
    
    private void createPowerImage(int x, int y) {
        Property property = resources.getProperty("PowerImage");
        createMunition(x, y, 2 * SPEED, property);
    }
    
    /** private void createEnemyPlaneUp(int x, int y) {
        createEnemyPlane(x, y, 90, "EnemyPlaneUp");
    }
    
    private void createEnemyPlaneLeft(int x, int y) {
        createEnemyPlane(x, y, 180, "EnemyPlaneLeft");
    }
    
    private void createEnemyPlaneRight(int x, int y) {
        createEnemyPlane(x, y, 0, "EnemyPlaneRight");
    }
    
    private void createEnemyPlane(int x, int y, int direction, String spriteName) {
        Enemy enemyPlane = new Enemy(x, y, 2 * SPEED, direction, 
                getSprite(spriteName), getExplosion("EnemyPlane"), 
                resources.getEnemyList("Enemy"), "EnemyPlane");
        collisionDetector.add(enemyPlane);
    }*/
    
    // Munitions(Image image, Explosion explosion, ArrayList<String> enemyList, String name, Weapon owner)
    
    /** private void createEnemyBullet(Weapon owner) {
        Munitions enemyBullet = new Munitions(getSprite("EnemyBullet"), 
                getExplosion("EnemyBullet"), resources.getEnemyList("EnemyBullet"),
                "EnemyBullet", owner);
        collisionDetector.add(enemyBullet); 
    }
    
    private void createMyBulletUp(Weapon owner) {
        createMyBullet("MyBulletUp", owner);
    }
    
    private void createMyBulletDiagonal(Weapon owner) {
        createMyBullet("MyBulletLeft", owner);
        createMyBullet("MyBulletRightDown", owner);
    }
    
    private void createMyBullet(String spriteName, Weapon owner) {
        Munitions myBullet = new Munitions(getSprite(spriteName), 
                getExplosion("MyBullet"), resources.getEnemyList("MyBullet"),
                "MyBullet", owner);
        collisionDetector.add(myBullet);
    }
    
    //------------------------------------------------------------------------------------
    private void createEnemyBullet(int x, int y, int direction) {
        Enemy enemyBullet = new Enemy(x, y, 4 * SPEED, direction, getSprite("EnemyBullet"), 
                getExplosion("EnemyBullet"), resources.getEnemyList("EnemyBullet"),
                "EnemyBullet");
        collisionDetector.add(enemyBullet); 
    }
    
    private void createMyBulletUp(int x, int y) {
        createMyBullet(x, y, 90, "MyBulletUp");
    }
    
    private void createMyBullet(int x, int y, int direction, String spriteName) {
        Enemy myBullet = new Enemy(x, y, 4 * SPEED, direction, getSprite(spriteName), 
                getExplosion("MyBullet"), resources.getEnemyList("MyBullet"),
                "MyBullet");
        collisionDetector.add(myBullet); 
    } */
    //------------------------------------------------------------------------------------
    
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
        frameNumber++;
        Dimension d = getSize();
        Graphics2D g2 = createGraphics2D(d.width, d.height);
        draw(d.width, d.height, g2);
        informationPanel.repaint();
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
                thread.sleep(DELAY);
            } catch (InterruptedException e) {
                break;
            }
            
        }
    	    	
       // thread = null;
    }
    
    @Override
    public void update(Observable o, Object o1) {
        GameEvents gameEvents = (GameEvents)o;
        if(o1 instanceof Weapon) {
            Weapon owner = (Weapon)o1;
            String munitionName = owner.getCurrentMunitionName();
            switch(munitionName) {
                case "EnemyBullet":   createEnemyBullet(owner); break;
                case "MyBulletUp":    createMyBulletUp(owner); break;
                case "MyBulletLeft":  createMyBulletLeft(owner); break;
                case "MyBulletRight": createMyBulletRight(owner); break;
            }
        }
    }

    public JPanel getInformationPanel() {
        return informationPanel;
    }
    
    public static void main(String[] args) {
        final Wingman demo = new Wingman();
        demo.setPreferredSize(new Dimension(640, 480));
        demo.init();
        JFrame f = new JFrame("Welcome to Wingman");
        f.addWindowListener(new WindowAdapter() {});
        f.add(demo, BorderLayout.CENTER);
        f.add(demo.getInformationPanel(), BorderLayout.SOUTH);
        f.pack();
        f.setSize(new Dimension(640, 540));
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        f.setResizable(false);
        demo.start();
    }

    
}
class InformationPanel extends JPanel {
    private SmartWeapon myPlane1;
    private SmartWeapon myPlane2;
    private static final int WIDTH = 200;
    private static final int HEIGHT = 15;
    private double ratio;

    public InformationPanel(SmartWeapon myPlane1, SmartWeapon myPlane2) {
        this.myPlane1 = myPlane1;
        this.myPlane2 = myPlane2;
        ratio = WIDTH / myPlane1.getHealth();
        setFont(new Font("SansSerif", Font.BOLD, 16));
        setBackground(Color.GRAY);
        setPreferredSize(new Dimension(640, 60));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int health1 = (int) (myPlane1.getHealth() * ratio);
        int health2 = (int) (myPlane2.getHealth() * ratio);
        int score = myPlane1.getScore() + myPlane2.getScore();
        g.setColor(Color.green);
        g.drawString("Health 1: ", 20, 25);
        g.drawRect(100, 10, WIDTH, HEIGHT);
        g.fillRect(100, 10, health1, HEIGHT);
        g.setColor(Color.red);
        g.drawString("Health 2: ", 20, 50);
        g.drawRect(100, 35, WIDTH, HEIGHT);
        g.fillRect(100, 35, health2, HEIGHT);
        g.setColor(Color.BLUE);
        g.drawString("Score: " + score, 400, 35);
    }

}
