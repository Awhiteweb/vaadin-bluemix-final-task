package org.vaadin.presentation;

import java.io.IOException;

import javax.inject.Inject;

import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.vaadin.backend.Constants;
import org.vaadin.backend.session.UserSession;
import org.vaadin.viritin.label.RichText;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServletResponse;
import com.vaadin.server.VaadinSession;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class LoginWindow extends Window implements RequestHandler 
{
	private static final long serialVersionUID = 1L;
	private Link instagramLoginLink;
	private InstagramService service;
	
	@Inject
	UserSession userSession;

	public LoginWindow()
	{
		super();
	}
	
	public void authDenied( String reason ) {
        Notification.show( "authDenied:" + reason,
                Notification.Type.ERROR_MESSAGE );
    }
	
	@Override
	public void attach()
	{
		super.attach();
		service = new InstagramAuthService().apiKey( Constants.CLIENT_ID ).apiSecret( Constants.CLIENT_SECRET )
				.callback( Constants.REDIRECT_URL ).build();
		String url = service.getAuthorizationUrl( null );
		instagramLoginLink = new Link( "Login with Instagram", new ExternalResource( url ) );
		instagramLoginLink.addStyleName( ValoTheme.LINK_LARGE );
		VaadinSession.getCurrent().addRequestHandler(this);
		HorizontalLayout hl = new HorizontalLayout();
		Label title = new Label( "<h2>Instagram Insights</h2>" );
		title.setContentMode( ContentMode.HTML );
		hl.addComponent( title );
		hl.addComponent( instagramLoginLink );
		hl.setComponentAlignment( title, Alignment.BOTTOM_LEFT );
		hl.setComponentAlignment( instagramLoginLink, Alignment.MIDDLE_RIGHT );
		hl.setWidth( "100%" );
		Label label = new RichText().withMarkDownResource("/about.md");
		VerticalLayout layout = new VerticalLayout( hl, label );
		layout.setDefaultComponentAlignment( Alignment.TOP_CENTER );
		layout.setSpacing( true );
		layout.setMargin( true );
		setContent( layout );
		setModal( true );
		setWidth( "75%" );
		setHeight( "75%" );
		setResizable( false );
		setClosable( false );
	}
	
	@Override
	public boolean handleRequest( VaadinSession session, VaadinRequest request, VaadinResponse response )
			throws IOException
	{
		if ( request.getParameter( "code" ) != null )
		{
			String code = request.getParameter( "code" );
			Verifier v = new Verifier( code );
			Token t = service.getAccessToken( null, v );
			Instagram instagramObject = new Instagram( t );

			userSession.setObject( instagramObject );
			
			( (VaadinServletResponse) response ).getHttpServletResponse()
				.sendRedirect( Constants.REDIRECT_URL );
			return true;
		}
		return false;
	}

}
