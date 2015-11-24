package org.vaadin.backend;

public enum UserMap
{
	ID ( "user id" ), 
	USERNAME ( "username" ), 
	FIRSTNAME ( "firstname" ), 
	LASTNAME ( "lastname" ), 
	FULLNAME ( "fullname" ), 
	BIO ( "bio" );

	private String name;
	
	UserMap( String name )
	{
		this.name = name;
	}
	
	@Override
	public String toString()
	{
		return name;
	}
}
