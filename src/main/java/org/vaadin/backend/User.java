package org.vaadin.backend;

import org.jinstagram.entity.users.feed.MediaFeed;

public class User
{
	private String username;
	private MediaFeed recentMediaFeed;
	
	public User( String username )
	{
		this.username = username;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername( String username )
	{
		this.username = username;
	}

	public MediaFeed getRecentMediaFeed()
	{
		return recentMediaFeed;
	}

	public void setRecentMediaFeed( MediaFeed recentMediaFeed )
	{
		this.recentMediaFeed = recentMediaFeed;
	}
}
