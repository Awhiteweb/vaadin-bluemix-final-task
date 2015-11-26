package org.vaadin.presentation.views;

import java.util.HashMap;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.backend.UserMap;
import org.vaadin.backend.session.UserSession;
import org.vaadin.cdiviewmenu.ViewMenuItem;
import org.vaadin.presentation.LoginWindow;
import org.vaadin.viritin.layouts.MVerticalLayout;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/*
 * A very simple view that just displays an "about text". The view also has 
 * a button to reset the demo date in the database.
 */
@CDIView( "" )
@ViewMenuItem( icon = FontAwesome.INFO, order = 1 )
public class AboutView extends MVerticalLayout implements View
{
	private static final long serialVersionUID = 1L;

	@Inject
	UserSession userSession;
	
	@Inject
	Instance<LoginWindow> loginWindow;
	
	@PostConstruct
	void init()
	{
		if ( userSession.isLoggedIn() )
		{
			showDetails();
		}
		else
			auth();
		setStyleName( ValoTheme.LAYOUT_CARD );
	}

	private void auth()
	{
		add( new Label( "Not logged in" ) );
		setMargin( new MarginInfo( false, true, true, true ) );
	}
	
	private void showDetails()
	{
		removeAllComponents();
		HashMap<UserMap, String> userDetails = userSession.getUserDetails();
		String hello = "Hello " + userDetails.get( UserMap.FULLNAME );
		GridLayout userGrid = new GridLayout( 2, 7 );
		userGrid.addComponent( new Label( hello + " here is your Instagram user information." ),
				0, 0, 1, 0 );
		userGrid.addComponent( new Label( UserMap.ID.toString() ), 0, 1 );
		userGrid.addComponent( new Label( userDetails.get( UserMap.ID ) ), 1, 1 );
		userGrid.addComponent( new Label( UserMap.USERNAME.toString() ), 0, 2 );
		userGrid.addComponent( new Label( userDetails.get( UserMap.USERNAME ) ), 1, 2 );
		userGrid.addComponent( new Label( UserMap.FIRSTNAME.toString() ), 0, 3 );
		userGrid.addComponent( new Label( userDetails.get( UserMap.FIRSTNAME ) ), 1, 3 );
		userGrid.addComponent( new Label( UserMap.LASTNAME.toString() ), 0, 4 ); 
		userGrid.addComponent( new Label( userDetails.get( UserMap.LASTNAME ) ), 1, 4 );
		userGrid.addComponent( new Label( UserMap.FULLNAME.toString() ), 0, 5 );
		userGrid.addComponent( new Label( userDetails.get( UserMap.FULLNAME ) ), 1, 5 );
		userGrid.addComponent( new Label( UserMap.BIO.toString() ), 0, 6 );
		userGrid.addComponent( new Label( userDetails.get( UserMap.BIO ) ), 1, 6 );
		add( userGrid );
	}
	
	@Override
	public void enter( ViewChangeListener.ViewChangeEvent viewChangeEvent )
	{
	}
}
