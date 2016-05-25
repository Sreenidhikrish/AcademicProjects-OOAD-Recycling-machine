package EcoRecycle;

import java.sql.PreparedStatement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Database.ServiceLayer;
import GUI.EcoRecycleGUI;
import GUI.RMOSGUI;

public class RCMInfo {

	// Data members
	
	//List of all the items acceptable
	public static ArrayList<Item> listOfItems = new ArrayList<Item>();

	private String machineId;
	String location;
	double totalCapacityOfTheMachine;
	double maxCreditOfTheMachine;
	double currentCapacityOfTheMachine;
	ArrayList<Date> timeLastEmptied = new ArrayList<Date>();
	double currentCredit;
	String operationalStatus;
	public Date transactionInitiated;
	public ReportGenerator reportgenObj = new ReportGenerator();

	// Default Constructor
	public RCMInfo() {
	}

	// Parameterized Constructor
	public RCMInfo(String machineId, String location,
			double maxCreditOfTheMachine, double totalCapacityOfTheMachine) {
		this.machineId = machineId;
		this.location = location;
		this.totalCapacityOfTheMachine = totalCapacityOfTheMachine;
		this.maxCreditOfTheMachine = maxCreditOfTheMachine;
		java.util.Date utilDate = new java.util.Date();
		this.timeLastEmptied.add(utilDate);
		this.currentCapacityOfTheMachine = 0.0;
		this.currentCredit = maxCreditOfTheMachine;
		operationalStatus = "Working";
	}

	/**
	 * Method to initialize the start time stamp of the transaction
	 */
	public void setTransactionInitiated() {
		java.util.Date utilDate = new java.util.Date();
		
		

		DateFormat df = new SimpleDateFormat("yyyy/mm/dd HH:mm:ss"); 
		Date dateobj = new Date(); 
		//System.out.println(df.format(dateobj));
		this.transactionInitiated = dateobj;
		
		//System.out.println("in transactionInitiated setter: " + transactionInitiated);
	}

	/**
	 * Getter to get the machine id
	 * 
	 * @return machine id
	 */
	public String getMachineId() {
		return this.machineId;
	}

	/**
	 * Getter to get the machine location
	 * 
	 * @return machine location
	 */
	public String getLocation() {
		return this.location;
	}

	/**
	 * Getter to get the machine last emptied time
	 * 
	 * @return machine last emptied time
	 */
	public Date getTimeLastEmptied() {
		int temp = timeLastEmptied.size();
		return timeLastEmptied.get(temp - 1);
	}

	/**
	 * Getter to get the machine TotalCapacity
	 * 
	 * @return TotalCapacity
	 */
	public double getTotalCapacity() {
		return this.totalCapacityOfTheMachine;
	}

	/**
	 * Getter to get the machine currentCapacity
	 * 
	 * @return currentCapacity
	 */
	public double getCurrentCapacity() {
		return this.currentCapacityOfTheMachine;
	}

	/**
	 * Getter to get the machine currentCredit
	 * 
	 * @return currentCredit
	 */
	public double getCurrentCredit() {
		return this.currentCredit;
	}

	/**
	 * Getter to get the machine operational status
	 * 
	 * @return operational status
	 */
	public String getOperationalStatus() {
		return this.operationalStatus;
	}

	/**
	 * Getter to get the machine max credit
	 * 
	 * @return max credit
	 */
	public double getMaxCredit() {
		return this.maxCreditOfTheMachine;
	}

	/**
	 * Setter to set the machine current credit
	 * 
	 * @param currentCredit
	 */
	public void setCurrentCredit(double currentCredit) {
		this.currentCredit = currentCredit;
	}

	/**
	 * Setter to set the machine operational status
	 * 
	 * @param operationalStatus
	 */
	public void setOperationalStatus(String operationalStatus) {
		this.operationalStatus = operationalStatus;
	}

