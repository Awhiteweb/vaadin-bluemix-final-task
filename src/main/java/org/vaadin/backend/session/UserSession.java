package org.vaadin.backend.session;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import org.jinstagram.Instagram;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;
import org.vaadin.backend.User;
import org.vaadin.backend.UserMap;

@SessionScoped
public class UserSession implements Serializable
{
	private static final long serialVersionUID = 1L;
	private User user;
	private Instagram object;
	private MediaFeed mediaFeed;

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
		try
		{
			userInfo = object.getCurrentUserInfo();
			user = new User( object, userInfo );
			mediaFeed = object.getRecentMediaFeed( user.getId() );
		}
		catch ( InstagramException e )
		{
			e.printStackTrace();
		}
		
		this.object = object;
//		user = new User( object );
	}
	
	public Instagram getObject()
	{
		return user.getObject();
	}
	
//	public MediaFeed getMediaFeed()
//	{
//		return user.getPopularMediaFeed();
//	}
	
	public MediaFeed getMyRecentMediaFeed()
	{
		if ( mediaFeed != null )
			return mediaFeed;
		return null;
	}
	
//	public List<MediaFeedData> getMyRecentData()
//	{
//		return user.getRecentFeedData();
//	}

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

}
