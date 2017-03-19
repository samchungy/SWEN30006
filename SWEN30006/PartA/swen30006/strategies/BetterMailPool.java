package strategies;
import java.util.PriorityQueue;
import automail.IMailPool;
import automail.MailItem;


public class BetterMailPool implements IMailPool{
	public final int INITIALCAPACITY = 11;
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
    
    public void remove(){
        queue.remove();
    }
}
