
package CollidableObject;

import Helper.Constants;
import ResourceManagement.Property;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.ImageObserver;
import java.util.Observable;

/**
 * BasicObject represents the very basic functionality of objects in the game.
 * It can move and draw its own image. 
 * Note here direction is used to calculate position which simplifies 
 * calculation greatly. direction is also used to choose the correct image
 * if the object has multiple images, 
 * BacicObject is the most general class in the Wingman, and it can be easily
 * reused in Tankwar or any other game.
 */
public abstract class BasicObject extends Observable {

    private String name;
    private int x;
    private int y;
    private int speed;
    private int direction;
    private Image currentImage;
    private Image[] imageList;
    
    public BasicObject(int x, int y, Property property) {
        this.name = property.getName();
        this.x = x;
        this.y = y;
        this.speed = property.getSpeed();
        this.direction = property.getDirection();
        initImage(property);
    }
    
    protected final void initImage(Property property) {
        imageList = property.getImageList();
        int size = imageList.length;
        if (size == 1) currentImage = imageList[0];
        else mapCorrectImage();      
    }
    
    // if has multiple images, map image according current direction
    protected void mapCorrectImage() {
        if(imageList.length > 1) {
            while(direction < 0) direction += 360;
            direction %= Constants.TOTAL_DEGREE;
            int index = direction / getSmallestChangeInDirection();    
            currentImage = imageList[index];   
        }            
    }
    
    protected void increaseDirection() {
        direction += getSmallestChangeInDirection();
        direction %= Constants.TOTAL_DEGREE;
        mapCorrectImage();
    }
    
    protected void decreaseDirection() {
        direction -= getSmallestChangeInDirection();
        direction %= Constants.TOTAL_DEGREE;
        mapCorrectImage();
    }
    
    protected int getSmallestChangeInDirection() {
        int count = imageList.length;
        return Constants.TOTAL_DEGREE / count;
    }
    
    public void draw(Graphics g, ImageObserver obs) {
        if(currentImage != null)
            g.drawImage(currentImage, x, y, obs);
    }
    
    public void update(int width, int height) {
        double radian = 2 * Math.PI * direction / 360;
        x += speed * Math.cos(radian);
        y -= speed * Math.sin(radian);
    }

    public String getName() {
        return name;
    }

    public Image[] getImageList() {
        return imageList;
    }
    
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSizeX() {
        return currentImage.getWidth(null);
    }

    public int getSizeY() {
        return currentImage.getHeight(null);
    }

    public int getSpeed() {
        return speed;
    }
    
    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
        mapCorrectImage();
    }

    public Image getImage() {
        return currentImage;
    }

    public void setImage(Image image) {
        this.currentImage = image;
    }
}
