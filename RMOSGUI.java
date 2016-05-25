package GUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.border.Border;

import Database.ServiceLayer;
import EcoRecycle.Activator;
import EcoRecycle.Item;
import EcoRecycle.RCMInfo;
import EcoRecycle.ReportGenerator.ReportGeneratorPanel;
import EcoRecycle.connectToDB;

public class RMOSGUI implements ActionListener  {

	Activator act = new Activator();
	RCMInfo rcmObj = new RCMInfo();
	ServiceLayer serviceObj = new ServiceLayer();
	//ReportGenerator reportgenRMOSObj = new ReportGenerator ();
	int removeMachineFlag=0;
	
	String user = "admin";
	String password = "password";
	
	//changed
	ArrayList<Double>score = new ArrayList<Double>();
	ArrayList<Object>score2 = new ArrayList<Object>();
	Date startDate;
    //*
	JFrame guiFrameRMOS;
	CardLayout rmosCards;
	JPanel buttonPanel;
	JPanel rmosCardPanel;
	
	JPanel topPanelRMOS;
	JPanel middlePanelRMOS;
	JPanel bottomPanelRMOS;
	JPanel panel1;
	JPanel panel2;
	JPanel panel3;
	JPanel panel4;

	//ch
	JLabel xAxisInfo  = new JLabel("");
	JLabel yAxisInfo  = new JLabel(""); 
	String 	xAxisData ="";
	String	yAxisData ="";
	String	graphHeading ="";
	String  dateGraphMessage ="";
	JLabel headingInfo = new JLabel("" + graphHeading); 
	String mostUsedMachineId = "";
	String mostUsedMachineLocation ="";
	
	java.sql.Date sqlDate;
	JPanel statisticsCard;
	JPanel showGraphCard;
	
	JRadioButton creditButton;
	JRadioButton weightButton;
	
	JRadioButton rcm1;
	JRadioButton rcm2;
	
	
	String sqlDateFromUser;
	JTextField rcmMessageTextFieldStatistics = new JTextField();

	//*
	
	JPanel rmosFirstCard;
	JPanel rmosSecondCard;

	JButton addMachine;
	JButton removeMachine;
	JButton activate;
	JButton deactivate;
	JButton emptyMachine;

	JButton operationalStatus;
	JButton creditStatus;
	JButton lastEmptied;
	JButton collectedItems;
	JButton capacity;
	JButton mostUsedRCM;

	JButton addItem;
	JButton removeItem;
	JButton updatePrice;

	JRadioButton day;
	JRadioButton week;

	JTabbedPane tabbedPane;
	JTextField TextField1;
	JTextField  updateItemPriceTextField;
	String operationalStatusMachineID;

	/**
	 * Tabbed Pane Panel
	 */
	public void UpdateRCM() {
		panel1 =  new JPanel();


		panel1.setBackground(new Color(255,235,205));
		addMachine = new JButton("Add Machine");
		removeMachine= new JButton("Remove Machine");
		activate = new JButton("Activate");
		deactivate = new JButton("Deactivate");
		emptyMachine = new JButton("Empty Machine");

		panel1.setLayout(new FlowLayout());
		panel1.add(addMachine);
		panel1.add(removeMachine);
		panel1.add(activate);
		panel1.add(deactivate);
		panel1.add(emptyMachine);
		panel1.setComponentOrientation(
				ComponentOrientation.LEFT_TO_RIGHT);

		addMachine.addActionListener(this);
		removeMachine.addActionListener(this);
		activate.addActionListener(this);
		deactivate.addActionListener(this);
		emptyMachine.addActionListener(this);

	}

	/**
	 * Tabbed Pane Panel
	 */

	public void Status() {

		panel2 =  new JPanel();

		panel2.setBackground(new Color(255,235,205));
		operationalStatus = new JButton("Operational Status");
		creditStatus = new JButton("Credit Status");
		lastEmptied = new JButton("Last Emptied");
		collectedItems = new JButton("Collected Items");
		capacity = new JButton("Capacity");
		mostUsedRCM = new JButton("Most Used RCM"); 

		panel2.setLayout(new FlowLayout());
		panel2.add(operationalStatus);
		panel2.add(creditStatus);
		panel2.add(lastEmptied);
		panel2.add(collectedItems);
		panel2.add(capacity);
		panel2.add(mostUsedRCM);
		panel2.setComponentOrientation(
				ComponentOrientation.LEFT_TO_RIGHT);

		operationalStatus.addActionListener(this);
		creditStatus.addActionListener(this);
		lastEmptied.addActionListener(this);
		collectedItems.addActionListener(this);
		capacity.addActionListener(this);
		mostUsedRCM.addActionListener(this); 
	}   


	/**
	 * Tabbed Pane Panel
	 */
	public void Item() {

		panel3 =  new JPanel();

		panel3.setBackground(new Color(255,235,205));
		addItem = new JButton("Add Item");
		removeItem = new JButton("Remove Item");
		updatePrice = new JButton("Update Price");			

		panel3.setLayout(new FlowLayout());
		panel3.add(addItem);
		panel3.add(removeItem);
		panel3.add(updatePrice);

		panel3.setComponentOrientation(
				ComponentOrientation.LEFT_TO_RIGHT);

		addItem.addActionListener(this);
		removeItem.addActionListener(this);
		updatePrice.addActionListener(this);

	}    
	
	/**
	 * Tabbed Pane Panel
	 */

	public void Statistics() {
		panel4 =  new JPanel();

		ButtonGroup group1 = new ButtonGroup();
		group1.clearSelection();
		panel4.setBackground(new Color(255,235,205));
		day = new JRadioButton("Day");
		week = new JRadioButton("Week");

		group1.add(day);
		group1.add(week);
		panel4.setLayout(new FlowLayout());
		panel4.add(day);
		panel4.add(week);

		day.addActionListener(this);
		week.addActionListener(this);
		panel4.setComponentOrientation(
				ComponentOrientation.LEFT_TO_RIGHT);

	}    
	
	/**
	 * Action Listener
	 */

