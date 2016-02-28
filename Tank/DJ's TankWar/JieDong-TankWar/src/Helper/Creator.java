
package Helper;

import CollidableObject.CollidableObject;
import CollidableObject.DestructibleWall;
import CollidableObject.PermanentWall;
import CollidableObject.Pickup;
import Munitions.Munitions;
import Munitions.TargetMunition;
import ResourceManagement.ResourceTable;
import Weapon.Tank;
import Weapon.Weapon;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.Scanner;

/**
 * Creator creates all the gaming components for TankWar game. It is created to
 * decrease responsibilities of TankWar class.
 * It reads the layoutFile to set up the initial point of TankWar.
 * 
 */
public class Creator implements Observer {
    private String LayoutFileName;
    private ArrayList<CollidableObject> objectList;
    private ResourceTable resourceTable;
    private GameEvents gameEvents;
    
    public Creator(String fileName, ArrayList<CollidableObject> objectList, 
            ResourceTable resourceTable, GameEvents gameEvents) {
        this.LayoutFileName = fileName;
        this.objectList = objectList;
        this.resourceTable = resourceTable;
        this.gameEvents = gameEvents;
    }
    
    /** */
    public void initTankWar() {
        Scanner fileScanner = openFile();
        start(fileScanner);
    }
    
    private Scanner openFile() {
        Scanner fileScanner = null;
        System.out.println("User's current working directory: " + System.getProperty("user.dir"));
        try {
            fileScanner = new Scanner(new File(LayoutFileName));
        } catch (FileNotFoundException ex) {
            System.out.println("File not found");
        }
        return fileScanner;
    }
    
    /**
     * Scan the file to create the gaming components at according position
     * every new line - x = 0; y += height;
     * every new read - x += width; y keeps the same
     */
    private void start(Scanner fileScanner) {
        // (x, y) - next position
        int x = 0;
        int y = 0;
        
        // (width, height) - area of previously created gaming component
        int width = 0;
        int height = 0;
        
        String name = null;
        CollidableObject gamingComponent = null;
        
        while(fileScanner.hasNextLine()) {
            //System.out.println();  // for debugging
            Scanner lineScanner = new Scanner(fileScanner.nextLine());
            x = 0;
            y += height;
            
            while(lineScanner.hasNext()) {
                int type = lineScanner.nextInt();
                //System.out.print(type);  // for debugging
                
                switch (type) {
                    case 1: name = "PermanentWall"; break;
                    case 2: name = "DestructibleWall"; break;
                    case 3: name = "Tank1"; break;
                    case 4: name = "Tank2"; break;  
                    case 5: name = "Pickup"; break;
                    /** if type is 0, no need to create a component.
                      * also reset name to wipe out previous value
                      * Note: must need!
                      */
                    default: name = null;  
                }
                if(name != null) {
                    gamingComponent = create(name, x, y);
                    width = gamingComponent.getSizeX();
                    height = gamingComponent.getSizeY(); 
                }
                x += width;
            }           
        }
    }
           
    /** Create a gaming component according to the name and position
     *  Add the newly created object into objectList
     */
    private CollidableObject create(String name, int x, int y) {
        CollidableObject gamingComponent = null;
        switch(name) {
            case "PermanentWall":
                gamingComponent = new PermanentWall(x, y, resourceTable.getProperty("PermanentWall"));
                break;
            case "DestructibleWall":
                gamingComponent = new DestructibleWall(x, y, resourceTable.getProperty("DestructibleWall"));
                break;
            case "Tank1": 
                gamingComponent = new Tank(x, y, resourceTable.getProperty("Tank1"), gameEvents);
                gameEvents.addObserver((Tank)gamingComponent);
                break;
            case "Tank2": 
                gamingComponent = new Tank(x, y, resourceTable.getProperty("Tank2"), gameEvents);
                gameEvents.addObserver((Tank)gamingComponent);
                break;
            case "Pickup": 
                gamingComponent = new Pickup(x, y, resourceTable.getProperty("Pickup"));
                break;
        }
        //System.out.println("new " + name + " at: (" + x + ", " + y + ")");
        if (gamingComponent != null) objectList.add(gamingComponent);
        return gamingComponent;
    }
    
    @Override
    public void update(Observable o, Object o1) {
        GameEvents gameEvents = (GameEvents)o;
        if(o1 instanceof Weapon) {
            Weapon owner = (Weapon)o1;
            String munitionName = owner.getCurrentMunitionName();
            CollidableObject gamingComponent = null;
            switch(munitionName) {
                case "Shell":   
                    gamingComponent = new Munitions(resourceTable.getProperty("Shell"), owner);
                    break;
                case "Rocket":   
                    Weapon enemy = null;
                    String enemyName = owner.getEnemy();
                    for(CollidableObject object: objectList) {
                        if(object.getName().equalsIgnoreCase(enemyName)) {
                            enemy = (Weapon)object;
                        }
                    }
                    gamingComponent = new TargetMunition(resourceTable.getProperty("Rocket"), owner, enemy);
                    break;
                case "BouncingBomb":  
                    gamingComponent = new Munitions(resourceTable.getProperty("BouncingBomb"), owner);
                    break;
                case "Shield": 
                    gamingComponent = new Munitions(resourceTable.getProperty("Shield"), owner);
                    break;
            }
            if (gamingComponent != null) objectList.add(gamingComponent);
        }
    }
}
