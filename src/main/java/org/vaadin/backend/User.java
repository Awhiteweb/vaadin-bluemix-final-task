package org.vaadin.backend;

import java.util.ArrayList;
import java.util.List;

import org.jinstagram.Instagram;
import org.jinstagram.entity.users.basicinfo.UserInfo;
import org.jinstagram.entity.users.basicinfo.UserInfoData;
import org.jinstagram.exceptions.InstagramException;

public class User
{
	private String id;
	private String username;
	private String bio;
	private String firstname;
	private String lastname;
	private String fullname;
	private Instagram object;
	private UserInfoData userInfoData;
	private List<ImageData> images = new ArrayList<ImageData>();
	
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
		}
		catch ( InstagramException e )
		{
			e.printStackTrace();
		}
	}

	public User( Instagram object, UserInfo userInfo )
	{
		this.userInfoData = userInfo.getData();
		setUserDetails();
		this.object = object;
	}

	private void setUserDetails()
	{
		setUsername( userInfoData.getUsername() );
		setFirstname( userInfoData.getFirstName() );
		setLastname( userInfoData.getLastName() );
		setFullname( userInfoData.getFullName() );
		setBio( userInfoData.getBio() );
		setId( userInfoData.getId() );
	}
	
	private void setUsername( String name )
	{
		username = name;
	}

	private void setFirstname( String name )
	{
		firstname = name;
	}

	private void setLastname( String name )
	{
		lastname = name;
	}

	private void setFullname( String name )
	{
		fullname = name;
	}

	private void setBio( String bio )
	{
		this.bio = bio;
	}
	
	private void setId( String id )
	{
		this.id = id;
	}

	public void setObject( Instagram object )
	{
		this.object = object;
	}
	
	public String getUsername()
	{
		return username;
	}

	public Instagram getObject()
	{
		return object;
	}

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
	
	public String getId()
	{
		return id;
	}

	public List<ImageData> getImages()
	{
		return images;
	}

	public void setImages( List<ImageData> images )
	{
		this.images = images;
	}
	
	public void addImage( ImageData image )
	{
		this.images.add( image );
	}
	public ImageData getImage( String id )
	{
		for ( ImageData d : images )
			if ( d.getId().equals( id ) )
				return d;
		return null;
	}
}
