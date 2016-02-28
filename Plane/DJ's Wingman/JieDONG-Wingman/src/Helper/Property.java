
package Helper;

import java.util.ArrayList;

/**
 *
 * 
 */
public class Property {
    private String name;
    private int damageOther;
    private int reward;
    private int direction;
    private String spriteName;
    private ArrayList<String> explosionSpriteNameList;
    private String explosionSoundName;
    private ArrayList<String> enemyList;
    private ArrayList<MunitionUsageProperty> munitionUsagePropertyList;
    private int life;
    private int health;
    private int[] keyControl;

    // For BasicObject
    public Property(String name, int direction, String spriteName) {
        this.name = name;
        this.direction = direction;
        this.spriteName = spriteName;
    }
    
    // For Munition 
    public Property(String name, int damageOther, int reward, int direction, String spriteName, 
            ArrayList<String> explosionSpriteNameList, String explosionSoundName, 
            ArrayList<String> enemyList) {
        this.name = name;
        this.damageOther = damageOther;
        this.reward = reward;
        this.direction = direction;
        this.spriteName = spriteName;
        this.explosionSpriteNameList = explosionSpriteNameList;
        this.explosionSoundName = explosionSoundName;
        this.enemyList = enemyList;
    }
    
    // for Weapon
    public Property(String name, int damageOther, int reward, int direction, 
            String spriteName, ArrayList<String> explosionSpriteNameList, 
            String explosionSoundName, ArrayList<String> enemyList, 
            ArrayList<MunitionUsageProperty> munitionUsagePropertyList) {
        this(name, damageOther, reward, direction, spriteName, explosionSpriteNameList, 
                explosionSoundName, enemyList);
        this.munitionUsagePropertyList = munitionUsagePropertyList;
    }
    
    // for SmartWeapon
    public Property(String name, int damageOther, int reward, int life, int health,
            int direction, String spriteName, ArrayList<String> explosionSpriteNameList, 
            String explosionSoundName, ArrayList<String> enemyList, 
            ArrayList<MunitionUsageProperty> munitionUsagePropertyList,
            int[] keyControl) {
        this(name, damageOther, reward, direction, spriteName, explosionSpriteNameList,
                explosionSoundName, enemyList, munitionUsagePropertyList);
        this.life = life;
        this.health = health;
        this.keyControl = keyControl;
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

    public String getSpriteName() {
        return spriteName;
    }

    public ArrayList<String> getExplosionSpriteNameList() {
        return explosionSpriteNameList;
    }

    public String getExplosionSoundName() {
        return explosionSoundName;
    }

    public ArrayList<String> getEnemyList() {
        return enemyList;
    }

    public ArrayList<MunitionUsageProperty> getMunitionUsagePropertyList() {
        return munitionUsagePropertyList;
    }

    public int getLife() {
        return life;
    }

    public int getHealth() {
        return health;
    }

    public int getReward() {
        return reward;
    } 

    public int[] getKeyControl() {
        return keyControl;
    }
}
