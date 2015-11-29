package org.vaadin.backend.session;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import org.apache.commons.io.FileUtils;
import org.jinstagram.Instagram;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;
import org.vaadin.backend.Constants;
import org.vaadin.backend.User;
import org.vaadin.backend.UserMap;
import org.vaadin.backend.data.ImageData;
import org.vaadin.backend.data.Insight;

import com.ibm.watson.developer_cloud.visual_insights.v1.VisualInsights;
import com.ibm.watson.developer_cloud.visual_insights.v1.model.Summary;

@SessionScoped
public class UserSession implements Serializable
{
	private static final long serialVersionUID = 1L;
	private User user;
	private MediaFeed mediaFeed;
	private VisualInsights visualInsights;

	public boolean isLoggedIn()
	{
		return user != null ? true : false;
	}
	
	public void setUser( User user )
	{
		this.user = user;
	}

	public void setObject( Instagram object )
	{
		UserInfo userInfo;
		initVisualInsights();
		try
		{
			userInfo = object.getCurrentUserInfo();			
			user = new User( object, userInfo );
			mediaFeed = object.getRecentMediaFeed( user.getId() );
			addMediaToUser();
		}
		catch ( InstagramException e )
		{
			e.printStackTrace();
		}
	}
	
	private void initVisualInsights()
	{
		visualInsights = new VisualInsights();
		visualInsights.setUsernameAndPassword( Constants.V_USERNAME, Constants.V_PASSWORD );
	}
	
	public Instagram getObject()
	{
		return user.getObject();
	}
	
	public MediaFeed getMyRecentMediaFeed()
	{
		if ( mediaFeed != null )
			return mediaFeed;
		return null;
	}

	public String getUsername()
	{
		return user.getUsername();		
	}
	
	public HashMap<UserMap, String> getUserDetails()
	{
		HashMap<UserMap, String> userDetails = new HashMap<UserMap, String>();
		userDetails.put( UserMap.ID, user.getId() );
		userDetails.put( UserMap.USERNAME, user.getUsername() );
		userDetails.put( UserMap.FIRSTNAME, user.getFirstname() );
		userDetails.put( UserMap.LASTNAME, user.getLastname() );
		userDetails.put( UserMap.FULLNAME, user.getFullname() );
		userDetails.put( UserMap.BIO, user.getBio() );
		return userDetails;
	}
	
	public List<ImageData> getImages()
	{
		return user.getImages();
	}
	
	public ImageData getImage( String id )
	{
		return user.getImage( id );
	}
	
	private void addMediaToUser()
	{
		for ( MediaFeedData mfd : mediaFeed.getData() )
		{
			ImageData im = new ImageData();
			im.setId( mfd.getId() );
			if ( mfd.getCaption() != null )
			{
				im.setCaptionId( mfd.getCaption().getId() );
				im.setCaptionText( mfd.getCaption().getText() );
			}
			if ( mfd.getLocation() != null )
			{
				im.setLocation( new double[]{ mfd.getLocation().getLatitude(), mfd.getLocation().getLongitude() } );
				im.setLocationId( mfd.getLocation().getId() );
				im.setLocationName( mfd.getLocation().getName() );
			}
			im.setLowResolution( mfd.getImages().getLowResolution().getImageUrl(),
					mfd.getImages().getLowResolution().getImageWidth(),
					mfd.getImages().getLowResolution().getImageHeight() );
			im.setStandardResolution( mfd.getImages().getStandardResolution().getImageUrl(),
					mfd.getImages().getStandardResolution().getImageWidth(),
					mfd.getImages().getStandardResolution().getImageHeight() );
			im.setThumbnail( mfd.getImages().getThumbnail().getImageUrl(),
					mfd.getImages().getThumbnail().getImageWidth(),
					mfd.getImages().getThumbnail().getImageHeight() );
			im.setInsights( generateInsight( im ) );
			user.addImage( im );
		}
	}

	private Insight generateInsight( ImageData im )
	{
		try
		{
			File file = File.createTempFile( "tmpImage", "jpg" );
			URL url = new URL( im.getStandardResolution() );
			FileUtils.copyURLToFile( url, file );
			Summary summary = visualInsights.getSummary( file );
			return new Insight( summary.getSummary() );
		}
		catch ( IOException e )
		{
			e.printStackTrace();
		}
		return null;
	}

	public void exit()
	{
		user = null;
		mediaFeed = null;
		visualInsights = null;
	}

}
