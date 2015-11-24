package org.vaadin.backend;

import org.jinstagram.auth.model.Token;

public class Constants {

	/**
     * Config Properties
     */
	public static final String AUTH_URL = "https://api.instagram.com/oauth/authorize/?"
					+ "client_id=%s"
					+ "&redirect_uri=%s"
					+ "&response_type=code";
    public static final String LOCAL_CID = "4bf576627e9f48ef973455098f7c9063";
    public static final String LOCAL_CS = "cf30dfb9fe4e42dab0ce59293e68e900";
    public static final String LOCAL_RD_URL = "http://localhost:9080/vaadin-final-task-aw/";
    public static final String ACCESS_TOKEN_URL = "https://api.instagram.com/oauth/access_token";
    public static final Token EMPTY_TOKEN = null;
    public static final String LIVE_CID = "a5e9504af6da4ae48481617c02d6417a";
    public static final String LIVE_CS = "cf30dfb9fe4e42dab0ce59293e68e900";
    public static final String LIVE_RD_URL = "http://vaadin-final-task-aw.eu-gb.mybluemix.net";
    public static final String CLIENT_ID = LIVE_CID;
    public static final String CLIENT_SECRET = LIVE_CS;
    public static final String REDIRECT_URL = LIVE_RD_URL;

    /**
     * Http Session Attributes
     */
    public static final String INSTAGRAM_OBJECT = "igObject";
    public static final String INSTAGRAM_SERVICE = "igService";

    public static final int MAX_PAGE_SIZE = 5;

    public static final String getAUTH_URL()
    {
    	return String.format( "https://api.instagram.com/oauth/authorize/?"
				+ "client_id=%s"
				+ "&redirect_uri=%s"
				+ "&response_type=code", CLIENT_ID, CLIENT_SECRET );
    }
}
