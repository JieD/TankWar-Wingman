
package CollidableObject;

import ResourceManagement.Property;

/**
 * PermanentWall does not move or explode. It will collide with other objects. 
 * However, it does not react to any collision.
 */
public class PermanentWall extends CollidableObject {
    
    public PermanentWall (int x, int y, Property property) { 
        super(x, y, property);  
    }

    @Override
    public void collide(CollidableObject other) {}  
}
