package EcoRecycle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import Database.ServiceLayer;


public class CreditCalculator {

	// Establish a connection
	connectToDB connectToDBObj = new connectToDB();

	// Data member
	private double moneyToBeIssued;
	
	public String updateRCMTransactionCurrentCapacityFlag="null";
	public String updateRCMTransactionFlag="null";


	//Service Layer object
	ServiceLayer serviceObj = new ServiceLayer();
	String rcmTableName = "";

	/**
	 * Getter for the data member
	 * 
	 * @return money to be issued for each session
	 */
	public double getMoneyToBeIssued() {
		return this.moneyToBeIssued;
	}

	/**
	 * Calculate the weight and price for the item user drops and display
	 * Computes check/ money payment based on the requirement
	 * 
	 * @param rcmData
	 *            Input from the user to indicate which RCM
	 * @param itemData
	 *            Input from the user to indicate which Item
	 * @param itemWeight
	 *            Input from the user to indicate weight dropped
	 * @return returns status of operation
	 */
	public int calculatePriceWeight(String rcmData, String itemData,
			double itemWeight) {

		int returnStatus = -1;
		int i;
		int index = 0;
		int j;
		int machineIndex = 0;
		int flag = 0;

		// Getting the machine index
		for (j = 0; j < Activator.totalRCMMachines.size(); j++) {

			String temp = Activator.totalRCMMachines.get(j).getMachineId();
			temp = "RCM" + temp;
			if (rcmData.equals(temp)) {
				machineIndex = j;
				//System.out.println("\n Machineindex selected is "+ machineIndex);
			}
		}

		//System.out.println("\n itemdata is " + itemData);
		// Getting the item index
		for (i = 0; i < RCMInfo.listOfItems.size(); i++) {

			if (itemData.equals(RCMInfo.listOfItems.get(i).getItemName())) {
				index = i;
				//System.out.println("\n index " + index);
			}
		}

		// Checking if the item weight is greater than the minimum weight. If
		// not
		// setting the flag
		if (itemWeight <= RCMInfo.listOfItems.get(index).getMinWeight()) {
		//	System.out.println("\n Min weight "+ RCMInfo.listOfItems.get(index).getMinWeight());
		//	System.out.println("\nInsufficient weight of item");
			returnStatus = 0;
			return returnStatus;
		}

		// Machine capacity calculation
		double totalCapacity = Activator.totalRCMMachines.get(machineIndex).getTotalCapacity();
	//	System.out.println("total capacity in credit calc: " +totalCapacity);
		
		double temp1 = totalCapacity - Activator.totalRCMMachines.get(machineIndex)
						.getCurrentCapacity();

		//System.out.println("MACHINE CAPACITY LEFT" + temp1);

		// if the item weight is greater than the machine capacity, machine
		// overloaded payment not issued
		if (itemWeight > temp1) {

			//System.out.println("\nSorry, machine capacity exceeded");
			Activator.totalRCMMachines.get(machineIndex).setOperationalStatus("NotWorking");
			Activator.totalRCMMachines.get(machineIndex).setCurrentCapacity(totalCapacity);

			// Update the machine capacity in the database
			
			try {

				String machineId = Integer.toString(machineIndex + 1);
				rcmTableName = "RCM";
				String operationalStatus = "NotWorking";
				
				updateRCMTransactionCurrentCapacityFlag =serviceObj.updateRCMTransactionCurrentCapacity(machineId,rcmTableName,operationalStatus,totalCapacity);
				} 
				catch (Exception se) {
				System.out.println(se.getMessage());
				}	
			//}
		//	if(updateRCMTransactionCurrentCapacityFlag.equals("Success")){
			returnStatus = 1;
			return returnStatus;
		}

		// If item weight greater than minimum weight and sufficient machine
		// capacity, proceed to modify the RCM details such as current capacity
		Activator.totalRCMMachines.get(machineIndex).listOfItems.get(index).weightDropped += itemWeight;

		double currentCapacity = Activator.totalRCMMachines.get(machineIndex).getCurrentCapacity();
		
		currentCapacity = currentCapacity + itemWeight;
	
		Activator.totalRCMMachines.get(machineIndex).setCurrentCapacity(currentCapacity);
		//System.out.println("currcapacity"+ Activator.totalRCMMachines.get(machineIndex).getCurrentCapacity());
			try {	
					String machineId = Integer.toString(machineIndex + 1);
					serviceObj.updateRCMCurrentCapacity(currentCapacity,machineId);

				} 
				catch (Exception e) {
					System.out.println(e.getMessage());
				}
			//	returnStatus = 3;
			//	return returnStatus;
		//}

		// Calculate the money to be issued for the current item placed on the
		// scale
		moneyToBeIssued = 0.0;
		//System.out.println("money before" + moneyToBeIssued);
		moneyToBeIssued += (Activator.totalRCMMachines.get(machineIndex).listOfItems
				.get(index).getUnitPrice() * itemWeight);

		//System.out.println("unitprice" + Activator.totalRCMMachines.get(machineIndex).listOfItems.get(index).getUnitPrice());
		//System.out.println("weight" + itemWeight);
		//System.out.println("\nMoney to be issued calculated" + moneyToBeIssued);

		rcmTableName = "";
		rcmTableName = rcmData + "TRANSACTION";

		// Update the database the details of transaction such as item data,
		// item weight and money to be issued along with date of transaction
				
		try {
			
			updateRCMTransactionFlag = serviceObj.updateRCMTransaction(rcmTableName,itemData,itemWeight,moneyToBeIssued);

			} 
			catch (Exception e) {
			// Handle errors for Class.forName
			e.printStackTrace();
		}
	//	if(updateRCMTransactionFlag=="Success"){

			returnStatus = 2;
			return returnStatus;
		//}
	}

