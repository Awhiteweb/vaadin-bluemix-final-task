package org.vaadin.backend;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class NewDB 
{
	
	public static void main( String[] args ) throws SQLException
	{
		Connection conn = DriverManager.getConnection( "jdbc:derby:vaadindb;create=true;" );
	}
}
