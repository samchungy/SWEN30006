package automail;

import strategies.*;

public class Automail {
	      
    public Robot robot;
    public IMailPool mailPool;
    
    Automail(IMailDelivery delivery) {
    	
    /** CHANGE NOTHING ABOVE HERE */
    	
    	/** Initialize the MailPool */
    	BetterMailPool betterMailPool = new BetterMailPool();
    	mailPool = betterMailPool;
    	
        /** Initialize the MailSorter */
    	IMailSorter sorter = new SimpleMailSorter(betterMailPool);
    	
    /** CHANGE NOTHING BELOW HERE */
    	
    	/** Initialize robot */
    	robot = new Robot(sorter, delivery);
    	
    }
    
}
