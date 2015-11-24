package org.vaadin.presentation.views;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jinstagram.Instagram;
import org.jinstagram.auth.oauth.InstagramService;
import org.jinstagram.entity.common.ImageData;
import org.jinstagram.entity.common.Location;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.vaadin.backend.LoginWindow;
import org.vaadin.backend.session.UserSession;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
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
		add( new Label( "Not logged in" ) );
		setMargin( new MarginInfo( false, true, true, true ) );
	}
	
	private void showDetails()
	{
		removeAllComponents();
		Label label = new Label( "Hello " + userSession.getUsername() );
		MediaFeedData mfd = getMediaFeedData( 0 );
		String imageUrl = getImage( mfd ).getImageUrl();
		ExternalResource resource = new ExternalResource( imageUrl );
		Image image = new Image( "first low res image", resource );
		double[] loc = getImageLocation( mfd );
		add( label );
		add( image );
		add( new Label( String.format( "Lat: %d - Long: %d", loc[0], loc[1] ) ) );
	}
	
	private MediaFeedData getMediaFeedData( int itemNumber )
	{
		return userSession.getMyRecentData().get( itemNumber );
	}

	private ImageData getImage( MediaFeedData data )
	{
		return data.getImages().getLowResolution();
	}
	
	private double[] getImageLocation( MediaFeedData data )
	{
		Location loc = data.getLocation();
		return new double[]{ loc.getLatitude(), loc.getLongitude() };
	}
	
	@Override
	public void enter( ViewChangeListener.ViewChangeEvent viewChangeEvent )
	{
		// http://vaadin-final-task-aw.eu-gb.mybluemix.net
	}
}
