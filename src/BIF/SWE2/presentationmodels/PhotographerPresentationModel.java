package BIF.SWE2.interfaces.presentationmodels;

import java.time.LocalDate;

public interface PhotographerPresentationModel {
	/**
	 * Database primary key
	 */
	int getID();

	/**
	 * Firstname, including middle name
	 */
	String getFirstName();

	void setFirstName(String value);

	/**
	 * Lastname
	 */
	String getLastName();

	void setLastName(String value);

	/**
	 * Birthday
	 */
	LocalDate getBirthDay();

	void setBirthDay(LocalDate value);

	/**
	 * Notes
	 */
	String getNotes();

	void setNotes(String value);
	
    /*
     * Returns the number of Pictures
     */
    int getNumberOfPictures();


	/**
	 * Returns true, if the model is valid
	 */
	boolean isValid();

	/**
	 * Returns a summary of validation errors
	 */
	String getValidationSummary();

	/**
	 * returns true if the last name is valid
	 */
	boolean isValidLastName();

	/**
	 * returns true if the birthday is valid
	 */
	boolean isValidBirthDay();

}
