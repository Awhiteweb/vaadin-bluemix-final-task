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
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;

/*
 * A very simple view that just displays an "about text". The view also has 
 * a button to reset the demo date in the database.
 */
@CDIView( "" )
@ViewMenuItem( icon = FontAwesome.INFO )
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
		String hello = "Hello " + userDetails.get( UserMap.USERNAME );
		Grid userGrid = new Grid();
		userGrid.addColumn( "Title", String.class );
		userGrid.addColumn( "Info", String.class );
		userGrid.addRow( UserMap.ID.toString(), userDetails.get( UserMap.ID ) );
		userGrid.addRow( UserMap.USERNAME.toString(), userDetails.get( UserMap.USERNAME ) );
		userGrid.addRow( UserMap.FIRSTNAME.toString(), userDetails.get( UserMap.FIRSTNAME ) );
		userGrid.addRow( UserMap.LASTNAME.toString(), userDetails.get( UserMap.LASTNAME ) );
		userGrid.addRow( UserMap.FULLNAME.toString(), userDetails.get( UserMap.FULLNAME ) );
		userGrid.addRow( UserMap.BIO.toString(), userDetails.get( UserMap.BIO ) );
		userGrid.setWidth( "50%" );
		Label label = new Label( hello );
		add( label );
		add( userGrid );
	}
	
	@Override
	public void enter( ViewChangeListener.ViewChangeEvent viewChangeEvent )
	{
	}
}
