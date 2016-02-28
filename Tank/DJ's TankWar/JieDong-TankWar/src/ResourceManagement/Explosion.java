
package ResourceManagement;

import java.applet.AudioClip;
import java.awt.Image;

/**
 *
 * 
 */
public class Explosion {
    private int current = 0;
    private Image[] animation;
    private AudioClip sound;
    private int type;
    
    public Explosion(Image[] animation, AudioClip sound) {
        this.animation = animation;
        this.sound = sound;
        type = 2;
    }
    
    public Explosion(AudioClip sound) {
        this.sound = sound;
        type =  1;
    }

    public Explosion() {
        type = 0;
    }   
    
    public Image getCurrentImage() {
        Image currentImage;
        if (type == 2) {
            if (current < animation.length) {
                currentImage = animation[current];
                current++;
            } else {
                currentImage = null;
                current = 0;
            }  
        } else
            currentImage = null;
        return currentImage;
    }

    public void playAudio() {
        if((type != 0) && (sound != null))
            sound.play();
    }
    
    public AudioClip getSound() {
        return sound;
    }

    public int getType() {
        return type;
    }
    
    public boolean isVisible() {
        return type == 2;
    }
}
