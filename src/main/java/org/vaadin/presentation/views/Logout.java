package org.vaadin.presentation.views;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.vaadin.backend.session.UserSession;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.viritin.label.Header;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.FontAwesome;

@CDIView( "logout" )
@ViewMenuItem( icon = FontAwesome.POWER_OFF, order = 4 )
public class Logout extends MVerticalLayout implements View
{
	private static final long serialVersionUID = 1L;
	@Inject
	UserSession userSession;
	
	@PostConstruct
	void init()
	{
		userSession.exit();
		addComponent( new Header( "You have logged out" ).setHeaderLevel( 1 ) );
	}
	
	@Override
	public void enter( ViewChangeEvent event )
	{
	}

}
