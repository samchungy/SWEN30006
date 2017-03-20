package strategies;

import exceptions.TubeFullException;

import automail.MailItem;
import automail.IMailSorter;
import automail.Clock;
import automail.StorageTube;

/**
 * A sample class for sorting mail:  this strategy just takes a MailItem
 * from the MailPool (if there is one) and attempts to add it to the Robot's storageTube.
 * If the MailItem doesn't fit, it will tell the robot to start delivering (return true).
 */
public class SimpleMailSorter implements IMailSorter{
	private static final int STARTOFQUEUE = 0;
	BetterMailPool betterMailPool;
	private int arraypos;
	private int floornum;
	private boolean tubefull;
	
	public SimpleMailSorter(BetterMailPool betterMailPool) {
		this.betterMailPool = betterMailPool;
		arraypos = STARTOFQUEUE;
		tubefull = false;
	}
    /**
     * Fills the storage tube
     */
    @Override
    public boolean fillStorageTube(StorageTube tube) {

    	// System.out.println("SimpleMailPool.mailItems.count: "+SimpleMailPool.mailItems.size());
    	/** Strategy 1: Fill Tube based on the First Item in Queue's floor number.
    	 */
    	while(!betterMailPool.isEmptyPool() && !tubefull){
            try{
                if (!betterMailPool.isEmptyPool()) {
    	            /** Gets the first item from the ArrayList */
    	            MailItem mailItem = betterMailPool.get();
    	            /** Add the item to the tube */
    	            tube.addItem(mailItem);
    	            /** Remove the item from the ArrayList */
    	            betterMailPool.remove(mailItem);
                }
            }
            /** Refer to TubeFullException.java --
             *  Usage below illustrates need to handle this exception. However you should
             *  structure your code to avoid the need to catch this exception for normal operation
             */
            catch(TubeFullException e){
            	return true;
            }   
    	}
   
        /** 
         * Handles the case where the last delivery time has elapsed and there are no more
         * items to deliver.
         */
        if(Clock.Time() > Clock.LAST_DELIVERY_TIME && betterMailPool.isEmptyPool() && !tube.isEmpty()){
            return true;
        }
        return false;

    }
}
