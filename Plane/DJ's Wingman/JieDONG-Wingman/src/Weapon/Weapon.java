
package Weapon;

import CollidableObject.CollidableObject;
import GameEvents.GameEvents;
import Helper.Explosion;
import Helper.MunitionUsage;
import Helper.MunitionUsageProperty;
import Helper.Property;
import java.awt.Image;
import java.util.ArrayList;

/**
 * Weapon represents a CollidableObject that can shoot. 
 * 
 */
public abstract class Weapon extends CollidableObject {
    private ArrayList<MunitionUsage> munitionUsageList = new ArrayList();
    private GameEvents gameEvents;
    private MunitionUsage currentMunition;

    public Weapon(int x, int y, int speed, Property property, Image image, 
            Explosion explosion, GameEvents gameEvents) {
        super(x, y, speed, property, image, explosion);
        ArrayList<MunitionUsageProperty> propertyList = property.getMunitionUsagePropertyList();
        for(MunitionUsageProperty munitionUsageproperty: propertyList) {
            munitionUsageList.add(new MunitionUsage(munitionUsageproperty));
        }
        this.gameEvents = gameEvents;
    }
      
    public void fire() {
        for(MunitionUsage munition: munitionUsageList) {
            if (munition.isAvailable()) {
                fire(munition);
                munition.decreaseStorage(1);
            }
        }
    }
    
    public void fire(MunitionUsage munition) {
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

    public abstract void collide(CollidableObject another);
    public abstract void hit(CollidableObject enemy);
    
    public String getCurrentMunitionName() {
        return currentMunition.getMunitionName();
    }

    public ArrayList<MunitionUsage> getMunitionUsageList() {
        return munitionUsageList;
    }
    
    
    
}
