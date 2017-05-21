package BIF.SWE2.interfaces.presentationmodels;

import BIF.SWE2.interfaces.presentationmodels.PictureListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import BIF.SWE2.interfaces.presentationmodels.SearchPresentationModel;

public interface MainWindowPresentationModel {
	/**
	 * The current picture ViewModel
	 */
	PicturePresentationModel getCurrentPicture();

	/**
	 * ViewModel with a list of all Pictures
	 */
	PictureListPresentationModel getList();

	/**
	 * Search ViewModel
	 */
	SearchPresentationModel getSearch();

}
