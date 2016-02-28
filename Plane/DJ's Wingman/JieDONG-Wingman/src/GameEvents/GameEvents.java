
package GameEvents;

import Weapon.Weapon;
import java.awt.event.KeyEvent;
import java.util.Observable;

/**
 *
 * @author ASUS
 */
public class GameEvents extends Observable {
       int type;
       Object event;
       
   public void setValue(KeyEvent key) {
          type = 1; // let's assume this mean key input. Should use CONSTANT value for this
          event = key;
          setChanged();
         // trigger notification
          //System.out.println("KeyEvent: " + key.getClass().getName());
          notifyObservers(key);     
   }

    public void setValue(Weapon weapon) {
       type = 2; 
       event = weapon;
       setChanged();
      // trigger notification
       //System.out.println("FireEvent: " + weapon.getName());
       notifyObservers(weapon); 
    }

    public int getType() {
        return type;
    }

    public Object getEvent() {
        return event;
    }
    
}