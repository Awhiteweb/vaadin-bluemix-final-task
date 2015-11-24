package org.vaadin.backend;

import java.io.IOException;

import javax.inject.Inject;

import org.jinstagram.Instagram;
import org.jinstagram.auth.InstagramAuthService;
import org.jinstagram.auth.model.OAuthRequest;
import org.jinstagram.auth.model.Token;
import org.jinstagram.auth.model.Verifier;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.http.Response;
import org.jinstagram.http.Verbs;
import org.vaadin.backend.session.UserSession;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.RequestHandler;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinResponse;
import com.vaadin.server.VaadinServletResponse;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Link;
import com.vaadin.ui.Notification;
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
		super( "Login" );
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
		
		setContent( new MVerticalLayout( instagramLoginLink )
				.alignAll( Alignment.MIDDLE_CENTER )
				.withFullHeight() );
		setModal( true );
		setWidth( "300px" );
		setHeight( "200px" );
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
	
//	static {
//	    //for localhost testing only
//	    javax.net.ssl.HttpsURLConnection.setDefaultHostnameVerifier(
//	    new javax.net.ssl.HostnameVerifier(){
//
//	        public boolean verify(String hostname,
//	                javax.net.ssl.SSLSession sslSession) {
//	            if (hostname.equals("localhost")) {
//	                return true;
//	            }
//	            return false;
//	        }
//	    });
//	}

}
