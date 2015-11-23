package org.vaadin.presentation;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.vaadin.backend.LoginWindow;
import org.vaadin.backend.session.UserSession;
import org.vaadin.cdiviewmenu.ViewMenuUI;


/**
 * UI class and its init method  is the "main method" for Vaadin apps.
 * But as we are using Vaadin CDI, Navigator and Views, we'll just
 * extend the helper class ViewMenuUI that provides us a top level layout,
 * automatically generated top level navigation and Vaadin Navigator usage.
 * <p>
 * We also configure the theme, host page title and the widgetset used
 * by the application.
 * </p>
 */
@CDIUI("")
@Theme("valo")
@Title("Simple CRM")
@Widgetset("AppWidgetset")
public class AppUI extends ViewMenuUI 
{
	private static final long serialVersionUID = 1L;

	@Inject
	UserSession userSession;
	
	@Inject
	Instance<LoginWindow> loginWindow;

	
	@Override
    protected void init( VaadinRequest request )
	{
        super.init( request );
        if ( !userSession.isLoggedIn() )
        {
            getContent().setVisible( false );
            addWindow( loginWindow.get() );
        }
    }
	
	/**
     * @return the currently active UI instance with correct type.
     */
    public static AppUI get() 
    {
        return (AppUI) UI.getCurrent();
    }

}
