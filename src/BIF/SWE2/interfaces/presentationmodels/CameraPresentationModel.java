package BIF.SWE2.interfaces.presentationmodels;

import BIF.SWE2.interfaces.ISORatings;

import java.time.LocalDate;

public interface CameraPresentationModel {
	/**
	 * Database primary key
	 */
	int getID();

	/**
	 * Name of the producer
	 */
	String getProducer();
	void setProducer(String value);

	/**
	 * Name of camera
	 */
	String getMake();
	void setMake(String value);

	/**
	 * Optional: date, when the camera was bought
	 */
	LocalDate getBoughtOn();
	void setBoughtOn(LocalDate value);

	/**
	 * Notes
	 */
	String getNotes();
	void setNotes(String value);
	
    /**
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
	 * returns true if the producer name is valid
	 */
	boolean isValidProducer();
	
	/**
	 * returns true if the make is valid
	 */
	boolean isValidMake();

	/**
	 * returns true if the "bought on" date is valid
	 */
	boolean isValidBoughtOn();

	/**
	 * Max ISO Value for good results. 0 means "not defined"
	 */
	double getISOLimitGood();
	void setISOLimitGood(double value);
	/**
	 * Max ISO Value for acceptable results. 0 means "not defined"
	 */
	double getISOLimitAcceptable();
	void setISOLimitAcceptable(double value);

	/**
	 * Translates a given ISO value to a ISO rating
	 * @param iso
	 * @return
     */
	ISORatings translateISORating(double iso);
}
