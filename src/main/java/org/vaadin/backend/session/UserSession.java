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
	private User user;
	private Instagram object;

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
		if ( object == null )
		{
			user = new User( "Null value" );
		}
		else
		{
			this.object = object;
			user = new User( object );
		}
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
		return user.getRecentMediaFeed();
	}
	
	public List<MediaFeedData> getMyRecentData()
	{
		return user.getRecentFeedData();
	}

	public String getUsername()
	{
		return user.getUsername();		
	}

}