	/**
	 * Issue credit to the user based on the weight of item/items dropped
	 * 
	 * @param rcmData
	 *            Input from user to indicate which RCM
	 * @return return status of the operation
	 */
	public boolean issueCredit(String rcmData) {

		String rcmTrim = rcmData.substring(3,4);
	   // System.out.println("rcmData trim" + rcmTrim);
		
		boolean issue = false;
		int j;
		int machineIndex = 0;

		for (j = 0; j < Activator.totalRCMMachines.size(); j++) {

			if (rcmTrim.equals(Activator.totalRCMMachines.get(j).getMachineId())) {
				machineIndex = j;
				//System.out.println("\n Machineindex " + machineIndex);
			}
		}

		// Calculate the cumulative credit of the item(s) dropped by the user in
		// the transaction session
		try {
			double currentCredit = 0.0;
			Date fromDate = new Date();
			String machineId = Integer.toString(machineIndex + 1);
			String issueCreditSql;

			java.util.Date currDate = new java.util.Date();
			java.sql.Date currDateSql = new java.sql.Date(currDate.getTime());
			
			//System.out.println();

			for (RCMInfo rcmObj : Activator.totalRCMMachines) {
				if (rcmObj.getMachineId().equals(machineId)) {
					
					//System.out.println("rcmObj.transactionInitiated: " +rcmObj.transactionInitiated);
					
					fromDate = rcmObj.transactionInitiated;
					//System.out.println("fromdate: " + fromDate);
				}
			}
			java.sql.Date fromDateSql = new java.sql.Date(fromDate.getTime());
			//System.out.println("fromDateSql: " + fromDateSql);

			rcmTableName = "";
			rcmTableName = rcmData + "TRANSACTION";
			double issueCreditReturn= 0.0;
			
			issueCreditReturn = serviceObj.issueCredit(rcmTableName,fromDate,currDate);
				
			if(issueCreditReturn != 0.0){
				currentCredit = (Activator.totalRCMMachines.get(machineIndex)
					.getMaxCredit()) - issueCreditReturn;
				
				Activator.totalRCMMachines.get(machineIndex).setCurrentCredit(
					currentCredit);
				
				System.out.println("issueCreditReturn: " +issueCreditReturn);
				System.out.println("current credit in credit calculator within the loop:" +currentCredit);
				System.out.println("totalRCMMachines current credit: " +Activator.totalRCMMachines.get(machineIndex).getCurrentCredit());
				
			}
			
			moneyToBeIssued = currentCredit;
			//System.out.println("money to issued from db: " + moneyToBeIssued);

			String creditIssueSql;
			String creditIssueMachineId = Integer.toString(machineIndex + 1);
			//System.out.println("Credit Calculator creditIssueMachineId: " +creditIssueMachineId);
			//System.out.println("current credit in credit calculator:" +currentCredit);
			
			// Update the current cvredit of the RCM
			try{
				serviceObj.updateCurrentCredit(currentCredit,creditIssueMachineId);
				System.out.println("current credit:  " +currentCredit);
				System.out.println("machine id:  " +creditIssueMachineId);
			} 
			catch (Exception e) {
			System.out.println(e.getMessage());
			}
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		// If money insufficient Coupons are issued
	//	System.out.println("moneyToBeIssued before checking if exceeded: " +moneyToBeIssued);
		if (moneyToBeIssued > (Activator.totalRCMMachines.get(machineIndex).getCurrentCredit())) {

			//System.out.println("\nSorry,Cash insufficient coupon issued for $"
					//+ moneyToBeIssued);
			issue = false;

			return issue;
		   } 
			else {
				//System.out.println("Issuing cash for  $" + moneyToBeIssued);
				double temp = Activator.totalRCMMachines.get(machineIndex)
					.getCurrentCredit();
				temp = temp - moneyToBeIssued;
				Activator.totalRCMMachines.get(machineIndex).setCurrentCredit(temp);

				issue = true;
				return issue;
			}		
	}
}