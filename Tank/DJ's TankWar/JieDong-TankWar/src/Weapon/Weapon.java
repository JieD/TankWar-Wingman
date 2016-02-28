
package Weapon;

import CollidableObject.CollidableObject;
import Helper.GameEvents;
import Helper.MunitionUsage;
import ResourceManagement.Property;
import java.util.ArrayList;

/**
 * Weapon represents a CollidableObject that can shoot. 
 * When needs to shoot, check whether the Munition is available. The availability
 * depends on the storage and delay. MunitionUsage is used to here to control.
 */
public abstract class Weapon extends CollidableObject {
    private ArrayList<MunitionUsage> munitionUsageList = new ArrayList();
    private String enemy;
    private GameEvents gameEvents;
    private MunitionUsage currentMunition;

    public Weapon(int x, int y, Property property, GameEvents gameEvents) {
        super(x, y, property);
        enemy = property.getEnemy();
        munitionUsageList = property.getMunitionUsageList();
        this.gameEvents = gameEvents;
    }
    
    public void fire(MunitionUsage munition) {
        if ((munition != null) && munition.isAvailable()) {
            munition.decreaseStorage(1);
            long currentTime = System.currentTimeMillis();
            if ((currentTime - munition.getLastFireTime()) >= munition.getDelay()) {
                currentMunition = munition;
                //System.out.println("(" + getX() + "," + getY() + ") " + "shoots " + currentMunition.getMunitionName());
                //System.out.println("storage " + currentMunition.getStorage());
                //System.out.println(getName() + " fire!");
                gameEvents.setValue(this);
                munition.setLastFireTime(System.currentTimeMillis());
            }       
        } 
    }

    public abstract void collide(CollidableObject another);
    public abstract void hit(CollidableObject enemy);
    
    public String getCurrentMunitionName() {
        return currentMunition.getMunitionName();
    }

    public ArrayList<MunitionUsage> getMunitionUsageList() {
        return munitionUsageList;
    }

    public String getEnemy() {
        return enemy;
    }
}
