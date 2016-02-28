
package ResourceManagement;

import java.applet.AudioClip;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.JApplet;
import tankwar1.TankWar;

/**
 * ResourceLoader reads the file name, e.g. spriteName or soundName, and return
 * BufferedImage or AudioClip.
 * It is created to decrease responsibilities of TankWar(JApplet).
 * It is used by ResourceTable to group data for each Object. 
 * It is also used by TankWar to get data directly and easily. 
 * e.g. background image
 */
public class ResourceLoader {
    private TankWar applet;
    
    public ResourceLoader(TankWar applet) {
        this.applet = applet;
    }
    
    public BufferedImage loadSingleImage(String spriteName) {
        URL url = TankWar.class.getResource(spriteName);
        BufferedImage bimg = null;
        
        /** two ways to load image: 
         *  1. MediaTracker
         *  2. ImageIO - I use ImageIO to get BufferedImage easily
         */
        try {
            bimg = ImageIO.read(url);
        } catch (IOException ex) {
            System.out.println("IOException of ImageIO");
        }
        return bimg;
        
        /** Image img = applet.getToolkit().getImage(url);
        try {
            MediaTracker tracker = new MediaTracker(applet);
            tracker.addImage(bimg, 0);
            tracker.waitForID(0);  //wait for the image to fully load
        } catch (Exception e) {
            System.out.println("MediaTracker has not finished loading image.");
        }*/
    }
    
    public Image[] loadImage(String SpriteName) {
        Image[] imageList;
        BufferedImage animationImage = loadSingleImage(SpriteName);
        int height = animationImage.getHeight();
        int width  = animationImage.getWidth();
        int numberOfImage = width / height;
        imageList = new Image[numberOfImage];
        for(int i = 0; i < numberOfImage; i++) {
            BufferedImage bimg = animationImage.getSubimage(i * height, 0, height, height);
            imageList[i] = bimg;
        }
        return imageList;
    }
    
    public AudioClip loadSound(String soundName) {
        Class metaObject = applet.getClass();
        URL url = metaObject.getResource(soundName);
        return JApplet.newAudioClip(url);
    }
}
