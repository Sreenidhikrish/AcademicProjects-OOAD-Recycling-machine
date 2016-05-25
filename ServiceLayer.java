package Database;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import EcoRecycle.Activator;
import EcoRecycle.Item;
import EcoRecycle.RCMInfo;
import EcoRecycle.connectToDB;


public class ServiceLayer {

	connectToDB connectToDBObj = new connectToDB();
	Activator activator = new Activator();
	

	public void deleteRCM(){
		
		String deleteRCM;
		try{
			deleteRCM = "DELETE FROM RCM";
			PreparedStatement deleteRCMPstmt = (PreparedStatement) connectToDBObj.conn.prepareStatement(deleteRCM);
			deleteRCMPstmt.executeUpdate();
		}
		catch(SQLException se){
			System.out.println("Remove RCM Exception");
		}
	}
	
	public void addRCM(RCMInfo rcm){
		
		String addRCM;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		
		try{
			
			addRCM = "INSERT INTO RCM VALUES (?,?,?,?,?,?,?,?)";

			PreparedStatement addRCMPstmt = (PreparedStatement) connectToDBObj.conn.prepareStatement(addRCM);
			addRCMPstmt.setString(1, rcm.getMachineId());
			addRCMPstmt.setString(2,rcm.getLocation());
			addRCMPstmt.setString(3,rcm.getOperationalStatus());
			addRCMPstmt.setDouble(4,rcm.getMaxCredit());
			addRCMPstmt.setDouble(5,rcm.getTotalCapacity());
			addRCMPstmt.setDouble(6,rcm.getCurrentCapacity());
			addRCMPstmt.setDate(7,sqlDate);
			addRCMPstmt.setDouble(8,rcm.getCurrentCredit());
			addRCMPstmt.executeUpdate();
			
			
		}
		catch(SQLException se){
			System.out.println("Add RCM Exception");

		}
	} 
	
	public void deleteItem(){
		String removeItem;
		try{
			removeItem = "DELETE FROM ITEM";
			PreparedStatement removeItemPstmt = (PreparedStatement) connectToDBObj.conn
					.prepareStatement(removeItem);
			removeItemPstmt.executeUpdate();

		}
		catch(SQLException se){
			System.out.println("Remove Item Exception");

		}
	}
	
	public void addBasicItem(Item itemObj){

		String addItemSql;
		try{
			addItemSql = "INSERT INTO ITEM VALUES (?,?,?)";									
			PreparedStatement pstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(addItemSql);
			pstmt.setString(1, itemObj.getItemName());
			pstmt.setDouble(2, itemObj.getUnitPrice() );
			pstmt.setDouble(3, itemObj.getMinWeight());
			pstmt.executeUpdate();
		}
		catch(SQLException se){
			System.out.println("Add Item Exception");

		}
	}
	
	public void addItem(Item itemObj){

		String addItemSql;
		try{
			addItemSql = "INSERT INTO ITEM VALUES (?,?,?)";									
			PreparedStatement pstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(addItemSql);
			pstmt.setString(1, itemObj.getItemName());
			pstmt.setDouble(2, itemObj.getUnitPrice() );
			pstmt.setDouble(3, itemObj.getMinWeight());
			pstmt.executeUpdate();
			activator.addItem(itemObj);

			System.out.println("in service layer: itemname: " + itemObj.getItemName());
			System.out.println("in service layer: unitprice: "+itemObj.getUnitPrice());
			System.out.println("in service layer: minweight: "+itemObj.getMinWeight());
		}
		catch(SQLException se){
			System.out.println("Add Item Exception");

		}
	}

