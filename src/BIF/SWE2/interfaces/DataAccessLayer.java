package BIF.SWE2.interfaces;

import java.util.Collection;

import BIF.SWE2.interfaces.models.*;


public interface DataAccessLayer {
	/**
	 * Returns a filterd list of Pictures from the directory, based on a
	 * database query.
	 * 
	 * @return
	 */
	Collection<PictureModel> getPictures(String namePart,
			PhotographerModel photographerParts, IPTCModel iptcParts,
			EXIFModel exifParts) throws Exception;

	/**
	 * Returns ONE Picture from the database.
	 * 
	 * @param id
	 * @return
	 */
	PictureModel getPicture(int id) throws Exception;

	/**
	 * Saves all changes to the database.
	 * 
	 * @param picture
	 */
	void save(PictureModel picture) throws Exception;

	/**
	 * Deletes a Picture from the database.
	 *
	 * @param id
	 */
	void deletePicture(int id) throws Exception;

	/**
	 * Returns a list of ALL Photographers.
	 * 
	 * @return
	 */
	Collection<PhotographerModel> getPhotographers() throws Exception;

	/**
	 * Returns ONE Photographer
	 * 
	 * @param ID
	 * @return
	 */
	PhotographerModel getPhotographer(int ID) throws Exception;

	/**
	 * Saves all changes.
	 * 
	 * @param photographer
	 */
	void save(PhotographerModel photographer) throws Exception;

	/**
	 * Deletes a Photographer. A Exception is thrown if a Photographer is still
	 * linked to a picture.
	 * 
	 * @param ID
	 */
	void deletePhotographer(int ID) throws Exception;

	/**
	 * Returns a list of ALL Cameras.
	 *
	 * @return
     */
	Collection<CameraModel> getCameras();

    /**
     * Returns ONE Camera
     *
     * @param ID
     * @return
     */
    CameraModel getCamera(int ID);
}
