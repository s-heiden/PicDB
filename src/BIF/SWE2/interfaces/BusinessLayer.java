package BIF.SWE2.interfaces;

import java.util.Collection;

import BIF.SWE2.interfaces.models.*;

public interface BusinessLayer {

	/**
	 * Returns a list of ALL Pictures from the directory, based on a database
	 * query.
	 * 
	 * @return
	 */
	Collection<PictureModel> getPictures() throws Exception;

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
	 * @param ID
	 * @return
	 */
	PictureModel getPicture(int ID) throws Exception;

	/**
	 * Saves all changes to the database.
	 * 
	 * @param picture
	 */
	void save(PictureModel picture) throws Exception;

	/**
	 * Deletes a Picture from the database AND from the file system.
	 * 
	 * @param ID
	 */
	void deletePicture(int ID) throws Exception;

	/**
	 * Syncs the picture folder with the database.
	 */
	void sync() throws Exception;

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
	 * Extracts IPTC information from a picture. NOTE: You may simulate the
	 * action.
	 * 
	 * @param filename
	 * @return
	 */
	IPTCModel extractIPTC(String filename) throws Exception;

	/**
	 * Extracts EXIF information from a picture. NOTE: You may simulate the
	 * action.
	 * 
	 * @param filename
	 * @return
	 */
	EXIFModel extractEXIF(String filename) throws Exception;

	/**
	 * Writes IPTC information back to a picture. NOTE: You may simulate the
	 * action.
	 * 
	 * @param filename
	 * @param iptc
	 */
	void writeIPTC(String filename, IPTCModel iptc) throws Exception;

	/**
	 * Returns a list of ALL Cameras.
	 *
	 * @return
	 */
	Collection<CameraModel> getCameras();

	/**
	 * Returns ONE Camera from the database.
	 *
	 * @param ID
	 * @return
	 */
	CameraModel getCamera(int ID);
}
