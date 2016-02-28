
package CollidableObject;

import Helper.Explosion;
import Helper.Property;
import Weapon.Weapon;
import java.awt.Image;
import wingman.Wingman;

/**
 * Munitions represents any CollidableObject that can only hurt its enemies by 
 * collision. Munitions cannot fire bullet.
 * If it collides with its enemy, MyPlane and MyBullet, it explodes. 
 * It is created with 3 constructor:
 * 1) created by the programmer (speed and direction manually passed in)
 * e.g: EnemyPlaneDown, EnemyPlaneUp, EnemyPlaneLeft, EnemyPlaneRight
 * 2) created by a Weapon(direction manually passed in, speed is twice of Weapon's)
 * e.g: EnemyBullet, MyBulletUp, MyBulletLeft, MyBulletRight
 */
public class Munitions extends CollidableObject {
    private Weapon owner = null;

    // created by the programmer
    public Munitions(int x, int y, int speed, Property property, Image image,
            Explosion explosion) {
        super(x, y, speed, property, image, explosion);
    }
    
    // created by a Weapon
    public Munitions(Property property, Image image, Explosion explosion, Weapon owner) {
        this(owner.getX(), owner.getY(), 2 * owner.getSpeed(), property, image, 
                explosion);
        if (property.getDirection() < 0) setDirection(owner.getDirection());
        if(owner.getSpeed() == 0) setSpeed(2 * Wingman.SPEED);
        this.owner = owner;
    }

    @Override
    public void collide(CollidableObject another) {
        if(owner != null) {
            //System.out.println(getName() + ": " + getEnemyList());
            //System.out.println(getName() + " hits " + another.getName());
            owner.hit(another);
        } else {
            //System.out.println(getName() + " hits " + another.getName());
        }
        explode();
    }

    public Weapon getOwner() {
        return owner;
    }
}
