
package Helper;

import BasicObject.BasicObject;

/**
 *
 * 
 */
public class MunitionUsage {
    private MunitionUsageProperty munitionUsageProperty;
    private long lastFireTime = 0;
    private int storage;
    private boolean equipped;
    private boolean limited;
    
    public MunitionUsage(MunitionUsageProperty munitionUsageProperty) {
        this.munitionUsageProperty = munitionUsageProperty;
        this.storage = munitionUsageProperty.getInitialStorage();
        this.equipped = munitionUsageProperty.isinitiallyEquipped();
        this.limited = munitionUsageProperty.isLimited();
        lastFireTime = System.currentTimeMillis();
    }

    public String getMunitionName() {
        return munitionUsageProperty.getMunitionName();
    }

    public long getDelay() {
        return munitionUsageProperty.getDelay();
    }

    public boolean isAvailable() {
        boolean available = false;
        if (equipped) {
            if(!limited) available = true;
            else {
                if (storage > 0) available = true;
            }
        }
        return available;
    }
    
    public void increaseStorage(int increase) {
        storage += increase;
    }
    
    public void decreaseStorage(int decrease) {
        if (limited) storage -= decrease;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }

    public long getLastFireTime() {
        return lastFireTime;
    }

    public void setLastFireTime(long lastFireTime) {
        this.lastFireTime = lastFireTime;
    }

    public int getStorage() {
        return storage;
    }

    public void setStorage(int storage) {
        this.storage = storage;
    }

    public int getInitialStorage() {
        return munitionUsageProperty.getInitialStorage();
    }
    
}
