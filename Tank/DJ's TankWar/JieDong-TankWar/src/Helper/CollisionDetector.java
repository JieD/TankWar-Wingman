
package Helper;

import CollidableObject.CollidableObject;
import Weapon.SmartWeapon;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.ListIterator;
import tankwar1.TankWar;

/**
 *
 * @author ASUS
 */
public class CollisionDetector{
    private ArrayList<CollidableObject> list; 
    
    public CollisionDetector(ArrayList<CollidableObject> list) {
        this.list = list;
    }
    
    /**
     * update list to remove dead objects.
     * Need to use iterator to remove objects due to ConcurrentModificationException.
     * e.g. list.remove(object); -- throws ConcurrentModificationException
     */
    public void update() {
        ListIterator it = list.listIterator();
        while(it.hasNext()) {
            CollidableObject object = (CollidableObject)it.next();
            if(!object.isAlive()) {
                if(object instanceof SmartWeapon) {
                    TankWar.gameOver = true;
                }
                it.remove();
            }    
        }
    }
    
    public void checkCollision(ImageObserver observer) {
        for(int i = 0; i < list.size(); i++) {
            for(int j = i + 1; j < list.size(); j++) {
                CollidableObject me = list.get(i);
                CollidableObject other = list.get(j);
                if(!me.isExploding() && !other.isExploding() && me.intersects(other, observer)) {
                    /** System.out.println(me.getName() + " (" + me.getX() + ", " + me.getY() + ")" +
                            " collides with " + other.getName() + " (" + other.getX() + ", " 
                            + other.getY() + ")"); */
                    me.collide(other);
                    other.collide(me);
                }
            }
        }
    }
}
