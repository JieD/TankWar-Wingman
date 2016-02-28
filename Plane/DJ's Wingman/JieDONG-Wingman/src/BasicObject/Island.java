/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BasicObject;

import BasicObject.BasicObject;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;
import wingman.Wingman;

/**
 * Island represents island in Wingman game. Since Island have multiple images,
 * an ArrayList<image> is used. A static variable counter is used to keep track
 * of the number of Island created, in order to assign proper images from the
 * ArrayList.
 */
public class Island extends BasicObject{
    private Random gen;
    private Image image;
    
    public Island(int x, int y, int speed, int direction, Image image, Random gen) {
        super(x, y, speed, direction, image);
        this.gen = gen;
    }
    
    @Override
    public void update(int width, int height) {
        super.update(width, height);
        if (isOut(width, height)) {
            setY(0 - Wingman.CUSHION + 10);
            setX(Math.abs(gen.nextInt() % (width - 30)));
        }
    }
}
