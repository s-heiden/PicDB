package at.twif.picdb.viewmodels;

import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;
import java.util.ArrayList;

import java.util.List;

/**
 * Photographer List Presentation Model.
 */
public class PhotographerListPM implements PhotographerListPresentationModel {

    private List<PhotographerPresentationModel> photographerPMs;
    private int currentPhotographerIndex = -1;
    private static PhotographerListPM photographerListPMInstance;

    public static PhotographerListPM getInstance(List<PhotographerModel> photographers) {
        if (photographerListPMInstance == null) {
            photographerListPMInstance = new PhotographerListPM(photographers);
        }
        return photographerListPMInstance;
    }

    private PhotographerListPM(List<PhotographerModel> photographers) {
        photographerPMs = new ArrayList<>();
        photographers.forEach((p) -> {
            photographerPMs.add(new PhotographerPM(p));
        });
    }

    @Override
    public List<PhotographerPresentationModel> getList() {
        return photographerPMs;
    }

    @Override
    public PhotographerPresentationModel getCurrentPhotographer() {
        for(PhotographerPresentationModel p : photographerPMs) {
            if(p.getID() == currentPhotographerIndex) {
                return p;
            }
        }
        return null;
    }

    public void setCurrentPhotographerIndex(int id) {
        currentPhotographerIndex = id;
    }

    public int getCurrentPhotographerIndex() {
        return currentPhotographerIndex;
    }

    public void addNewPhotographer(PhotographerPresentationModel photographerPM) {
        photographerPMs.add(photographerPM);
    }

    public void deleteCurrentPhotographer() {
        for(PhotographerPresentationModel p : photographerPMs) {
            if(p.getID() == currentPhotographerIndex) {
                photographerPMs.remove(p);
                return;
            }
        }
    }
}
