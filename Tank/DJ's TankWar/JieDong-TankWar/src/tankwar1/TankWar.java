
package tankwar1;

import CollidableObject.CollidableObject;
import Helper.CollisionDetector;
import Helper.Constants;
import Helper.Creator;
import Helper.GameEvents;
import ResourceManagement.ResourceLoader;
import ResourceManagement.ResourceTable;
import Weapon.Tank;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JApplet;
import javax.swing.JFrame;

/**
 *
 * @author ASUS
 */
public class TankWar extends JApplet implements Runnable {

    private Thread thread;

    public static boolean gameOver;
    private ImageObserver observer;

    private ResourceLoader loader = new ResourceLoader(this);
    private ArrayList<CollidableObject> objectList = new ArrayList();
    private CollisionDetector collisionDetector = new CollisionDetector(objectList);
    private GameEvents gameEvents = new GameEvents();
    private Tank player1;
    private Tank player2;
    private AudioClip backgroundSound = loader.loadSound("Resources/Music.mid");

    @Override
    public void init() {
        setBackground(Color.WHITE);
        gameOver = false;
        observer = this;
        ResourceTable resourceTable = new ResourceTable(loader);
        resourceTable.init();
        Creator creator = new Creator("MapLayout", objectList, resourceTable, gameEvents);
        creator.initTankWar();
        setFocusable(true);
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                gameEvents.setValue(e);
            }
            /** @Override
            public void keyReleased(KeyEvent e) {
                gameEvents.setValue(e);
            } */
        });
        gameEvents.addObserver(creator);
        findPlayers();
        
        backgroundSound.loop();
    }
    
    private void findPlayers() {
        for(CollidableObject object: objectList) {
            if(object.getName().equalsIgnoreCase("Tank1")) player1 = (Tank)object;
            else if(object.getName().equalsIgnoreCase("Tank2")) player2 = (Tank)object;
        }
    }

    public void drawBackGroundWithTileImage(int w, int h, Graphics2D g2) {
        Image imgBackground;
        imgBackground = loader.loadSingleImage("Resources/Background.png");
        int TileWidth = imgBackground.getWidth(this);
        int TileHeight = imgBackground.getHeight(this);

        int NumberX = (int) (w / TileWidth);
        int NumberY = (int) (h / TileHeight);
        
        for (int i = -1; i <= NumberY; i++) {
            for (int j = 0; j <= NumberX; j++) {
                g2.drawImage(imgBackground, j * TileWidth, i * TileHeight, TileWidth, TileHeight, this);
            }
        }
    }

    public void draw(int w, int h, Graphics2D g2) throws IOException {

        if (!gameOver) {
            drawBackGroundWithTileImage(w, h, g2);
            collisionDetector.update();
            collisionDetector.checkCollision(observer);
            for(CollidableObject object: objectList) {
                object.update(w, h);
                object.draw(g2, observer);
            } 
            /** ArrayList<CollidableObject> original = objectList;
            ArrayList<CollidableObject> copy = (ArrayList<CollidableObject>)original.clone();
            ListIterator it = copy.listIterator();
            while(it.hasNext()) {
                CollidableObject object = (CollidableObject)it.next();
                object.update(w, h);
                object.draw(g2, observer);
            }  
            copy = null; */
        } else {
            backgroundSound.stop();
        }
    }

    private Graphics2D createGraphics2D(int w, int h, BufferedImage img) {
        Graphics2D g2 = null;
        g2 = img.createGraphics();
        g2.setBackground(Color.BLACK);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g2.clearRect(0, 0, w, h);
        return g2;
    }

    private BufferedImage paintMap() {
        Dimension dMap = Constants.MAP_DIMENSION;
        BufferedImage imgMap = new BufferedImage(dMap.width, dMap.height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gMap = createGraphics2D(dMap.width, dMap.height, imgMap);
        try {
            draw(dMap.width, dMap.height, gMap);
        } catch (IOException e) {
            System.out.println("IOException");
        }
        gMap.dispose();
        return imgMap;
    }
    
    private Point findView(Tank player) {
        int width = Constants.VIEW_DIMENSION.width;
        int height = Constants.VIEW_DIMENSION.height;
        int x1 = player.getX() - width / 2;
        int y1 = player.getY() - height / 2;
        if(x1 < 0) x1 = 0;
        if(y1 < 0) y1 = 0;
        int x2 = x1 + width;
        int y2 = y1 + height;
        if(x2 > Constants.MAP_DIMENSION.width) x1 = Constants.MAP_DIMENSION.width - width;
        if(y2 > Constants.MAP_DIMENSION.height) y1 = Constants.MAP_DIMENSION.height - height;
        return new Point(x1, y1);
    } 
    
    
    private BufferedImage paintWindow(BufferedImage imgMap) {
        Dimension dView = Constants.VIEW_DIMENSION;
        Dimension dMini = Constants.MINI_DIMENSION;
        Dimension dWindow = getSize();
        Point pLeftView = Constants.LEFT_VIEW_POINT;
        Point pRightView = Constants.RIGHT_VIEW_POINT;
        Point pMiniMap = Constants.MINI_MAP_POINT;
        
        Point pPlayerView1 = findView(player1);
        Point pPlayerView2 = findView(player2);
        
        BufferedImage view1 = imgMap.getSubimage(pPlayerView1.x, pPlayerView1.y, dView.width, dView.height);
        BufferedImage view2 = imgMap.getSubimage(pPlayerView2.x, pPlayerView2.y, dView.width, dView.height);
        Image miniMap = imgMap.getScaledInstance(dMini.width, dMini.height, Image.SCALE_SMOOTH);

        BufferedImage imgWindow = (BufferedImage)createImage(dWindow.width, dWindow.height);
        Graphics2D gWindow = createGraphics2D(dWindow.width, dWindow.height, imgWindow);
        gWindow.drawImage(view1, pLeftView.x, pLeftView.y, null);
        gWindow.drawImage(view2, pRightView.x, pRightView.y, null);
        gWindow.drawImage(miniMap, pMiniMap.x, pMiniMap.y, null); 
        
        gWindow.setColor(Color.RED);
        gWindow.setFont(new Font("SansSerif", Font.BOLD, 50));
        String score = Integer.toString(player1.getScore());
        gWindow.drawString(score, 360, 60);
        
        gWindow.setColor(Color.BLUE);
        gWindow.setFont(new Font("SansSerif", Font.BOLD, 50));
        score = Integer.toString(player2.getScore());
        gWindow.drawString(score, 430, 60);
        return imgWindow;
    }
    
    @Override
    public void paint(Graphics g) {
        BufferedImage imgMap = paintMap();
        BufferedImage imgWindow = paintWindow(imgMap);
        g.drawImage(imgWindow, 0, 0, this);
    }

    @Override
    public void start() {
        thread = new Thread(this);
        thread.setPriority(Thread.MIN_PRIORITY);
        thread.start();
    }

    @Override
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
    }
    
     public static void main(String[] args) {
        final TankWar tankWar = new TankWar();
        tankWar.init();
        JFrame f = new JFrame("Welcome to Tankwar");
        //f.addWindowListener(new WindowAdapter() {});
        f.add(tankWar, BorderLayout.CENTER);
        f.pack();
        f.setSize(new Dimension(820, 520));
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
        //f.setResizable(false);
        tankWar.start();  
    }
}