	public String checkMachineId(String rcmId){

		String addCheckMachineIdSql;
		ResultSet checkMachineIdResultSet;
		int checkMachineIdflag=0;
		try{
			addCheckMachineIdSql = "SELECT *FROM RCM WHERE MACHINEID=?";
			PreparedStatement addCheckMachineIdPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(addCheckMachineIdSql);
			addCheckMachineIdPstmt.setString(1,rcmId);
			checkMachineIdResultSet = addCheckMachineIdPstmt.executeQuery();	

			checkMachineIdResultSet.beforeFirst();
			if (checkMachineIdResultSet.next()){
				checkMachineIdflag=1;
			}
		}
		catch(SQLException se){
			System.out.println("Add Item Exception");
		}
		if(checkMachineIdflag==1){
			return "Success";
		}
		else{
			return "Failure";
		}
	}

	public void addMachine (RCMInfo newrcmInfo){

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		String addRCMSql;
		String addRCMLastEmtiedSql;
		String addRCMTransactionSql;
		int addMachineFlag=0;

		String rcmId = newrcmInfo.getMachineId();
		String operationalStatus = "Working";
		Double currentCapacity = 0.0;
		Double currentCredit = 0.0;
		try{
			addRCMSql = "INSERT INTO RCM VALUES (?,?,?,?,?,?,?,?)";									
			PreparedStatement addRCMPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(addRCMSql);
			addRCMPstmt.setString(1,newrcmInfo.getMachineId());
			addRCMPstmt.setString(2,newrcmInfo.getLocation());
			addRCMPstmt.setString(3,operationalStatus);
			addRCMPstmt.setDouble(4,newrcmInfo.getMaxCredit());
			addRCMPstmt.setDouble(5,newrcmInfo.getTotalCapacity());
			addRCMPstmt.setDouble(6,currentCapacity);
			addRCMPstmt.setDate(7,sqlDate);
			addRCMPstmt.setDouble(8,currentCredit);
			addRCMPstmt.executeUpdate();
			activator.addMachine(newrcmInfo);
			addMachineFlag=1;
		}
		catch(SQLException se){
			System.out.println("Add Machine Exception");
		}
		if(addMachineFlag==1){

			try{
				addRCMLastEmtiedSql = "create table rcm"+rcmId+"LastEmptied(date timestamp NOT NULL)";
				PreparedStatement addRCMLastEmptiedPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(addRCMLastEmtiedSql);
				addRCMLastEmptiedPstmt.executeUpdate();
			}
			catch(SQLException se){
				System.out.println("Creation of new RCM Last Emptied Exception");
			}
			try{
				addRCMTransactionSql = "create table rcm"+rcmId+"Transaction(Itemname varchar(15) NOT NULL, Weight double NOT NULL, Date timestamp NOT NULL, Credit double NOT NULL)";
				PreparedStatement addRCMTransactionPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(addRCMTransactionSql);
				System.out.println("sql stmt: " +  addRCMTransactionSql);
				addRCMTransactionPstmt.executeUpdate(); 

			}
			catch(SQLException se){
				System.out.println("Creation of new RCM transaction Exception");
			}
		}
	}


	public String checkItemName(String itemName){

		String addCheckItemNameSql;
		ResultSet checkItemNameResultSet;
		int checkItemNameflag=0;
		try{
			addCheckItemNameSql = "SELECT *FROM ITEM WHERE ITEMNAME=?";
			PreparedStatement checkItemNamePStmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(addCheckItemNameSql);
			checkItemNamePStmt.setString(1,itemName);
			checkItemNameResultSet = checkItemNamePStmt.executeQuery();	

			checkItemNameResultSet.beforeFirst();
			if(checkItemNameResultSet.next()){
				checkItemNameflag=1;
			}
		}	
		catch(SQLException se){
			System.out.println("Item name doesn't exist");
		}	

		if(checkItemNameflag==1){
			return "Success";
		}
		else{
			return "Failure";
		}
	}