	public void actionPerformed( ActionEvent evt ) {
		if(evt.getSource() == addMachine) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Add Machine"); 
		}
		else if(evt.getSource() == removeMachine) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Remove Machine"); 
		}
		else if(evt.getSource() == activate) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Activate Card"); 
		} 
		else if(evt.getSource() == deactivate) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"deactivate Card"); 
		}
		else if(evt.getSource() == emptyMachine) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Empty Machine"); 
		} 
		else if(evt.getSource() == operationalStatus) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Operational Status Card"); 
		} 
		else if(evt.getSource() == creditStatus) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Credit Status Card"); 
		} 
		else if(evt.getSource() == lastEmptied) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Last Emptied Status Card"); 
		} 
		else if(evt.getSource() == collectedItems) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Collected Items Card"); 
		} 
		else if(evt.getSource() == capacity) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Capacity Card"); 
		} 
		else if(evt.getSource() == mostUsedRCM) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Most Used RCM"); 
		} 		  
		else if(evt.getSource() == addItem) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Add Item Card"); 
		}
		else if(evt.getSource() == removeItem) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Remove Item Card"); 
		} 
		else if(evt.getSource() == updatePrice) {
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Update Item Price Card"); 
		} 
		else if(evt.getSource() == day) {

			week.setSelected(false);
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Select the Machine"); 
		} 	
		else if(evt.getSource() == week) {
			
			day.setSelected(false);
			CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
			cl.show(rmosCardPanel,"Select the Machine"); 
		} 	
	}


	public RMOSGUI() { 

		/**
		 * required for persistence
		 */
		connectToDB connectToDBObj = new connectToDB(); 
		String OperationalStatus;

		/**
		 * RMOS Frame
		 */
		guiFrameRMOS = new JFrame();
		guiFrameRMOS.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrameRMOS.setTitle("EcoRecycle Monitoring Station");
		guiFrameRMOS.setSize(500,300);

		//This will center the JFrame in the middle of the screen
		guiFrameRMOS.setLocationRelativeTo(null);
		guiFrameRMOS.setLayout(new BorderLayout());

		//creating a border to highlight the JPanel areas
		Border outline = BorderFactory.createLineBorder(Color.black);

		/**
		 * top Panel
		 */
		JPanel topPanelRMOS = new JPanel();
		topPanelRMOS.setBorder(outline);
		topPanelRMOS.setBackground(Color.blue);
		topPanelRMOS.setPreferredSize(new Dimension(400, 125));
		topPanelRMOS.setLayout(new BoxLayout(topPanelRMOS,BoxLayout.Y_AXIS));

		JPanel topMainPanel = new JPanel();
		topMainPanel.setLayout(new FlowLayout( FlowLayout.CENTER, 598,5));
		topMainPanel.setComponentOrientation(
				ComponentOrientation.LEFT_TO_RIGHT);
		topMainPanel.setBackground(Color.blue);
		JLabel welcomeTextArea = new JLabel("Welcome to EcoRecycle Monitoring Station");

		topMainPanel.add(welcomeTextArea);

		JLabel DateLabel = new JLabel(); 
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		//get current date time with Date()
		Date date = new Date();
		dateFormat.format(date);
		String s = dateFormat.format(date);
		DateLabel.setText(s);
		topMainPanel.add(DateLabel);
		topPanelRMOS.add(topMainPanel,BoxLayout.X_AXIS); 


		JPanel picPanel1 = new JPanel();
		picPanel1.setBorder(outline); 
		picPanel1.setBackground(new Color(255,235,205));
		picPanel1.setLayout(new FlowLayout());

		JLabel username = new JLabel("Username"); 
		JTextArea Admin = new JTextArea(1,10);
		JScrollPane scrollPane = new JScrollPane( Admin );
		
		Admin.setBackground(Color.white);
		Admin.setForeground(Color.black);


		JLabel password = new JLabel("Password"); 
		JTextArea passwordTextArea = new JTextArea(1,10);
		JScrollPane scrollPane1 = new JScrollPane( passwordTextArea );

		passwordTextArea.setBackground(Color.white);
		passwordTextArea.setForeground(Color.black);

		picPanel1.add(username);
		picPanel1.add(Admin);
		picPanel1.add(password); 
		picPanel1.add(passwordTextArea); 
		
		JButton buttonLogin = new JButton("LOGIN");
		picPanel1.add(Box.createHorizontalStrut(20));
		picPanel1.add( buttonLogin);
		picPanel1.add(Box.createHorizontalStrut(200));
		
		
		//Icon iconhome1 = new ImageIcon("src/homebutton.jpg");
		JButton buttonHome1 = new JButton("HOME");
		
				
		buttonHome1.setPreferredSize(new Dimension(100,20));
		
		/**
		 * Admin Login check
		 */
		buttonLogin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				
				
				if((Admin.getText()).equals("admin") && ((passwordTextArea.getText()).equals("password"))){
				
				//middlePanelRMOS.setVisible(false);
				Admin.setText("");
				passwordTextArea.setText("");
				JOptionPane.showMessageDialog(picPanel1,"Login successful");
				CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
				cl.show(rmosCardPanel, "Start");
			}
			else{
				Admin.setText("");
				passwordTextArea.setText("");
				JOptionPane.showMessageDialog(picPanel1,"Username or password is invalid");
			}
		}

		});

		buttonHome1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				
				CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
				cl.show(rmosCardPanel, "Start");

			}

		});
		//*
		picPanel1.add(buttonHome1);

		topPanelRMOS.add(picPanel1,BoxLayout.Y_AXIS);
		guiFrameRMOS.add(topPanelRMOS,BorderLayout.NORTH);
		//end of Top Panel
		
		/**
		 * middle panel
		 */
		JPanel middlePanelRMOS = new JPanel();
		middlePanelRMOS.setBorder(outline);
		middlePanelRMOS.setBackground(Color.BLUE);
		middlePanelRMOS.setPreferredSize(new Dimension(400, 50));

		// middlePanelRMOS.setLayout(new FlowLayout());
		JTabbedPane tabbedPane = new JTabbedPane();

		UpdateRCM();
		Status();
		Item();
		Statistics();

		tabbedPane.addTab("Update RCM", panel1);
		tabbedPane.addTab("Status", panel2);
		tabbedPane.addTab("Item",  panel3);
		tabbedPane.addTab("Statistics",  panel4);

		middlePanelRMOS.add(tabbedPane);
		guiFrameRMOS.add(middlePanelRMOS, BorderLayout.CENTER);
		

		/**
		 * bottom panel
		 */
		JPanel bottomPanelRMOS = new JPanel();
		bottomPanelRMOS.setBorder(outline);
		bottomPanelRMOS.setBackground(Color.green);
		bottomPanelRMOS.setPreferredSize(new Dimension(400,500));
		bottomPanelRMOS.setLayout(new CardLayout());

		/*
		 * Card Layout
		 */
		rmosCards = new CardLayout();
		rmosCardPanel = new JPanel(new CardLayout());
		rmosCardPanel.setPreferredSize(new Dimension(400,500));
		rmosCardPanel.setBackground(new Color(255,235,205));
		// set the card layout
		rmosCardPanel.setLayout(rmosCards);

		/**
		 * FIRST CARD
		 */
		JPanel rmosFirstCard = new JPanel();
		JLabel startMessage = new JLabel("RMOS home screen");    
		Icon iconStart = new ImageIcon("src/home.jpg");
		JButton buttonstart = new JButton(iconStart);
		rmosFirstCard.setBackground(new Color(255,235,205));

		buttonstart.setPreferredSize(new Dimension(400,400));
		rmosFirstCard.add(startMessage);
		rmosFirstCard.add(buttonstart);
		//end of First Card
		
		//ch
		// Credit/ Weight Selection CARD
		
		JPanel creditWeightSelection = new JPanel();
		creditWeightSelection.setLayout(new BoxLayout(creditWeightSelection,BoxLayout.Y_AXIS));
		creditWeightSelection.setBackground(new Color(255,235,205));
		JLabel messageLabel2 = new JLabel("Select the category");
		creditWeightSelection.add(Box.createVerticalStrut(15)); 
		messageLabel2.setAlignmentX(messageLabel2.CENTER_ALIGNMENT);
		creditWeightSelection.add( messageLabel2);
		
		
		ButtonGroup group3 = new ButtonGroup();
		group3.clearSelection();
		creditButton = new JRadioButton("Credit");
		creditButton.setAlignmentX(creditButton.CENTER_ALIGNMENT);
		
		
		creditWeightSelection.add(Box.createVerticalStrut(15)); 
		weightButton= new JRadioButton("Weight");

		weightButton.setAlignmentX(weightButton.CENTER_ALIGNMENT);
		
		
		JButton proceedButton = new JButton("PROCEED");
		proceedButton.setAlignmentX(proceedButton.CENTER_ALIGNMENT);
		
		group3.add(creditButton);
		group3.add(weightButton);
		creditWeightSelection.add(creditButton);
		creditWeightSelection.add(weightButton);
		

		creditWeightSelection.add(proceedButton);
		
		/**
		 * Based on selection(day/week for credit/weight for rcm1/rcm2) setting the X-axis, Y-axis and the names of the graph
		 */
		proceedButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				String data = df.format(new Date());
				
				xAxisData ="";
				yAxisData ="";
				graphHeading =" Plot of ";
				dateGraphMessage = " . The date values are for the Period from  2015/08/08" + " to " + data;
				
				String dateGetTextValue="";
				String categoryName=""; 
				String tableName="";
				
				
				for (RCMInfo rcmObj : Activator.totalRCMMachines) {
					if(rcmObj.getMachineId().equals(rcmMessageTextFieldStatistics.getText()))
					{
												
						tableName = "RCM"+rcmMessageTextFieldStatistics.getText()+ "TRANSACTION";

				if(creditButton.isSelected()){
					//System.out.println("inside credit button");
					
					weightButton.setSelected(false);
				//	categoryName = "Credit";
					yAxisData = " Y Axis: Credit (in dollars) ";
			    	//System.out.println(" Yaxis" +yAxisData );
			    	yAxisInfo.setText(" Y Axis: " + yAxisData );
			    	graphHeading += yAxisData;
			    	headingInfo.setText("" + graphHeading + tableName +dateGraphMessage);
				}

				else if(weightButton.isSelected()){
					
					//System.out.println("inside weight button");
					
					
					creditButton.setSelected(false);
				//	categoryName = " Weight";
					yAxisData = " Y Axis: Weight (in pounds) ";
			    	//System.out.println(" Y Axis" +yAxisData );
			    	yAxisInfo.setText("Y Axis: " + yAxisData );
			    	graphHeading += yAxisData;
			    	headingInfo.setText("" + graphHeading + tableName+dateGraphMessage); 
				}
				
				//System.out.println("outside of the weight button selection");		
				
				if(day.isSelected())
				{
				
				if(creditButton.isSelected()){

				
						
						xAxisData = " X Axis: Date ";
				    	//System.out.println(" Xaxis" +xAxisData );
				    	yAxisInfo.setText(" X Axis: " + xAxisData );
				    	graphHeading += xAxisData;
				    	headingInfo.setText("" + graphHeading + tableName+dateGraphMessage); 
						
						try{
							
							rcmObj.reportgenObj.clearScore();
							serviceObj.creditDay(tableName, rcmObj);	
						} 
						catch(Exception s){
							System.out.println(" exception in day-credit in gui");
							s.printStackTrace();
						}
					}	
				
				else if(weightButton.isSelected())
				{

					
					xAxisData = " X Axis: Date ";
			    	System.out.println(" X Axis" +xAxisData );
			    	xAxisInfo.setText(" X Axis: " + xAxisData );	
			    	graphHeading += xAxisData;
			    	headingInfo.setText("" + graphHeading + tableName+dateGraphMessage);
					
					try{
						rcmObj.reportgenObj.clearScore();
						serviceObj.weightDay(tableName,rcmObj);	
					}
					catch(Exception s){
						System.out.println(" exception in day-weight in gui");
						s.printStackTrace();
					}

				}
				}
			else if(week.isSelected()){
				
				if(creditButton.isSelected()){
				
						
						xAxisData = " X Axis: Week ";
				    	//System.out.println(" X Axis" +xAxisData );
				    	xAxisInfo.setText(" X Axis: " + xAxisData );
				    	graphHeading += xAxisData;
				    	headingInfo.setText("" + graphHeading + tableName+dateGraphMessage); 
						
						try{
							rcmObj.reportgenObj.clearScore();	
							serviceObj.creditWeek(tableName,rcmObj);
					}
					catch(Exception s){
							System.out.println(" exception in day-credit in gui");
							s.printStackTrace();
					}
				}
			
			else if(weightButton.isSelected()){
						
					
						
						xAxisData = " X Axis: Week  ";
				    //	System.out.println(" X Axis" +xAxisData );
				    	xAxisInfo.setText(" X Axis: " + xAxisData );
				    	graphHeading += xAxisData;	
				    	headingInfo.setText("" + graphHeading + tableName+dateGraphMessage);
						
				    	try{
				    		rcmObj.reportgenObj.clearScore();
							serviceObj.weightWeek(tableName,rcmObj);	
						}
						catch(Exception s){
							System.out.println(" exception in week-weight in gui");
							s.printStackTrace();
						}							
					}
				}

				CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
				cl.show(rmosCardPanel,"Show Graph"); 
			}
		  }
		}
	});


		/**
		 * adding graph to the RMOS Frame
		 */
		
		//SHOW GRAPH CARD
		JPanel showGraphCard = new JPanel();
		showGraphCard.setLayout(new BorderLayout());
		JButton displayGraph = new JButton("Display Graph");
		displayGraph.setPreferredSize(new Dimension(50,50));
		showGraphCard.add(displayGraph, BorderLayout.NORTH);
		showGraphCard.setBackground(new Color(255,235,205));
		
		displayGraph.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{
				 showGraphCard.removeAll();
				 
					for (RCMInfo rcmObj : Activator.totalRCMMachines) {
						if(rcmObj.getMachineId().equals(rcmMessageTextFieldStatistics.getText()))
						{
				 String machineNameFromUser = "RCM"+rcmMessageTextFieldStatistics.getText();

				 JLabel genfo = new JLabel("Note : Week/Day Number-0 denotes First Week/Day ");
				 showGraphCard.add( genfo,BorderLayout.EAST);
				 
				 headingInfo.setAlignmentX(headingInfo.CENTER_ALIGNMENT);
				 showGraphCard.add(Box.createVerticalStrut(15)); 
				 showGraphCard.add(headingInfo, BorderLayout.NORTH);


				 xAxisInfo = new JLabel("" + xAxisData);
				 yAxisInfo = new JLabel("" + yAxisData);
				 showGraphCard.add(xAxisInfo, BorderLayout.SOUTH);				
				 showGraphCard.add(yAxisInfo,BorderLayout.WEST);

				xAxisData = "";
				yAxisData = "";
				graphHeading = "";
				 
				//System.out.println("reached show card");
		
				ReportGeneratorPanel gp2 = rcmObj.reportgenObj.new  ReportGeneratorPanel();
				gp2.repaint();
			    showGraphCard.add(gp2);
			    guiFrameRMOS.setVisible(true); 

						}
					}
			}
			});

		//SHOW GRAPH FAILURE CARD	
		JPanel showGraphCardFailure = new JPanel();
		showGraphCardFailure.setBackground(new Color(255,235,205));
		JLabel rcmMessageFailure = new JLabel( "The machine id doesnot exist, Please try again");
		 rcmMessageFailure.setAlignmentX( rcmMessageFailure.CENTER_ALIGNMENT);
		 showGraphCardFailure.add(Box.createVerticalStrut(5)); 
		 showGraphCardFailure.add(rcmMessageFailure  );
			
		
		//SECOND CARD
		JPanel rmosSecondCard = new JPanel();
		rmosSecondCard.setBackground(new Color(255,235,205));
		rmosSecondCard.setLayout(new BoxLayout(rmosSecondCard, BoxLayout.Y_AXIS));
		JLabel rcmMessage = new JLabel( "Please enter the machine that you want to access");
		 rcmMessage.setAlignmentX( rcmMessage.CENTER_ALIGNMENT);
		 rmosSecondCard.add(Box.createVerticalStrut(5)); 
		 rmosSecondCard.add(rcmMessage  );
			
		
		rcmMessageTextFieldStatistics.setMaximumSize(new Dimension(200,30));
		rcmMessageTextFieldStatistics.setAlignmentX( rcmMessageTextFieldStatistics.CENTER_ALIGNMENT);
		rmosSecondCard.add(Box.createVerticalStrut(10)); 
		rmosSecondCard.add(rcmMessageTextFieldStatistics);
		
		
		JButton buttonProceed = new JButton("PROCEED");
		buttonProceed.setAlignmentX( buttonProceed.CENTER_ALIGNMENT);
		rmosSecondCard.add(Box.createVerticalStrut(15)); 
				
		rmosSecondCard.add(buttonProceed);
			
		/**
		 * to check if the entered machine ID exists or not
		 */
		buttonProceed.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{
				String rcmIdValue = rcmMessageTextFieldStatistics.getText();
				String checkaddMachine =  serviceObj.checkMachineId(rcmIdValue);	
				  	    	
		    	if(checkaddMachine.equals("Success"))
		    	{
		    		CardLayout c3 = (CardLayout)rmosCardPanel.getLayout();
		    		c3.show(rmosCardPanel,"Credit Weight Selection");    
		    	}
		    	else
		    	{
		  		  CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
		        cl.show(rmosCardPanel,"Show Graph Failure Card");  
		    	} 	
			}
		});
		//end of Second Card

		/**
		 * Add New Machine
		 */
		JPanel addMachineCard = new JPanel();
		addMachineCard.setBackground(new Color(255,235,205));
		addMachineCard.setBorder( BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
		GroupLayout addMachinelayout = new GroupLayout(addMachineCard);
		addMachineCard.setLayout( addMachinelayout );
		addMachinelayout.setAutoCreateGaps( true );
		addMachineCard.setPreferredSize(new Dimension(400,400));

		JLabel addMachineMessageLabel = new JLabel( "Please enter the details for the new machine" );
		JButton addMachineSave =  new JButton("Save");
		JLabel rcmIdLabel = new JLabel( "Machine Id" );
		JTextField rcmIdTextField = new JTextField();
		rcmIdTextField.setMaximumSize(new Dimension(150,150));
		JLabel locationLabel = new JLabel( "Location" );
		JTextField  locationTextField = new JTextField();
		locationTextField.setMaximumSize(new Dimension(150,150));
		JLabel maxCreditLabel = new JLabel( "MaxCredit" );
		JTextField maxCreditTextField = new JTextField();
		maxCreditTextField.setMaximumSize(new Dimension(150,150));
		JLabel totalCapacityLabel = new JLabel( "Total Capacity" );
		JTextField totalCapacityTextField = new JTextField( );
		totalCapacityTextField.setMaximumSize(new Dimension(150,150));


		addMachinelayout.setHorizontalGroup( addMachinelayout.createSequentialGroup()
				.addGroup( addMachinelayout.createParallelGroup( GroupLayout.Alignment.LEADING )
				.addComponent(addMachineMessageLabel  )
				.addComponent(rcmIdLabel)
				.addComponent(locationLabel)
				.addComponent(maxCreditLabel)
				.addComponent(totalCapacityLabel))
				.addGroup( addMachinelayout.createParallelGroup( GroupLayout.Alignment.LEADING )
				.addComponent(   addMachineSave)
				.addComponent( rcmIdTextField )
				.addComponent( locationTextField )
				.addComponent( maxCreditTextField )
				.addComponent(totalCapacityTextField ) )
				);

		addMachinelayout.setVerticalGroup( addMachinelayout.createSequentialGroup()
				.addGroup( addMachinelayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
				.addComponent(addMachineMessageLabel  )
				.addComponent(   addMachineSave ) )
				.addGroup( addMachinelayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
				.addComponent( rcmIdLabel )
				.addComponent(  rcmIdTextField ) )
				.addGroup( addMachinelayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
				.addComponent(locationLabel )
				.addComponent( locationTextField ) )
				.addGroup( addMachinelayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
				.addComponent( maxCreditLabel)
		    	.addComponent( maxCreditTextField) )
				.addGroup( addMachinelayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
				.addComponent( totalCapacityLabel)
			    .addComponent( totalCapacityTextField))
			);

		/**
		 * check if the machine ID exists. If not then add it to the list of RCM's
		 */
		addMachineSave.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{
				
				String rcmId =  rcmIdTextField.getText();
				String checkaddMachine;
			    try{
				
			    	checkaddMachine =  serviceObj.checkMachineId(rcmId);	
			  
			    	if(checkaddMachine.equals("Success")){
			    		rcmIdTextField.setText("");
			    		locationTextField.setText("");
			    		maxCreditTextField.setText("");
			    		totalCapacityTextField.setText("");	
			    		CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
			    		cl.show(rmosCardPanel,"Add Machine Failure Card");  
				}	
				else{

				String rcmLocation  = locationTextField.getText();
				Double rcmMaxCredit = Double.parseDouble(maxCreditTextField.getText());
				Double rcmTotalCapacity = Double.parseDouble(totalCapacityTextField.getText());
				
				RCMInfo newrcm = new RCMInfo(rcmId,rcmLocation,rcmMaxCredit,rcmTotalCapacity);
				
				try{
					  serviceObj.addMachine(newrcm);	
		        }
				catch(Exception se){
					System.out.println(se.getMessage());
				}
				rcmIdTextField.setText("");
				locationTextField.setText("");
				maxCreditTextField.setText("");
				totalCapacityTextField.setText("");
				CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
				cl.show(rmosCardPanel,"Add Machine Success Card");  
			   }
			  }	
			  catch(Exception se){
					System.out.println(se.getMessage());
			 }	
			}
		});
		//end of Add Machine Card

		/*
		 * ADD MACHINE SUCCESS CARD
		 */
		JPanel addMachineSuccess = new JPanel();
		addMachineSuccess.setBackground(new Color(255,235,205));
		JLabel addMachinesuccessMessage = new JLabel("Machine has been successfully added");
		addMachineSuccess.add(addMachinesuccessMessage);
		// end of ADD MACHINE SUCCESS CARD
		
		/**
		 * ADD MACHINE FAILURE CARD
		 * 
		 */
		JPanel addMachineFailure = new JPanel();
		addMachineFailure.setBackground(new Color(255,235,205));
		JLabel addMachineErrorMessage = new JLabel("Machine with the specified id already exist");
		addMachineFailure.add(addMachineErrorMessage);   
		//end of ADD MACHINE FAILURE CARD
	
		/**
		 * Removing Machine 
		 */
		JPanel removeMachineCard = new JPanel();
		removeMachineCard.setBackground(new Color(255,235,205));
		removeMachineCard.setLayout(new BoxLayout(removeMachineCard,BoxLayout.Y_AXIS));
		JLabel removeMessageLabel = new JLabel( "Please enter the Machine ID which needs to be removed" );
		removeMessageLabel.setAlignmentX(removeMessageLabel.CENTER_ALIGNMENT);
		removeMachineCard.add(Box.createVerticalStrut(50)); 
		removeMachineCard.add(removeMessageLabel);
		
		removeMachineCard.add(Box.createVerticalStrut(15)); 
		JTextField removeMachineTextField = new JTextField();
		removeMachineTextField.setMaximumSize(new Dimension(200,30));
		removeMachineTextField.setAlignmentX(removeMachineTextField.CENTER_ALIGNMENT);
		removeMachineCard.add(removeMachineTextField);

		removeMachineCard.add(Box.createVerticalStrut(15)); 
		JButton removeMachineSave =  new JButton("Save");
		removeMachineSave.setAlignmentX(removeMachineSave.CENTER_ALIGNMENT);
		removeMachineCard.add(removeMachineSave);

		/**
		 * check if the machine with the ID exists. If so removes it.
		 */
		removeMachineSave.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{
				String removeMachineId = removeMachineTextField.getText();
				//System.out.println("remove machine id:" +removeMachineId);
			
				String checkMachineId;
				String removeMachine;
				try{
					
					checkMachineId = serviceObj.checkMachineId(removeMachineId);
					
					if(checkMachineId.equals("Success")){
						removeMachine = serviceObj.removeMachine(removeMachineId);
							
						if(removeMachine.equals("Success")){
							removeMachineTextField.setText("");
							CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
							cl.show(rmosCardPanel,"Remove Machine Success Card");     
						}
					   else{
						removeMachineTextField.setText("");
						CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
						cl.show(rmosCardPanel,"Remove Machine Failure Card");     
					   }	
					}
					else{
						removeMachineTextField.setText("");
						CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
						cl.show(rmosCardPanel,"Remove Machine Failure Card");   
				    }
				}
				catch(Exception se){
					System.out.println(se.getMessage());
				}	
			}
		});
		//end of REMOVE MACHINE CARD


		/**
		 * REMOVE MACHINE SUCCESS
		 */
		JPanel removeMachineSuccess = new JPanel();
		removeMachineSuccess.setBackground(new Color(255,235,205));
		JLabel removeMachineSuccessMessage = new JLabel("Machine has been removed succesfully");
		removeMachineSuccess.add(removeMachineSuccessMessage);
		//end of REMOVE MACHINE SUCCESS Card
		
		/**
		 * REMOVE MACHINE FAILURE
		 * 
		 */
		JPanel removeMachineFailure = new JPanel();
		removeMachineFailure.setBackground(new Color(255,235,205));
		JLabel removeMachineErrorMessage = new JLabel("Machine with the specified id doesn't exist");
		removeMachineFailure.add(removeMachineErrorMessage);   
		//end of REMOVE MACHINE FAILURE CARD
		
		/**
		 * Activates the Machine
		 */
		JPanel activateCard = new JPanel();
		activateCard.setBackground(new Color(255,235,205));
		activateCard.setLayout(new BoxLayout(activateCard,BoxLayout.Y_AXIS));
		JLabel activateMessage = new JLabel( "Please enter the Machine ID which needs to be activated");
		activateMessage.setAlignmentX(activateMessage.CENTER_ALIGNMENT);
		activateCard.add(Box.createVerticalStrut(50));
		activateCard.add(activateMessage);
		
		activateCard.add(Box.createVerticalStrut(15));
		JTextField activateTextField = new JTextField();
		activateTextField.setMaximumSize(new Dimension(200,30));
		activateTextField.setAlignmentX(activateTextField.CENTER_ALIGNMENT);
		activateCard.add(activateTextField);
		
		activateCard.add(Box.createVerticalStrut(15));
		JButton activateSave =  new JButton("Save");
		activateSave.setAlignmentX(activateSave.CENTER_ALIGNMENT);
		activateCard.add(activateSave);
		activateSave.addActionListener(this);
		
		/**
		 * Activates the machine and appropriately sets the operatonal status, current credit and current capacity
		 */
		activateSave.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				String selectedMachineId = activateTextField.getText();
				//System.out.println("selected Machine in rmosgui: "+selectedMachineId);
		
				String activateFlag;
				int checkFlag=0;
				for (RCMInfo rcmObj : Activator.totalRCMMachines) {
					if(rcmObj.getMachineId().equals(selectedMachineId))
					{
						checkFlag=1;
						rcmObj.setCurrentCapacity(0.0);
						rcmObj.setOperationalStatus("Working");
						rcmObj.setCurrentCredit(rcmObj.getMaxCredit());
						
						try{
							activateFlag = serviceObj.activate(rcmObj);
						
							if(activateFlag.equals("Success")){
								activateTextField.setText("");
								
								serviceObj.emptyMachine(rcmObj);
								CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
								cl.show(rmosCardPanel,"Activated Success");
							}
							else{
								activateTextField.setText("");
								CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
								cl.show(rmosCardPanel,"Activated Failure"); 
								}
						}
						catch(Exception se){
							System.out.println(se.getMessage());
						}
					}
				}
				if(checkFlag==0){
					activateTextField.setText("");
					CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
					cl.show(rmosCardPanel,"Activated Failure"); 
				}
			 }
		});
		/**
		 * ACTIVATED SUCCESS CARD
		 */
		JPanel activatedSuccess = new JPanel();
		activatedSuccess.setBackground(new Color(255,235,205));
		JLabel activatedSuccessMessage = new JLabel("Machine has been activated succesfully");
		 activatedSuccess .add( activatedSuccessMessage );
		//end of ACTIVATED SUCCESS CARD
		 
		/**
		* ACTIVATED FAILURE CARD
		*/
		 JPanel activatedFailure = new JPanel();
		 activatedFailure.setBackground(new Color(255,235,205));
		JLabel activatedFailureMessage = new JLabel("Activated Failure Card:Machine with the specified doesn't exist");
		activatedFailure.add(activatedFailureMessage);
		//end of ACTIVATED FAILURE CARD
		
		 /**
		 * Deactivates the machine
		 */
		JPanel deactivateCard = new JPanel();
		deactivateCard.setBackground(new Color(255,235,205));
		deactivateCard.setLayout(new BoxLayout(deactivateCard,BoxLayout.Y_AXIS));
		JLabel deactivateMessage = new JLabel( "Please enter the Machine ID which needs to be deactivated");
		deactivateMessage.setAlignmentX(deactivateMessage.CENTER_ALIGNMENT);
		deactivateCard.add(Box.createVerticalStrut(50)); 
		deactivateCard.add(deactivateMessage );
		deactivateCard.add(Box.createVerticalStrut(15)); 
		
		JTextField deactivateTextField = new JTextField();
		deactivateTextField.setMaximumSize(new Dimension(200,30));
		deactivateTextField.setAlignmentX(deactivateTextField.CENTER_ALIGNMENT);
		deactivateCard.add(deactivateTextField);
			
		deactivateCard.add(Box.createVerticalStrut(15)); 
		JButton deactivateSave =  new JButton("Save");
		deactivateSave.setAlignmentX(deactivateSave.CENTER_ALIGNMENT);
		deactivateCard.add(deactivateSave);
		deactivateSave.addActionListener(this);
		
		/**
		 * sets the operational status to "NotWorking"
		 */
		deactivateSave.addActionListener(new ActionListener()
		{		
			@Override
			public void actionPerformed(ActionEvent event)
			{
				String deactivateSql; 
				String deactivatedselectedMachine = deactivateTextField.getText();
				String deactivateFlag;
				int deactivateCheckFlag=0;
				
				for (RCMInfo rcmObj : Activator.totalRCMMachines) {
					if(rcmObj.getMachineId().equals(deactivatedselectedMachine))
					{
						rcmObj.setOperationalStatus("NotWorking");
						//System.out.println("Deactivate rcmObj: operational status: " +rcmObj.getOperationalStatus());
						try{
						
							deactivateFlag=serviceObj.deactivate(rcmObj);
							
							if(deactivateFlag.equals("Success")){
								deactivateTextField.setText("");
								CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
								cl.show(rmosCardPanel,"Deactivated Success");
								deactivateCheckFlag=1;
							}
							else{
								deactivateTextField.setText("");
								CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
								cl.show(rmosCardPanel,"Deactivated Failure"); 
							} 
						}
						catch(Exception se){
						System.out.println(se.getMessage());
					}
				}
				if(deactivateCheckFlag==0){
					deactivateTextField.setText("");
					CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
					cl.show(rmosCardPanel,"Deactivated Failure"); 
				}
				
			}
		}
	});
					
		/**
		* DEACTIVATED SUCCESS CARD
		 */
		JPanel deactivatedSuccess = new JPanel();
		deactivatedSuccess.setBackground(new Color(255,235,205));
		JLabel deactivatedSuccessMessage = new JLabel("Machine has been deactivated succesfully");
		deactivatedSuccess.add(deactivatedSuccessMessage);
		//end of DEACTIVATED SUCCESS CARD
		
		/**
		* DEACTIVATED FAILURE CARD
		*/
		 JPanel deactivatedFailure = new JPanel();
		 deactivatedFailure.setBackground(new Color(255,235,205));
		JLabel deactivatedFailureMessage = new JLabel("Deactivated Failure Card : Machine with the specified doesn't exist");
		deactivatedFailure.add(deactivatedFailureMessage);
		//end of ACTIVATED FAILURE CARD

		/**
		 * Empties the Machine
		 */
		JPanel emptyMachineCard = new JPanel();
		emptyMachineCard.setBackground(new Color(255,235,205));
		emptyMachineCard.setLayout(new BoxLayout(emptyMachineCard,BoxLayout.Y_AXIS));
		JLabel emptyMachineMessageLabel = new JLabel( "Please enter the Machine ID which needs to be emptied" );
		emptyMachineMessageLabel.setAlignmentX(emptyMachineMessageLabel.CENTER_ALIGNMENT);
		emptyMachineCard.add(Box.createVerticalStrut(50)); 
		emptyMachineCard.add(emptyMachineMessageLabel);
		emptyMachineCard.add(Box.createVerticalStrut(15)); 
		
		JTextField emptyMachineTextField = new JTextField();
		emptyMachineTextField.setMaximumSize(new Dimension(200,30));
		emptyMachineTextField.setAlignmentX(emptyMachineTextField.CENTER_ALIGNMENT);
		emptyMachineCard.add(emptyMachineTextField );

		emptyMachineCard.add(Box.createVerticalStrut(15));
		JButton emptyMachineSave =  new JButton("Save");
		emptyMachineSave.setAlignmentX(emptyMachineSave.CENTER_ALIGNMENT);
		emptyMachineCard.add( emptyMachineSave);

		/**
		 * updates the operational status, last emptied time and the current capacity
		 */
		emptyMachineSave.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{
				String  emptyMachineID = emptyMachineTextField.getText();	
				String sql, sql1;
				int emptyMachineFlag=0;
				String emptyMachineStatus ="";
				java.util.Date utilDate = new java.util.Date();
				java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
				
				
				for (RCMInfo rcmObj : Activator.totalRCMMachines) {

					if(rcmObj.getMachineId().equals(emptyMachineID))	
					{
						rcmObj.setOperationalStatus("Working");		
						rcmObj.setTimeLastEmptied(utilDate);
						rcmObj.setCurrentCapacity(0.0);
						emptyMachineStatus = serviceObj.emptyMachine(rcmObj);
						emptyMachineFlag = 1;
					
					}	
					}	
					
				if(emptyMachineStatus.equals("Success") && emptyMachineFlag ==1)
					{
					emptyMachineTextField.setText("");
					
					CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
					cl.show(rmosCardPanel,"Empty Machine Success Card"); 
					}
					
				if( emptyMachineFlag==0 || emptyMachineStatus.equals("Failure"))
				{
						emptyMachineTextField.setText("");
						CardLayout c2 = (CardLayout)rmosCardPanel.getLayout();
						c2.show(rmosCardPanel,"Remove Machine Failure Card"); 
				}		
			}

		});
		//end of EMPTY MACHINE CARD

		/**
		 * EMPTY MACHINE SUCCESS
		 */
		JPanel emptyMachineSuccess = new JPanel();
		emptyMachineSuccess.setBackground(new Color(255,235,205));
		JLabel emptyMachineSuccessMessage = new JLabel("Machine has been emptied succesfully");
		emptyMachineSuccess.add(emptyMachineSuccessMessage);
		//end of EMPTY MACHINE SUCCESS CARD

		/**
		 * to check the operational status of the machine
		 */
		JPanel operationalStatusCard = new JPanel();
		operationalStatusCard.setBackground(new Color(255,235,205));
		operationalStatusCard.setLayout(new BoxLayout(operationalStatusCard,BoxLayout.Y_AXIS));
		JLabel operationalStatusMessageLabel = new JLabel( "Please enter the Machine ID to view its Operational Status" );
		operationalStatusMessageLabel.setAlignmentX(operationalStatusMessageLabel.CENTER_ALIGNMENT);
		operationalStatusCard.add(Box.createVerticalStrut(50)); 
		operationalStatusCard.add(operationalStatusMessageLabel);
		operationalStatusCard.add(Box.createVerticalStrut(15)); 
		
		JTextField operationalStatusTextField = new JTextField();
		operationalStatusTextField.setMaximumSize(new Dimension(200,30));
		operationalStatusTextField.setAlignmentX(operationalStatusTextField.CENTER_ALIGNMENT);
		operationalStatusCard.add(operationalStatusTextField);
	
		operationalStatusCard.add(Box.createVerticalStrut(15));
		JButton operationalStatusSave =  new JButton("Save");
		operationalStatusSave.setAlignmentX(operationalStatusSave.CENTER_ALIGNMENT);
		operationalStatusCard.add( operationalStatusSave);
		
		operationalStatusCard.add(Box.createVerticalStrut(15));
		JLabel operationalStatusMessage = new JLabel("");
		operationalStatusMessage.setAlignmentX(operationalStatusMessage.CENTER_ALIGNMENT);
		operationalStatusCard.add(operationalStatusMessage);
		
		operationalStatusSave.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{
						
				String operationalStatusMachineID = operationalStatusTextField.getText();	
				int operationalStatusFlag=0;
				for (RCMInfo rcmObj : Activator.totalRCMMachines) 
				{
					if(rcmObj.getMachineId().equals(operationalStatusMachineID))
					{
					 //  System.out.println("rcmObj.getMachineId "+operationalStatusMachineID);
					  
					   operationalStatusFlag=1;
					   operationalStatusTextField.setText("");	
					   operationalStatusMessage.setText("Operational Status of the machine is "
								  +rcmObj.getOperationalStatus());  
					
					 //  System.out.println("operational status in if loop: "+rcmObj.getOperationalStatus());				 
					 }
						
				}
				if(operationalStatusFlag==0){
					operationalStatusTextField.setText("");
			     	operationalStatusMessage.setText("Operational Status: Machine with specified ID doesn't exist");
			     	
				}
				Timer operationalTimer = new Timer(5000, new ActionListener() {

		            @Override
		            public void actionPerformed(ActionEvent e) {
		            	 operationalStatusMessage.setText("");
		            }
		        });
				operationalTimer.setRepeats(false);
				operationalTimer.start();
			 } 
			
		});
		//end of OPERATIONAL STATUS CARD

		/**
		 * to check the Credit Status of the Machine
		 */
		JPanel creditStatusCard = new JPanel();
		creditStatusCard.setBackground(new Color(255,235,205));

		creditStatusCard.setLayout(new BoxLayout(creditStatusCard,BoxLayout.Y_AXIS));
		JLabel creditStatusMessageLabel = new JLabel( "Please enter the Machine ID to view its Credit Status" );
		creditStatusMessageLabel.setAlignmentX(creditStatusMessageLabel.CENTER_ALIGNMENT);
		creditStatusCard.add(Box.createVerticalStrut(50)); 
	
		creditStatusCard.add(creditStatusMessageLabel);
		creditStatusCard.add(Box.createVerticalStrut(15));

		JTextField creditStatusTextField = new JTextField();
		creditStatusTextField.setMaximumSize(new Dimension(200,30));
		creditStatusTextField.setAlignmentX(creditStatusTextField.CENTER_ALIGNMENT);
		creditStatusCard.add(creditStatusTextField );

		creditStatusCard.add(Box.createVerticalStrut(15));
		JButton creditStatusSave =  new JButton("Save");
		creditStatusSave.setAlignmentX(creditStatusSave.CENTER_ALIGNMENT);
		creditStatusCard.add(creditStatusSave);
		
		creditStatusCard.add(Box.createVerticalStrut(15));
		JLabel creditStatusLabel = new JLabel("");
		creditStatusLabel .setAlignmentX(creditStatusLabel.CENTER_ALIGNMENT);
		creditStatusCard.add(creditStatusLabel);

		creditStatusSave.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				String currentcreditStatusMachineID = creditStatusTextField.getText();	
				int creditStatusFlag=0;
				for (RCMInfo rcmObj : Activator.totalRCMMachines) 
				{
					if(rcmObj.getMachineId().equals(currentcreditStatusMachineID ))
					{
						creditStatusFlag=1;
						creditStatusTextField.setText("");	
						
						System.out.println("machine id in gui: "+currentcreditStatusMachineID);
						System.out.println("current credit in obj: "+rcmObj.getCurrentCredit());
						
						System.out.println("current credit in credit status: " +rcmObj.getCurrentCredit());
						
			     		 creditStatusLabel.setText("Credit Status of the machine is "
						  +rcmObj.getCurrentCredit());
					}
				}
				if(creditStatusFlag==0){
						creditStatusTextField.setText("");
						creditStatusLabel.setText("Credit Status: Machine with specified ID doesn't exist");
				}
				
				Timer creditStatusTimer = new Timer(5000, new ActionListener() {
		        @Override
		        public void actionPerformed(ActionEvent e) {
		        	    creditStatusLabel.setText("");
		           }
		       });
			   creditStatusTimer.setRepeats(false);
			   creditStatusTimer.start();
			}
		});
		//end of CREDIT STATUS CARD
		
		/**
		 * to check the Last Emptied details of a Machine
		 */
		JPanel lastEmptiedStatusCard = new JPanel();
		lastEmptiedStatusCard.setBackground(new Color(255,235,205));
		lastEmptiedStatusCard.setLayout(new BoxLayout(lastEmptiedStatusCard,BoxLayout.Y_AXIS));
		JLabel lastEmptiedStatusMessageLabel = new JLabel( "Please enter the Machine ID to view when it was Last Emptied" );
		lastEmptiedStatusMessageLabel.setAlignmentX(lastEmptiedStatusMessageLabel.CENTER_ALIGNMENT);
		lastEmptiedStatusCard.add(Box.createVerticalStrut(50)); 
		lastEmptiedStatusCard.add(lastEmptiedStatusMessageLabel);
		lastEmptiedStatusCard.add(Box.createVerticalStrut(15)); 
		
		JTextField lastEmptiedStatusTextField = new JTextField();
		lastEmptiedStatusTextField.setMaximumSize(new Dimension(200,30));
		lastEmptiedStatusTextField.setAlignmentX(lastEmptiedStatusTextField.CENTER_ALIGNMENT);
		lastEmptiedStatusCard.add(lastEmptiedStatusTextField );

		lastEmptiedStatusCard.add(Box.createVerticalStrut(15)); 
		JButton lastEmptiedStatusSave =  new JButton("Save");
		lastEmptiedStatusSave.setAlignmentX(lastEmptiedStatusSave.CENTER_ALIGNMENT);
		lastEmptiedStatusCard.add(lastEmptiedStatusSave);
		
		lastEmptiedStatusCard.add(Box.createVerticalStrut(15)); 
		JLabel lastEmptiedStatusLabel = new JLabel("");
		lastEmptiedStatusLabel.setAlignmentX(lastEmptiedStatusLabel.CENTER_ALIGNMENT);
		lastEmptiedStatusCard.add(lastEmptiedStatusLabel);
		
		lastEmptiedStatusSave.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{
				
			 String lastEmptiedStatusMachineID = lastEmptiedStatusTextField.getText();
			 int lastEmptiedStatusFlag=0;
			 for (RCMInfo rcmObj : Activator.totalRCMMachines) 
			{
				if(rcmObj.getMachineId().equals(lastEmptiedStatusMachineID))
				{
					// System.out.println("last emptied status machine id "+lastEmptiedStatusMachineID);
					lastEmptiedStatusFlag=1;
					 lastEmptiedStatusTextField.setText("");     
					lastEmptiedStatusLabel.setText("Last Emptied details of the machine is "
						  +rcmObj.getTimeLastEmptied());				  
				}
			}
			if(lastEmptiedStatusFlag==0){
					lastEmptiedStatusTextField.setText(""); 
					lastEmptiedStatusLabel.setText("Last Emptied Status: Machine with specified ID doesn't exist");
			}
			Timer lastEmptiedStatusTimer = new Timer(5000, new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {
		    		lastEmptiedStatusLabel.setText("");
		    	}
		    });
			lastEmptiedStatusTimer.setRepeats(false);
			lastEmptiedStatusTimer.start();
		  }
	});
	//end of LAST EMPTIED STATUS CARD

	/**
	 * to check Capacity Status of the Machine
	 */
		JPanel capacityCard = new JPanel();
		capacityCard.setBackground(new Color(255,235,205));
		capacityCard.setLayout(new BoxLayout(capacityCard,BoxLayout.Y_AXIS));
		JLabel capcityCardMessageLabel = new JLabel( "Please enter the Machine ID to view it's Capacity Status");
		capcityCardMessageLabel.setAlignmentX(capcityCardMessageLabel.CENTER_ALIGNMENT);
		capacityCard.add(Box.createVerticalStrut(50)); 
		capacityCard.add(capcityCardMessageLabel);
		capacityCard.add(Box.createVerticalStrut(15));
		
		JTextField capcityCardTextField = new JTextField();
		capcityCardTextField.setMaximumSize(new Dimension(200,30));
		capcityCardTextField.setAlignmentX(capcityCardTextField.CENTER_ALIGNMENT);
		capacityCard.add(capcityCardTextField );

		capacityCard.add(Box.createVerticalStrut(15));
		JButton capacitySave =  new JButton("Save");
		capacitySave.setAlignmentX(capacitySave.CENTER_ALIGNMENT);
		capacityCard.add(capacitySave);
		
		capacityCard.add(Box.createVerticalStrut(15));
		JLabel currentCapacityStatusLabel = new JLabel("");
		currentCapacityStatusLabel.setAlignmentX(currentCapacityStatusLabel.CENTER_ALIGNMENT);
		capacityCard.add(currentCapacityStatusLabel);
		
		capacitySave.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{

				String currentCapcityStatusMachineID = capcityCardTextField.getText();	
				 int currentCapacityStatusFlag=0;
				for (RCMInfo rcmObj : Activator.totalRCMMachines) 
				{
					if(rcmObj.getMachineId().equals(currentCapcityStatusMachineID))
					{
						//System.out.println("current capacity status machine id "+currentCapcityStatusMachineID);
						currentCapacityStatusFlag=1;
						capcityCardTextField.setText(""); 
						currentCapacityStatusLabel.setText("Current Capacity of the machine is "
							  +rcmObj.getCurrentCapacity());
						      
					}
				}
				if(currentCapacityStatusFlag==0){
						capcityCardTextField.setText(""); 
						currentCapacityStatusLabel.setText("Current Capacity Status : Machine with specified ID doesn't exist");
				}
				Timer currentCapacityStatusTimer = new Timer(5000, new ActionListener() {
				@Override
			    public void actionPerformed(ActionEvent e) {
						currentCapacityStatusLabel.setText("");
			    	}
			    });
				currentCapacityStatusTimer.setRepeats(false);
				currentCapacityStatusTimer.start();
			}
		});
		//end of CAPACITY STATUS CARD	
		
		/**
		 * to check how many items are collected for a machine 
		 *
		 */
		JPanel collectedItemsCard = new JPanel();
		collectedItemsCard.setBackground(new Color(255,235,205));
		collectedItemsCard.setLayout(new BoxLayout(collectedItemsCard,BoxLayout.Y_AXIS));
		JLabel collectedItemsLabel = new JLabel( "Please enter the Machine ID" );
		collectedItemsLabel.setAlignmentX(collectedItemsLabel.CENTER_ALIGNMENT);
		collectedItemsCard.add(Box.createVerticalStrut(50)); 
		collectedItemsCard.add(collectedItemsLabel);
		collectedItemsCard.add(Box.createVerticalStrut(15)); 
		
		JTextField collectedItemsStatusTextField = new JTextField();
		collectedItemsStatusTextField.setMaximumSize(new Dimension(200,30));
		collectedItemsStatusTextField.setAlignmentX(collectedItemsStatusTextField.CENTER_ALIGNMENT);
		collectedItemsCard.add(collectedItemsStatusTextField );
		
		JLabel collectedItemsMonthLabel = new JLabel( "Please enter the Month in numerical format" );
		collectedItemsMonthLabel.setAlignmentX(collectedItemsMonthLabel.CENTER_ALIGNMENT);
		collectedItemsCard.add(Box.createVerticalStrut(50)); 
		collectedItemsCard.add(collectedItemsMonthLabel);
		collectedItemsCard.add(Box.createVerticalStrut(15)); 
		
		JTextField collectedItemsMonthTextField = new JTextField();
		collectedItemsMonthTextField.setMaximumSize(new Dimension(200,30));
		collectedItemsMonthTextField.setAlignmentX(collectedItemsMonthTextField.CENTER_ALIGNMENT);
		collectedItemsCard.add(collectedItemsMonthTextField );
		

		collectedItemsCard.add(Box.createVerticalStrut(15)); 
		JButton collectedItemsStatusSave =  new JButton("Save");
		collectedItemsStatusSave.setAlignmentX(collectedItemsStatusSave.CENTER_ALIGNMENT);
		collectedItemsCard.add(collectedItemsStatusSave );
		
		collectedItemsCard.add(Box.createVerticalStrut(15)); 
		JLabel collectedItemsStatusLabel = new JLabel("");
		collectedItemsStatusLabel.setAlignmentX(collectedItemsStatusLabel.CENTER_ALIGNMENT);
		collectedItemsCard.add(collectedItemsStatusLabel);
		
		JLabel collectedItemsStatusLabel1 = new JLabel("");
		collectedItemsStatusLabel1.setAlignmentX(collectedItemsStatusLabel1.CENTER_ALIGNMENT);
		collectedItemsCard.add(collectedItemsStatusLabel1);
			
		collectedItemsStatusSave.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{
				String collectedItemsMachineId = collectedItemsStatusTextField.getText();
				
				
				String collectedItemsMonth = collectedItemsMonthTextField.getText();
				int month = Integer.parseInt(collectedItemsMonth);
				
				if(month<=0 || month>12){

					JOptionPane.showMessageDialog(collectedItemsCard,"Month is invalid. Enter value between 1-12");
					collectedItemsStatusTextField.setText("");
					collectedItemsMonthTextField.setText("");
					CardLayout cl = (CardLayout) rmosCardPanel.getLayout();
					cl.show(rmosCardPanel,"Collected Items Card"); 
				}
				else{
				
				int collectedItems=0;
				int collectedItemFlag=0;
				for (RCMInfo rcmObj : Activator.totalRCMMachines) 
				{
					if(rcmObj.getMachineId().equals(collectedItemsMachineId))
					{	
						try{		
							//System.out.println("machine id in collected items: "+collectedItemsMachineId);
							//System.out.println("month for the collected items: "+collectedItemsMonth);
							
							collectedItems = serviceObj.collectedItems(collectedItemsMachineId,collectedItemsMonth);
							//System.out.println("collectedItems is : " +collectedItems);
						
							if(collectedItems!=0){
								collectedItemFlag=1;
								collectedItemsStatusLabel1.setText("Collected items for the Machine RCM: " +collectedItemsMachineId+ " for the Month: " +collectedItemsMonth+ " is "  +collectedItems);
								collectedItemsStatusTextField.setText("");
								collectedItemsMonthTextField.setText("");
							}
						}
						catch(Exception e){
							e.printStackTrace();
						}					
						
					}
				}
				if(collectedItemFlag==0){
						collectedItemsStatusTextField.setText("");
						collectedItemsMonthTextField.setText("");
						CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
						cl.show(rmosCardPanel,"Remove Machine Failure Card"); 
				}
				Timer operationalTimer = new Timer(8000, new ActionListener() {

		            @Override
		            public void actionPerformed(ActionEvent e) {
		            	collectedItemsStatusLabel1.setText("");
		            }
		        });
				operationalTimer.setRepeats(false);
				operationalTimer.start();
				}
			 } 

		});
		//end of COLLECTED ITEMS CARD
		
		/**
		 * to check which Machine is most used
		 */
		JPanel mostUsedRCMCard = new JPanel();
		mostUsedRCMCard.setBackground(new Color(255,235,205));
		mostUsedRCMCard.setLayout(new BoxLayout(mostUsedRCMCard,BoxLayout.Y_AXIS));
		JButton mostUsedRCMButton = new JButton("Click to view the most used RCM");
		mostUsedRCMButton.setAlignmentX(mostUsedRCMButton.CENTER_ALIGNMENT);
		mostUsedRCMCard.add(Box.createVerticalStrut(25)); 
		mostUsedRCMCard.add(mostUsedRCMButton);
		
		mostUsedRCMCard.add(Box.createVerticalStrut(15)); 
		JLabel mostUsedRCMMessageLabel = new JLabel("");
		mostUsedRCMMessageLabel.setAlignmentX(mostUsedRCMMessageLabel.CENTER_ALIGNMENT);
		mostUsedRCMCard.add(mostUsedRCMMessageLabel);
		
		mostUsedRCMButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent event)
			{
				
				
				mostUsedMachineId = serviceObj.mostUsedRCM();
		
				for (RCMInfo rcmObj : Activator.totalRCMMachines) {
					if(rcmObj.getMachineId().equals(mostUsedMachineId))
					{
						mostUsedMachineLocation = rcmObj.getLocation();
						
					}
					
				}
				//System.out.println("id Value: "+mostUsedMachineId);
				mostUsedRCMMessageLabel.setText("Most used machine is RCM  "+mostUsedMachineId + ". The location is "+ mostUsedMachineLocation );
				
				Timer operationalTimer = new Timer(5000, new ActionListener() {

		            @Override
		            public void actionPerformed(ActionEvent e) {
		            	mostUsedRCMMessageLabel.setText("");
		            }
		        });
				operationalTimer.setRepeats(false);
				operationalTimer.start();
				
			}
		});
		//end of MOST USED RCM CARD

		/**
		 * to add an item
		 */
		JPanel addItemCard = new JPanel();
		addItemCard.setBackground(new Color(255,235,205));
		addItemCard.setBorder(BorderFactory.createEmptyBorder( 10, 10, 10, 10 ) );
		GroupLayout addItemlayout = new GroupLayout(addItemCard);
		addItemCard.setLayout( addItemlayout );
		addItemlayout.setAutoCreateGaps( true );
		addItemCard.setPreferredSize(new Dimension(400,400));
		JLabel addItemMessageLabel = new JLabel( "Please enter the details of the new item" );
		JButton addItemSave =  new JButton("Save");	
		JLabel itemNameLabel = new JLabel( "Item Name" );
		JTextField itemNameTextField = new JTextField();
		itemNameTextField.setMaximumSize(new Dimension(150,150));
		JLabel itemUnitPriceLabel = new JLabel( "Unit Price" );
		JTextField  itemUnitPriceTextField = new JTextField();
		itemUnitPriceTextField.setMaximumSize(new Dimension(150,150));
		JLabel itemMinWeightLabel = new JLabel( "Minium Weight" );
		JTextField itemMinWeightTextField = new JTextField();
		itemMinWeightTextField.setMaximumSize(new Dimension(150,150));

		addItemlayout.setHorizontalGroup( addItemlayout.createSequentialGroup()
		.addGroup( addItemlayout.createParallelGroup( GroupLayout.Alignment.LEADING )
		.addComponent(addItemMessageLabel)	                                   
		.addComponent(itemNameLabel)
		.addComponent(itemUnitPriceLabel)
		.addComponent(itemMinWeightLabel))
		.addGroup( addItemlayout.createParallelGroup( GroupLayout.Alignment.LEADING )
		.addComponent(addItemSave)
		.addComponent(itemNameTextField )
		.addComponent(itemUnitPriceTextField )
		.addComponent(itemMinWeightTextField ))
		);

		addItemlayout.setVerticalGroup(  addItemlayout.createSequentialGroup()
		.addGroup(  addItemlayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
		.addComponent(addItemMessageLabel  )
		.addComponent(  addItemSave ) )
		.addGroup(  addItemlayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
		.addComponent( itemNameLabel )
		.addComponent( itemNameTextField ) )
		.addGroup(  addItemlayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
		.addComponent(itemUnitPriceLabel )
		.addComponent(itemUnitPriceTextField ) )
		.addGroup(  addItemlayout.createParallelGroup( GroupLayout.Alignment.BASELINE )
		.addComponent(itemMinWeightLabel)
		.addComponent( itemMinWeightTextField) )
		);

		addItemSave.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{

				String itemName =  itemNameTextField.getText();
				String checkItemName;
				try{
					
					checkItemName = serviceObj.checkItemName(itemName);
					
					if(checkItemName.equals("Success")){
					itemNameTextField.setText("");
					itemUnitPriceTextField.setText("");
					itemMinWeightTextField.setText("");	   
					CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
					cl.show(rmosCardPanel,"Add Item Failure Card");  	
				}	
			
				else{
				
				Double unitPrice = Double.parseDouble(itemUnitPriceTextField.getText());
				Double minWeight = Double.parseDouble(itemMinWeightTextField.getText());

				Item itemObj = new Item(itemName, unitPrice, minWeight);
				try{
					
					serviceObj.addItem(itemObj);
			      }
				catch(Exception se){
					
					System.out.println("add item exception" +se.getMessage());
				}  
				itemNameTextField.setText("");
				itemUnitPriceTextField.setText("");
				itemMinWeightTextField.setText("");

				CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
				cl.show(rmosCardPanel,"Add Item Success Card");  	
			  }
			    }
			 catch(Exception se){
				 System.out.println(se.getMessage());	
			  }
 			}
		});
		//end of ADD ITEM CARD

		/**
		 * ADD ITEM SUCCESS CARD
		 */
		JPanel addItemSuccess = new JPanel();
		addItemSuccess.setBackground(new Color(255,235,205));
		JLabel addItemSuccessMessage = new JLabel("Item has been successfully added");
		addItemSuccess.add(addItemSuccessMessage); 
		//end of ADD ITEM SUCCESS CARD
		
		/**
		 * ADD ITEM FAILURE CARD
		 */
		JPanel addItemFailure = new JPanel();
		addItemFailure.setBackground(new Color(255,235,205));
		JLabel addItemFailureMessage = new JLabel("Item with the specified name already exists");
		addItemFailure.add(addItemFailureMessage); 
		//end of ADD ITEM SUCCESS CARD

		/**
		 * to remove an Item
		 */
		JPanel removeItemCard = new JPanel();
		removeItemCard.setBackground(new Color(255,235,205));
		removeItemCard.setLayout(new BoxLayout(removeItemCard,BoxLayout.Y_AXIS));
		JLabel removeItemMessageLabel = new JLabel( "Please enter the name of the Item to be removed" );
		removeItemMessageLabel.setAlignmentX(removeItemMessageLabel.CENTER_ALIGNMENT);
		removeItemCard.add(Box.createVerticalStrut(50)); 
		removeItemCard.add(removeItemMessageLabel);
		removeItemCard.add(Box.createVerticalStrut(15)); 

		JTextField  removeItemTextField = new JTextField();
		removeItemTextField.setMaximumSize(new Dimension(200,30));
		removeItemTextField.setAlignmentX(removeItemTextField.CENTER_ALIGNMENT);
		removeItemCard.add(removeItemTextField );
		
		removeItemCard.add(Box.createVerticalStrut(15)); 
		JButton removeItemSave =  new JButton("Save");
		removeItemSave.setAlignmentX(removeItemSave.CENTER_ALIGNMENT);
		removeItemCard.add(removeItemSave);


		removeItemSave.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{
				String removeitemName =  removeItemTextField.getText();
				String removeItemcheckObj;	
				String removeItemObj;	
				try{
					//System.out.println("remove item name in rmos gui: "+removeitemName);
					
					removeItemcheckObj = serviceObj.checkItemName(removeitemName);
							
					if (removeItemcheckObj.equals("Success")){
						 removeItemTextField.setText("");
						 removeItemObj = serviceObj.removeItem(removeitemName);
						 
						 if(removeItemObj.equals("Success")){
							 removeItemTextField.setText("");
							 CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
							 cl.show(rmosCardPanel,"Remove Item Success Card"); 
						 }
						 else{
							 removeItemTextField.setText("");
							 CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
							 cl.show(rmosCardPanel,"Remove Item Failure Card");  
						 }
					}	
					else{
						removeItemTextField.setText("");
						 CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
						 cl.show(rmosCardPanel,"Remove Item Failure Card");    
				    }
				}
				catch(Exception se){
					System.out.println(se.getMessage());
				}
				       
			 }
		});
		//end of REMOVE ITEM CARD

		/**
		 * REMOVE ITEM SUCCESS CARD
		 */
		JPanel removeItemSuccess = new JPanel();
		removeItemSuccess.setBackground(new Color(255,235,205));
		JLabel removeItemSuccessMessage = new JLabel("Item has been removed succesfully");
		removeItemSuccess.add(removeItemSuccessMessage);
		//end of REMOVE ITEM SUCCESS CARD
		
		/**
		 * REMOVE ITEM FAILURE CARD
		 */
		JPanel removeItemFailure = new JPanel();
		removeItemFailure.setBackground(new Color(255,235,205));
		JLabel removeItemFailureMessage = new JLabel("Item with the specified name doesnt exist");
		removeItemFailure.add(removeItemFailureMessage);
		//end of REMOVE ITEM SUCCESS CARD


		/**
		 * to update the price of an item
		 */
		JPanel updateItemPriceCard = new JPanel();
		updateItemPriceCard.setBackground(new Color(255,235,205));
		updateItemPriceCard.setLayout(new BoxLayout(updateItemPriceCard,BoxLayout.Y_AXIS));
		JLabel updateItemPriceMessageLabel = new JLabel( "Please enter the name of the Item whose price needs to be changed" );
		
		updateItemPriceMessageLabel.setAlignmentX(updateItemPriceMessageLabel.CENTER_ALIGNMENT);
		updateItemPriceCard.add(Box.createVerticalStrut(50)); 
		
		updateItemPriceCard.add(updateItemPriceMessageLabel);
		updateItemPriceCard.add(Box.createVerticalStrut(15));
		
		JTextField  updateItemPriceTextField = new JTextField();
		updateItemPriceTextField.setMaximumSize(new Dimension(200,30));
		
		updateItemPriceTextField.setAlignmentX( updateItemPriceTextField.CENTER_ALIGNMENT);
		updateItemPriceCard.add(updateItemPriceTextField );
		
		updateItemPriceCard.add(Box.createVerticalStrut(15));
		JButton updateItemPriceSave =  new JButton("Save");
		updateItemPriceSave.setAlignmentX( updateItemPriceSave.CENTER_ALIGNMENT);
		updateItemPriceCard.add(updateItemPriceSave);
		

		/**
		 * checks if the item name exists.
		 */
		updateItemPriceSave.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{
				String updateItemPrice = updateItemPriceTextField.getText();	
			//	System.out.println("update price for item in first card : " +updateItemPrice);
				String updatePrice;
				
				try{
					
					updatePrice = serviceObj.checkItemName(updateItemPrice);
					
					if (updatePrice.equals("Success")){
						itemNameTextField.setText("");
						CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
						cl.show(rmosCardPanel,"Change the Item Price Card");
						
				  }
					else{
						itemNameTextField.setText("");
						CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
						cl.show(rmosCardPanel,"Change the Item Price Failure Card");
					}
				}
				catch(Exception se){
					System.out.println(se.getMessage());
				}		        

			}
		});
		//end of UPDATE ITEM PRICE CARD
		

		/**
		 * to update the price of an item
		 */
		JPanel updateItemPriceCard1 = new JPanel();
		updateItemPriceCard1.setBackground(new Color(255,235,205));
		updateItemPriceCard1.setLayout(new BoxLayout(updateItemPriceCard1,BoxLayout.Y_AXIS));
		JLabel updateItemPriceMessageLabel1 = new JLabel( "Please enter the new price" );
		updateItemPriceCard1.add(updateItemPriceMessageLabel1);

		JTextField updateItemPriceTextField1 = new JTextField();
		updateItemPriceTextField1.setMaximumSize(new Dimension(200,30));
		updateItemPriceCard1.add(updateItemPriceTextField1 );

		JButton updateItemPriceSave1 =  new JButton("Save");
		updateItemPriceCard1.add(updateItemPriceSave1);

		/**
		 * If the item name exists , it updates the price
		 */
		updateItemPriceSave1.addActionListener(new ActionListener()
		{
			@Override

			public void actionPerformed(ActionEvent event)
			{
				Double updateItemNewPriceInt = Double.parseDouble(updateItemPriceTextField1.getText());
				String updateItemPrice1 = updateItemPriceTextField.getText();	
				
				String updateItemPrice;
				try{
				
					updateItemPrice = serviceObj.updatePrice(updateItemNewPriceInt,updateItemPrice1);
					if (updateItemPrice.equals("Success")){
						updateItemPriceTextField1.setText("");
						updateItemPriceTextField1.setText("");
						CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
						cl.show(rmosCardPanel,"Change the Item Price Success Card");  
					}
					else{
						updateItemPriceTextField1.setText("");
						updateItemPriceTextField1.setText("");
						CardLayout cl = (CardLayout)rmosCardPanel.getLayout();
						cl.show(rmosCardPanel,"Change the Item Price Failure Card");
					}
				}
				catch(Exception se){
						System.out.println(se.getMessage());
				}	
			}
		});
		//end of CHANGE THE ITEM PRICE CARD



		/**
		 * CHANGE THE ITEM PRICE SUCCESS CARD
		 */
		JPanel updateItemPriceSuccess = new JPanel();
		updateItemPriceSuccess.setBackground(new Color(255,235,205));
		JLabel updateItemPriceSuccessMessage = new JLabel("Price for the specified item has been updated succesfully");
		updateItemPriceSuccess.add(updateItemPriceSuccessMessage);
		//end of CHANGE THE ITEM PRICE SUCCESS CARD
		
		/**
		 * CHANGE THE ITEM PRICE FAILURE CARD
		 */
		JPanel updateItemPriceFailure= new JPanel();
		updateItemPriceFailure.setBackground(new Color(255,235,205));
		JLabel updateItemPriceFailureMessage = new JLabel("Item with the specified name doesn't exist. hence can't update the price");
		updateItemPriceFailure.add(updateItemPriceFailureMessage);
		//end of CHANGE THE ITEM PRICE FAILURE  CARD



		rmosCardPanel.add(rmosFirstCard,"Start");
		rmosCardPanel.add(rmosSecondCard,"Select the Machine");
		rmosCardPanel.add(addMachineCard,"Add Machine");
		rmosCardPanel.add(addMachineSuccess,"Add Machine Success Card");
		rmosCardPanel.add(addMachineFailure,"Add Machine Failure Card");
		rmosCardPanel.add(removeMachineCard,"Remove Machine");
		rmosCardPanel.add(removeMachineSuccess,"Remove Machine Success Card");
		rmosCardPanel.add(removeMachineFailure,"Remove Machine Failure Card");
		
		rmosCardPanel.add(activateCard,"Activate Card");
		rmosCardPanel.add(activatedSuccess,"Activated Success");
		rmosCardPanel.add(activatedFailure,"Activated Failure");
	
		rmosCardPanel.add(deactivateCard,"deactivate Card");
		rmosCardPanel.add(deactivatedSuccess,"Deactivated Success");
		rmosCardPanel.add(deactivatedFailure,"Deactivated Failure");
		
		rmosCardPanel.add(emptyMachineCard,"Empty Machine");
		rmosCardPanel.add(emptyMachineSuccess,"Empty Machine Success Card");
		rmosCardPanel.add( operationalStatusCard,"Operational Status Card");
		
		//rmosCardPanel.add(operationalStatusDetails,"Operational Status Card Details");
		
		rmosCardPanel.add(creditStatusCard,"Credit Status Card");
		
	//	rmosCardPanel.add(creditStatusDetails,"Credit Status Card Details");
		
		rmosCardPanel.add(lastEmptiedStatusCard,"Last Emptied Status Card");
	
	//	rmosCardPanel.add(lastEmptiedDetails,"Last Emptied Status Card Details");
		
		rmosCardPanel.add(capacityCard,"Capacity Card");
