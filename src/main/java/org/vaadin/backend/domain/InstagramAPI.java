package org.vaadin.backend.domain;

import org.scribe.builder.api.DefaultApi10a;
import org.scribe.model.Token;
import org.vaadin.backend.Constants;

public class InstagramAPI extends DefaultApi10a
{

	@Override
	public String getAccessTokenEndpoint()
	{
		return Constants.ACCESS_TOKEN_URL;
	}

	@Override
	public String getRequestTokenEndpoint()
	{
		return Constants.getAUTH_URL();
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
