
package Helper;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author ASUS
 */
public class OldResourceTable {
    private String header = "Resources/";
    private HashMap<String, String> spriteTable = new HashMap();
    private HashMap<String, ArrayList<String>> explosionSpriteTable = new HashMap();
    private HashMap<String, String> explosionSoundTable = new HashMap();
    private HashMap<String, ArrayList<String>> enemyListTable = new HashMap();
    private HashMap<String, int[]> keyTable = new HashMap();
    private HashMap<String, ArrayList<MunitionUsage>> munitionTable = new HashMap();
    
    public OldResourceTable() {
        initSpriteTable();
        initExplosionSoundTable();
        initExplosionSpriteTable();
        initEnemyListTable();
        initKeyTable();
        //initMunitionTable();
    }
    
    private void initSpriteTable() {
        spriteTable.put("Sea", header + "water.png");
        spriteTable.put("Island1", header + "island1.png");
        spriteTable.put("Island2", header + "island2.png");
        spriteTable.put("Island3", header + "island3.png");
        spriteTable.put("EnemyPlaneDown", header + "enemy1_1.png");
        spriteTable.put("EnemyPlaneUp", header + "enemy4_1.png");
        spriteTable.put("EnemyPlaneLeft", header + "enemy2_1.png");
        spriteTable.put("EnemyPlaneRight", header + "enemy2_2.png");
        spriteTable.put("EnemyBullet", header + "enemybullet1.png");
        spriteTable.put("MyBulletUp", header + "bullet.png");
        spriteTable.put("MyBulletLeft", header + "bulletLeft.png");
        spriteTable.put("MyBulletRight", header + "bulletRight.png");
        spriteTable.put("MyPlane", header + "myplane1.png");
        spriteTable.put("EnemyPlaneShoot", header + "enemy3_1.png");
        spriteTable.put("EnemyPlaneTarget", header + "enemy2_1.png");
    }
    
    private void initExplosionSoundTable() {
        String explosionSoundName1 = header + "snd_explosion1.wav";
        String explosionSoundName2 = header + "snd_explosion2.wav";
        explosionSoundTable.put("EnemyPlane", explosionSoundName1);
        explosionSoundTable.put("MyPlane", explosionSoundName2);
        explosionSoundTable.put("EnemyBullet", explosionSoundName1);
    }
    
    private void initExplosionSpriteTable() {
        ArrayList<String> explosionSpriteNameList1 = new ArrayList();
        ArrayList<String> explosionSpriteNameList2 = new ArrayList();
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
        explosionSpriteTable.put("EnemyPlane", explosionSpriteNameList1);
        explosionSpriteTable.put("MyPlane", explosionSpriteNameList2);
    } 
    
    private void initEnemyListTable() {
        ArrayList<String> enemies = new ArrayList(); 
        enemies.add("EnemyPlane");
        enemies.add("EnemyBullet");
        enemies.add("EnemyPlaneShoot");
        enemies.add("EnemyPlaneTarget");
        
        ArrayList<String> allies = new ArrayList();
        allies.add("MyPlane");
        allies.add("MyBullet");
        
        ArrayList<String> enemyBulletEnemyList = new ArrayList();
        enemyBulletEnemyList.add("MyPlane");
        
        ArrayList<String> myBulletEnemyList = new ArrayList();
        myBulletEnemyList.add("EnemyPlane");
        myBulletEnemyList.add("EnemyPlaneShoot");
        myBulletEnemyList.add("EnemyPlaneTarget");
        
        enemyListTable.put("Enemy", allies);
        enemyListTable.put("Ally", enemies);
        enemyListTable.put("EnemyBullet", enemyBulletEnemyList);
        enemyListTable.put("MyBullet", myBulletEnemyList);
    }
    
    private void initKeyTable() {
        int[] myPlaneKey1 = {KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, 
            KeyEvent.VK_RIGHT, KeyEvent.VK_ENTER};
        int[] myPlaneKey2 = {KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, 
            KeyEvent.VK_D, KeyEvent.VK_SPACE};
        keyTable.put("MyPlane1", myPlaneKey1);
        keyTable.put("MyPlane2", myPlaneKey2);
    }
    
    /** private void initMunitionTable() {
        MunitionUsage myBulletUp = new MunitionUsage("MyBulletUp", 500);
        MunitionUsage myBulletLeft = new MunitionUsage("MyBulletLeft", 500);
        MunitionUsage myBulletRight = new MunitionUsage("MyBulletRight", 500);
        MunitionUsage enemyBullet = new MunitionUsage("EnemyBullet", 500);
        ArrayList<MunitionUsage> myPlaneMunition = new ArrayList();
        ArrayList<MunitionUsage> enemyPlaneShootMunition = new ArrayList();
        myPlaneMunition.add(myBulletUp);
        myPlaneMunition.add(myBulletLeft);
        myPlaneMunition.add(myBulletRight);
        enemyPlaneShootMunition.add(enemyBullet);
        munitionTable.put("MyPlane", myPlaneMunition);
        munitionTable.put("EnemyPlaneShoot", enemyPlaneShootMunition); 
    }*/
       
    public String getSpriteName(String className) {
        return spriteTable.get(className);
    } 

    public String getExplosionSoundName(String className) {
        return explosionSoundTable.get(className);
    }
    
    public ArrayList<String> getExplosionSpriteName(String className) {
        return explosionSpriteTable.get(className);
    }
        
    public ArrayList<String> getEnemyList(String className) {
        return enemyListTable.get(className);
    }
    
    public int[] getKey(String className) {
        return keyTable.get(className);
    }
    
    public ArrayList<MunitionUsage> getMunition(String className) {
        return munitionTable.get(className);
    }
}
