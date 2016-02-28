
package Weapon;

import CollidableObject.CollidableObject;
import Helper.GameEvents;
import Munitions.Munitions;
import ResourceManagement.Property;
import java.util.Observable;
import java.util.Observer;


/**
 *
 * @author ASUS
 */
public abstract class SmartWeapon extends Weapon implements Observer {
    
    private int life;
    private final int HEALTH;
    private int health;
    private int score = 0;
    private int[] keyControl;
    private boolean[] activeKey;
    private boolean move;
    private int control = -1;

    public SmartWeapon(int x, int y, Property property, GameEvents gameEvents) {
        super(x, y, property, gameEvents);
        this.life = property.getLife();
        HEALTH = property.getHealth();
        health = HEALTH;
        keyControl = property.getKeyControl();
        initActiveKey();
    }

    private void initActiveKey() {
        int size = keyControl.length;
        activeKey = new boolean[size];
        for(int i = 0; i < size; i++) {
            activeKey[i] = false;
        }
    }
    
    @Override
    public abstract void collide(CollidableObject another);
    public abstract void hit(CollidableObject another);
    
    public void collideWithMunition(CollidableObject another) {
        Munitions munition = (Munitions)another;
        setHealth(getHealth() - munition.getDamageOther());
    }

    
    @Override
    public abstract void update(Observable o, Object o1);

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getLife() {
        return life;
    }

    public void decreaseLife() {
        life--;
    }
    
    public void increaseScore() {
        score++;
    }
    
    public int getScore() {
        return score;
    }

    public int getHEALTH() {
        return HEALTH;
    }

    public int[] getKeyControl() {
        return keyControl;
    }

    public boolean isMove() {
        return move;
    }

    public int getControl() {
        return control;
    }

    public boolean[] getActiveKey() {
        return activeKey;
    }
    
    
    
}
