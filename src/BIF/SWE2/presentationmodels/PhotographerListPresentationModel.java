package BIF.SWE2.interfaces.presentationmodels;

import java.util.Collection;

import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;

public interface PhotographerListPresentationModel {
	/**
	 * List of all PhotographerViewModel
	 */
	Collection<PhotographerPresentationModel> getList();

	/**
	 * The currently selected PhotographerViewModel
	 */
	PhotographerPresentationModel getCurrentPhotographer();

}
