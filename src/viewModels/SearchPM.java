package viewModels;

import BIF.SWE2.interfaces.presentationmodels.SearchPresentationModel;
import BL.BL;

public class SearchPM implements SearchPresentationModel {

    private String searchText = "";

    public SearchPM() {
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
            // TODO: adapt to account for photographer, iptc, exif
            count = new BL().getPictures(searchText, null, null, null).size();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

}
