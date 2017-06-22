package at.twif.picdb.viewmodels;

import BIF.SWE2.interfaces.BusinessLayer;
import BIF.SWE2.interfaces.presentationmodels.SearchPresentationModel;
import at.twif.picdb.businesslayer.BL;

/**
 * The search presentation model.
 */
public class SearchPM implements SearchPresentationModel {

    private String searchText = "";
    private static BusinessLayer bl;

    public SearchPM() {
        if (bl == null) {
            bl = BL.getInstance();
        }
    }

    @Override
    public String getSearchText() {
        return searchText;
    }

    @Override
    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    @Override
    public boolean getIsActive() {
        return searchText.length() != 0;
    }

    @Override
    public int getResultCount() {
        int count = 0;
        try {
            count = bl.getPictures(searchText, null, null, null).size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
