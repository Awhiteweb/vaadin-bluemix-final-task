package org.vaadin.presentation.views;

import javax.annotation.PostConstruct;

import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.vaadin.addon.oauthpopup.OAuthListener;
import org.vaadin.addon.oauthpopup.OAuthPopupButton;
import org.vaadin.addon.oauthpopup.buttons.TwitterButton;
import org.vaadin.backend.Constants;
import org.vaadin.backend.domain.InstagramButton;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Notification;
import com.vaadin.ui.themes.ValoTheme;

/*
 * A very simple view that just displays an "about text". The view also has 
 * a button to reset the demo date in the database.
 */
@CDIView( "" )
@ViewMenuItem( icon = FontAwesome.INFO )
public class AboutView extends MVerticalLayout implements View
{
	private static final long serialVersionUID = 1L;
	private InstagramService service;

	@PostConstruct
	void init()
	{
		if ( service != null )
		{
			System.out.println( "Code: " );
		}
		else
			auth();
		setStyleName( ValoTheme.LAYOUT_CARD );
	}

	private void auth()
	{
		removeAllComponents();
		add( new RichText().withMarkDownResource( "/about.md" ) );

		Button button = new Button( "Authenticate", new Button.ClickListener() {
			private static final long serialVersionUID = 1L;

			@Override
			public void buttonClick( Button.ClickEvent event )
			{
				service = new InstagramAuthService().apiKey( Constants.CLIENT_ID ).apiSecret( Constants.CLIENT_SECRET )
						.callback( Constants.REDIRECT_URI ).build();
				System.out.println( service.getAuthorizationUrl( Constants.EMPTY_TOKEN ) );
				String authorizationUrl = service.getAuthorizationUrl( Constants.EMPTY_TOKEN );
				Verifier verifier = new Verifier( authorizationUrl );
				Token accessToken = service.getAccessToken( Constants.EMPTY_TOKEN, verifier );
				System.out.println( accessToken );
			}
		} );
		button.setStyleName( ValoTheme.BUTTON_LARGE );
		button.addStyleName( ValoTheme.BUTTON_PRIMARY );
		add( button );
		
		OAuthPopupButton oauthButton = new InstagramButton( Constants.CLIENT_ID, Constants.CLIENT_SECRET );
		oauthButton.addOAuthListener( new OAuthListener() 
		{
			@Override
			public void authSuccessful( String accessToken, String accessTokenSecret, String oauthRawResponse )
			{
				Notification.show( "Successful" );
			}
			
			@Override
			public void authDenied( String reason )
			{
				Notification.show( "Denied" );
			}
		} );
		
		add( oauthButton );
		setMargin( new MarginInfo( false, true, true, true ) );
	}

	@Override
	public void enter( ViewChangeListener.ViewChangeEvent viewChangeEvent )
	{
		// http://vaadin-final-task-aw.eu-gb.mybluemix.net
	}
}
