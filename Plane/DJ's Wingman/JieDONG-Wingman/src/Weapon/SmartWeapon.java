
package Weapon;

import CollidableObject.CollidableObject;
import GameEvents.GameEvents;
import Helper.Explosion;
import Helper.MunitionUsage;
import Helper.Property;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;
import wingman.Wingman;

/**
 *
 * @author ASUS
 */
public class SmartWeapon extends Weapon implements Observer {
    
    private int life;
    private final int HEALTH;
    private int health;
    private int score;
    private int[] keyControl;
    private boolean move;
    private int control = -1;

    public SmartWeapon(int x, int y, int speed, Property property, Image image, 
            Explosion explosion, GameEvents gameEvents) {
        super(x, y, speed, property, image, explosion, gameEvents);
        this.life = property.getLife();
        HEALTH = property.getHealth();
        health = HEALTH;
        score = 0;
        keyControl = property.getKeyControl();
    }

    @Override
    public void collide(CollidableObject another) {
        health -= another.getDamageOther();
        if(another.getName() == "PowerImage") {
            for(MunitionUsage munition: getMunitionUsageList()) {
                munition.setEquipped(true);
                munition.setStorage(munition.getInitialStorage());
            }
        }
        //System.out.println(getName() + "'s health: " + health);
    }

    @Override
    public void hit(CollidableObject another) {
        score += another.getReward();
        //System.out.println(getName() + " hits " + another.getName() + ", score: " + score);
    }
    
    @Override
    public void update(Observable o, Object o1) {
        GameEvents gameEvents = (GameEvents)o;
        if(o1 instanceof KeyEvent) {
            KeyEvent key = (KeyEvent)o1;
            int keyValue = key.getKeyCode();
            //System.out.println(keyValue);
            for(int i = 0 ; i < keyControl.length; i++) {
                if(keyValue == keyControl[i]) {
                    control = i;
                    //System.out.println(getName() + " control: " + control);
                }    
            }
            if(control != -1) {
                if(control == 4) {
                    fire(); 
                } else {
                    move = true;
                }
            }
        }
    }
    
    @Override
    public void update(int width, int height) {
        super.update(width, height);
        if(health < 0) {
            life--;
            if(life > 0) health = HEALTH;
            else {
                explode();
            }
        }
        if(move) {
            int imageWidth = getSizeX();
            int imageHeight = getSizeY();
            switch(control) {
                case 0: 
                    int right = getX() + imageWidth;
                    if ((right + 2 * Wingman.SPEED) >= width) {
                        setX(width - imageWidth);
                    } else {
                        setX(getX() + 2 * Wingman.SPEED);
                    }
                    //System.out.println("move left");
                    break;
                case 1: 
                    if(getY() - 2 * Wingman.SPEED <= 0) {
                        setY(0);
                    } else {
                        setY(getY() - 2 * Wingman.SPEED);
                    }
                    //System.out.println("move up");
                    break;
                case 2: 
                    if (getX() - 2 * Wingman.SPEED <= 0) {
                        setX(0);
                    } else {
                        setX(getX() - 2 * Wingman.SPEED);
                    }
                    //System.out.println("move right");    
                    break;
                case 3: 
                    int bottom = getY() + imageHeight;
                    if((bottom + 2 * Wingman.SPEED) > height) {
                        setY(height - imageHeight);
                    } else {
                        setY(getY() + 2 * Wingman.SPEED);;
                    }
                    //System.out.println("move down");
                    break;
            }
        }
        move = false;
        control = -1;
    }

    public int getHealth() {
        return health;
    }

    public int getScore() {
        return score;
    }
    
    
}
