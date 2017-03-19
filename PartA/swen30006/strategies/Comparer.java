package strategies;

import java.util.Comparator;
import automail.MailItem;

public class Comparer implements Comparator<MailItem> {

	@Override
	public int compare(MailItem o1, MailItem o2) {
		
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
