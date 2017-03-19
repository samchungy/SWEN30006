package strategies;

import java.util.Comparator;
import automail.MailItem;

public class Comparer implements Comparator<MailItem> {

	@Override
	public int compare(MailItem o1, MailItem o2) {
        double priority_weight1 = calcWeight(o1);
        double priority_weight2 = calcWeight(o2);
		
		if(o1.getArrivalTime()>o2.getArrivalTime()){
			return -1;
		}
		else if(o1.getArrivalTime()==o2.getArrivalTime()){
			if (priority_weight1 < priority_weight2){
				return -1;
			}
			else if (priority_weight1 == priority_weight2){
				return 0;
			}
			else{
				return 1;
			}
		}
		else{
			return 1;
		}
	}
	
	private double calcWeight(MailItem o){
        switch(o.getPriorityLevel()){
        	case "LOW":
        		return 1.0;
        	case "MEDIUM":
        		return 1.5;
        	case "HIGH":
        		return 2.0;
        }
    return -1;
	}
}
