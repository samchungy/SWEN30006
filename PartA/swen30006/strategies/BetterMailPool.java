package strategies;
import java.util.Comparator;
import java.util.PriorityQueue;

import automail.Clock;
import automail.IMailPool;
import automail.MailItem;


public class BetterMailPool implements IMailPool{
	public final int INITIALCAPACITY = 11;
	public final int PENALTY = 1.1;
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
    
    public void remove(MailItem mailItem){
        queue.remove(mailItem);
    }
    
    private double priorityscore(String level){
        switch(level){
        	case "LOW":
        		return 1;
        		break;
        	case "MEDIUM":
        		return 1.5;
        		break;
        	case "HIGH":
        		return 2;
        		break;
        }
    }  
    
    public class Comparer implements Comparator<MailItem> {
    	double score1, score2;
    	@Override
    	public int compare(MailItem o1, MailItem o2) {
    		
    		score1 = Math.pow(Clock.Time()-o1.getArrivalTime()+o1.getDestFloor(),PENALTY)*priorityscore(o1.getPriorityLevel());
    		
    		if(o1.getSize()>o2.getSize()){
    			return -1;
    		}
    		else if(o1.getSize()==o2.getSize()){
    			return 0;
    		}
    		else{
    			return 1;
    		}
    	}
    }
}