	/**
	 * Setter to set the machine current Capacity
	 * 
	 * @param currentCapacityOfTheMachine
	 */
	public void setCurrentCapacity(double currentCapacityOfTheMachine) {
		this.currentCapacityOfTheMachine = currentCapacityOfTheMachine;
	}

	/**
	 * Setter to set the machine max credit
	 * 
	 * @param maxCreditOfTheMachine
	 */
	public void setMaxCredit(double maxCreditOfTheMachine) {
		this.maxCreditOfTheMachine = maxCreditOfTheMachine;
	}

	/**
	 * Setter to set the machine last emptied date
	 * 
	 * @param lastEmptied
	 */
	public void setTimeLastEmptied(Date lastEmptied) {
		timeLastEmptied.add(lastEmptied);
		//System.out.println("after set time last emptied was called");
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Activator a = new Activator();
		ServiceLayer serviceObj = new ServiceLayer();
		// Establish a connection
		connectToDB connectToDBObj = new connectToDB();
		
		
		listOfItems.trimToSize();
		RCMInfo.listOfItems.clear();
		Activator.totalRCMMachines.clear();

		// Initialize RCM list with 2 RCMs

		RCMInfo rcm1 = new RCMInfo("1", "SanJose", 500, 200);
		RCMInfo rcm2 = new RCMInfo("2", "Sunnyvale", 1000, 500);

		a.addMachine(rcm1);
		a.addMachine(rcm2);

		// Initialize the machines in the database
		try {

			serviceObj.deleteRCM();	
			
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			
			serviceObj.addRCM(rcm1);
			serviceObj.addRCM(rcm2);
			
		} catch (Exception e2) {
			// Handle errors for Class.forName
			e2.printStackTrace();
		}

		// Initialize Item list with 2 items

		String itemName = "Plastics";
		Double unitPrice = 5.0;
		Double minWeight = 6.0;

		Item i1 = new Item(itemName, unitPrice, minWeight);
		
		itemName = "Glass";
		unitPrice = 7.0;
		minWeight = 8.0;

		Item i2 = new Item(itemName, unitPrice, minWeight);
		
		a.addItem(i1);
		a.addItem(i2);
		
		try {
			
			serviceObj.deleteItem();

			
		} catch (Exception e4) {
			// Handle errors for Class.forName
			e4.printStackTrace();
		}

		try {
			
			serviceObj.addBasicItem(i1);
			serviceObj.addBasicItem(i2);
			
		} catch (Exception e5) {
			// Handle errors for Class.forName
			e5.printStackTrace();
		}

		// RCM Gui is called
		EcoRecycleGUI egui = new EcoRecycleGUI();

		// RMOS Gui is called
		RMOSGUI rgui = new RMOSGUI();
		
		
		for(int i=0;i<listOfItems.size();i++){
			//System.out.println("1: "+listOfItems.get(i).getItemName());
		//	System.out.println("2: " +listOfItems.get(i).getUnitPrice());
		//	System.out.println("3: " +listOfItems.get(i).getMinWeight());
		}
		
		for(int j=0;j< Activator.totalRCMMachines.size();j++){
			//System.out.println("machine id: "+Activator.totalRCMMachines.get(j).getMachineId());
			//System.out.println("location: " +Activator.totalRCMMachines.get(j).getLocation());
			//System.out.println("OS: " +Activator.totalRCMMachines.get(j).getOperationalStatus());
		//System.out.println("Max Credit: " +Activator.totalRCMMachines.get(j).getMaxCredit());
		//System.out.println("total capacity: " +Activator.totalRCMMachines.get(j).getTotalCapacity());
		//	System.out.println("current capacity : " +Activator.totalRCMMachines.get(j).getCurrentCapacity());
			//System.out.println("last emptied: " +Activator.totalRCMMachines.get(j).getTimeLastEmptied());
		//	System.out.println("current credit: " +Activator.totalRCMMachines.get(j).getCurrentCredit());
					
		}

	}
}
