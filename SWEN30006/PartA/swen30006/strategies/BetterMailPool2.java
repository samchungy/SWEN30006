package strategies;
import java.util.ArrayList;
import java.util.Iterator;

import automail.Clock;
import automail.IMailPool;
import automail.MailItem;


public class BetterMailPool2 implements IMailPool{
	public final double PENALTY = 1.1;
	private ArrayList<MailItem> queue;
	
	public BetterMailPool2(){
		queue = new ArrayList<MailItem>();
	}
	
	@Override
	public void addToPool(MailItem mailItem) {
		queue.add(mailItem);
		insertSortPool();
	}
    public boolean isEmptyPool(){
        return queue.isEmpty();
    }
    
    public MailItem get(int num){
    	return queue.get(num);
    }
    
    public Iterator<MailItem> getI(){
    	return queue.iterator();
    }
    
    public void remove(MailItem mailItem){
        queue.remove(mailItem);
    }
    
    public int size(){
    	return queue.size();
    }
    

    
    private void insertSortPool(){
    	int i,j;
    	for (i=queue.size()-1;i>0;i--){
    		if ((j=i-1)<0){
    			return;
    		}
    		MailItem o1 = queue.get(i);
        	MailItem o2 = queue.get(j);
    		// If new Mail is of a lower priority than the next one, stop sorting.
        	if (compare(o1,o2)){
        		return;
        	}
        	// Swap mail positions
        	queue.set(j,o1);
        	queue.set(i,o2);
    	}
    }
    
    //Would have made a seperate file class for this but the spec said only to submit the 3 files :(
 	private boolean compare(MailItem o1, MailItem o2) {
    		double score1,score2;
    		//Score 
    		score1 = Math.pow(Clock.Time()-o1.getArrivalTime()+
    				o1.getDestFloor(),PENALTY)*priorityscore(o1.getPriorityLevel());
    		score2 = Math.pow(Clock.Time()-o2.getArrivalTime()+
    				o2.getDestFloor(),PENALTY)*priorityscore(o2.getPriorityLevel());
    		
    		//Will never be equal however the position doesn't matter in that scenario.
    		if(score1<=score2){
    			return true;
    		}
    		else{
    			return false;
    		}
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
}
