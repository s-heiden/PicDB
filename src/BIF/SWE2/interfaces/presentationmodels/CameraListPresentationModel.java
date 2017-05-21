package BIF.SWE2.interfaces.presentationmodels;

import java.util.Collection;

import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;

public interface CameraListPresentationModel {
	/**
	 * List of all CameraViewModel
	 */
	Collection<CameraPresentationModel> getList();

	/**
	 * The currently selected CameraViewModel
	 */
	CameraPresentationModel getCurrentCamera();

}
