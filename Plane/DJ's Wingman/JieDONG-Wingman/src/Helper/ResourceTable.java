
package Helper;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ASUS
 */
public class ResourceTable {
    private String header = "Resources/";
    private HashMap<String, Property> table = new HashMap();
    
    private ArrayList<String> explosionSpriteNameList1;
    private ArrayList<String> explosionSpriteNameList2;
    private ArrayList<String> enemies;
    private ArrayList<String> allies;
    private ArrayList<String> enemyBulletEnemyList;
    private ArrayList<String> myBulletEnemyList;
    private ArrayList<String> powerImageEnemyList;
    private ArrayList<MunitionUsageProperty> myPlaneMunition;
    private ArrayList<MunitionUsageProperty> enemyShootMunition;
    private String explosionSoundName1 = header + "snd_explosion1.wav";
    private String explosionSoundName2 = header + "snd_explosion2.wav";
    private int[] myPlaneKeyControl1 = {KeyEvent.VK_RIGHT, KeyEvent.VK_UP, KeyEvent.VK_LEFT, 
        KeyEvent.VK_DOWN, KeyEvent.VK_ENTER};
    private int[] myPlaneKeyControl2 = {KeyEvent.VK_D, KeyEvent.VK_W, KeyEvent.VK_A,  
            KeyEvent.VK_S, KeyEvent.VK_SPACE};
    
    private Property enemyPlaneDownProperty;
    private Property enemyPlaneUpProperty;
    private Property enemyPlaneLeftProperty;
    private Property enemyPlaneRightProperty;
    
    private Property enemyBulletProperty;
    private Property myBulletUpProperty;
    private Property myBulletLeftProperty;
    private Property myBulletRightProperty;
    
    private Property enemyShootProperty;
    private Property myPlaneProperty1;
    private Property myPlaneProperty2;
    private Property powerImageProperty;
    
    public ResourceTable() {
        initExplosionSpriteList();
        initEnemyList();
        initMunition();
        initProperty();
        initTable();
    }
    
    private void initExplosionSpriteList() {
        explosionSpriteNameList1 = new ArrayList();
        explosionSpriteNameList2 = new ArrayList();
        explosionSpriteNameList1.add(header + "explosion1_1.png");
        explosionSpriteNameList1.add(header + "explosion1_2.png");
        explosionSpriteNameList1.add(header + "explosion1_3.png");
        explosionSpriteNameList1.add(header + "explosion1_4.png");
        explosionSpriteNameList1.add(header + "explosion1_5.png");
        explosionSpriteNameList1.add(header + "explosion1_6.png");
        explosionSpriteNameList2.add(header + "explosion2_1.png");
        explosionSpriteNameList2.add(header + "explosion2_2.png");
        explosionSpriteNameList2.add(header + "explosion2_3.png");
        explosionSpriteNameList2.add(header + "explosion2_4.png");
        explosionSpriteNameList2.add(header + "explosion2_5.png");
        explosionSpriteNameList2.add(header + "explosion2_6.png");
        explosionSpriteNameList2.add(header + "explosion2_7.png");
    }
    
    private void initEnemyList() {
        enemies = new ArrayList(); 
        enemies.add("EnemyPlaneDown");
        enemies.add("EnemyPlaneUp");
        enemies.add("EnemyPlaneLeft");
        enemies.add("EnemyPlaneRight");
        enemies.add("EnemyBullet");
        enemies.add("EnemyShoot");
        enemies.add("EnemyTarget");
        enemies.add("PowerImage");
        
        allies = new ArrayList();
        allies.add("MyPlane1");
        allies.add("MyPlane2");
        allies.add("MyBulletUp");
        allies.add("MyBulletLeft");
        allies.add("MyBulletRight");
        
        
        enemyBulletEnemyList = new ArrayList();
        enemyBulletEnemyList.add("MyPlane1");
        enemyBulletEnemyList.add("MyPlane2");
        
        myBulletEnemyList = (ArrayList<String>)enemies.clone();
        myBulletEnemyList.remove("EnemyBullet");
        myBulletEnemyList.remove("PowerImage");
        
        powerImageEnemyList = enemyBulletEnemyList;
    }
    
