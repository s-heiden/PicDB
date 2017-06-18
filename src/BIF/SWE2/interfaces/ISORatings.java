package BIF.SWE2.interfaces;

/**
 * Defines possible ISORatings.
 */
public enum ISORatings {
	NotDefined(0),
	Good(1),
	Acceptable(2),
	Noisey(3);

	private int value;

	private ISORatings(int value) {
		this.value = value;
	}

	public int getValue() {
		return value;
	}
}
