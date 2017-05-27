package viewModels;

import BIF.SWE2.interfaces.models.IPTCModel;
import BIF.SWE2.interfaces.presentationmodels.IPTCPresentationModel;
import helpers.Constants;

import java.util.Collection;

public class IptcPM implements IPTCPresentationModel {

    private IPTCModel iptcModel;

    public IptcPM(IPTCModel iptcModel) {
        this.iptcModel = iptcModel;
    }

    @Override
    public String getKeywords() {
        return iptcModel.getKeywords();
    }

    @Override
    public void setKeywords(String keywords) {
        iptcModel.setKeywords(keywords);
    }

    @Override
    public String getByLine() {
        return iptcModel.getByLine();
    }

    @Override
    public void setByLine(String byLine) {
        iptcModel.setByLine(byLine);
    }

    @Override
    public String getCopyrightNotice() {
        return iptcModel.getCopyrightNotice();
    }

    @Override
    public void setCopyrightNotice(String copyrightNotice) {
        iptcModel.setCopyrightNotice(copyrightNotice);
    }

    @Override
    public Collection<String> getCopyrightNotices() {
        return Constants.COPYRIGHT_NOTICES;
    }

    @Override
    public String getHeadline() {
        return iptcModel.getHeadline();
    }

    @Override
    public void setHeadline(String headline) {
        iptcModel.setHeadline(headline);
    }

    @Override
    public String getCaption() {
        return iptcModel.getCaption();
    }

    @Override
    public void setCaption(String caption) {
        iptcModel.setCaption(caption);
    }
}
