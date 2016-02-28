
package Weapon;

import CollidableObject.CollidableObject;
import CollidableObject.Pickup;
import Helper.Constants;
import Helper.GameEvents;
import Helper.MunitionUsage;
import Munitions.Munitions;
import ResourceManagement.Property;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.Observable;

/**
 *
 * @author ASUS
 */
public class Tank extends SmartWeapon {
    private MunitionUsage currentSecondaryWeapon;
    private final int xStart;
    private final int yStart;
    private int xCenter;
    private int yCenter;
    private boolean friction = true;
    
    public Tank(int x, int y, Property property, GameEvents gameEvents) {
        super(x, y, property, gameEvents);
        xStart = x;
        yStart = y;
    }

    /**
     * @Override
     * collide with:
     * 1. PermanentWall, DestructibleWall or Tank, speed = -speed;
     * 2. Pickup - replace the previous secondary weapon
     * 3. Munitions - decrease health
     */
    public void collide(CollidableObject another) {
        if(another instanceof Munitions) {
            Munitions munition = (Munitions)another;
            if(munition.getOwner() != this) collideWithMunition(another);
        } else if (another instanceof Tank) setSpeed(-getSpeed());
        else {
            String name = another.getName();
            switch(name) {
                case "PermanentWall":
                case "DestructibleWall":
                    setSpeed(-getSpeed()); break;
                case "Pickup": collideWithPickup(another); break;
            }
        }
    }
    
    private void collideWithPickup(CollidableObject another) {
        Pickup pickup = (Pickup)another;
        resetSecondaryWeapon(pickup.getCurrentMunitionUsage());
    }

    // needs to check whether the list size is 2 and newSecondaryWeapon is null
    private void resetSecondaryWeapon(MunitionUsage newSecondaryWeapon) {
        ArrayList<MunitionUsage> munitionUsageList = getMunitionUsageList();
        int size = munitionUsageList.size();
        if((newSecondaryWeapon == null) && (size == 2)) {
            munitionUsageList.remove(1);
            currentSecondaryWeapon = null;
        } else if (newSecondaryWeapon != null) {
            if(size == 1) munitionUsageList.add(newSecondaryWeapon);
            else if(size == 2) munitionUsageList.set(1, newSecondaryWeapon);
            currentSecondaryWeapon = newSecondaryWeapon;
        } 
    }
    
    private Point getDrawPosition(Point offset) {
        Point p = null;
        int x = getX() + offset.x;
        int y = getY() + offset.y;
        p = new Point(x, y);
        return p;
    }
    
    private void drawHealthBar(Graphics g) {
        g.setColor(Color.GREEN);
        Point healthBarPosition = getDrawPosition(Constants.HEALTH_BAR_POINT);
        int x = healthBarPosition.x;
        int y = healthBarPosition.y;
        int width = (int) ((getHealth() * 1.0 / getHEALTH()) * Constants.HEALTH_BAR_DIMENSION.width);
        int height = Constants.HEALTH_BAR_DIMENSION.height;
        g.drawRect(x, y, Constants.HEALTH_BAR_DIMENSION.width, height);
        g.fillRect(x, y, width, height);
    }
    
    private void drawSecondaryWeapon(Graphics g, ImageObserver obs) {
        if(currentSecondaryWeapon != null) {
            Image img = currentSecondaryWeapon.getSign();
            Point pickupSignPosition = getDrawPosition(Constants.PICKUP_SIGN_POINT);
            int x = pickupSignPosition.x;
            int y = pickupSignPosition.y;
            g.drawImage(img, x, y, obs);

            Point pickupStoragePosition = getDrawPosition(Constants.PICKUP_STORAGE_POINT);
            x = pickupStoragePosition.x;
            y = pickupStoragePosition.y;
            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSerif", Font.BOLD, 14));
            String storage = Integer.toString(currentSecondaryWeapon.getStorage());
            g.drawString(storage, x, y);
        }   
    }
    
