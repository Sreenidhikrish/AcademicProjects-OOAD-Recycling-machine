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
import java.util.Date;
import java.util.Enumeration;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
//Java extension packages



import EcoRecycle.Activator;
import EcoRecycle.CreditCalculator;
import EcoRecycle.RCMInfo;
import EcoRecycle.connectToDB;

/**
 * 
 * EcoRecycle class
 */
public class EcoRecycleGUI {

	JFrame guiFrame;
	CardLayout cards;
	JPanel buttonPanel;
	JPanel cardPanel;

	/**
	 * Frame divided into 3 panels
	 */
	JPanel topPanel;
	JPanel middlePanel;
	JPanel bottomPanel;

	JPanel pane1;
	JPanel pane2;
	JPanel firstCard;
	JPanel secondCard;
	JLabel label1;
	JTextField firstTf, lastTf;
	boolean issue;
	private static final int PADDING = 25;

	JRadioButton rcm1;
	JRadioButton rcm2;
	JRadioButton recyclableItem1;
	JRadioButton recyclableItem2;
	String sql;
	String itemData;
	String rcmData;
	Double itemWeight;
	Double itemPrice;
	
	JLabel displayItemPriceWeightLabel1; 
	JLabel displayItemPriceWeightLabel2; 
	JLabel displayItemPriceWeightLabel3; 
	JLabel moneyToBeIssuedLabel = new JLabel("");
	double tempMoneyToBeIssued=0.0;
	JTextField rcmMessageTextField;
	JTextField itemMessageTextField = new JTextField();
	
	int startRCMFlag = -1;
	double moneyTemp;
	double weightTemp;
	CreditCalculator cr = new CreditCalculator();
		