    private void initMunition() {
        enemyShootMunition = new ArrayList();
        myPlaneMunition = new ArrayList();
        MunitionUsageProperty enemyBullet = new MunitionUsageProperty("EnemyBullet", 500, true, false);
        MunitionUsageProperty myBulletUp = new MunitionUsageProperty("MyBulletUp", 500, true, false);
        MunitionUsageProperty myBulletLeft = new MunitionUsageProperty("MyBulletLeft", 500, false, true, 50);
        MunitionUsageProperty myBulletRight = new MunitionUsageProperty("MyBulletRight", 500, false, true, 50);
        enemyShootMunition.add(enemyBullet);
        myPlaneMunition.add(myBulletUp);
        myPlaneMunition.add(myBulletLeft);
        myPlaneMunition.add(myBulletRight);
    }
    
    private void initProperty() {
        enemyPlaneDownProperty = new Property("EnemyPlaneDown", 10, 10, 270,
                header + "enemy1_1.png", explosionSpriteNameList1, explosionSoundName1, 
                allies);
        enemyPlaneUpProperty = new Property("EnemyPlaneUp", 10, 10, 90,
                header + "enemy4_1.png", explosionSpriteNameList1, explosionSoundName1, 
                allies);
        enemyPlaneLeftProperty = new Property("EnemyPlaneLeft", 10, 10, 180,
                header + "enemy2_1.png", explosionSpriteNameList1, explosionSoundName1, 
                allies);
        enemyPlaneRightProperty = new Property("EnemyPlaneRight", 10, 10, 0,
                header + "enemy2_2.png", explosionSpriteNameList1, explosionSoundName1, 
                allies);
   
        enemyBulletProperty = new Property("EnemyBullet", 5, 0, -1, header + "enemybullet1.png", 
                null, explosionSoundName2, enemyBulletEnemyList);
        myBulletUpProperty = new Property("MyBulletUp", 0, 0, 90, header + "bullet.png", 
                null, null, myBulletEnemyList);
        myBulletLeftProperty = new Property("MyBulletLeft", 0, 0, 135, header + "bulletLeft.png", 
                null, null, myBulletEnemyList);
        myBulletRightProperty = new Property("MyBulletRight", 0, 0, 45, header + "bulletRight.png", 
                null, null, myBulletEnemyList);
    
        enemyShootProperty = new Property("EnemyShoot", 10, 10, 270, header + "enemy3_1.png", 
                explosionSpriteNameList1, explosionSoundName1, allies, enemyShootMunition);
        myPlaneProperty1 = new Property("MyPlane1", 0, 0, 3, 100, 90, header + "myplane_1.png", 
                explosionSpriteNameList2, explosionSoundName2, enemies, myPlaneMunition,
                myPlaneKeyControl1);
        myPlaneProperty2 = new Property("MyPlane2", 0, 0, 3, 100, 90, header + "myplane_2.png", 
                explosionSpriteNameList2, explosionSoundName2, enemies, myPlaneMunition,
                myPlaneKeyControl2);
        
        powerImageProperty = new Property("PowerImage", 0, 0, 270, header + "powerup.png", 
                null, null, powerImageEnemyList);
    }

    private void initTable() {      
        table.put("EnemyPlaneDown", enemyPlaneDownProperty);
        table.put("EnemyPlaneUp", enemyPlaneUpProperty);
        table.put("EnemyPlaneLeft", enemyPlaneLeftProperty);
        table.put("EnemyPlaneRight", enemyPlaneRightProperty);
        
        table.put("EnemyBullet", enemyBulletProperty);
        table.put("MyBulletUp", myBulletUpProperty);
        table.put("MyBulletLeft", myBulletLeftProperty);
        table.put("MyBulletRight", myBulletRightProperty);
        
        table.put("EnemyShoot", enemyShootProperty);
        table.put("MyPlane1", myPlaneProperty1);
        table.put("MyPlane2", myPlaneProperty2);
        table.put("PowerImage", powerImageProperty);
    }
    
    public Property getProperty(String name) {
        return table.get(name);
    }
}