//		rmosCardPanel.add(capacityDetails,"Capacity Card Details");
		rmosCardPanel.add(collectedItemsCard ,"Collected Items Card");
		rmosCardPanel.add(mostUsedRCMCard,"Most Used RCM");
		rmosCardPanel.add(addItemCard,"Add Item Card");
		rmosCardPanel.add(addItemSuccess,"Add Item Success Card");
		rmosCardPanel.add(addItemFailure,"Add Item Failure Card");
		rmosCardPanel.add(removeItemCard,"Remove Item Card");
		rmosCardPanel.add(removeItemSuccess,"Remove Item Success Card");
		rmosCardPanel.add(removeItemFailure,"Remove Item Failure Card");
		rmosCardPanel.add(updateItemPriceCard,"Update Item Price Card");
		rmosCardPanel.add(updateItemPriceCard1,"Change the Item Price Card");
		rmosCardPanel.add(updateItemPriceSuccess,"Change the Item Price Success Card");
		rmosCardPanel.add(updateItemPriceFailure,"Change the Item Price Failure Card");

		//ch
		rmosCardPanel.add(creditWeightSelection,"Credit Weight Selection");
		rmosCardPanel.add(showGraphCard,"Show Graph");
		rmosCardPanel.add(showGraphCardFailure,"Show Graph Failure Card");
		//*
		

		bottomPanelRMOS.add(rmosCardPanel);
		guiFrameRMOS.add(bottomPanelRMOS,BorderLayout.SOUTH); 
	//	guiFrameRMOS.pack();
		guiFrameRMOS.setVisible(true);
	}
			



}



