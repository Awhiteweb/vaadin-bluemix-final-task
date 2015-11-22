package org.vaadin.backend;

import org.jinstagram.auth.model.Token;

public class Constants {

	/**
     * Config Properties
     */
	public static final String AUTH_URL = "https://api.instagram.com/oauth/authorize/?client_id=%s&redirect_uri=%s&response_type=code";
    public static final String CLIENT_ID = "a5e9504af6da4ae48481617c02d6417a";
    public static final String CLIENT_SECRET = "cf30dfb9fe4e42dab0ce59293e68e900";
    public static final String REDIRECT_URI = "http://vaadin-final-task-aw.eu-gb.mybluemix.net";
    public static final Token EMPTY_TOKEN = null;

    /**
     * Http Session Attributes
     */
    public static final String INSTAGRAM_OBJECT = "igObject";
    public static final String INSTAGRAM_SERVICE = "igService";

    public static final int MAX_PAGE_SIZE = 5;

}
