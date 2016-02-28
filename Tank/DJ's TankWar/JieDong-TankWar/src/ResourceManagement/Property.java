
package ResourceManagement;

import Helper.MunitionUsage;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * 
 */
public class Property {
    private String name;
    private int speed;
    private int direction;
    private Image[] imageList;
    private Explosion explosion;
    
    private HashMap<String, MunitionUsage> munitionUsageTable;   
    private ArrayList<MunitionUsage> munitionUsageList;
    
    private int damageOther;
    private int lifeSpan;
    private int life;
    private int health;
    private int[] keyControl;
    private String enemy;
    
    // For BasicObject
    public Property(String name, int speed, int direction, Image[] imageList) {
        this.name = name;
        this.speed = speed;
        this.direction = direction;
        this.imageList = imageList;
    }
    
    // For CollidableObject
    public Property(String name, int speed, int direction, Image[] imageList, 
            Explosion explosion) {
        this(name, speed, direction, imageList);
        this.explosion = explosion;
    }
    
    // For Pickup
    public Property(String name, int speed, int direction, Image[] imageList, 
            Explosion explosion, HashMap<String, MunitionUsage> munitionUsageTable) {
        this(name, speed, direction, imageList, explosion);
        this.munitionUsageTable = munitionUsageTable;
    }
    
    // For Munition 
    public Property(String name, int speed, int direction, Image[] imageList, 
            Explosion explosion, int damageOther, int lifeSpan) {
        this(name, speed, direction, imageList, explosion);       
        this.damageOther = damageOther;
        this.lifeSpan = lifeSpan;
    }
    
    // For Weapon
    public Property(String name, int speed, int direction, Image[] imageList,  
            Explosion explosion, ArrayList<MunitionUsage> munitionUsageList, 
            String enemy) {
        this(name, speed, direction, imageList, explosion);
        this.munitionUsageList = munitionUsageList;
        this.enemy = enemy; 
    }
    
    // For SmartWeapon
    public Property(String name, int speed, int direction, Image[] imageList, 
            Explosion explosion, ArrayList<MunitionUsage> munitionUsageList,
            String enemy, int life, int health, int[] keyControl) {
        this(name, speed, direction, imageList, explosion, munitionUsageList, enemy);
        this.life = life;
        this.health = health;
        this.keyControl = keyControl;
    } 

    public boolean hasMultiImage() {
        return imageList != null;
    }
    
    public String getName() {
        return name;
    }

    public int getDamageOther() {
        return damageOther;
    }

    public int getDirection() {
        return direction;
    }

    public Image[] getImageList() {
        return imageList;
    }


    public Explosion getExplosion() {
        return explosion;
    }

    public int getSpeed() {
        return speed;
    }

    public int getLife() {
        return life;
    }

    public int getHealth() {
        return health;
    }

    public HashMap<String, MunitionUsage> getMunitionUsageTable() {
        return munitionUsageTable;
    }

    public int getLifeSpan() {
        return lifeSpan;
    }

    public int[] getKeyControl() {
        return keyControl;
    }

    public ArrayList<MunitionUsage> getMunitionUsageList() {
        return munitionUsageList;
    }

    public String getEnemy() {
        return enemy;
    }

    
    
    
}
