
package CollidableObject;

import BasicObject.BasicObject;
import Helper.Explosion;
import Helper.Property;
import Weapon.SmartWeapon;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import wingman.Wingman;

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
    
    private String name;
    private int damageOther;
    private int reward;
    private Explosion explosion;
    private ArrayList<String> enemyList;
    private boolean alive = true;
    private boolean exploding = false;
    
    public CollidableObject(int x, int y, int speed, Property property, Image image, 
            Explosion explosion) { 
        super(x, y, speed, property.getDirection(), image);
        this.explosion = explosion;
        name = property.getName();
        damageOther = property.getDamageOther();
        reward = property.getReward();
        enemyList = property.getEnemyList();       
    }
    
    public abstract void collide(CollidableObject other);
    
    protected void explode() {
        exploding = true;
        explosion.playAudio();
    }
    
    public Rectangle getVisualRect(ImageObserver observer) {
        int width  = getSizeX();
        int height = getSizeY();;
        int newX = (int) (getX() + (1 - Wingman.SCALE) * width);
        int newY = (int) (getY() + (1 - Wingman.SCALE) * height);
        int newWidth = (int) (width * Wingman.SCALE);
        int newHeight = (int) (height * Wingman.SCALE);
        return new Rectangle(newX, newY, newWidth, newHeight);
    }
    
    public boolean intersects(CollidableObject another, ImageObserver observer) {
        boolean intersect = false;
        if(enemyList.contains(another.getName())) {
            Rectangle recMe = getVisualRect(observer);
            Rectangle recAnother = another.getVisualRect(observer);
            intersect = recMe.intersects(recAnother);
        }
        return intersect;
    }

    public void update(int width, int height) {
        super.update(width, height);
        if(isOut(width, height)) alive = false;
        if(exploding) {
            Image newImage = explosion.getCurrentImage();
            if(newImage != null) setImage(newImage);
            else {
                exploding = false;
                alive = false;
            }
        }      
    }

    public String getName() {
        return name;
    }

    public int getDamageOther() {
        return damageOther;
    }

    public int getReward() {
        return reward;
    }

    public ArrayList<String> getEnemyList() {
        return enemyList;
    }

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

    public void setName(String name) {
        this.name = name;
    }

    public void setExplosion(Explosion explosion) {
        this.explosion = explosion;
    }

    public void setEnemyList(ArrayList<String> enemyList) {
        this.enemyList = enemyList;
    }
}
