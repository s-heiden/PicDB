package BIF.SWE2.interfaces.presentationmodels;

import java.util.Collection;

import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;

public interface PictureListPresentationModel {
	/**
	 * ViewModel of the current picture
	 */
	PicturePresentationModel getCurrentPicture();

	/**
	 * List of all PictureViewModels
	 */
	Collection<PicturePresentationModel> getList();

	/**
	 * All prev. pictures to the current selected picture.
	 */
	Collection<PicturePresentationModel> getPrevPictures();

	/**
	 * All next pictures to the current selected picture.
	 */
	Collection<PicturePresentationModel> getNextPictures();

	/**
	 * Number of all images
	 */
	int getCount();

	/**
	 * The current Index, 1 based
	 */
	int getCurrentIndex();

	/**
	 * {CurrentIndex} of {Cout}
	 */
	String getCurrentPictureAsString();

}
