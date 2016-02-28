
package ResourceManagement;

import Helper.Constants;
import Helper.MunitionUsage;
import java.applet.AudioClip;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ASUS
 */
public class ResourceTable {
    
    private ResourceLoader loader;
    private String header = "Resources/";
    private HashMap<String, Property> table = new HashMap();
    private HashMap<String, MunitionUsage> munitionUsageTable = new HashMap();
    private Explosion explosionSmall;
    private Explosion explosionLarge;
    
    private Property permanentWallProperty;
    private Property destructibleWallProperty;
    private Property pickupProperty;
    private Property shellProperty;
    private Property rocketProperty;
    private Property bouncingBombProperty;
    private Property shieldProperty;
    
    private Property tankProperty1;
    private Property tankProperty2;
    
    public ResourceTable(ResourceLoader loader) {
        this.loader = loader;
    }
    public void init() {
        initExplosion();
        initMunitionUsageTable();
        initProperty();
        initTable();
    }
    
    private void initExplosion() {
        Image[] explosionSmallSpriteList = loader.loadImage(header + "Explosion_small_strip6.png");
        Image[] explosionLargeSpriteList = loader.loadImage(header + "Explosion_large_strip7.png");
        AudioClip explosionSmallSound = loader.loadSound(header + "Explosion_small.wav");
        AudioClip explosionLargeSound = loader.loadSound(header + "Explosion_large.wav");
        explosionSmall = new Explosion(explosionSmallSpriteList, explosionSmallSound);
        explosionLarge = new Explosion(explosionLargeSpriteList, explosionLargeSound);
    }
    
    private void initMunitionUsageTable() {
        Image[] munitionSignList = loader.loadImage(header + "Weapon_strip3.png");
        MunitionUsage rocketUsage = new MunitionUsage("Rocket", munitionSignList[0], 
                Constants.SHOOT_DELAY, 10);
        MunitionUsage bouncingBombUsage = new MunitionUsage("BouncingBomb", 
                munitionSignList[1], Constants.SHOOT_DELAY, 10);
        MunitionUsage shieldUsage = new MunitionUsage("Shield", munitionSignList[2], 
                Constants.SHOOT_DELAY, 10);
        MunitionUsage toolboxUsage = new MunitionUsage("Toolbox");
        munitionUsageTable.put("Rocket", rocketUsage);
        munitionUsageTable.put("BouncingBomb", bouncingBombUsage);
        munitionUsageTable.put("Shield", shieldUsage);
        munitionUsageTable.put("Toolbox", toolboxUsage);
    }
    
    //String name, int speed, int direction, Image[] imageList, Explosion explosion
    private void initProperty() {
        permanentWallProperty = new Property("PermanentWall", 0, 0, 
                loader.loadImage(header + "Wall1.png"), null);
        destructibleWallProperty = new Property("DestructibleWall", 0, 0, 
                loader.loadImage(header + "Wall2.png"), null);
        pickupProperty = new Property("Pickup", 0, 0, 
                loader.loadImage(header + "Pickup_strip4.png"), null, munitionUsageTable);
        shellProperty = new Property("Shell", Constants.SHELL_SPEED, -1, 
                loader.loadImage(header + "Shell_strip60.png"), explosionSmall, 
                10, Constants.SHELL_LIFE_SPAN);
        rocketProperty = new Property("Rocket", Constants.ROCKET_SPEED, -1, 
                loader.loadImage(header + "Rocket_strip60.png"), explosionSmall, 
                10, Constants.ROCKET_LIFE_SPAN);
        bouncingBombProperty = new Property("BouncingBomb", Constants.BOUNCING_BOMB_SPEED, 
                -1, loader.loadImage(header + "Bouncing_strip60.png"), 
                explosionSmall, 10, Constants.BOUNCING_BOMB_LIFE_SPAN);
        
        Image imgShield1 = loader.loadSingleImage(header + "Shield1.png");
        Image imgShiled2 = loader.loadSingleImage(header + "Shield2.png");
        Image[] imgShield = {imgShield1, imgShiled2};
        shieldProperty = new Property("Shield", 0, -1, imgShield, null, 0, 
                Constants.SHIELD_LIFE_SPAN);
        
        MunitionUsage shellUsage = new MunitionUsage("Shell", Constants.SHOOT_DELAY);
        ArrayList<MunitionUsage> tankMunitionUsageList1 = new ArrayList();
        tankMunitionUsageList1.add(shellUsage);
        ArrayList<MunitionUsage> tankMunitionUsageList2 = 
                (ArrayList<MunitionUsage>) tankMunitionUsageList1.clone();
        int[] tankKeyControl2 = {KeyEvent.VK_ENTER, KeyEvent.VK_DELETE, 
            KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT,  KeyEvent.VK_RIGHT};
        int[] tankKeyControl1 = {KeyEvent.VK_SPACE, KeyEvent.VK_CONTROL, 
            KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A,  KeyEvent.VK_D};
        tankProperty1 = new Property("Tank1", 0, 0, 
                loader.loadImage(header + "Tank1_strip60.png"), explosionLarge, 
                tankMunitionUsageList1, "Tank2", 3, 100, tankKeyControl1);
        tankProperty2 = new Property("Tank2", 0, 0, 
                loader.loadImage(header + "Tank2_strip60.png"), explosionLarge, 
                tankMunitionUsageList2, "Tank1", 3, 100, tankKeyControl2);
    }
    
    private void initTable() {
        table.put("PermanentWall", permanentWallProperty);
        table.put("DestructibleWall", destructibleWallProperty);
        table.put("Pickup", pickupProperty);
        table.put("Shell", shellProperty);
        table.put("Rocket", rocketProperty);
        table.put("BouncingBomb", bouncingBombProperty);
        table.put("Shield", shieldProperty);
        table.put("Tank1", tankProperty1);
        table.put("Tank2", tankProperty2);
    }
    
    public Property getProperty(String name) {
        return table.get(name);
    }
}
