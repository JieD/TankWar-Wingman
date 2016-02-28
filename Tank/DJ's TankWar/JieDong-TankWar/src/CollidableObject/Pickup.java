
package CollidableObject;

import Helper.Constants;
import Helper.MunitionUsage;
import ResourceManagement.Property;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.HashMap;
import java.util.Random;

/**
 * Pickup appears randomly and can be collected by driving into it.
 * Pickup will change its kind time to time and jump to a new position.
 * It will also jump to a new position when it is collected by a tank.
 * 4 kinds of Pickups:
 * Rocket, BouncingBomb, Shield and Toolbox
 */
public class Pickup extends CollidableObject {
    private Random generator = Constants.GENERATOR;
    private String currentName;
    private boolean conflict = false;
    private long appearStartTime;
    private long appearTimeLength;
    private HashMap<String, MunitionUsage> munitionUsageTable;
    
    public Pickup(int x, int y, Property property) {
        super(x, y, property);
        this.munitionUsageTable = property.getMunitionUsageTable();
        chooseType();
        setClock();
    }
    
    private void chooseType() {
        Image[] imageList = getImageList();
        int numberOfImage = imageList.length;
        //int x = generator.nextInt();
        //int type = Math.abs(x % numberOfImage);
        int type = Math.abs(generator.nextInt() % numberOfImage);
        //System.out.println("random number: " + x + ", choose type: " + type);
        switch(type) {
            case 0: currentName = "Rocket"; 
                    setImage(imageList[0]);
                    break;
            case 1: currentName = "BouncingBomb"; 
                    setImage(imageList[1]);
                    break;
            case 2: currentName = "Shield";
                    setImage(imageList[2]);
                    break;
            case 3: currentName = "Toolbox";
                    setImage(imageList[3]);
                    break;
        }
        //System.out.println("choose type: " + currentName);
    }
    
    private void choosePosition() {
        int xMax = Constants.MAP_DIMENSION.width;
        int yMax = Constants.MAP_DIMENSION.height;
        setX(Math.abs(generator.nextInt() % xMax));
        setY(Math.abs(generator.nextInt() % yMax));
        //System.out.println("choose new position: (" + getX() + ", " + getY() + ")");
    }
    
    private void setClock() {
        appearStartTime = System.currentTimeMillis();
        appearTimeLength = (3 + Math.abs(generator.nextInt() % 17)) * 1000;
        //System.out.println("appearTimeLength: " + appearTimeLength);
    }
    
    private void reappear() {
        chooseType();
        choosePosition();
        setClock();
        //System.out.println("reappear()");
    }
    
    /** 
     * @Override
     * Pickup needs to check whether tank runs over it. 
     * It also needs to check whether it conflicts with other gaming components 
     * every time it jumps to a random position. If it does, it needs to jump to 
     * another random position until none confliction happens. If it conflicts 
     * with others, it will not be drawn.
     */
    public boolean intersects(CollidableObject another, ImageObserver observer) {
        boolean result = super.intersects(another, observer);
        if(!result) conflict = false; // reset conflict value
        return result;
    }

    /**
     * @Override
     * If colliding with Tank, jumps to a random position.
     * Else conflict is true, and jumps to a random position. 
     * Note here no need to change type nor reset clock.
     */ 
    public void collide(CollidableObject other) {
        String name = other.getName();
        choosePosition();
        switch(name) {
            case "Tank1": break;
            case "Tank2": break;
            default: conflict = true;      
        } 
    }

    @Override
    public void draw(Graphics g, ImageObserver obs) {
        if(!conflict) super.draw(g, obs);
    } 

    /**
     * @Override
     * Check whether time expires. If does, reappear().
     */  
    public void update(int width, int height) {
        if(!conflict) checkJump();

        //super.update(width, height);
    }
    
    private void checkJump() {
        long currentTime = System.currentTimeMillis();
        if((currentTime - appearStartTime) >= appearTimeLength) {
            reappear();   
        }
    }
    
    public MunitionUsage getCurrentMunitionUsage() {
        return munitionUsageTable.get(currentName);
    }
}
