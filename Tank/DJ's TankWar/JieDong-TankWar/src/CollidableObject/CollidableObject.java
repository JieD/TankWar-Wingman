
package CollidableObject;

import Helper.Constants;
import ResourceManagement.Explosion;
import ResourceManagement.Property;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;

/**
 * CollidableObject represents any BasicObject that is collidable. 
 * CollidableObject is the super class for MyPlane, EnemyPlane and etc. 
 * Since it is collidable, it should have its explosion, enemyList and collide(). 
 * LifeEvent is used here to separate collision detection from CollidableObject.
 * The reason is the object itself should not be aware of the existence of any
 * other objects. 
 * DataField enemyList is used to check whether collision is effective. If object
 * collides with an object that is not its enemy, no collision action will be 
 * triggered.
 */
public abstract class CollidableObject extends BasicObject {
    
    private Explosion explosion;
    private boolean alive = true;
    private boolean exploding = false;
    
    public CollidableObject(int x, int y, Property property) { 
        super(x, y, property);
        this.explosion = property.getExplosion();     
    }
    
    public abstract void collide(CollidableObject other);
    
    protected void explode() {
        exploding = true;
        explosion.playAudio();
    }
    
    public Rectangle getVisualRect(ImageObserver observer) {
        int width  = getSizeX();
        int height = getSizeY();
        int newX = (int) (getX() + (1 - Constants.SCALE) * width);
        int newY = (int) (getY() + (1 - Constants.SCALE) * height);
        int newWidth = (int) (width * Constants.SCALE);
        int newHeight = (int) (height * Constants.SCALE);
        return new Rectangle(newX, newY, newWidth, newHeight);
    }
    
    public boolean intersects(CollidableObject another, ImageObserver observer) {
        boolean intersect = false;
        Rectangle recMe = getVisualRect(observer);
        Rectangle recAnother = another.getVisualRect(observer);
        intersect = recMe.intersects(recAnother);
        return intersect;
    }

    public void update(int width, int height) {
        //super.update(width, height);
        if(exploding) {
            Image newImage = explosion.getCurrentImage();
            if(newImage != null) setImage(newImage);
            else {
                exploding = false;
                alive = false;
            }
        } 
        if(alive) super.update(width, height);
    }

    @Override
    public void draw(Graphics g, ImageObserver obs) {
        if(alive) super.draw(g, obs);
    }

   
    /** public int getDamageOther() {
        return damageOther;
    }*/

    public Explosion getExplosion() {
        return explosion;
    }

    public boolean isExploding() {
        return exploding;
    }

    public void setExploding(boolean exploding) {
        this.exploding = exploding;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public void setExplosion(Explosion explosion) {
        this.explosion = explosion;
    }
}
