/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Munitions;

import CollidableObject.CollidableObject;
import ResourceManagement.Property;
import Weapon.Weapon;

/**
 * BouncingMunition bounces back if colliding with Walls.
 * It will only explode if colliding with its enemies.
 */
public class BouncingMunition extends Munitions {
    
    public BouncingMunition(Property property, Weapon owner) {
        super(property, owner);
    }

    @Override
    public void collide(CollidableObject another) {
        
        super.collide(another);
    }
    
    private void checkBouncing(CollidableObject another) {
        String name = another.getName();
        if(name.equalsIgnoreCase("PermanentWall") || name.equalsIgnoreCase("DestructibleWall")) {}
            //bounce(another);
    }
    
    /** private void bounce(CollidableObject another) {
        int xLeft1 = getX();
        int xRight1 = xLift1 + 
        int y = getY();
        int oldDirection = getDirection();
        int x1 = another.getX();
        int x2 = x1 + another.getSizeX();
        int y1 = another.getY();
        int y2 = y1 + another.getSizeY();
        if ((x == x1) || (x == x2)) setDirection(540 - oldDirection);
        
    }*/
}