	public String removeMachine(String removeMachineId){

		String removeMachineSql;
		String removeRCMLastEmptiedSql;
		String removeRCMTransactionSql;
		int removeMachineFlag=0;
		try{

			removeMachineSql = "DELETE FROM RCM WHERE MACHINEID =?";									
			PreparedStatement removeMachinePstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(removeMachineSql);
			removeMachinePstmt.setString(1, removeMachineId);
			removeMachinePstmt.executeUpdate();	
			activator.removeMachine(removeMachineId);

			try{
				removeRCMLastEmptiedSql = "drop table rcm"+removeMachineId+"LastEmptied";
				PreparedStatement removeRCMLastEmptiedPstmt  = (PreparedStatement)connectToDBObj.conn.prepareStatement(removeRCMLastEmptiedSql);
				removeRCMLastEmptiedPstmt.executeUpdate();
			}
			catch(SQLException se){
				System.out.println("Remove Machine Last Emptied Exception");
			}
			try{
				removeRCMTransactionSql = "drop table rcm"+removeMachineId+"Transaction";
				PreparedStatement removeRCMTransactionPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(removeRCMTransactionSql);
				removeRCMTransactionPstmt.executeUpdate(); 
				removeMachineFlag=1;
			}
			catch(SQLException se){
				System.out.println("Remove Machine Transaction Exception");
			}

		}
		catch(SQLException se){
			System.out.println("Remove Machine Exception");
		}
		if(removeMachineFlag==1){
			return "Success";
		}
		else{
			return "Failure";
		}
	}

