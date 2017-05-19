package BIF.SWE2.interfaces.presentationmodels;

import BIF.SWE2.interfaces.presentationmodels.EXIFPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.IPTCPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;

public interface PicturePresentationModel {
	/**
	 * Database primary key
	 */
	int getID();

	/**
	 * Name of the file
	 */
	String getFileName();

	/**
	 * Full file path, used to load the image
	 */
	String getFilePath();

	/**
	 * The line below the Picture. Format: {IPTC.Headline|FileName} (by
	 * {Photographer|IPTC.ByLine}).
	 */
	String getDisplayName();

	/**
	 * The IPTC ViewModel
	 */
	IPTCPresentationModel getIPTC();

	/**
	 * The EXIF ViewModel
	 */
	EXIFPresentationModel getEXIF();

	/**
	 * The Photographer ViewModel
	 */
	PhotographerPresentationModel getPhotographer();

	/**
	 * The Camera ViewModel
	 */
	CameraPresentationModel getCamera();
}
