package EcoRecycle;

public class Item {

	// Data members
	public String itemName;

	private Double minWeight;
	Double unitPrice;
	Double weightDropped = 0.0;

	// Constructor
	public Item() {
	}

	public Item(String itemName, Double unitPrice, Double minWeight) {
		this.itemName = itemName;
		this.minWeight = minWeight;
		this.unitPrice = unitPrice;

	}

	public Item(String itemName, Double unitPrice) {
		this.itemName = itemName;
		this.unitPrice = unitPrice;
	}
	
	/**
	 * Setter to set the min weight
	 * 
	 * @param w
	 *            : Input min weight
	 */
	public void setMinWeight(Double w) {
		this.minWeight = w;
	}

	/**
	 * Setter to set the unit price
	 * 
	 * @param p
	 *            : Input unit price
	 */
	public void setUnitPrice(Double p) {
		this.unitPrice = p;
	}

	/**
	 * Getter to get the item name
	 * 
	 * @return item name
	 */
	public String getItemName() {
		return this.itemName;
	}

	/**
	 * Getter to get the unit price of the item
	 * 
	 * @return unitPrice
	 */
	public Double getUnitPrice() {
		return this.unitPrice;
	}

	/**
	 * Getter to get the min weight of the item
	 * 
	 * @return min Weight
	 */
	public Double getMinWeight() {
		return this.minWeight;
	}

	/**
	 * Getter to get the weight of the item dropped
	 * 
	 * @return weight Dropped
	 */
	public Double getWeightDropped() {
		return this.weightDropped;
	}

}