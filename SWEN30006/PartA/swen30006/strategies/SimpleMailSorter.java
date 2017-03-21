package strategies;

import exceptions.TubeFullException;

import automail.MailItem;
import automail.IMailSorter;

import java.util.Iterator;

import automail.Building;
import automail.Clock;
import automail.StorageTube;

/**
 * A sample class for sorting mail:  this strategy just takes a MailItem
 * from the MailPool (if there is one) and attempts to add it to the Robot's storageTube.
 * If the MailItem doesn't fit, it will tell the robot to start delivering (return true).
 */
public class SimpleMailSorter implements IMailSorter{
	private final int STARTOFQUEUE = 0;
	BetterMailPool2 betterMailPool;
	
	public SimpleMailSorter(BetterMailPool2 betterMailPool) {
		this.betterMailPool = betterMailPool;
	}
    /**
     * Fills the storage tube
     */
    @Override
    public boolean fillStorageTube(StorageTube tube) {

    	// System.out.println("SimpleMailPool.mailItems.count: "+SimpleMailPool.mailItems.size());
    	/**Keeps track of Array Iterration*/
    	int floornum = Building.MAILROOM_LOCATION;
    	int tubespaceleft = tube.MAXIMUM_CAPACITY;
    	MailItem prioritymail = null;
    	if(!betterMailPool.isEmptyPool()){
    		prioritymail = betterMailPool.get(STARTOFQUEUE);
        	floornum = prioritymail.getDestFloor();
        	tubespaceleft -= prioritymail.getSize();
        	try {
    			tube.addItem(prioritymail);
    		} catch (TubeFullException e) {
    			return true;
    		}
        	betterMailPool.remove(prioritymail);
    	}
    	
    	/** Strategy 1: Fill Tube with First Priority Item, Next Fill Tube with High Priority
    	 * That is on the way to the first parcel's delivery floor. Repeat. If not just send.
    	 */
    	for (Iterator<MailItem> iterator = betterMailPool.getI(); iterator.hasNext() && tubespaceleft > 0; ){
    		prioritymail = iterator.next();
    		if(Math.abs(floornum-prioritymail.getDestFloor())<=Math.abs(floornum-Building.MAILROOM_LOCATION)
    				&& tubespaceleft-prioritymail.getSize() >= 0){
    			floornum = prioritymail.getDestFloor();
    			tubespaceleft -= prioritymail.getSize();
    	    	try {
    				tube.addItem(prioritymail);
    			} catch (TubeFullException e) {
    				return true;
    			}
    			iterator.remove();
    		}
    	}
        if(Clock.Time() > Clock.LAST_DELIVERY_TIME && betterMailPool.isEmptyPool() && !tube.isEmpty()){
            return true;
        }
        if (tubespaceleft == tube.MAXIMUM_CAPACITY){
        	return false;
        }
        else {
        	return true;
        }
    	

    }
}
