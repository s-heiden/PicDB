package viewModels;

import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

public class PhotographerListPM implements PhotographerListPresentationModel {

    private List<PhotographerPresentationModel> photographerPMs;
    private int currentPhotographerIndex = 0;
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
        return photographerPMs.get(currentPhotographerIndex);
    }
}
