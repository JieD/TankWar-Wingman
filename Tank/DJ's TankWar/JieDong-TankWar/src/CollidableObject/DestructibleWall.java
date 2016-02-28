
package CollidableObject;

import Helper.Constants;
import ResourceManagement.Property;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.ImageObserver;

/**
 * DestructibleWall is destructible. If it collides with Shell or Rocket, it 
 * will jump outside the visible area. After disappear for disappearTimeLenghth,
 * it will try to go back to its start position. If the position is occupied, it
 * will check for every occupyWaitTimeLength. Once the position is not occupied,
 * it will go back.
 */
public class DestructibleWall extends CollidableObject {
    private final Point startPosition;
    private Point disappearPosition = Constants.DISAPPEAR;
    private int disappearTimeLength = Constants.DISAPPEAR_TIME_LENGTH;
    private int occupyWaitTimeLength = Constants.OCCUPY_WAIT_TIME_LENGTH;
    private long disappearStartTime;
    private boolean disappear = false;
    private boolean occupied = false;
    
    public DestructibleWall (int x, int y, Property property) { 
        super(x, y, property);  
        startPosition = new Point(x, y);
    }

    /**
     * @Override
     * If checking collision against its kind, return false - do not checking collision
     * If not collide, reset occupied and disappear.
     */
    public boolean intersects(CollidableObject another, ImageObserver observer) {
        boolean result = false;
        if(getX() != disappearPosition.x) {
            result = super.intersects(another, observer);
            if (!result) {
                occupied = false;
                disappear = false;
            }
        }
        return result;
    }
    
    /** 
     * @Override 
     * 
     */
    public void collide(CollidableObject other) {
        String name = other.getName();
        if (!disappear) {
            //if((name.equalsIgnoreCase("Shell")) || (name.equalsIgnoreCase("Rocket"))) {
            if(name.equalsIgnoreCase("Shell") || name.equalsIgnoreCase("Rocket")) {
                moveTo(disappearPosition);
                disappearStartTime = System.currentTimeMillis();
                disappear = true;  
                //System.out.println(startPosition + " collides with pickup, disappear at time: " + disappearStartTime);
            }
            /** switch (name) {
            case "Shell": 
            case "Rocket":
                moveTo(disappearPosition);
                disappearStartTime = System.currentTimeMillis();
                disappear = true;   
            } */            
        } else {
            occupied = true;
            moveTo(disappearPosition);
            disappearStartTime = System.currentTimeMillis();
            //System.out.println(startPosition + " occupied, wait start: " + disappearStartTime);
        }
    }
    
    private void moveTo(Point point) {
        setX(point.x);
        setY(point.y);
    }
    
    private void checkReappear(long wait) {
        //System.out.println("checkReappear()");
        long currentTime = System.currentTimeMillis();
        if ((currentTime - disappearStartTime) >= wait) {
            moveTo(startPosition);
            //System.out.println(startPosition + " disappear time expires, move back");
        }
    }

    
    @Override
    public void update(int width, int height) {
        if(disappear) {
            int j = occupied? 1 : 0;
            //System.out.println(startPosition + " disappears & occupied: " + j);
        }
        if (disappear && !occupied) checkReappear(disappearTimeLength);
        else if(disappear && occupied) checkReappear(occupyWaitTimeLength);
        //super.update(width, height);  
    }  

    @Override
    public void draw(Graphics g, ImageObserver obs) {
        if (!disappear) super.draw(g, obs);
    } 
}
