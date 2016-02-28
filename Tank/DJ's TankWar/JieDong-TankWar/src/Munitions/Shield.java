
package Munitions;

import CollidableObject.CollidableObject;
import ResourceManagement.Property;
import Weapon.Weapon;

/**
 * Shield protects its owner from other Munitions. When Munitions collide with
 * Shield, they explode and owner does not get damaged.
 * Shield has the same position as owner.
 */
public class Shield extends Munitions{
    
    public Shield(Property property, Weapon owner) {
        super(property, owner);
        if(owner.getName().equalsIgnoreCase("Tank1")) setImage(getImageList()[0]);
        else setImage(getImageList()[1]);
    }

    @Override
    public void update(int width, int height) {
        super.update(width, height);
        Weapon owner = getOwner();
        setX(owner.getX());
        setY(owner.getY());
    } 

    @Override
    public void collide(CollidableObject another) {}
    
    
}
