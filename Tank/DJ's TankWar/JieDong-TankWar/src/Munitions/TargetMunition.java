
package Munitions;

import ResourceManagement.Property;
import Weapon.Weapon;

/**
 * TargetMunition can change its direction according to its target.
 */
public class TargetMunition extends Munitions {
    private Weapon target;
    
    public TargetMunition (Property property, Weapon owner, Weapon target) {
        super(property, owner);
        this.target = target;     
    }

    @Override
    public void update(int width, int height) {
        if (target != null) {
            int targetX = target.getX();
            int targetY = target.getY();
            setDirection((int) Math.toDegrees(Math.atan2(getY() - targetY, targetX - getX())));
        }        
        super.update(width, height);
    }
    
    
}
