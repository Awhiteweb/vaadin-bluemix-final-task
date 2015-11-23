package org.vaadin.presentation.views;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.vaadin.addon.oauthpopup.OAuthListener;
import org.vaadin.addon.oauthpopup.OAuthPopupButton;
import org.vaadin.addon.oauthpopup.buttons.TwitterButton;
import org.vaadin.backend.Constants;
import org.vaadin.backend.InstagramButton;
import org.vaadin.backend.LoginWindow;
import org.vaadin.backend.session.UserSession;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.label.RichText;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
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
	private Instagram instagram;

	@Inject
	UserSession userSession;
	
	@Inject
	Instance<LoginWindow> loginWindow;
	
	@PostConstruct
	void init()
	{
		if ( userSession.isLoggedIn() )
		{
			showDetails();
		}
		else
			auth();
		setStyleName( ValoTheme.LAYOUT_CARD );
	}

	private void auth()
	{
//		UI.getCurrent().getContent().setVisible( false );
//		UI.getCurrent().addWindow( loginWindow.get() );
//		add( new RichText().withMarkDownResource( "/about.md" ) );
//
//		Button button = new Button( "Authenticate", new Button.ClickListener() {
//			private static final long serialVersionUID = 1L;
//
//			@Override
//			public void buttonClick( Button.ClickEvent event )
//			{
//				
//			}
//		} );
//		button.setStyleName( ValoTheme.BUTTON_LARGE );
//		button.addStyleName( ValoTheme.BUTTON_PRIMARY );
//		add( button );
//		
//		OAuthPopupButton oauthButton = new InstagramButton( Constants.CLIENT_ID, Constants.CLIENT_SECRET );
//		oauthButton.addOAuthListener( new OAuthListener() 
//		{
//			@Override
//			public void authSuccessful( String accessToken, String accessTokenSecret, String oauthRawResponse )
//			{
//				Notification.show( "Successful" );
//			}
//			
//			@Override
//			public void authDenied( String reason )
//			{
//				Notification.show( "Denied" );
//			}
//		} );
//		
		add( new Label( "Not logged in" ) );
		setMargin( new MarginInfo( false, true, true, true ) );
	}
	
	private void showDetails()
	{
		Label label = new Label( userSession.getUsername() );
		removeAllComponents();
		add( label );
	}

	@Override
	public void enter( ViewChangeListener.ViewChangeEvent viewChangeEvent )
	{
		// http://vaadin-final-task-aw.eu-gb.mybluemix.net
	}
}
