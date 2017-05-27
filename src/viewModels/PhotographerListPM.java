package viewModels;

import BIF.SWE2.interfaces.models.PhotographerModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.PhotographerPresentationModel;
import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

public class PhotographerListPM implements PhotographerListPresentationModel {

    private Collection<PhotographerPresentationModel> photographerPMs;

    public PhotographerListPM(List<PhotographerModel> photographers) {
        photographerPMs = new ArrayList<>();
        photographers.forEach((p) -> {
            photographerPMs.add(new PhotographerPM(p));
        });
    }

    @Override
    public Collection<PhotographerPresentationModel> getList() {
        return photographerPMs;
    }

    @Override
    public PhotographerPresentationModel getCurrentPhotographer() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
