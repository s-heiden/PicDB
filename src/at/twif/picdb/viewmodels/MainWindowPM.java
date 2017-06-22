package at.twif.picdb.viewmodels;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.presentationmodels.MainWindowPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PictureListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;
import BIF.SWE2.interfaces.presentationmodels.SearchPresentationModel;
import at.twif.picdb.businesslayer.BL;

/**
 * Main Window Presentation Model.
 */
public class MainWindowPM implements MainWindowPresentationModel {
    
    private PictureListPresentationModel pictureListPM;
    private SearchPresentationModel searchPM;
    private static BusinessLayer bl;

    public MainWindowPM() {
        if (bl == null) {
            bl = BL.getInstance();
        }

        // PictureListPresentationModel
        try {
            pictureListPM = new PictureListPM(bl.getPictures());
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }

        // SearchPresentationModel
        searchPM = new SearchPM();
    }

    @Override
    public PicturePresentationModel getCurrentPicture() {
        return pictureListPM.getCurrentPicture();
    }
    
    /**
     * ViewModel with a list of all Pictures
     */
    @Override
    public PictureListPresentationModel getList() {
        return pictureListPM;
    }

    /**
     * Search ViewModel
     */
    @Override
    public SearchPresentationModel getSearch() {
        return searchPM;
    }
}