    /**
     * @Override
     * if not exploding, draw health bar. if has secondary weapon, draw information
     * if is exploding, just draw explosion
     */
    public void draw(Graphics g, ImageObserver obs) {
        if(!isExploding()) {
            drawHealthBar(g);
            if(currentSecondaryWeapon != null) drawSecondaryWeapon(g, obs);
        }
        super.draw(g, obs);
    }

    private void reset() {
        setHealth(getHEALTH());
        setX(xStart);
        setY(yStart);
        setDirection(0);
        resetSecondaryWeapon(null);
    }
    
    /**
     * @Override
     * if not exploding, check health. If health < 0, explode; else super.update();
     * if is exploding, check whether explosion animation finishes.
     * if finishes, check life.
     * if life > 0, reset both tanks; else game is over.
     * 
     */
    public void update(int width, int height) {
        if(isAlive()) {
            mapCorrectImage();
            checkFriction();
            double radian = 2 * Math.PI * getDirection() / 360;
            int x = (int) (getX() + getSpeed() * Math.cos(radian));
            int y = (int) (getY() - getSpeed() * Math.sin(radian));
            setX(x);
            setY(y);
            xCenter = x + getSizeX() / 4;
            yCenter = y + getSizeY() / 4;
            if(!isExploding()) {
                int health = getHealth();
                int life = getLife();
                if(getHealth() < 0) {
                    decreaseLife();
                    explode();                
                }
            }
            if(isExploding()) {
                Image newImage = getExplosion().getCurrentImage();
                if(newImage != null) setImage(newImage);
                else {
                    setExploding(false);
                    if(getLife() > 0) reset();
                    else setAlive(false);
                }
            } 
            friction = true;
        }   
    }
 
    /**
     * @Override
     * it Tank's Munition hit enemy, check enemy's health and life.
     * if < 0, needs to reset Tanks' state(back to start)
     */
    public void hit(CollidableObject another) {
        Tank enemy = (Tank)another;
        if(enemy.getHealth() < 0) {
            increaseScore();
            if(enemy.getLife() > 0) reset();
        }
    }
    
    private void checkFriction() {
        if(friction) {
            int currentSpeed = getSpeed();
            if(currentSpeed != 0) {
                    currentSpeed *= Constants.FRICTION;
                    setSpeed(currentSpeed);
            }
        }   
    }
    
    private void moveForward() {
        friction = false;
        int currentSpeed = getSpeed();
        if(currentSpeed == 0) currentSpeed = 4;
        if(currentSpeed < Constants.MAX_SPEED) {
            setSpeed(++currentSpeed);
        }
    }
    
    private void moveBackward() {
        friction = false;
        int currentSpeed = getSpeed();
        if(currentSpeed == 0) currentSpeed = -4;
        if(currentSpeed > Constants.MIN_SPEED) {
            setSpeed(--currentSpeed);
        }
    }
    
    public void update(Observable o, Object o1) {
        GameEvents gameEvents = (GameEvents)o;
        if(o1 instanceof KeyEvent) {
            KeyEvent key = (KeyEvent)o1;
            int keyValue = key.getKeyCode();
            int control = getControl();
            int[] keyControl = getKeyControl();
            boolean[] activeKey = getActiveKey();
            for(int i = 0 ; i < keyControl.length; i++) {
                if(keyValue == keyControl[i]) {
                    activeKey[i] = true;
                    switch(i) {
                        case 0: fire(getMunitionUsageList().get(0)); break;
                        case 1: fire(currentSecondaryWeapon); break;
                        case 2: moveForward(); break;
                        case 3: moveBackward(); break;
                        case 4: increaseDirection(); break;
                        case 5: decreaseDirection(); break;
                    }
                    activeKey[i] = false;
                }    
            }
        }
    }

    public int getxCenter() {
        return xCenter;
    }

    public int getyCenter() {
        return yCenter;
    }
}
