
package GameEvents;

import CollidableObject.CollidableObject;
import Weapon.SmartWeapon;
import java.awt.image.ImageObserver;
import java.util.ArrayList;
import java.util.ListIterator;
import wingman.Wingman;

/**
 *
 * @author ASUS
 */
public class CollisionDetector{
    private ArrayList<CollidableObject> list = new ArrayList(); 
    
    /**
     * update list to remove dead objects.
     * Need to use iterator to remove objects due to ConcurrentModificationException.
     */
    public void update() {
        ListIterator it = list.listIterator();
        while(it.hasNext()) {
            CollidableObject object = (CollidableObject)it.next();
            if(!object.isAlive()) {
                if(object instanceof SmartWeapon) Wingman.gameOver = true;
                it.remove();
            }
                
                //list.remove(object); -- throws ConcurrentModificationException
        }
    }
    
    public void checkCollision(ImageObserver observer) {
        for(int i = 0; i < list.size(); i++) {
            for(int j = i; j < list.size(); j++) {
                CollidableObject me = list.get(i);
                CollidableObject other = list.get(j);
                if(!me.isExploding() && !other.isExploding() && me.intersects(other, observer)) {
                    //System.out.println(me.getName() + " collides with " + other.getName());
                    me.collide(other);
                    other.collide(me);
                }
            }
        }
    }

    public ArrayList<CollidableObject> getList() {
        return list;
    }
    
    public void add(CollidableObject newObject) {
        list.add(newObject);
    }
    
    public void remove(CollidableObject oldObject) {
        list.remove(oldObject);
    }
    
}
