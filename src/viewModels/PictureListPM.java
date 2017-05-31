package viewModels;

import BIF.SWE2.interfaces.models.PictureModel;
import BIF.SWE2.interfaces.presentationmodels.PictureListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PicturePresentationModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PictureListPM implements PictureListPresentationModel {

    private List<PicturePresentationModel> picturePMs;
    private int currentPictureIndex = 0;

    public PictureListPM(Collection<PictureModel> pictures) {
        picturePMs = new ArrayList<>();
        pictures.forEach((p) -> {
            picturePMs.add(new PicturePM(p));
        });
    }

    @Override
    public PicturePresentationModel getCurrentPicture() {
        return picturePMs.get(currentPictureIndex);
    }

    @Override
    public Collection<PicturePresentationModel> getList() {
        return picturePMs;
    }

    @Override
    public Collection<PicturePresentationModel> getPrevPictures() {
        if (currentPictureIndex == 0) {
            return null;
        } else {
            return picturePMs.subList(0, currentPictureIndex - 1);
        }
    }

    @Override
    public Collection<PicturePresentationModel> getNextPictures() {
        if (currentPictureIndex == picturePMs.size() - 1) {
            return null;
        } else {
            return picturePMs.subList(currentPictureIndex + 1, picturePMs.size() - 1);
        }
    }

    /**
     * Number of all images
     */
    @Override
    public int getCount() {
        return picturePMs.size();
    }

    /**
     * The current Index, 1 based
     */
    @Override
    public int getCurrentIndex() {
        return currentPictureIndex + 1;
    }
    
    public void setCurrentIndex(int index){
        this.currentPictureIndex = index;
    }

    /**
     * {CurrentIndex} of {Count}
     */
    @Override
    public String getCurrentPictureAsString() {
        return getCurrentIndex() + " of " + (picturePMs.size() + 1);
    }
}
