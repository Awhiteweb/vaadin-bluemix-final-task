package org.vaadin.backend.domain;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;
import org.vaadin.backend.Constants;

public class InstagramAPI extends DefaultApi10a
{
	private static final String REQUEST_TOKEN_RESOURCE = "api.twitter.com/oauth/request_token";
	private static final String ACCESS_TOKEN_RESOURCE = "api.twitter.com/oauth/access_token";

	@Override
	public String getAccessTokenEndpoint()
	{
		return "https://" + ACCESS_TOKEN_RESOURCE;
	}

	@Override
	public String getRequestTokenEndpoint()
	{
		return "https://" + REQUEST_TOKEN_RESOURCE;
	}

	@Override
	public String getAuthorizationUrl( Token requestToken )
	{
		return String.format( Constants.AUTH_URL, requestToken.getToken(), requestToken.getSecret() );
	}

	/**
	 * Instagram 'friendlier' authorization endpoint for OAuth.
	 */
	public static class Authenticate extends InstagramAPI
	{
		@Override
		public String getAuthorizationUrl( Token requestToken )
		{
			return String.format( Constants.AUTH_URL, requestToken.getToken(), requestToken.getSecret() );
		}
	}
}
