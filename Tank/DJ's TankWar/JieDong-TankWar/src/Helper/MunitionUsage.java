
package Helper;

import java.awt.Image;

/**
 *
 * 
 */
public class MunitionUsage {
    private String munitionName;
    private Image sign;
    private int storage;
    private boolean limited = false;
    private int delay;
    private long lastFireTime;
    
    // For Toolbox
    public MunitionUsage(String munitionName) {
        this.munitionName = munitionName;
    }
    
    // For unlimited Munitions e.g. shell
    public MunitionUsage(String munitionName, int delay) {
        this(munitionName);
        this.delay = delay;
        lastFireTime = System.currentTimeMillis();
    }
    
    // // For Rocket, Bouncing bombs and Shield
    public MunitionUsage(String munitionName,  Image sign, int delay, int storage) {
        this(munitionName, delay);
        this.sign = sign;
        limited = true;
        this.storage = storage;
    }
    
    public boolean isAvailable() {
        boolean available = false;
        if(!limited) available = true;
        else {
            if (storage > 0) available = true;
        }
        return available;
    }
    
    public void decreaseStorage(int decrease) {
        if (limited) storage -= decrease;
    }

    public String getMunitionName() {
        return munitionName;
    }

    public int getStorage() {
        return storage;
    }

    public boolean isLimited() {
        return limited;
    }

    public int getDelay() {
        return delay;
    }

    public long getLastFireTime() {
        return lastFireTime;
    }

    public void setLastFireTime(long lastFireTime) {
        this.lastFireTime = lastFireTime;
    }

    public Image getSign() {
        return sign;
    }
    
    
}