	public EcoRecycleGUI() {
		
		
		connectToDB connectToDBObj = new connectToDB();
		
		/**
		 * Frame Settings 
		 */
		guiFrame = new JFrame();
		guiFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		guiFrame.setTitle("EcoRecycle Machine");
		guiFrame.setSize(500, 300);

		// This will center the JFrame in the middle of the screen
		guiFrame.setLocationRelativeTo(null);
		guiFrame.setLayout(new BorderLayout());

		// creating a border to highlight the JPanel areas
		Border outline = BorderFactory.createLineBorder(Color.black);

		
		// top Panel
		JPanel topPanel = new JPanel();
		topPanel.setBorder(outline);
		topPanel.setBackground(Color.BLUE);
		topPanel.setPreferredSize(new Dimension(400, 125));
		topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

		JPanel topMainPanel = new JPanel();
		topMainPanel.setBackground(Color.BLUE);
		topMainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 598, 1));
		topMainPanel
				.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		JLabel welcomeTextArea = new JLabel("Welcome to EcoRecycle Machine");

		topMainPanel.add(welcomeTextArea);

		JLabel DateLabel = new JLabel();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		// get current date time with Date()
		Date date = new Date();
		dateFormat.format(date);
		String s = dateFormat.format(date);
		DateLabel.setText(s);
		topMainPanel.add(DateLabel);
		topPanel.add(topMainPanel, BoxLayout.X_AXIS);

		JPanel picPanel1 = new JPanel();
		picPanel1.setBorder(outline);
		picPanel1.setBackground(Color.BLUE);
		JLabel Items = new JLabel("Please recycle these Items");
		picPanel1.add(Items);
		
		 Icon iconPlastic = new ImageIcon("src/img001plastic.PNG");
		 Icon iconGlass = new ImageIcon("src/img002glass.PNG");
		// Icon iconhome = new ImageIcon("homebutton.jpg");
		
		JButton button1 = new JButton(iconPlastic);
		button1.setToolTipText("Unit Price: 5  Minimum Weight: 6");

		JButton button2 = new JButton(iconGlass);
		button2.setToolTipText("Unit Price: 7  Minimum Weight: 8");
	 
		 button1.setPreferredSize(new Dimension(65,65));
		 button2.setPreferredSize(new Dimension(65,65));
		
		/*	JLabel button1 = new JLabel("Plastics"); 
		JLabel button2 = new JLabel("Glass"); 
		
		
		int sizeValue = RCMInfo.listOfItems.size();
		int index = 2;
		while(sizeValue > index)
		{
			
			String newText   = RCMInfo.listOfItems.get(index).getItemName();
		//	JLabel newItem = new JLabel(newText);
			JButton button  = new JButton("");	
			button.setText(newText);
			
			picPanel1.add(button);
			++index;	
		} */

		picPanel1.add(button1);
		picPanel1.add(button2);
		
		picPanel1.add(Box.createHorizontalStrut(300));
		
		JButton buttonHome = new JButton("HOME");
		picPanel1.add(buttonHome);
		buttonHome.setPreferredSize(new Dimension(100, 25));


		buttonHome.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				
				for (RCMInfo rcmObj : Activator.totalRCMMachines) {
					if( rcmObj.getMachineId().equals(rcmMessageTextField.getText()))         
					{
						   rcmObj.transactionInitiated = null;
						}
						//System.out.println("transactionIntiated in home button: " + rcmObj.transactionInitiated);
					}
				CardLayout cl = (CardLayout) cardPanel.getLayout();
				cl.show(cardPanel, "Start");

			}

		});

		
		topPanel.add(picPanel1, BoxLayout.Y_AXIS);
		guiFrame.add(topPanel, BorderLayout.NORTH);

		JPanel middlePanel = new JPanel();
		middlePanel.setBorder(outline);
		middlePanel.setBackground(Color.blue);
		middlePanel.setPreferredSize(new Dimension(400, 250));

		guiFrame.add(middlePanel, BorderLayout.CENTER);

		/**
		 * CardLayout 
		 */
		cards = new CardLayout();
		cardPanel = new JPanel(new CardLayout());
		cardPanel.setPreferredSize(new Dimension(1500, 850));
		cardPanel.setBackground(Color.CYAN);
		// set the card layout
		cardPanel.setLayout(cards);
		
		/**
		 * Home card
		 */
		JPanel firstCard = new JPanel();
		firstCard.setLayout(new BoxLayout(firstCard, BoxLayout.Y_AXIS));
		firstCard.setBackground(new Color(255,235,205));
		JLabel rcmMessage = new JLabel( "Please enter the machine that you want to access");
		 rcmMessage.setAlignmentX( rcmMessage.CENTER_ALIGNMENT);
		firstCard.add(Box.createVerticalStrut(25)); 
		firstCard.add(rcmMessage  );
			
		rcmMessageTextField = new JTextField();
		rcmMessageTextField.setMaximumSize(new Dimension(200,30));
		rcmMessageTextField.setAlignmentX( rcmMessageTextField.CENTER_ALIGNMENT);
		firstCard.add(Box.createVerticalStrut(15)); 
		firstCard.add(rcmMessageTextField);
		
		
		JLabel startMessage = new JLabel("Press START");
		
		startMessage.setAlignmentX( startMessage.CENTER_ALIGNMENT);
		firstCard.add(Box.createVerticalStrut(15)); 
		firstCard.add(startMessage);
		
		
		//Icon iconStart = new ImageIcon("start.jpg");
		JButton buttonstart = new JButton("START");
		buttonstart .setAlignmentX( buttonstart .CENTER_ALIGNMENT);
		firstCard.add(Box.createVerticalStrut(20)); 
		buttonstart.setPreferredSize(new Dimension(100, 20));
		buttonstart.setActionCommand("switch1");
		
		firstCard.add(buttonstart);
		
		buttonstart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {

				startRCMFlag =0;

				for (RCMInfo rcmObj : Activator.totalRCMMachines) {
					if(   rcmObj.getMachineId().equals(rcmMessageTextField.getText()) && (rcmObj.getOperationalStatus().equals("Working") )  )          
					{
						startRCMFlag = 1;
					}
					
				}
				
				if(startRCMFlag == 1)
				{
				moneyTemp = 0.0 ;
				weightTemp = 0.0 ;
				
						rcmData = "RCM"+rcmMessageTextField.getText();
				

				CardLayout cl = (CardLayout) cardPanel.getLayout();
				cl.show(cardPanel, "Select the item");
				}
				else 
				{ 

					JOptionPane.showMessageDialog(firstCard,"Invalid/Deactivated machine Id. Please try again");
					
					
				}

			}

		});

		/**
		 * To capture which item to be recycled card
		 */
		JPanel secondCard = new JPanel();
		secondCard.setBackground(new Color(255,235,205));
		secondCard.setLayout(new BoxLayout(	secondCard, BoxLayout.Y_AXIS));
		
		JLabel itemLabel = new JLabel("Please enter the item to be recycled ( Plastics, Glass etc )");
		itemLabel.setAlignmentX( itemLabel.CENTER_ALIGNMENT);
		secondCard.add(Box.createVerticalStrut(5)); 
		secondCard.add(itemLabel );
		
		itemMessageTextField.setMaximumSize(new Dimension(200,20));
		itemMessageTextField.setAlignmentX( itemMessageTextField.CENTER_ALIGNMENT);
		secondCard.add(Box.createVerticalStrut(30)); 
		secondCard.add(itemMessageTextField);


		JButton proceed2 = new JButton("PROCEED");

		secondCard.add(proceed2);
		proceed2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent event) {
				
				int flag=-1;
				for (RCMInfo rcmObj : Activator.totalRCMMachines) {
					if( rcmObj.getMachineId().equals(rcmMessageTextField.getText()))         
					{
						if(rcmObj.transactionInitiated == null){
							
							//System.out.println("inside not null");
						   rcmObj.setTransactionInitiated();
						}
						//System.out.println("transactionIntiated: " + rcmObj.transactionInitiated);
					}
				}
				
				itemData = itemMessageTextField.getText();
				
				for (int i = 0; i < RCMInfo.listOfItems.size(); i++) {

					if ((itemMessageTextField.getText()).equals(RCMInfo.listOfItems.get(i).getItemName())) {
						
						flag=1;
						}
				}
				if(flag==1){
					CardLayout cl = (CardLayout) cardPanel.getLayout();
					cl.show(cardPanel, "Place the item");
				}
				
				if(flag!=1){
					JOptionPane.showMessageDialog(firstCard,"Item name doesn't exist");
					CardLayout cl = (CardLayout) cardPanel.getLayout();
					cl.show(cardPanel, "Select the item");
				}
			}

		});

		/**
		 * Item placed on the weighing scale card
		 */
		JPanel thirdCard = new JPanel();
		thirdCard.setBackground(new Color(255,235,205));
		thirdCard.add(new JLabel(
				"Item placed on the weighing scale. Click to PROCEED"));
		
		//this generates an image file
		ImageIcon icon = new ImageIcon("dispenser.png"); 
		JLabel dispenserLabel = new JLabel();
		dispenserLabel.setIcon(icon);
		thirdCard.add(dispenserLabel);
				
		
		JButton proceed = new JButton("PROCEED");
		
		/**
		 * Confirm & Print receipt, Continue and Exit card
		 */
		JPanel fourthCard = new JPanel();
		fourthCard.setBackground(new Color(255,235,205));
		
		
		
		displayItemPriceWeightLabel1 = new JLabel("");
		displayItemPriceWeightLabel2 = new JLabel("");
		displayItemPriceWeightLabel3 = new JLabel("");

		fourthCard.add(displayItemPriceWeightLabel1);
		fourthCard.add(displayItemPriceWeightLabel2);
		fourthCard.add(displayItemPriceWeightLabel3);

		thirdCard.add(proceed);

		proceed.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				
				double weighinKG = 0.0;
				Random randomGenerator = new Random();

				itemWeight = (double) (randomGenerator.nextInt(30));
		
				//System.out.println("item name " + itemData);
				//System.out.println("weight of item " + itemWeight);
				//System.out.println("machine" + rcmData);
				//System.out.println("calling caluclate price wt method");

				/**
				 * CalculatePriceWeight Method called to calculate price and weight of the item
				 */
				int returnStatus  = cr.calculatePriceWeight(rcmData, itemData, itemWeight);
				
				if(returnStatus==0){
					//System.out.println("case0");
				//	displayItemPriceWeightLabel1.setText("Item Selected  : "
					//		+ itemData);
				//		displayItemPriceWeightLabel2.setText("Weight : "
				//			+ itemWeight);
					JOptionPane.showMessageDialog(firstCard,"Item weight is less than min weight required");
					CardLayout cl = (CardLayout) cardPanel.getLayout();
					cl.show(cardPanel, "Select the item");
				}
				else if(returnStatus==1){
					
					//System.out.println("case1");
				//	displayItemPriceWeightLabel1.setText("Item Selected  : "
				//			+ itemData);
					//	displayItemPriceWeightLabel2.setText("Weight : "
					//		+ itemWeight);
						
					JOptionPane.showMessageDialog(firstCard,"Item weight is greater than the machine capacity. Machine overloaded");
					CardLayout cl = (CardLayout) cardPanel.getLayout();
					cl.show(cardPanel, "Start");
					//cl.show(firstCard, "Start");
				}
				else if(returnStatus==2){
					
					weighinKG = itemWeight * 0.45;
					
					//System.out.println("case2");
					itemPrice = cr.getMoneyToBeIssued();
				
					weightTemp += itemWeight;
					moneyTemp += itemPrice;

					displayItemPriceWeightLabel1.setText("Item Selected  : "
						+ itemData);
					displayItemPriceWeightLabel2.setText("Weight (in pounds) : "
							+ itemWeight + "Weight (in kg) :" +weighinKG);
					displayItemPriceWeightLabel3.setText("Price :  "
						+ itemPrice);

					CardLayout cl = (CardLayout) cardPanel.getLayout();
					cl.show(cardPanel, "confirm & print,continue");
				}
			}
		});

		// fourthCard.add(new JButton());
		JButton confirmbtn = new JButton("Confirm & Print Recepit");
		confirmbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				
				/**
				 * issue credit method called to check the money to be issued
				 */
				
				issue = cr.issueCredit(rcmData);
				tempMoneyToBeIssued = cr.getMoneyToBeIssued();
				
				//System.out.println("tempMoneyToBeIssued: " +tempMoneyToBeIssued);
				moneyToBeIssuedLabel.setText(" Money issued is : $" +tempMoneyToBeIssued );
				
				if (issue) 
				{
				
					CardLayout cl = (CardLayout) cardPanel.getLayout();
					cl.show(cardPanel, "cash or coupon");

				}

				else

				{
					
					CardLayout cl = (CardLayout) cardPanel.getLayout();
					cl.show(cardPanel, "cash insufficient coupon issued");
				}

				
				
			}
		});

		
		/**
		 * for recycling more than one item
		 */
		JButton continuebtn = new JButton("Continue with next item");
		continuebtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				CardLayout cl = (CardLayout) cardPanel.getLayout();
				cl.show(cardPanel, "Select the item");

			}
		});

		JButton exitbtn = new JButton("Exit");
		fourthCard.add(confirmbtn);
		fourthCard.add(continuebtn);
		fourthCard.add(exitbtn);

		fourthCard.add(displayItemPriceWeightLabel1);
		fourthCard.add(displayItemPriceWeightLabel2);
		fourthCard.add(displayItemPriceWeightLabel3);
		

		exitbtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				for (RCMInfo rcmObj : Activator.totalRCMMachines) {
					if( rcmObj.getMachineId().equals(rcmMessageTextField.getText()))         
					{
						   rcmObj.transactionInitiated = null;
						}
						//System.out.println("transactionIntiated in exit button: " + rcmObj.transactionInitiated);
					}
				
				CardLayout cl = (CardLayout) cardPanel.getLayout();
				cl.show(cardPanel, "Start");

			}
		});

		/**
		 * user can select cash or coupon
		 */
		JPanel fifthCard = new JPanel();
		fifthCard.setBackground(new Color(255,235,205));
		JLabel selectcashorcoupon = new JLabel("Select cash or coupon");
		JButton cashBtn = new JButton("Cash");
		JButton couponBtn = new JButton("Coupon");
		fifthCard.add(selectcashorcoupon);
		fifthCard.add(cashBtn);
		fifthCard.add(couponBtn);

		cashBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				
					CardLayout cl = (CardLayout) cardPanel.getLayout();
					cl.show(cardPanel, "cash issued");

			}
		});

		couponBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				CardLayout cl = (CardLayout) cardPanel.getLayout();
				cl.show(cardPanel, "coupon issued");

			}
		});

		/**
		 * user can collect the cash
		 */
		JPanel sixthCard = new JPanel();
		sixthCard.setBackground(new Color(255,235,205));
		JLabel cashLabel = new JLabel("Please collect the cash from the counter");

		JButton exitbtn1 = new JButton("Exit Transaction");
		sixthCard.add(cashLabel);
		sixthCard.add(exitbtn1);
		
		sixthCard.add(moneyToBeIssuedLabel);

		exitbtn1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				moneyTemp = 0.0 ;
				weightTemp = 0.0 ;
				CardLayout cl = (CardLayout) cardPanel.getLayout();
				cl.show(cardPanel, "Start");

			}
		});

		/**
		 * user can collect the coupons
		 */
		JPanel seventhCard = new JPanel();
		seventhCard.setBackground(new Color(255,235,205));
		JLabel couponLabel = new JLabel("Please collect the coupons from the counter");
		seventhCard.add(couponLabel);
		JButton exitbtn2 = new JButton("Exit Transaction");
		seventhCard.add(exitbtn2);
		
		seventhCard.add(moneyToBeIssuedLabel);

		exitbtn2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				moneyTemp = 0.0 ;
				weightTemp = 0.0 ;
				
				CardLayout cl = (CardLayout) cardPanel.getLayout();
				cl.show(cardPanel, "Start");

			}
		});
		
		
		JPanel eigthCard = new JPanel();
		eigthCard.setBackground(new Color(255,235,205));
		JLabel couponLabel1 = new JLabel("Cash Insufficient.Please collect the coupons from the counter");
		eigthCard.add(couponLabel1);
		JButton exitbtn3 = new JButton("Exit Transaction");
		eigthCard.add(exitbtn3);
		
		eigthCard.add(moneyToBeIssuedLabel);

		exitbtn3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				moneyTemp = 0.0 ;
				weightTemp = 0.0 ;
				
				CardLayout cl = (CardLayout) cardPanel.getLayout();
				cl.show(cardPanel, "Start");

			}
		});


		cardPanel.add(firstCard, "Start");
		cardPanel.add(secondCard, "Select the item");
		cardPanel.add(thirdCard, "Place the item");
		cardPanel.add(fourthCard, "confirm & print,continue");
		cardPanel.add(fifthCard, "cash or coupon");
		cardPanel.add(sixthCard, "cash issued");
		cardPanel.add(seventhCard, "coupon issued");
		cardPanel.add(eigthCard, "cash insufficient coupon issued");

		middlePanel.add(cardPanel);
		guiFrame.add(middlePanel, BorderLayout.CENTER);

		JPanel bottomPanel = new JPanel();
		bottomPanel.setBorder(outline);
		bottomPanel.setBackground(Color.BLUE);
		bottomPanel.setPreferredSize(new Dimension(400, 75));
		// bottomPanel.setLayout(new BorderLayout());
		guiFrame.add(bottomPanel, BorderLayout.SOUTH);
	//	guiFrame.pack();
		guiFrame.setVisible(true);

	}
}
