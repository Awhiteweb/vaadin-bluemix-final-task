package org.vaadin.backend.domain;

import org.vaadin.addon.oauthpopup.OAuthPopupButton;

import com.vaadin.server.ClassResource;

public class InstagramButton extends OAuthPopupButton
{
	private static final long serialVersionUID = 1L;

	public InstagramButton( String key, String secret )
	{
		super( InstagramAPI.class, key, secret );
		
		setIcon(new ClassResource("/org/vaadin/addon/oauthpopup/icons/twitter16.png"));
		setCaption("Instagram");
	}

}