	public String activate(RCMInfo rcmObj){

		String activateSql;
		String activateUpdateLastEmptiedSql;

		java.util.Date utilDate = new java.util.Date();
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime()); 
		
		
		int activateFlag=0;
		String rcmMachineId = rcmObj.getMachineId();
		try{

			activateSql = "UPDATE RCM SET LASTEMPTIED = ?, CURRENTCAPACITY = ?, CURRENTCREDIT = ?, OPERATIONALSTATUS = ?  WHERE MACHINEID =?";
			PreparedStatement activatePstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(activateSql);
			activatePstmt.setDate(1,sqlDate);
			activatePstmt.setDouble(2,rcmObj.getCurrentCapacity() );
			activatePstmt.setDouble(3, rcmObj.getCurrentCredit());
			activatePstmt.setString(4,rcmObj.getOperationalStatus() );
			activatePstmt.setString(5, rcmObj.getMachineId());

			System.out.println(activateSql);
			activatePstmt.executeUpdate();		
		}
		catch(SQLException se){
			System.out.println("Activate Exception");
		}
		try{
			activateUpdateLastEmptiedSql = "INSERT INTO rcm"+rcmMachineId+"lastemptied VALUES(?)";
			PreparedStatement activateUpdateLastEmptiedPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(activateUpdateLastEmptiedSql);
			activateUpdateLastEmptiedPstmt.setDate(1,sqlDate);
			activateUpdateLastEmptiedPstmt.executeUpdate();
			activateFlag=1;
		}
		catch(SQLException se){
			System.out.println("Activation Exception: while updating lastemptied table");
		}
		if(activateFlag==1){
			return "Success";	
		}
		else{
			return "Failure";
		}
	}

	public String deactivate(RCMInfo rcmObj){

		String deactivateSql;
		int deactivateFlag=0;
		try{
			deactivateSql = "UPDATE RCM SET OPERATIONALSTATUS=?  WHERE MACHINEID =?";
			PreparedStatement deactivatePstmt1 = (PreparedStatement)connectToDBObj.conn.prepareStatement(deactivateSql);
			deactivatePstmt1.setString(1,rcmObj.getOperationalStatus());
			deactivatePstmt1.setString(2,rcmObj.getMachineId());
			deactivatePstmt1.executeUpdate();
			deactivateFlag=1;
		}
		catch(SQLException se){
			System.out.println("Deactivation Exception");
		}
		if(deactivateFlag==1){
			return "Success";
		}
		else{
			return "Failure";
		}
	}

	public int collectedItems(String machineId, String month){

		String collectedItemsSql;
		ResultSet collectedItemsResult;
		int value1=0;
		int value2=0;
		int result=0;
		int monthValue = Integer.parseInt(month);
		System.out.println("monthValue: "+monthValue);

		try{
			collectedItemsSql = "SELECT MONTH(DATE) AS MONTHDATA, COUNT(ITEMNAME) "
					+ "AS COUNTVALUE FROM " +"RCM"+machineId+"TRANSACTION GROUP BY MONTHDATA";
			System.out.println("collectedItemsSql: "+collectedItemsSql);
			PreparedStatement collectedItemsPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(collectedItemsSql);
			collectedItemsResult = collectedItemsPstmt.executeQuery();	

			while(collectedItemsResult.next())
			{
				value1 = collectedItemsResult.getInt("MONTHDATA");
				value2 = collectedItemsResult.getInt("COUNTVALUE");

				if(value1==monthValue){
					System.out.println("value1 and month value are equal");
					result=value2;
					System.out.println("result when month is found: "+result);
				}

			}
		}
		catch(SQLException se){
			System.out.println("Collected Items Exception");
		}
		System.out.println("result: "+result);
		return result;

	}

	public String removeItem(String removeitemName){

		String removeItemSql;
		int removeItemFlag=0;
		try{
			removeItemSql = "DELETE FROM ITEM WHERE ITEMNAME =?";									
			PreparedStatement removeItemPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(removeItemSql);
			removeItemPstmt.setString(1, removeitemName);
			removeItemPstmt.executeUpdate();
			removeItemFlag=1;
		}
		catch(SQLException se){
			System.out.println("Remove Item Exception");
		}
		if(removeItemFlag==1){
			activator.removeItem(removeitemName);
			return "Success";
		}
		else{
			return "Failure";
		}
	}

	public String updatePrice(Double newprice, String itemname){

		String updateItemPriceSql;
		ResultSet updateItemPriceResult;

		try{
			updateItemPriceSql = "UPDATE ITEM SET UNITPRICE =? WHERE ITEMNAME=?";
			PreparedStatement updateItemPricePstmt1 = (PreparedStatement)connectToDBObj.conn.prepareStatement(updateItemPriceSql);
			updateItemPricePstmt1.setDouble(1,newprice);
			updateItemPricePstmt1.setString(2,itemname);
			updateItemPricePstmt1.executeUpdate();	
			activator.updatePrice(itemname,newprice);

			return "Success";
		}
		catch(SQLException se){
			System.out.println("Update Item Price Exception");
		}
		return "Failure";

	}

	public void creditDay(String tablename,RCMInfo rcmObj){

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String sqlDateCurrent = df.format(new Date());
		System.out.println("currDate iss" + sqlDateCurrent);

		String creditDaySql;
		PreparedStatement creditDayPstmt;
		ResultSet creditDayResultSet;

		try{
			creditDaySql = "SELECT DATE, sum(credit) AS credittotal FROM " +tablename+" GROUP BY DATE ORDER BY DATE ASC";

			System.out.println(creditDaySql);
			creditDayPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(creditDaySql);
			creditDayResultSet = creditDayPstmt.executeQuery();

			while(creditDayResultSet.next()){

				Date dateValue = creditDayResultSet.getDate("Date");	 
				System.out.println("date is " +dateValue);	
				//X axis
				rcmObj.reportgenObj.scores2.add(dateValue);

				Double creditValue = creditDayResultSet.getDouble("CreditTotal");
				System.out.println("credit is " +creditValue);
				//Y axis
				rcmObj.reportgenObj.scores.add(creditValue);

			}
			creditDayResultSet.close(); 
		}
		catch(SQLException se){
			System.out.println("Credit day Exception");
		}
	}

	public void creditWeek(String tablename, RCMInfo rcmObj){

		String creditWeekSql;
		PreparedStatement creditWeekPstmt;
		ResultSet creditWeekResultSet;

		try{
			creditWeekSql = "SELECT WEEK( DATE) AS WEEKNO, SUM(CREDIT) AS CREDITTOTAL FROM "+tablename+
					" GROUP BY WEEKNO" + " ORDER BY WEEKNO ASC";
			System.out.println(creditWeekSql);
			creditWeekPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(creditWeekSql);

			creditWeekResultSet =creditWeekPstmt.executeQuery();

			while(creditWeekResultSet.next()){

				Double weekValue = creditWeekResultSet.getDouble("WeekNo");	 
				System.out.println("week is " +weekValue);
				rcmObj.reportgenObj.scores2.add(weekValue);

				Double creditValue = creditWeekResultSet.getDouble("CreditTotal");
				System.out.println("credit is " +creditValue);
				rcmObj.reportgenObj.scores.add(creditValue);


			}

		} 
		catch(SQLException se){
			System.out.println(" exception in credit week");
			se.printStackTrace();
		}			
	}

	public void weightDay(String tablename, RCMInfo rcmObj){

		String weightDaySql;
		PreparedStatement weightDayPstmt;
		ResultSet weightDayResultSet;

		try{
			weightDaySql = "SELECT DATE, sum(WEIGHT) AS WEIGHTTOTAL FROM " +tablename+" GROUP BY DATE ORDER BY DATE ASC";

			System.out.println(weightDaySql);
			weightDayPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(weightDaySql);
			weightDayResultSet = weightDayPstmt.executeQuery();

			while(weightDayResultSet.next()){

				Date dateValue = weightDayResultSet.getDate("Date");	 
				System.out.println("date is " +dateValue);
				rcmObj.reportgenObj.scores2.add(dateValue);

				Double weightValue = weightDayResultSet.getDouble("WEIGHTTotal");
				System.out.println("weightValue " +weightValue);
				rcmObj.reportgenObj.scores.add(weightValue);

			} 

		} 
		catch(Exception se){
			System.out.println(" exception in day-weight");
			se.printStackTrace();
		}

	}

	public void weightWeek(String tablename,RCMInfo rcmObj){

		String weightWeekSql;
		PreparedStatement weightWeekPstmt;
		ResultSet weightWeekResultSet;

		try{
			weightWeekSql = "SELECT WEEK( DATE) AS WEEKNO, SUM(weight) AS WEIGHTTOTAL FROM "+tablename+
					" GROUP BY WEEKNO" + " ORDER BY WEEKNO ASC";
			System.out.println(weightWeekSql);
			weightWeekPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(weightWeekSql);

			weightWeekResultSet =weightWeekPstmt.executeQuery();

			while(weightWeekResultSet.next()){
				Double weekValue = weightWeekResultSet.getDouble("WeekNo");	 
				System.out.println("week is " +weekValue);
				rcmObj.reportgenObj.scores2.add(weekValue);

				Double weightValue = weightWeekResultSet.getDouble("WEIGHTTotal");
				System.out.println("weightValue is " +weightValue);
				rcmObj.reportgenObj.scores.add(weightValue);
			}

		} 
		catch(Exception se){
			System.out.println("exception in week-weight");
			se.printStackTrace();
		}						

	}

	public String emptyMachine(RCMInfo rcmObj)
	{

		java.util.Date utilDate = new java.util.Date();
		
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
		String rcmIdValue = rcmObj.getMachineId();
		int emptyMachineflag =0;
		try
		{

			String emptyMachineSql = "UPDATE RCM SET LASTEMPTIED=?, CURRENTCAPACITY = ? WHERE MACHINEID =?";
			PreparedStatement emptyMachinePstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(emptyMachineSql);
			emptyMachinePstmt.setTimestamp(1,sqlDate);
			emptyMachinePstmt.setDouble(2,rcmObj.getCurrentCapacity());
			emptyMachinePstmt.setString(3,rcmIdValue);
			emptyMachinePstmt.executeUpdate();
		} catch(Exception se){
			System.out.println("exception while updating lastemptied table");
		}

		try{


			String sql1 = "INSERT INTO rcm"+rcmIdValue+"lastemptied VALUES(?)";
			PreparedStatement pstmt1 = (PreparedStatement)connectToDBObj.conn.prepareStatement(sql1);
			pstmt1.setTimestamp(1,sqlDate);
			pstmt1.executeUpdate();
			emptyMachineflag =1;

		} catch(Exception se){
			System.out.println("exception while updating lastemptied table");
		}

		if(emptyMachineflag ==1)

		{
			return "Success";

		}
		else
		{
			return "Failure";

		}

	}

	public String mostUsedRCM()
	{


		String mostUsedMachineId;
		int count=Activator.totalRCMMachines.size();
		double [] temparray = new double[count];
		System.out.println("count: "+count);
		int index=0;
		int indexOfMax=0;
		String mostUsedRCMSql;
		for (int i=1; i<=count;i++){
			String tableName = "RCM"+i+"TRANSCATION";
			try{

				mostUsedRCMSql = "SELECT COUNT(ITEMNAME)FROM " +"RCM"+i+"TRANSACTION";
				System.out.println("sql:" +mostUsedRCMSql);

				PreparedStatement mostUsedRCMPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(mostUsedRCMSql);
				ResultSet r = mostUsedRCMPstmt.executeQuery();

				while(r.next()){
					Double mostUsedRCMCount = r.getDouble("count(itemname)");
					System.out.println("mostUsedRCMCount: "+mostUsedRCMCount);
					temparray[index] = mostUsedRCMCount;
					System.out.println("temparray[index]:  " +temparray[index]);
				}

			}
			catch(SQLException se){
				System.out.println("most used rcm exception");
				se.printStackTrace();
			}
			index++;
		}//for
		double maxScore=0.0;

		for(int k=0; k<count; k++){
			if(maxScore<temparray[k]){
				maxScore=temparray[k];
				System.out.println("maxscore: "+maxScore);
			}

		}
		System.out.println("max score is: "+maxScore);
		for(int j=0;j<count;j++){
			if (maxScore == temparray[j]){
				indexOfMax = j;
				System.out.println("indexOfMax " +indexOfMax);
			}

		}

		return (Integer.toString(indexOfMax +1));	

	}
	
	public String updateRCMTransactionCurrentCapacity(String machineId,String rcmTableName, 
			String operationalStatus, double totalCapacity){
		
		String updateRCMTransactionCurrentCapacitySql;
		
		try{
			updateRCMTransactionCurrentCapacitySql = "UPDATE "
					+ rcmTableName
					+ " SET OPERATIONALSTATUS = ?, CURRENTCAPACITY=? WHERE MACHINEID = ?";
			System.out.println(updateRCMTransactionCurrentCapacitySql);

			PreparedStatement updateRCMTransactionCurrentCapacityPstmt = (PreparedStatement)connectToDBObj.conn.prepareStatement(updateRCMTransactionCurrentCapacitySql);
			updateRCMTransactionCurrentCapacityPstmt.setString(1, operationalStatus);
			updateRCMTransactionCurrentCapacityPstmt.setDouble(2, totalCapacity);
			updateRCMTransactionCurrentCapacityPstmt.setString(3, machineId);
			updateRCMTransactionCurrentCapacityPstmt.executeUpdate();
			
			return "Success";
		}
		catch(SQLException se){
			System.out.println("Credit Calculator Service class: Update RCM transaction current capacity exception");
			se.printStackTrace();
		}
		return "Failure";
	}

	
	public String updateRCMCurrentCapacity(double currentCapacity,String machineId){
		
		String updateRCMCurrentCapacitySql;	
		try{
			
			updateRCMCurrentCapacitySql = "UPDATE RCM SET CURRENTCAPACITY = ? WHERE MACHINEID = ?";
			System.out.println(updateRCMCurrentCapacitySql);
			PreparedStatement updateRCMCurrentCapacitySqlPstmt = (PreparedStatement) connectToDBObj.conn.prepareStatement(updateRCMCurrentCapacitySql);
			updateRCMCurrentCapacitySqlPstmt.setDouble(1, currentCapacity);
			updateRCMCurrentCapacitySqlPstmt.setString(2, machineId);
			updateRCMCurrentCapacitySqlPstmt.executeUpdate();
			return "Success";
		}
		catch(SQLException se){
			System.out.println("Credit Calculator: Update RCM exception");
			se.printStackTrace();
		}
		return "Failure";
	}
	
	public String updateRCMTransaction(String rcmTableName, String itemData, double itemWeight, double moneyToBeIssued){
		
		String updateRCMTransactionSql;
		java.util.Date utilDate = new java.util.Date();
		java.sql.Timestamp sqlDate = new java.sql.Timestamp(utilDate.getTime());
		
		try{
			
			updateRCMTransactionSql = "INSERT INTO " + rcmTableName + " VALUES(?,?,?,?)";

			PreparedStatement updateRCMTransactionPstmt = (PreparedStatement) connectToDBObj.conn
					.prepareStatement(updateRCMTransactionSql);

			updateRCMTransactionPstmt.setString(1, itemData);
			updateRCMTransactionPstmt.setDouble(2, itemWeight);
			updateRCMTransactionPstmt.setTimestamp(3,sqlDate);
			updateRCMTransactionPstmt.setDouble(4, moneyToBeIssued);
			updateRCMTransactionPstmt.executeUpdate();

			return "Success";
			
		}
		catch(SQLException se){
			System.out.println("Credit Calculator: Update RCM Transaction exception");
			se.printStackTrace();
		}
		return "Failure";
	}
	
	public double issueCredit(String rcmTableName, Date fromDate,Date currDate){
		
		String issueCreditSql;
		ResultSet issueCreditResultSet;
		double creditValue = 0.0;
		
		java.sql.Timestamp fromDateSql = new java.sql.Timestamp(fromDate.getTime());	
		java.sql.Timestamp currDateSql = new java.sql.Timestamp(currDate.getTime());

		
		try{
			
			issueCreditSql = "SELECT SUM(CREDIT) AS CREDITVALUE FROM "
					+ rcmTableName + " where date between ? and ?";

			System.out.println(issueCreditSql);
			PreparedStatement issueCreditPstmt4 = (PreparedStatement) connectToDBObj.conn
					.prepareStatement(issueCreditSql);
			issueCreditPstmt4.setTimestamp(1, fromDateSql);
			issueCreditPstmt4.setTimestamp(2, currDateSql);

			issueCreditResultSet = issueCreditPstmt4.executeQuery();
			
			
			while (issueCreditResultSet.next()) {
				creditValue = issueCreditResultSet.getDouble("CreditValue");
				System.out.println("credit fm Service class" + creditValue);			
			}
			
		}
		catch(SQLException se){
			System.out.println("Credit Calculator Service class: issue credit exception");
			se.printStackTrace();
		}
		System.out.println("creditValue in Service class : "+creditValue);
		return creditValue;
	}
	
	public void updateCurrentCredit(double currentCredit, String creditIssueMachineId){
		
		String updateCurrentCreditSql;	
		try{
			updateCurrentCreditSql = "UPDATE RCM SET CURRENTCREDIT = ? WHERE MACHINEID = ?";
			System.out.println(updateCurrentCreditSql);
			PreparedStatement updateCurrentCreditPstmt = (PreparedStatement) connectToDBObj.conn
					.prepareStatement(updateCurrentCreditSql);
			updateCurrentCreditPstmt.setDouble(1, currentCredit);
			updateCurrentCreditPstmt.setString(2, creditIssueMachineId);
			updateCurrentCreditPstmt.executeUpdate();

		}
		catch(SQLException se){
			System.out.println("Credit Calculator Service class: update current credit exception");
			se.printStackTrace();
		}
	}
}


