package strategies;

import exceptions.TubeFullException;

import automail.MailItem;
import automail.IMailSorter;

import java.util.ArrayList;

import automail.Clock;
import automail.StorageTube;

/**
 * A sample class for sorting mail:  this strategy just takes a MailItem
 * from the MailPool (if there is one) and attempts to add it to the Robot's storageTube.
 * If the MailItem doesn't fit, it will tell the robot to start delivering (return true).
 */
public class SimpleMailSorter implements IMailSorter{

	BetterMailPool betterMailPool;
	ArrayList<MailItem> WaitingList = new ArrayList<MailItem>();
	
	public SimpleMailSorter(BetterMailPool betterMailPool) {
		this.betterMailPool = betterMailPool;
	}
    /**
     * Fills the storage tube
     */
    @Override
    public boolean fillStorageTube(StorageTube tube) {
    	while(!tube.isFull()&&!betterMailPool.isEmptyPool()){
            try{
                if (!betterMailPool.isEmptyPool()) {
    	            /** Gets the first item from the ArrayList */
    	            MailItem mailItem = betterMailPool.get();
    	            /** Add the item to the tube */
    	            tube.addItem(mailItem);
    	            /** Remove the item from the ArrayList */
    	            betterMailPool.remove();
                }
            }
            /** Refer to TubeFullException.java --
             *  Usage below illustrates need to handle this exception. However you should
             *  structure your code to avoid the need to catch this exception for normal operation
             */
            catch(TubeFullException e){
            	WaitingList.add(betterMailPool.get());
            	betterMailPool.remove();
            }   
    	}
    	for(MailItem mail:WaitingList){
    		betterMailPool.addToPool(mail);
    	}
        if(Clock.Time() > Clock.LAST_DELIVERY_TIME && betterMailPool.isEmptyPool() && !tube.isEmpty()){
            return true;
        }
        if(tube.isFull()||betterMailPool.isEmptyPool()){
        	return true;
        }
    	return false;
    	// System.out.println("SimpleMailPool.mailItems.count: "+SimpleMailPool.mailItems.size());
   
        /** 
         * Handles the case where the last delivery time has elapsed and there are no more
         * items to deliver.
         */
    }
}
