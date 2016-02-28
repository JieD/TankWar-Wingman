
package Munitions;

import CollidableObject.CollidableObject;
import ResourceManagement.Property;
import Weapon.Tank;
import Weapon.Weapon;

/**
 * Munitions represents any CollidableObject that can only hurt its enemies by 
 * collision. Munitions cannot fire bullets.
 * If Munition is fired by a Weapon, its initial position is the same as the Weapon.
 * If Munition has a lifeSpan, it will disappear if its lifetime expires.
 */
public class Munitions extends CollidableObject {
    private int damageOther;
    private int lifeSpan;
    private Weapon owner = null;
    private long lifeStartTime;

    // created by the programmer
    public Munitions(int x, int y, Property property) {
        super(x, y, property);
    }
    
    // created by a Weapon
    public Munitions(Property property, Weapon owner) {
        this(((Tank)owner).getxCenter(), ((Tank)owner).getyCenter(), property);
        if (property.getDirection() < 0) setDirection(owner.getDirection());
        //if(owner.getSpeed() == 0) setSpeed(2 * Wingman.SPEED);
        this.damageOther = property.getDamageOther();
        this.lifeSpan = property.getLifeSpan();
        this.owner = owner;
        lifeStartTime = System.currentTimeMillis();
    }
    
    @Override
    public void collide(CollidableObject another) {
        if(isEffectiveCollision(another)) {
            if((owner != null) && another instanceof Tank) owner.hit(another);
            explode();
        }
    }

    private boolean isEffectiveCollision(CollidableObject another) {
        String name = another.getName();
        boolean result = false;
        boolean test1 = name.equalsIgnoreCase("Pickup");
        boolean test2 = name.equalsIgnoreCase(owner.getName());
        result = !(test1 || test2);
        return result;
    }
    
    @Override
    public void update(int width, int height) {
        long currentTime = System.currentTimeMillis();
        if((currentTime - lifeStartTime) >= lifeSpan) setAlive(false);
        super.update(width, height);
    }

    public Weapon getOwner() {
        return owner;
    } 

    public int getDamageOther() {
        return damageOther;
    }
    
    
}
