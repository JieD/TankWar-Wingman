/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.awt.Dimension;
import java.awt.Point;
import java.util.Random;

/**
 *
 * @author ASUS
 */
public class Constants {
    public final static double SCALE = 0.833;
    public final static int TOTAL_DEGREE = 360;
    
    // for DestructibleWall
    public final static Point DISAPPEAR = new Point(10000, 10000);
    public final static int DISAPPEAR_TIME_LENGTH  = 10000;
    public final static int OCCUPY_WAIT_TIME_LENGTH = 5000;
    
    // for TankWar drawing
    public final static Dimension MAP_DIMENSION = new Dimension(1280, 1280);
    public final static Dimension VIEW_DIMENSION = new Dimension(400, 480);
    public final static Dimension MINI_DIMENSION = new Dimension(120, 120);
    public final static Point LEFT_VIEW_POINT = new Point(0, 0);
    public final static Point RIGHT_VIEW_POINT = new Point(420, 0);
    public final static Point MINI_MAP_POINT = new Point(350, 350);
    
    // for Munitions and MunitionUsage
    public final static Random GENERATOR = new Random(1234567);
    public final static int SHOOT_DELAY = 333;
    public final static int SHELL_SPEED = 16;
    public final static int ROCKET_SPEED = 8;
    public final static int BOUNCING_BOMB_SPEED = 16;
    public final static int SHELL_LIFE_SPAN = 5000;
    public final static int ROCKET_LIFE_SPAN = 10000;
    public final static int BOUNCING_BOMB_LIFE_SPAN = 5000;
    public final static int SHIELD_LIFE_SPAN = 1250;
    
    // for Tank
    public final static Point HEALTH_BAR_POINT = new Point(12, -4);
    public final static Dimension HEALTH_BAR_DIMENSION = new Dimension(40, 5);
    public final static Point PICKUP_SIGN_POINT = new Point(18, 60);
    public final static Point PICKUP_STORAGE_POINT = new Point(35, 70);
    public final static double FRICTION  = 0.5;
    public final static int MAX_SPEED = 16;
    public final static int MIN_SPEED = -16;
}
