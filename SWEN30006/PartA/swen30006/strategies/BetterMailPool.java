package strategies;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

import automail.Clock;
import automail.IMailPool;
import automail.MailItem;


public class BetterMailPool implements IMailPool{
	public final int INITIALCAPACITY = 11;
	public final double PENALTY = 1.1;
	Comparer comparison = new Comparer();
	private PriorityQueue<MailItem> queue;
	
	public BetterMailPool(){
		queue = new PriorityQueue<MailItem>(INITIALCAPACITY, comparison);
	}
	
	@Override
	public void addToPool(MailItem mailItem) {
		queue.add(mailItem);
	}
    public boolean isEmptyPool(){
        return queue.isEmpty();
    }
    
    public MailItem get(){
    	return queue.element();
    }
    
    public Iterator<MailItem> getI(){
    	return queue.iterator();
    }
    
    public void remove(MailItem mailItem){
        queue.remove(mailItem);
    }
    
    private double priorityscore(String level){
        if (level == "LOW"){
        	return 1;
        }
        else if (level == "MEDIUM"){
        	return 1.5;
        }
        else{
        	return 2;
        }
    }  
    
    //Would have made a seperate file class for this but the spec said only to submit the 3 files :(
    private class Comparer implements Comparator<MailItem> {
    	double score1, score2;
    	@Override
    	public int compare(MailItem o1, MailItem o2) {
    		
    		//Score 
    		score1 = Math.pow(Clock.Time()-o1.getArrivalTime()+
    				o1.getDestFloor(),PENALTY)*priorityscore(o1.getPriorityLevel());
    		score2 = Math.pow(Clock.Time()-o2.getArrivalTime()+
    				o2.getDestFloor(),PENALTY)*priorityscore(o2.getPriorityLevel());
    		
    		//Will never be equal however the position doesn't matter in that scenario.
    		if(score1>=score2){
    			return 1;
    		}
    		else{
    			return -1;
    		}
    	}
    }
}
