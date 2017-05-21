package Models;

import BIF.SWE2.interfaces.models.IPTCModel;

/**
 * IPTC Model.
 */
public class IPTC implements IPTCModel {

    private String caption;
    private String headline;
    private String keywords;
    private String byLine;
    private String copyrightNotice;

    public IPTC() {
    }

    public IPTC(String caption, String headline, String keywords, String byLine, String copyrightNotice) {
        this.caption = caption;
        this.headline = headline;
        this.keywords = keywords;
        this.byLine = byLine;
        this.copyrightNotice = copyrightNotice;
    }

    /**
     * A list of keywords
     */
    @Override
    public String getKeywords() {
        return keywords;
    }

    @Override
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    /**
     * Name of the photographer
     */
    @Override
    public String getByLine() {
        return byLine;
    }

    @Override
    public void setByLine(String byLine) {
        this.byLine = byLine;
    }

    /**
     * copyright notes.
     */
    @Override
    public String getCopyrightNotice() {
        return copyrightNotice;
    }

    @Override
    public void setCopyrightNotice(String copyrightNotice) {
        this.copyrightNotice = copyrightNotice;
    }

    /**
     * Summary/Headline of the picture
     */
    @Override
    public String getHeadline() {
        return headline;
    }

    @Override
    public void setHeadline(String headline) {
        this.headline = headline;
    }

    /**
     * Caption/Abstract, a description of the picture
     */
    @Override
    public String getCaption() {
        return caption;
    }

    @Override
    public void setCaption(String caption) {
        this.caption = caption;
    }

    @Override
    public String toString() {
        return "IPTCModelImpl{"
                + "caption='" + caption + '\''
                + ", headline='" + headline + '\''
                + ", keywords='" + keywords + '\''
                + ", byLine='" + byLine + '\''
                + ", copyrightNotice='" + copyrightNotice + '\''
                + '}';
    }
}
