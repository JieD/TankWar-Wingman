/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tankwar1;

import java.awt.Image;
import java.awt.MediaTracker;
import java.net.URL;
import javax.swing.JApplet;

/**
 *
 * @author ASUS
 */
public class Test {
    private Image wall1;
    private JApplet applet;
    
    public Test(TankWar applet) {
        this.applet = applet;
    }
    
    public Image loadImage(String spriteName) {
        URL url = TankWar.class.getResource(spriteName);
        Image img = applet.getToolkit().getImage(url);
        try {
            MediaTracker tracker = new MediaTracker(applet);
            tracker.addImage(img, 0);
            tracker.waitForID(0);  //wait for the image to fully load
        } catch (Exception e) {
        }
        return img;
   }
}
