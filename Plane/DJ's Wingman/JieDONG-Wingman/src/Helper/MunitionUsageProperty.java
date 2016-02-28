/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import java.util.logging.Logger;

/**
 *
 * @author ASUS
 */
public class MunitionUsageProperty {
    private String munitionName;
    private long delay;
    private boolean limited;
    private boolean initiallyEquipped;
    private int initialStorage;

    public MunitionUsageProperty(String munitionName, long delay, boolean initiallyEquipped, 
            boolean limited) {
        this.munitionName = munitionName;
        this.delay = delay;
        this.initiallyEquipped = initiallyEquipped;
        this.limited = limited;
        if(!limited) initialStorage = 1;
    }

    public MunitionUsageProperty(String munitionName, long delay, boolean initiallyEquipped, 
            boolean limited, int initialStorage) {
        this(munitionName, delay, initiallyEquipped, limited);
        this.initialStorage = initialStorage;
    }

    public String getMunitionName() {
        return munitionName;
    }

    public long getDelay() {
        return delay;
    }

    public int getInitialStorage() {
        return initialStorage;
    }

    public boolean isinitiallyEquipped() {
        return initiallyEquipped;
    }

    public boolean isLimited() {
        return limited;
    }

}
