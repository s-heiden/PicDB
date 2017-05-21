package BIF.SWE2.interfaces.presentationmodels;

public interface SearchPresentationModel {
	/**
	 * The search text
	 */
	String getSearchText();

	void setSearchText(String value);

	/**
	 * True, if a search is active
	 * TODO: Rename to isActive();
	 */
	boolean getIsActive();

	/**
	 * Number of photos found.
	 */
	int getResultCount();

}
