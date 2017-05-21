package BIF.SWE2.interfaces.presentationmodels;

import java.util.Collection;

public interface IPTCPresentationModel {
	/**
	 * A list of keywords
	 */
	String getKeywords();

	void setKeywords(String value);

	/**
	 * Name of the photographer
	 */
	String getByLine();

	void setByLine(String value);

	/**
	 * copyright noties.
	 */
	String getCopyrightNotice();

	void setCopyrightNotice(String value);

	/**
	 * A list of common copyright noties. e.g. All rights reserved, CC-BY,
	 * CC-BY-SA, CC-BY-ND, CC-BY-NC, CC-BY-NC-SA, CC-BY-NC-ND
	 */
	Collection<String> getCopyrightNotices();

	/**
	 * Summary/Headline of the picture
	 */
	String getHeadline();

	void setHeadline(String value);

	/**
	 * Caption/Abstract, a description of the picture
	 */
	String getCaption();

	void setCaption(String value);

}
