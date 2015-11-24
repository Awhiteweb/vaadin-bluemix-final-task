package org.vaadin.backend;

import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.feed.MediaFeed;
import org.jinstagram.entity.users.feed.MediaFeedData;
import org.jinstagram.exceptions.InstagramException;

public class User
{
	private String username;
	private String bio;
	private String firstname;
	private String lastname;
	private String fullname;
	private MediaFeed recentMediaFeed;
//	private MediaFeed popularMediaFeed;
	private Instagram object;	
	
	public User( String username )
	{
		this.username = username;
	}
	
	public User( Instagram object )
	{
		this.object = object;
		setUserDetails();
		try
		{
			username = object.getCurrentUserInfo().getData().getFirstName();
//			popularMediaFeed = object.getPopularMedia();
			recentMediaFeed = object.getRecentMediaFeed( username );
		}
		catch ( InstagramException e )
		{
			e.printStackTrace();
		}
	}

	private void setUserDetails()
	{
		UserInfo info = null;
		try
		{
			info = object.getCurrentUserInfo();
		}
		catch ( InstagramException e )
		{
			e.printStackTrace();
		}
		if ( info != null )
		{
			setUsername( info );
			setFirstname( info );
			setLastname( info );
			setFullname( info );
			setBio( info );
		}
	}
	
	private void setUsername( UserInfo info )
	{
		if ( info.getData().getUsername() != null )
			username = info.getData().getUsername();
		username = null;
	}

	private void setFirstname( UserInfo info )
	{
		if ( info.getData().getFirstName() != null )
			firstname = info.getData().getFirstName();
		firstname = null;
	}

	private void setLastname( UserInfo info )
	{
		if ( info.getData().getLastName() != null )
			lastname =  info.getData().getLastName();
		lastname = null;
	}

	private void setFullname( UserInfo info )
	{
		if ( info.getData().getFullName() != null )
			fullname = info.getData().getFullName();
		else
			fullname = null;
	}

	private void setBio( UserInfo info )
	{
		if ( info.getData().getBio() != null )
			bio = info.getData().getBio();
		else
			bio = null;
	}

	public void setObject( Instagram object )
	{
		this.object = object;
	}
	
	public List<MediaFeedData> getRecentFeedData()
	{
		return recentMediaFeed.getData();
	}

	public String getUsername()
	{
		return username;
	}

	public MediaFeed getRecentMediaFeed()
	{
		return recentMediaFeed;
	}

	public Instagram getObject()
	{
		return object;
	}

//	public MediaFeed getPopularMediaFeed()
//	{
//		return popularMediaFeed;
//	}

	public String getFirstname()
	{
		return firstname;
	}

	public String getBio()
	{
		return bio;
	}

	public String getLastname()
	{
		return lastname;
	}

	public String getFullname()
	{
		return fullname;
	}

}
