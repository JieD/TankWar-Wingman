
package Weapon;

import CollidableObject.CollidableObject;
import GameEvents.GameEvents;
import Helper.Explosion;
import Helper.MunitionUsage;
import Helper.Property;
import java.awt.Image;
import java.util.ArrayList;

/**
 * DumpWeapon is the weapon that can only shoot at a fixed rate. When its bullets
 * hit enemy, it does nothing. When it collides with enemy, it explodes.
 * Its instances: EnemyPlaneShoot and EnemyPlaneTarget.
 */
public class DumpWeapon extends Weapon {

    public DumpWeapon(int x, int y, int speed, Property property, Image image, 
            Explosion explosion, GameEvents gameEvents) {
        super(x, y, speed, property, image, explosion, gameEvents);
    }

    @Override
    public void collide(CollidableObject another) {
        explode();
    }

    @Override
    public void hit(CollidableObject object) {
        // do nothing
    }

    @Override
    public void update(int width, int height) {
        super.update(width, height);
        fire();
    }
    
    
    
}
