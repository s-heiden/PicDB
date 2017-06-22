package at.twif.picdb.viewmodels;

import BIF.SWE2.interfaces.models.CameraModel;
import BIF.SWE2.interfaces.presentationmodels.CameraListPresentationModel;
import BIF.SWE2.interfaces.presentationmodels.CameraPresentationModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * The camera list presentation model.
 */
public class CameraListPM implements CameraListPresentationModel {

    private static int currentCameraIndex = 0;
    private List<CameraPresentationModel> cameraPMs;

    /**
     * Constructor.
     */
    public CameraListPM(List<CameraModel> cameras) {
        cameraPMs = new ArrayList<>();
        cameras.forEach((camera) -> {
            cameraPMs.add(new CameraPM(camera));
        });
    }

    @Override
    public Collection<CameraPresentationModel> getList() {
        return cameraPMs;
    }

    @Override
    public CameraPresentationModel getCurrentCamera() {
        throw new UnsupportedOperationException("Not implemented yet.");
    }
}
