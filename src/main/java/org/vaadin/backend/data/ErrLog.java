package org.vaadin.backend.data;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ErrLog
{
	public static void submit( String clazz, Throwable error )
	{
		Logger.getLogger( clazz ).log( Level.SEVERE, "MY Caught ERR", error );
	}
}
