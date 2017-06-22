package at.twif.picdb.models;

import BIF.SWE2.interfaces.models.IPTCModel;

/**
 * Iptc Model.
 */
public class Iptc implements IPTCModel {

    private String caption;
    private String headline;
    private String keywords;
    private String byLine;
    private String copyrightNotice;

    /**
     * The default constructor.
     */
    public Iptc() {
    }

    /**
     * Custom constructor which sets all fields.
     */
    public Iptc(String caption, String headline, String keywords, String byLine, String copyrightNotice) {
        this.caption = caption;
        this.headline = headline;
        this.keywords = keywords;
        this.byLine = byLine;
        this.copyrightNotice = copyrightNotice;
    }

    @Override
    public String getKeywords() {
        return keywords;
    }

    @Override
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String getByLine() {
        return byLine;
    }

    @Override
    public void setByLine(String byLine) {
        this.byLine = byLine;
    }

    @Override
    public String getCopyrightNotice() {
        return copyrightNotice;
    }

    @Override
    public void setCopyrightNotice(String copyrightNotice) {
        this.copyrightNotice = copyrightNotice;
    }

    @Override
    public String getHeadline() {
        return headline;
    }

    @Override
    public void setHeadline(String headline) {
        this.headline = headline;
    }

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
