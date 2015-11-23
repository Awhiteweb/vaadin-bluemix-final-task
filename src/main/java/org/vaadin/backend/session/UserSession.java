package org.vaadin.backend.session;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.SessionScoped;

import org.jinstagram.Instagram;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;
import org.vaadin.backend.User;

@SessionScoped
public class UserSession implements Serializable
{
	private static final long serialVersionUID = 1L;
	private Instagram object;
	private User user;

	public boolean isLoggedIn()
	{
		return user != null ? true : false;
	}
	
	public void setUser( User user )
	{
		this.user = user;
	}

	public void setObject( Instagram instagramObject )
	{
		object = instagramObject;
		try
		{
			 user = new User( instagramObject.getCurrentUserInfo().getData().getUsername() );
		}
		catch ( InstagramException e )
		{
			e.printStackTrace();
		}
	}
	
	public Instagram getObject()
	{
		return object;
	}
	
	public MediaFeed getMediaFeed()
	{
		try
		{
			return object.getPopularMedia();
		}
		catch ( InstagramException e )
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public boolean setUserMediaFeed()
	{
		try
		{
			user.setRecentMediaFeed( object.getRecentMediaFeed( user.getUsername() ) );
			return true;
		}
		catch ( InstagramException e )
		{
			e.printStackTrace();
		}
		return false;
	}
	
	public MediaFeed getMyRecentMediaFeed()
	{
		return user.getRecentMediaFeed();
	}
	
	public List<MediaFeedData> getMyRecentData()
	{
		return getMyRecentMediaFeed().getData();
	}

	public String getUsername()
	{
		return user.getUsername();		
	}

}
