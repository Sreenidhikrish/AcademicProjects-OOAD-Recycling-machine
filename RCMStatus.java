package EcoRecycle;

import java.util.Iterator;

public class RCMStatus {
	/**
	 * Method to check the operational status of the machine
	 * 
	 * @param rcmObj
	 *            Input from the user to indicate which RCM
	 */
	public void checkOperationalStatus(RCMInfo rcmObj) {

		int i;
		double temp = 0.0;

		// Loop through the list of items and get the weight dropped

		for (i = 0; i < rcmObj.listOfItems.size(); i++) {

			temp = temp + rcmObj.listOfItems.get(i).getWeightDropped();
		}

		// If the current capacity is greater than total capacity machine is
		// over loaded and not working
		if (rcmObj.getCurrentCapacity() > rcmObj.getTotalCapacity()) {
			rcmObj.setOperationalStatus("Not working");
			//System.out.println("Not working");
		}

		// If the weight dropped is greater than total capacity machine is over
		// loaded and not working
		else if (rcmObj.getTotalCapacity() < temp) {
			rcmObj.setOperationalStatus("Not working");
			//System.out.println("Not working");

		}
		// Else set the operational status as working
		else {
			rcmObj.setOperationalStatus("Working");
			//System.out.println("Working");
		}
	}

	/**
	 * Method to check the money in machine
	 * 
	 * @param rcmObj
	 *            Input to indicate which RCM
	 */
	public void checkMoney(RCMInfo rcmObj) {
		//System.out.println(rcmObj.getCurrentCredit());

	}

	/**
	 * Method to check the last emptied time of the machine
	 * 
	 * @param rcmObj
	 *            Input to indicate which RCM
	 */
	public void checkLastEmptiedTime(RCMInfo rcmObj) {
		//System.out.println(rcmObj.getTimeLastEmptied());
	}

	/**
	 * Method to check the current weight of the items in the machine
	 * 
	 * @param rcmObj
	 *            Input to indicate which RCM
	 */
	public void checkCurrentWeight(RCMInfo rcmObj) {
		//System.out.println(rcmObj.getCurrentCapacity());
		//System.out.println(rcmObj.getMaxCredit());
	}

}
