package EcoRecycle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import com.mysql.jdbc.ResultSetMetaData;
import EcoRecycle.connectToDB;
import java.sql.Connection;

public class Activator {

	// List of all the RCMs
	public static ArrayList<RCMInfo> totalRCMMachines = new ArrayList<RCMInfo>();

	public static int addIndex = 0;
	RCMInfo rcmObj;
	int i;


	/**
	 * Method to add an item to the list of items acceptable by RCM
	 * 
	 * @param itemObj
	 *            Input from the user
	 */
	public void addItem(Item itemObj) {

		System.out.println("ACTIVATOR ADD ITEM size of array before: "
				+ RCMInfo.listOfItems.size());
		RCMInfo.listOfItems.add(itemObj);

		System.out.println("ACTIVATOR ADD ITEM size of array after: "
				+ RCMInfo.listOfItems.size());

	}

	/**
	 * Method to remove an item to the list of items acceptable by RCM
	 * 
	 * @param itemName
	 *            Input from the user
	 */
	public void removeItem(String itemName) {
		int key = 0;
		System.out.println("ACTIVATOR REMOVE MACHINE size of array before: "
				+ RCMInfo.listOfItems.size());
		for (Item itemObj : RCMInfo.listOfItems) {
			if (itemObj.getItemName().equals(itemName)) {
				key = RCMInfo.listOfItems.indexOf(itemObj);
			}

		}
		RCMInfo.listOfItems.remove(key);
		System.out.println("ACTIVATOR REMOVE ITEM size of array after: "
				+ RCMInfo.listOfItems.size());
	}

	/**
	 * 
	 * @param itemName
	 * @param price
	 */
	public void updatePrice(String itemName, Double price) {
		System.out.println("ACTIVATOR UPDATE PRICE:new price is: " + price);
		for (Item itemObj : RCMInfo.listOfItems) {
			if (itemObj.getItemName().equals(itemName)) {
				RCMInfo.listOfItems.indexOf(itemObj);
				itemObj.setUnitPrice(price);
				// System.out.println("ACTIVATORE itemObj: "
				// +itemObj.getUnitPrice());
			}

		}
	}

	/**
	 * Method to add a machine to the list of RCMs
	 * 
	 * @param rcmObj
	 *            Input from the user using the details entered
	 */
	public void addMachine(RCMInfo rcmObj) {
		System.out.println("ACTIVATOR ADD MACHINE size of array before: "
				+ totalRCMMachines.size());
		totalRCMMachines.add(rcmObj);
		System.out.println("ACTIVATOR ADD MACHINE size of array after: "
				+ totalRCMMachines.size());
	}

	/**
	 * Method to remove a machine to the list of RCMs
	 * 
	 * @param removeMachineID
	 *            Input from the user to indicate which machine to be removed
	 */
	public void removeMachine(String removeMachineID) {

		int key = 0;
		System.out.println("ACTIVATOR REMOVE MACHINE size of array before: "
				+ totalRCMMachines.size());
		for (RCMInfo rcmObj1 : totalRCMMachines) {
			if (rcmObj1.getMachineId().equals(removeMachineID)) {
				// System.out.println("rcmObj1  location: "+rcmObj.getLocation());

				key = totalRCMMachines.indexOf(rcmObj1);
				System.out.println("key: " + key);
			}

		}
		totalRCMMachines.remove(key);
		System.out.println("ACTIVATOR REMOVE MACHINE size of array after:  "
				+ totalRCMMachines.size());
	}

}
