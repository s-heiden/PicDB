package BIF.SWE2.interfaces.presentationmodels;

import BIF.SWE2.interfaces.ISORatings;

public interface EXIFPresentationModel {
	/**
	 * Name of camera
	 */
	String getMake();

	/**
	 * Aperture number
	 */
	double getFNumber();

	/**
	 * Exposure time
	 */
	double getExposureTime();

	/**
	 * ISO value
	 */
	double getISOValue();

	/**
	 * Flash yes/no
	 */
	boolean getFlash();

	/**
	 * The Exposure Program as string
	 */
	String getExposureProgram();

	/**
	 * The Exposure Program as image resource
	 */
	String getExposureProgramResource();

	/**
	 * Gets or sets a optional camera presentation model
	 * @return
     */
	CameraPresentationModel getCamera();
	void setCamera(CameraPresentationModel value);

	/**
	 * Returns a ISO rating if a camera is set.
	 * @return
     */
	ISORatings getISORating();

	/**
	 * Returns a image resource of a ISO rating if a camera is set.
	 * @return
     */
	String getISORatingResource();
}
