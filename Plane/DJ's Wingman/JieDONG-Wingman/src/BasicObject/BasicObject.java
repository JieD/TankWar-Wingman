
package BasicObject;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Observable;
import wingman.Wingman;

/**
 * BasicObject represents the very basic functionality of objects in the game.
 * It can move and draw its own image.
 * Note here direction is used to calculate position, which simplifies 
 * calculation greatly.
 * BacicObject is the most general class in the Wingman, and it can be easily
 * reused in Tankwar or any other game.
 */
public class BasicObject extends Observable {

    private int x;
    private int y;
    private int speed;
    private int direction;
    private Image image;
    private int sizeX;
    private int sizeY;
    
    public BasicObject(int x, int y, int speed, int direction, Image image) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.direction = direction;
        this.image = image;
        sizeX = image.getWidth(null);
        sizeY = image.getHeight(null);
    }
    
    public void draw(Graphics g, ImageObserver obs) {
        if(image != null)
            g.drawImage(image, x, y, obs);
    }
    
    public void update(int width, int height) {
        double radian = 2 * Math.PI * direction / 360;
        x += speed * Math.cos(radian);
        y -= speed * Math.sin(radian);
    }
    
    /**
     * isOut() checks whether the object is outside of window.
     * Here cushion is used to improve visual effect of the game. 
     * For example, when an island appears, if its position is within the window, 
     * it looks like the island jumps into the window. It is necessary to set 
     * the initial position of the island outside the visible space. 
     * At the same time, isOut() method need to change so that when the island 
     * position is within the cushion, it is not considered outside the window.
     */
    public boolean isOut(int width, int height) {
        int top = 0 - Wingman.CUSHION;
        int down = height + Wingman.CUSHION;
        int left = 0 - Wingman.CUSHION;
        int right = width + Wingman.CUSHION;
        return ((x < left) || (x > right) || (y < top) || (y > down));
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getSizeX() {
        return sizeX;
    }

    public int getSizeY() {
        return sizeY;
    }

    public int getSpeed() {
        return speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
